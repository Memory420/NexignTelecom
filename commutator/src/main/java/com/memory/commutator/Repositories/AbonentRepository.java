package com.memory.commutator.Repositories;

import com.memory.commutator.Models.Abonent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonentRepository extends JpaRepository<Abonent, Long> {
}
