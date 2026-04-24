package org.example.fraudservice.web;


import org.example.fraudservice.entities.Fraud;
import org.example.fraudservice.services.FraudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frauds")
public class FraudController {
    private final FraudService fraudeService ;

    public FraudController(FraudService fraudService){
        this.fraudeService = fraudService;
    }

    @GetMapping("/all/admin")
    public List<Fraud> getAllFrauds(){
        return fraudeService.showAllFrauds();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFraud( @PathVariable Long id){
    }

}
