package com.memory.commutator.Repositories;

import com.memory.commutator.Models.CDRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDRecordRepository extends JpaRepository<CDRecord, Long> {
}
