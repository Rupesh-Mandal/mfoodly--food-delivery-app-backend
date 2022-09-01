package com.soft_kali.mfoodly.repository.location;

import com.soft_kali.mfoodly.dto.location.DistrictsDto;
import com.soft_kali.mfoodly.entity.location.CountryEntity;
import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictsRepository extends JpaRepository<DistrictsEntity,Integer> {
    List<DistrictsEntity> findAllByCountryEntity(CountryEntity countryEntity);
}
