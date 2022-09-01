package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.role.Role;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.location.CountryEntity;
import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.repository.CategoryRepository;
import com.soft_kali.mfoodly.repository.OrderStatusRepository;
import com.soft_kali.mfoodly.repository.RoleRepository;
import com.soft_kali.mfoodly.repository.UserRepository;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.repository.location.CountryRepository;
import com.soft_kali.mfoodly.repository.location.DistrictsRepository;
import com.soft_kali.mfoodly.service.ConstantService;
import com.soft_kali.mfoodly.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.soft_kali.mfoodly.utils.AppConstants.admin;
import static com.soft_kali.mfoodly.utils.AppConstants.defaultPass;

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

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveConstant() {
        registerRole();
        registerCategory();
        registerLocation();
        registerOrderStatus();
        registerAdmin();

    }

    private void registerAdmin() {
        if (userRepository.findByPhoneNumber(admin).isPresent()){
            return;
        }
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

        Role role=roleRepository.findById(AppConstants.ROLE_ADMIN).get();

        List<Role> roleList=new ArrayList<>();
        roleList.add(role);

        UserEntity userEntity=new UserEntity();
        userEntity.setPhoneNumber(admin);
        userEntity.setName(admin);
        userEntity.setPassword(passwordEncoder.encode(defaultPass));
        UserEntity savedAdmin=userRepository.save(userEntity);

        savedAdmin.setRoles(roleList);
        userRepository.save(savedAdmin);
    }

    private void registerOrderStatus() {
        OrderStatus status=new OrderStatus();
        List<OrderStatusEntity> orderStatusEntityList=new ArrayList<>();
        orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_PENDING_TO_ACCEPT(),
                status.getSTATUS_PENDING_TO_ACCEPT_NAME(),status.getSTATUS_PENDING_TO_ACCEPT_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_WATING_TO_ORDER_READY(),
                status.getSTATUS_WATING_TO_ORDER_READY_NAME(),status.getSTATUS_WATING_TO_ORDER_READY_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_WATING_ACCEPT_BY_PICKUPBOY(),
                status.getSTATUS_WATING_ACCEPT_BY_PICKUPBOY_NAME(),status.getSTATUS_WATING_ACCEPT_BY_PICKUPBOY_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_ORDER_STARTED_TO_DELIVERED(),
                status.getSTATUS_ORDER_STARTED_TO_DELIVERED_NAME(),status.getSTATUS_ORDER_STARTED_TO_DELIVERED_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_ORDER_SUCCESSFULLY_DELIVERED(),
                status.getSTATUS_ORDER_SUCCESSFULLY_DELIVERED_NAME(),status.getSTATUS_ORDER_SUCCESSFULLY_DELIVERED_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_ORDER_CANCELLED_BY_SELLER(),
                status.getSTATUS_ORDER_CANCELLED_BY_SELLER_NAME(),status.getSTATUS_ORDER_CANCELLED_BY_SELLER_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_ORDER_CANCELLED_BY_USER(),
                status.getSTATUS_ORDER_CANCELLED_BY_USER_NAME(),status.getSTATUS_ORDER_CANCELLED_BY_USER_COLORS()));

         orderStatusEntityList.add(new OrderStatusEntity(status.getSTATUS_ORDER_FAILD_TO_DELIVERED(),
                status.getSTATUS_ORDER_FAILD_TO_DELIVERED_NAME(),status.getSTATUS_ORDER_FAILD_TO_DELIVERED_COLORS()));

        orderStatusRepository.saveAll(orderStatusEntityList);
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
        roleList.add(new Role(AppConstants.ROLE_SELLER, "ROLE_SELLER"));
        roleList.add(new Role(AppConstants.ROLE_SUB_OUTLET, "ROLE_SUB_OUTLET"));
        roleList.add(new Role(AppConstants.ROLE_PICKUP_BOY, "ROLE_PICKUP_BOY"));
        roleList.add(new Role(AppConstants.ROLE_CUSTOMER, "ROLE_CUSTOMER"));
        List<Role> result = roleRepository.saveAll(roleList);

        System.out.println(result);
    }

}
