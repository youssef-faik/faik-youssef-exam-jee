package com.example.faikyoussef.mapper;

import com.example.faikyoussef.dto.CreditDto;
import com.example.faikyoussef.entity.Credit;
import com.example.faikyoussef.entity.CreditImmobilier;
import com.example.faikyoussef.entity.CreditPersonnel;
import com.example.faikyoussef.entity.CreditProfessionnel;

public class CreditMapper {

    public static CreditDto toDto(Credit credit) {
        if (credit == null) {
            return null;
        }
        CreditDto creditDto = new CreditDto();
        creditDto.setId(credit.getId());
        creditDto.setAmount(credit.getAmount());
        creditDto.setDate(credit.getDate());
        creditDto.setStatus(credit.getStatus());
        if (credit.getClient() != null) {
            creditDto.setClientId(credit.getClient().getId());
        }

        // Handle specific credit types
        if (credit instanceof CreditPersonnel) {
            creditDto.setTypeCredit("PERSONNEL");
            CreditPersonnel cp = (CreditPersonnel) credit;
            creditDto.setMotif(cp.getMotif());
        } else if (credit instanceof CreditImmobilier) {
            creditDto.setTypeCredit("IMMOBILIER");
            CreditImmobilier ci = (CreditImmobilier) credit;
            creditDto.setBienType(ci.getBienType());
        } else if (credit instanceof CreditProfessionnel) {
            creditDto.setTypeCredit("PROFESSIONNEL");
            CreditProfessionnel cpro = (CreditProfessionnel) credit;
            creditDto.setMotif(cpro.getMotif());
            creditDto.setRaisonSociale(cpro.getRaisonSociale());
        }
        // Remboursements are not mapped here.
        return creditDto;
    }

    public static Credit toEntity(CreditDto creditDto) {
        if (creditDto == null) {
            return null;
        }
        Credit credit;
        String typeCredit = creditDto.getTypeCredit();

        if ("PERSONNEL".equalsIgnoreCase(typeCredit)) {
            CreditPersonnel cp = new CreditPersonnel();
            cp.setMotif(creditDto.getMotif());
            credit = cp;
        } else if ("IMMOBILIER".equalsIgnoreCase(typeCredit)) {
            CreditImmobilier ci = new CreditImmobilier();
            ci.setBienType(creditDto.getBienType());
            credit = ci;
        } else if ("PROFESSIONNEL".equalsIgnoreCase(typeCredit)) {
            CreditProfessionnel cpro = new CreditProfessionnel();
            cpro.setMotif(creditDto.getMotif());
            cpro.setRaisonSociale(creditDto.getRaisonSociale());
            credit = cpro;
        } else {
            // Handle unknown or null credit type. Since Credit is abstract,
            // we cannot instantiate it directly without a concrete type.
            // Throwing an exception is a common way to handle this.
            throw new IllegalArgumentException("Unknown or unspecified credit type: " + typeCredit);
        }

        credit.setId(creditDto.getId());
        credit.setAmount(creditDto.getAmount());
        credit.setDate(creditDto.getDate());
        credit.setStatus(creditDto.getStatus());
        // Client and Remboursements are not set here.
        // They should be managed by their respective services when creating/updating entities.
        return credit;
    }
}
