package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.admin.AdminDashboardData;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.dto.location.CountryDto;
import com.soft_kali.mfoodly.dto.location.DistrictsDto;
import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    ApiResponse login(JwtAuthRequest request) throws Exception;

    AdminDashboardData getDashboardData();

    CategoriesResponse getAllProductCategories(Pageable pageable);

    CategoryDto upLoadCategory(CategoryDto categoryDto);

    ApiResponse deleteCategory(Long categoryId);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    CategoriesResponse getSearchProductCategories(String search_query, Pageable pageable);

    UserResponse getCustomer(Pageable pageable);

    UserResponse getSeller(Pageable pageable);

    ProductOrderResponse getProductOrders(Pageable pageable);

    UserResponse getSearchCutomer(String orElse, Pageable pageable);

    UserResponse getSearchseller(String orElse, Pageable pageable);

    ProductOrderResponse getSearchProductOrders(String orElse, Pageable pageable);

    OutletResponse getSearchOutlet(String search_query, Pageable pageable);

    OutletResponse getOutlet(Pageable pageable);

    ProductResponse getSearchProduct(String orElse, Pageable pageable);

    ProductResponse getProduct(Pageable pageable);

    List<CountryDto> getAllCountry();

    List<DistrictsDto> getAllDistrictByCountry(int countrytId);

    List<CityDto> getAllCityByDistrict(int districtsId);

    DistrictsDto addDistrict(int countrytId, DistrictsDto districtsDto);

    CityDto addCity(int districtsId, CityDto cityDto);
}
