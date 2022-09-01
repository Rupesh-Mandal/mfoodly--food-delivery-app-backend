package com.soft_kali.mfoodly.repository.location;

import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity,Integer> {
    List<CityEntity> findAllByDistrictsEntity(DistrictsEntity districtsEntity);

}
