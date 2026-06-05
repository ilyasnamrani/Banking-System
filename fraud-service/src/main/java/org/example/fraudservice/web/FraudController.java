package org.example.fraudservice.web;


import org.example.fraudservice.entities.Fraud;
import org.example.fraudservice.services.FraudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frauds")
@CrossOrigin("*")
public class FraudController {
    private final FraudService fraudService ;

    public FraudController(FraudService fraudService){
        this.fraudService = fraudService;
    }

    @GetMapping("/admin")
    public List<Fraud> getAllFrauds(){
        return fraudService.showAllFrauds();
    }


    @DeleteMapping("/delete/{id}")
    public void deleteFraud( @PathVariable Long id){
        fraudService.deleteFraud(id);
    }
    @GetMapping("/{idFraud}")
    public Fraud getFraud(@PathVariable Long idFraud){
        return fraudService.showFraud(idFraud);
    }

    @GetMapping("/account/{idAccount}")
    public List<Fraud>  getFrauds(@PathVariable Long idAccount){
       return  fraudService.showAllFraudsByAccount(idAccount);
    }

}
