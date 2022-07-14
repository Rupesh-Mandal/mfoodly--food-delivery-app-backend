package com.soft_kali.mfoodly.repository.location;

import com.soft_kali.mfoodly.entity.location.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,Integer> {
}
