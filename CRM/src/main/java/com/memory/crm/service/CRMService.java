package com.memory.crm.service;

import com.memory.crm.model.ChangeTariffRequest;
import com.memory.crm.model.CreateSubscriberRequest;
import com.memory.crm.model.SubscriberInfo;
import com.memory.crm.model.TopUpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CRMService {
    private final RestTemplate rest;
    private final String brtUrl = "http://localhost:8083";

    public CRMService(RestTemplate rest) {
        this.rest = rest;
    }

    public SubscriberInfo getSubscriber(String number) {
        return rest.getForObject(
                brtUrl + "/api/abonents/{number}",
                SubscriberInfo.class,
                number
        );
    }

    public void topUp(String number, double amount) {
        TopUpRequest req = new TopUpRequest();
        req.setAmount(amount);
        rest.postForEntity(
                brtUrl + "/api/abonents/{number}/topup",
                req,
                Void.class,
                number
        );
    }

    public void createSubscriber(CreateSubscriberRequest dto) {
        rest.postForEntity(
                brtUrl + "/api/abonents",
                dto,
                Void.class
        );
    }

    public void changeTariff(String number, long tariffId) {
        ChangeTariffRequest dto = new ChangeTariffRequest();
        dto.setTariffId(tariffId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChangeTariffRequest> entity =
                new HttpEntity<>(dto, headers);

        rest.exchange(
                brtUrl + "/api/abonents/{number}/tariff",
                HttpMethod.PATCH,
                entity,
                Void.class,
                number
        );
    }
}