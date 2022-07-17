package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.model.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse getAllProductByUserDefaultLocation(Pageable pageable);
    ProductResponse getAllProductByCity(int cityId,Pageable pageable);
    ProductResponse getAllProductByOutlet(Pageable pageable,Long outletId);
    ProductResponse getAllProductByCagegory(Long categoryId,Pageable pageable);

    ProductDto addProduct(ProductDto productDto,Long outletId);
    ProductDto UpdateProduct(ProductDto productDto,Long productId);
    void deleteProduct(Long productId);

    List<CategoryDto> getAllCategory();
}
