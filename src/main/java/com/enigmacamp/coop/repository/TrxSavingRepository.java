package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.entity.TrxSaving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrxSavingRepository extends JpaRepository<TrxSaving, String> {

}
