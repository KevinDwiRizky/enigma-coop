package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, String> {
}
