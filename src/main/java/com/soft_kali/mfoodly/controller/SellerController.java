package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.seller.SellerDashboardData;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.JwtAuthRequest;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import com.soft_kali.mfoodly.service.SellerService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody JwtAuthRequest request) throws Exception {
        ApiResponse apiResponse = sellerService.login(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-user-info")
    public ResponseEntity<UserDto> getUserInformation() {
        UserDto userDto = sellerService.getUSerInfo();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/dashboard-data")
    public ResponseEntity<SellerDashboardData> getDashboard() {
        SellerDashboardData sellerDashboardData = sellerService.getDashboardData();
        return new ResponseEntity<>(sellerDashboardData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/cancel-order/{productOrderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long productOrderId) {
        ApiResponse apiResponse = sellerService.cancelOrder(productOrderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/accept-order/{productOrderId}")
    public ResponseEntity<ApiResponse> acceptOrder(@PathVariable Long productOrderId) {
        ApiResponse apiResponse = sellerService.acceptOrder(productOrderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-pending-order")
    public ResponseEntity<ProductOrderResponse> getAllPendingOrder(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                   @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                   @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                   @RequestParam(value = "sortDir") Optional<Sort.Direction> sort) {


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse = sellerService.getAllPendingOrder(pageable);
        return new ResponseEntity<>(productOrderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-started-order")
    public ResponseEntity<ProductOrderResponse> getAllStartedOrder(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                   @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                   @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                   @RequestParam(value = "sortDir") Optional<Sort.Direction> sort) {


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse = sellerService.getAllStartedOrder(pageable);
        return new ResponseEntity<>(productOrderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-cancelled-order")
    public ResponseEntity<ProductOrderResponse> getAllCancelledOrder(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                   @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                   @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                   @RequestParam(value = "sortDir") Optional<Sort.Direction> sort) {


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse = sellerService.getAllCancelledOrder(pageable);
        return new ResponseEntity<>(productOrderResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-failed-order")
    public ResponseEntity<ProductOrderResponse> getAllFailedOrder(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                   @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                   @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                   @RequestParam(value = "sortDir") Optional<Sort.Direction> sort) {


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse = sellerService.getAllFailedOrder(pageable);
        return new ResponseEntity<>(productOrderResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/get-order-history")
    public ResponseEntity<ProductOrderResponse> getAllOrderHistory(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                                                   @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
                                                                   @RequestParam(value = "sortBy") Optional<String> sortBy,
                                                                   @RequestParam(value = "sortDir") Optional<Sort.Direction> sort) {


        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_PRODUCT_ORDER));

        ProductOrderResponse productOrderResponse = sellerService.getAllOrderHistory(pageable);
        return new ResponseEntity<>(productOrderResponse, HttpStatus.OK);
    }



}
