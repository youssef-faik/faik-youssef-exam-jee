package com.example.faikyoussef.repository;

import com.example.faikyoussef.entity.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
}
