package com.memory.crm.controller;

import com.memory.crm.model.ChangeTariffRequest;
import com.memory.crm.model.CreateSubscriberRequest;
import com.memory.crm.model.SubscriberInfo;
import com.memory.crm.model.TopUpRequest;
import com.memory.crm.service.CRMService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private final CRMService crm;

    public ManagerController(CRMService crm) {
        this.crm = crm;
    }

    @PostMapping("/abonents")
    public void create(@RequestBody CreateSubscriberRequest dto) {
        crm.createSubscriber(dto);
    }

    @GetMapping("/abonents/{number}")
    public SubscriberInfo get(@PathVariable String number) {
        return crm.getSubscriber(number);
    }

    @PostMapping("/abonents/{number}/topup")
    public void topUp(@PathVariable String number, @RequestBody TopUpRequest req) {
        crm.topUp(number, req.getAmount());
    }

    @PatchMapping("/abonents/{number}/tariff")
    public void changeTariff(@PathVariable String number, @RequestBody ChangeTariffRequest dto) {
        crm.changeTariff(number, dto.getTariffId());
    }
}
