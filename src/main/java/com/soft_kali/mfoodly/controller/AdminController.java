package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.admin.AdminDashboardData;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.dto.location.CountryDto;
import com.soft_kali.mfoodly.dto.location.DistrictsDto;
import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.model.*;
import com.soft_kali.mfoodly.service.AdminService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody JwtAuthRequest request) throws Exception {
        ApiResponse apiResponse=adminService.login(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dashboard-data")
    public ResponseEntity<AdminDashboardData> getDashboaredData(){
        AdminDashboardData adminDashboardData =adminService.getDashboardData();
        return new ResponseEntity<>(adminDashboardData, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-categories")
    public ResponseEntity<CategoriesResponse> getAllCategory(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_CATEGORIES));

        CategoriesResponse categoriesResponse=adminService.getAllProductCategories(pageable);
        return new ResponseEntity<>(categoriesResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-customer")
    public ResponseEntity<UserResponse> getCustomer(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_USER));

        UserResponse userResponse=adminService.getCustomer(pageable);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-seller")
    public ResponseEntity<UserResponse> getSeller(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_USER));

        UserResponse userResponse=adminService.getSeller(pageable);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-outlet")
    public ResponseEntity<OutletResponse> getOutlet(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_OUTLET));

        OutletResponse outletResponse=adminService.getOutlet(pageable);
        return new ResponseEntity<>(outletResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-outlet/q/{search_query}")
    public ResponseEntity<OutletResponse> getOutlet(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_OUTLET));

        OutletResponse outletResponse=adminService.getSearchOutlet(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(outletResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-product-orders")
    public ResponseEntity<ProductOrderResponse> getProductOrders(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse=adminService.getProductOrders(pageable);
        return new ResponseEntity<>(productOrderResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-categories/q/{search_query}")
    public ResponseEntity<CategoriesResponse> getAllCategory(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_CATEGORIES));

        CategoriesResponse categoriesResponse=adminService.getSearchProductCategories(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(categoriesResponse,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-product-orders/q/{search_query}")
    public ResponseEntity<ProductOrderResponse> getProductOrders(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse=adminService.getSearchProductOrders(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(productOrderResponse,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-seller/q/{search_query}")
    public ResponseEntity<UserResponse> getSeller(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_USER));

        UserResponse userResponse=adminService.getSearchseller(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-customer/q/{search_query}")
    public ResponseEntity<UserResponse> getCustomer(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_USER));

        UserResponse userResponse=adminService.getSearchCutomer(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uload-category")
    public ResponseEntity<CategoryDto> uploadCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(adminService.upLoadCategory(categoryDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uload-category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<>(adminService.updateCategory(categoryId,categoryDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<>(adminService.deleteCategory(categoryId), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-product")
    public ResponseEntity<ProductResponse> getProduct(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=adminService.getProduct(pageable);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-product/q/{search_query}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable(value = "search_query") Optional<String> search_query,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=adminService.getSearchProduct(search_query.orElse(" "),pageable);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-country")
    public ResponseEntity<List<CountryDto>> getAllCountry(){

        List<CountryDto> countryDtoList=adminService.getAllCountry();
        return new ResponseEntity<>(countryDtoList,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-district-by-country/{countrytId}")
    public ResponseEntity<List<DistrictsDto>> getAllDistrictByCountry(
            @PathVariable(value = "countrytId") int countrytId
            ){

        List<DistrictsDto> districtsDtoList=adminService.getAllDistrictByCountry(countrytId);
        return new ResponseEntity<>(districtsDtoList,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-city-by-district/{districtsId}")
    public ResponseEntity<List<CityDto>> getAllCityByDistrict(
            @PathVariable(value = "districtsId") int districtsId
            ){

        List<CityDto> cityDtos=adminService.getAllCityByDistrict(districtsId);
        return new ResponseEntity<>(cityDtos,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-district/{countrytId}")
    public ResponseEntity<DistrictsDto> addDistrict(
            @PathVariable(value = "countrytId") int countrytId,
            @Valid @RequestBody DistrictsDto districtsDto
    ){

        DistrictsDto newDistrictsDto=adminService.addDistrict(countrytId,districtsDto);
        return new ResponseEntity<>(newDistrictsDto,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-city/{districtsId}")
    public ResponseEntity<CityDto> addCity(
            @PathVariable(value = "districtsId") int districtsId,
            @Valid @RequestBody CityDto cityDto
    ){

        CityDto newCityDtoDto=adminService.addCity(districtsId,cityDto);
        return new ResponseEntity<>(newCityDtoDto,HttpStatus.OK);
    }


}
