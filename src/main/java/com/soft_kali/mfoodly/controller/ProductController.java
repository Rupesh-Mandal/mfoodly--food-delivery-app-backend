package com.soft_kali.mfoodly.controller;


import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.ProductResponse;
import com.soft_kali.mfoodly.service.ProductService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get-products-by-default-city")
    public ResponseEntity<ProductResponse> getAllProductByUserDefaultLocation(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=productService.getAllProductByUserDefaultLocation(pageable);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping("/cityId/{cityId}/get-products")
    public ResponseEntity<ProductResponse> getAllProductByCity(
            @PathVariable int cityId,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=productService.getAllProductByCity(cityId,pageable);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping("/outletId/{outletId}/get-products")
    public ResponseEntity<ProductResponse> getAllProductByOutlet(
            @PathVariable Long outletId,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=productService.getAllProductByOutlet(pageable,outletId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }



    @GetMapping("/categoryId/{categoryId}/get-products")
    public ResponseEntity<ProductResponse> getAllProductByCagegory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort
    ){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT));

        ProductResponse productResponse=productService.getAllProductByCagegory(categoryId,pageable);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/outletId/{outletId}/add-product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, @PathVariable Long outletId){
        ProductDto savedProduct=productService.addProduct(productDto,outletId);
        return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
    }

    @PostMapping("/productId/{productId}/update-product")
    public ResponseEntity<ProductDto> UpdateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId){
        ProductDto savedProduct=productService.UpdateProduct(productDto,productId);
        return new ResponseEntity<>(savedProduct,HttpStatus.OK);
    }

    @DeleteMapping("/productId/{productId}/delete-product")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse("Successfully Deleted",true),HttpStatus.OK);
    }

    @GetMapping("/get-categories")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryEntityList=productService.getAllCategory();
        return new ResponseEntity<>(categoryEntityList,HttpStatus.OK);
    }

}
