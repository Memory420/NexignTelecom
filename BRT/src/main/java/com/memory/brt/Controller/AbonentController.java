package com.memory.brt.Controller;

import com.memory.brt.Repository.AbonentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/abonents")
public class AbonentController {

    private final AbonentRepository abonentRepository;

    public AbonentController(AbonentRepository abonentRepository) {
        this.abonentRepository = abonentRepository;
    }

    //    @GetMapping("/{number}")
//    public ResponseEntity<SubscriberInfo> get(@PathVariable String number) {
//    }

//    @PostMapping
//    public ResponseEntity<Void> create(@RequestBody CreateSubscriberRequest req) {
//    }

//    @PostMapping("/{number}/topup")
//    public ResponseEntity<Void> topUp(@PathVariable String number, @RequestBody TopUpRequest req) {
//    }

//    @PatchMapping("/{number}/tariff")
//    public ResponseEntity<Void> changeTariff(@PathVariable String number, @RequestBody ChangeTariffRequest req) {
//    }
}
