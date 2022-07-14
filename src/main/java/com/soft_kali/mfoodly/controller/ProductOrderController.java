package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import com.soft_kali.mfoodly.service.ProductOrderService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product-order")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @PostMapping("/cityId/{cityId}/addressBookId/{addressBookId}/new-order")
    public ResponseEntity<ApiResponse> startNewOrder(@RequestBody ProductOrderDto productOrderDto,
                                                     @PathVariable int cityId,@PathVariable Long addressBookId){
        ApiResponse apiResponse=productOrderService.startNewOrder(productOrderDto,cityId,addressBookId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/outletId/{outletId}/get-orders")
    ResponseEntity<ProductOrderResponse> getAllProductOrderByOutletId(@PathVariable Long outletId,
                                                                      @RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                      @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                      @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                      @RequestParam(value = "sortDir") Optional<Sort.Direction> sort){


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse=productOrderService.getAllProductOrderByOutletId(outletId,pageable);
        return new ResponseEntity<>(productOrderResponse,HttpStatus.OK);
    }

}
