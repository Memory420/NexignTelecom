package com.memory.crm.controller;

import com.memory.crm.model.SubscriberInfo;
import com.memory.crm.model.TopUpRequest;
import com.memory.crm.service.CRMService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {
    private final CRMService crm;

    public SubscriberController(CRMService crm) {
        this.crm = crm;
    }

    @GetMapping("/me")
    public SubscriberInfo me(Authentication auth) {
        return crm.getSubscriber(auth.getName());
    }

    @PostMapping("/me/topup")
    public void topUp(@RequestBody TopUpRequest req, Authentication auth) {
        crm.topUp(auth.getName(), req.getAmount());
    }
}
