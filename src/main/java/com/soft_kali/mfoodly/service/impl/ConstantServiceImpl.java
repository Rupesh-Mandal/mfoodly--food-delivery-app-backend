package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.entity.CategoryEntity;
import com.soft_kali.mfoodly.entity.Role;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.location.CountryEntity;
import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import com.soft_kali.mfoodly.repository.CategoryRepository;
import com.soft_kali.mfoodly.repository.RoleRepository;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.repository.location.CountryRepository;
import com.soft_kali.mfoodly.repository.location.DistrictsRepository;
import com.soft_kali.mfoodly.service.ConstantService;
import com.soft_kali.mfoodly.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConstantServiceImpl implements ConstantService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    DistrictsRepository districtsRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public void saveConstant() {
        registerRole();
        registerCategory();
        registerLocation();
    }

    private void registerLocation() {
        CountryEntity nepal=countryRepository.save(new CountryEntity(CountryConstant.NEPAL,CountryConstant.NEPAL_NAME));

        DistrictsEntity siraha=districtsRepository.save(new DistrictsEntity(DistrictsConstant.SIRAHA,DistrictsConstant.SIRAHA_NAME,nepal));

        cityRepository.save(new CityEntity(CityConstant.LAHAN,CityConstant.LAHAN_NAME,siraha));
    }

    private void registerCategory() {
        List<CategoryEntity> categoryEntityList=new ArrayList<>();
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Vegetables,"Vegetables",CategoryConstants.Vegetables_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Fruits,"Fruits",CategoryConstants.Fruits_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Meat,"Meat",CategoryConstants.Meat_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Dairy,"Dairy",CategoryConstants.Dairy_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Grains,"Grains",CategoryConstants.Grains_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Legumes,"Legumes",CategoryConstants.Legumes_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Baked_Goods,"Baked Goods",CategoryConstants.Baked_Goods_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Seafood,"Seafood",CategoryConstants.Seafood_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Fast_Food,"Fast Food",CategoryConstants.Fast_Food_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Veg,"Veg",CategoryConstants.Veg_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Non_Veg,"Non Veg",CategoryConstants.Non_Veg_Description));
        categoryEntityList.add(new CategoryEntity(CategoryConstants.Chinese_Food,"Chinese Food",CategoryConstants.Chinese_Food_Description));

        List<CategoryEntity> result=categoryRepository.saveAll(categoryEntityList);
        System.out.println(result);

    }

    private void registerRole() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(AppConstants.ROLE_ADMIN, "ROLE_ADMIN"));
        roleList.add(new Role(AppConstants.ROLE_SUB_ADMIN, "ROLE_SUB_ADMIN"));
        roleList.add(new Role(AppConstants.ROLE_OUTLET, "ROLE_OUTLET"));
        roleList.add(new Role(AppConstants.ROLE_SUB_OUTLET, "ROLE_SUB_OUTLET"));
        roleList.add(new Role(AppConstants.ROLE_PICKUP_BOY, "ROLE_PICKUP_BOY"));
        roleList.add(new Role(AppConstants.ROLE_CUSTOMER, "ROLE_CUSTOMER"));
        List<Role> result = roleRepository.saveAll(roleList);

        System.out.println(result);
    }

}
