package com.hps.Transfer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Optional<Transfer> getTransferById(Long id) {
        return transferRepository.findById(id);
    }

    public Transfer createTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    public Optional<Transfer> updateTransfer(Long id, Transfer transfer) {
        if (transferRepository.existsById(id)) {
            transfer.setId(id);
            return Optional.of(transferRepository.save(transfer));
        }
        return Optional.empty();
    }

    public boolean deleteTransfer(Long id) {
        if (transferRepository.existsById(id)) {
            transferRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Autowired
    private RestTemplate restTemplate;

    public Transfer processTransaction(Transfer transfer) {
        // Envoyer la transaction au service Fee
        String feeServiceUrl = "http://FeeService/fee/processTransaction";
        ResponseEntity<Transfer> response = restTemplate.postForEntity(feeServiceUrl, transfer, Transfer.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Retourner la transaction mise à jour
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to process transaction");
        }
    }
    }
