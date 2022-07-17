package com.soft_kali.mfoodly.controller;


import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.CartResponse;
import com.soft_kali.mfoodly.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/quantity/{quantity}/productId/{productId}/add-to-cart")
    public ResponseEntity<ApiResponse> addToCat(@PathVariable int quantity, @PathVariable Long productId){
        ApiResponse apiResponse=cartService.addToCat(quantity,productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/cartId/{cartId}/subCartId/{subCartId}/quantity/{quantity}/update")
    public ResponseEntity<ApiResponse> udateCart(@PathVariable Long cartId, @PathVariable Long subCartId, @PathVariable int quantity){
        ApiResponse apiResponse=cartService.udateCart(cartId,subCartId,quantity);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deletAllCartByUser(){
        ApiResponse apiResponse=cartService.deletAllCartByUser();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @DeleteMapping("/cartId/{cartId}/delete")
    public ResponseEntity<ApiResponse> deletCart(@PathVariable Long cartId){
        ApiResponse apiResponse=cartService.deletCart(cartId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/cartId/{cartId}/subCartId/{subCartId}/delete")
    public ResponseEntity<ApiResponse> deletSubCart(@PathVariable Long cartId, @PathVariable Long subCartId){
        ApiResponse apiResponse=cartService.deletSubCart(cartId,subCartId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/get-carts")
    public ResponseEntity<CartResponse> getAllByUser(){
        CartResponse cartResponse=cartService.getAllByUser();
        return new ResponseEntity<>(cartResponse,HttpStatus.OK);
    }

    @GetMapping("/outletId/{outletId}/get-carts")
    public ResponseEntity<CartResponse> getAllByOutlet(@PathVariable Long outletId){
        CartResponse cartResponse=cartService.getAllByOutlet(outletId);
        return new ResponseEntity<>(cartResponse,HttpStatus.OK);
    }

}
