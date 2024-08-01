package com.hps.fee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hps.Transfer.DTO.TransferDTO;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fee")
public class FeeController {
    private final FeeService feeService;

    @PostMapping
    public Fee createFees(@RequestBody Fee fee) {
        return feeService.createFees(fee);
    }

    @GetMapping
    public List<Fee> getAllFees() {
        return feeService.getAllFees();
    }

    @GetMapping("/{id}")
    public Fee getFeesById(@PathVariable Long id) {
        return feeService.getFeesById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFees(@PathVariable Long id) {
        feeService.deleteFees(id);
    }

    @PostMapping("/calculate")
    public Fee calculateFee(@RequestParam BigDecimal amount, @RequestParam String description) {
        // Utilisation du service pour calculer les frais
        return feeService.calculateFee(amount);
    }

    @PostMapping("/processTransaction")
    public ResponseEntity<TransferDTO> processTransaction(@RequestBody TransferDTO transferDTO) {
        try {
            // Calculer les frais en utilisant le montant du DTO
            BigDecimal feeAmount = feeService.calculateFee(transferDTO.getAmount()).getAmount();

            // Mettre à jour le montant du transfert avec les frais calculés
            BigDecimal newAmount = transferDTO.getAmount().subtract(feeAmount);

            // Créer un nouvel objet TransferDTO avec le montant mis à jour
            TransferDTO updatedTransferDTO = new TransferDTO();
            updatedTransferDTO.setId(transferDTO.getId());
            updatedTransferDTO.setAmount(newAmount);

            // Retourner le DTO mis à jour avec un statut HTTP 200 OK
            return ResponseEntity.ok(updatedTransferDTO);
        } catch (Exception e) {
            // Gérer les exceptions et retourner un statut HTTP 500 en cas d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


