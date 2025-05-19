package com.example.faikyoussef.mapper;

import com.example.faikyoussef.dto.RemboursementDto;
import com.example.faikyoussef.entity.Remboursement;

public class RemboursementMapper {

    public static RemboursementDto toDto(Remboursement remboursement) {
        if (remboursement == null) {
            return null;
        }
        RemboursementDto remboursementDto = new RemboursementDto();
        remboursementDto.setId(remboursement.getId());
        remboursementDto.setDate(remboursement.getDate());
        remboursementDto.setAmount(remboursement.getAmount());
        remboursementDto.setType(remboursement.getType());
        if (remboursement.getCredit() != null) {
            remboursementDto.setCreditId(remboursement.getCredit().getId());
        }
        return remboursementDto;
    }

    public static Remboursement toEntity(RemboursementDto remboursementDto) {
        if (remboursementDto == null) {
            return null;
        }
        Remboursement remboursement = new Remboursement();
        remboursement.setId(remboursementDto.getId());
        remboursement.setDate(remboursementDto.getDate());
        remboursement.setAmount(remboursementDto.getAmount());
        remboursement.setType(remboursementDto.getType());
        return remboursement;
    }
}
