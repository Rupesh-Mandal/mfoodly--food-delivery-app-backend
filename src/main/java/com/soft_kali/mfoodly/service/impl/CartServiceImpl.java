package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.cart.CartDto;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.entity.cart.CartEntity;
import com.soft_kali.mfoodly.entity.cart.SubCartEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.exeptions.UnauthorizeRequest;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.CartResponse;
import com.soft_kali.mfoodly.repository.CartRepository;
import com.soft_kali.mfoodly.repository.OutletRepository;
import com.soft_kali.mfoodly.repository.ProductRepositoy;
import com.soft_kali.mfoodly.repository.SubCartRepository;
import com.soft_kali.mfoodly.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepositoy productRepositoy;

    @Autowired
    SubCartRepository subCartRepository;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ApiResponse addToCat(int quantity, Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        ProductEntity productEntity=productRepositoy.findById(productId).orElseThrow(()-> new ResourceNotFountException("Product","id",productId));
        OutletEntity outletEntity=productEntity.getOutletName();

        Optional<CartEntity> cartEntityOptional=cartRepository.findByUserEntityAndOutletEntity(userEntity,outletEntity);

        if (cartEntityOptional.isPresent()){
            CartEntity cartEntity=cartEntityOptional.get();
            List<SubCartEntity> subCartEntityList=cartEntity.getSubCartEntityList();

            SubCartEntity subCartEntity=new SubCartEntity();
            subCartEntity.setQuantity(quantity);
            subCartEntity.setProductEntity(productEntity);
            subCartEntityList.add(subCartEntity);

            cartEntity.setSubCartEntityList(subCartEntityList);
            cartRepository.save(cartEntity);


            return new ApiResponse("Product Successfully Added to Cart",true);
        }

        CartEntity cartEntity =new CartEntity();
        cartEntity.setOutletEntity(outletEntity);
        cartEntity.setUserEntity(userEntity);

        SubCartEntity subCartEntity=new SubCartEntity();
        subCartEntity.setQuantity(quantity);
        subCartEntity.setProductEntity(productEntity);
        List<SubCartEntity> subCartEntityList=new ArrayList<>();
        subCartEntityList.add(subCartEntity);

        cartEntity.setSubCartEntityList(subCartEntityList);
        cartRepository.save(cartEntity);


        return new ApiResponse("Product Successfully Added to Cart",true);
    }

    @Override
    public ApiResponse udateCart(Long cartId, Long subCartId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        CartEntity cartEntity=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFountException("Cart","id",cartId));

        if (cartEntity.getUserEntity().getUserId()!=userEntity.getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }
        SubCartEntity subCartEntity=subCartRepository.findById(subCartId).orElseThrow(()-> new ResourceNotFountException("Sub Cart","id",subCartId));

        subCartEntity.setQuantity(quantity);
        subCartRepository.save(subCartEntity);

        return new ApiResponse("Cart Product Successfully Updated",true);
    }

    @Override
    public ApiResponse deletAllCartByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        cartRepository.deleteAllByUserEntity(userEntity);
        return new ApiResponse("All Cart Product Deleted Successfully",true);
    }

    @Override
    public ApiResponse deletCart(Long cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        CartEntity cartEntity=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFountException("Cart","id",cartId));

        if (cartEntity.getUserEntity().getUserId()!=userEntity.getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }
        cartRepository.delete(cartEntity);

        return new ApiResponse("Cart Product Deleted Successfully",true);
    }

    @Override
    public ApiResponse deletSubCart(Long cartId, Long subCartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        CartEntity cartEntity=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFountException("Cart","id",cartId));

        if (cartEntity.getUserEntity().getUserId()!=userEntity.getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }
        SubCartEntity subCartEntity=subCartRepository.findById(subCartId).orElseThrow(()-> new ResourceNotFountException("Sub Cart","id",subCartId));
        subCartRepository.delete(subCartEntity);

        return new ApiResponse("Sub Cart Product Successfully deleted",true);
    }

    @Override
    public CartResponse getAllByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        List<CartEntity> cartEntityList=cartRepository.findAllByUserEntity(userEntity);

        List<CartDto> cartDtoList=cartEntityList.stream().map(cartEntity ->
                modelMapper.map(cartEntity,CartDto.class)).collect(Collectors.toList());

        CartResponse cartResponse=new CartResponse();
        cartResponse.setContent(cartDtoList);
        cartResponse.setPageNumber(0);
        cartResponse.setPageSize(cartEntityList.size());
        cartResponse.setTotalElement(cartEntityList.size());
        cartResponse.setTotalPages(1);
        cartResponse.setLastPage(true);
        return cartResponse;
    }

    @Override
    public CartResponse getAllByOutlet(Long outletId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        OutletEntity outletEntity=outletRepository.findById(outletId).orElseThrow(()-> new ResourceNotFountException("Outlet","id",outletId));

        List<CartEntity> cartEntityList=cartRepository.findAllByUserEntityAndOutletEntity(userEntity,outletEntity);

        List<CartDto> cartDtoList=cartEntityList.stream().map(cartEntity ->
                modelMapper.map(cartEntity,CartDto.class)).collect(Collectors.toList());

        CartResponse cartResponse=new CartResponse();
        cartResponse.setContent(cartDtoList);
        cartResponse.setPageNumber(0);
        cartResponse.setPageSize(cartEntityList.size());
        cartResponse.setTotalElement(cartEntityList.size());
        cartResponse.setTotalPages(1);
        cartResponse.setLastPage(true);
        return cartResponse;
    }
}
