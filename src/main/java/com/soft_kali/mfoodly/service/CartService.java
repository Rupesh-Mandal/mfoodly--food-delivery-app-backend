package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.cart.CartDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.CartResponse;

public interface CartService {

    ApiResponse addToCat(int quantity,Long productId);
    ApiResponse udateCart(Long cartId,Long subCartId,int quantity);
    ApiResponse deletAllCartByUser();
    ApiResponse deletCart(Long cartId);
    ApiResponse deletSubCart(Long cartId,Long subCartId);

    CartResponse getAllByUser();
    CartResponse getAllByOutlet(Long outletId);
}
