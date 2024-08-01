package com.hps.fee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeeService {
    @Autowired
    private FeeRepository FeeRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Fee createFees(Fee fee) {
        return FeeRepository.save(fee);
    }

    public List<Fee> getAllFees() {
        return FeeRepository.findAll();
    }

    public Fee getFeesById(Long id) {
        return FeeRepository.findById(id).orElse(null);
    }

    public void deleteFees(Long id) {
        FeeRepository.deleteById(id);
    }


    public Fee calculateFee(BigDecimal amount) {
        BigDecimal feeAmount = amount.multiply(BigDecimal.valueOf(0.02)); // Exemple de calcul de frais
        Fee fee = Fee.builder()   // Utilisation de Fee pour la construction
                .amount(feeAmount)
                .build();
        return FeeRepository.save(fee);  // Sauvegarde via feeRepository
    }

    // Méthode pour obtenir les frais depuis le service Fee
    public Fee getFeeFromService(Long feeId) {
        String url = "http://FeeService/fee/" + feeId; // URL du service Fee
        return restTemplate.getForObject(url, Fee.class);
    }
}