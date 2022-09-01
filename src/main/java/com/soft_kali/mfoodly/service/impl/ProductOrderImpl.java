package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.dto.product_order.SingleProductOrderDto;
import com.soft_kali.mfoodly.entity.cart.CartEntity;
import com.soft_kali.mfoodly.entity.cart.SubCartEntity;
import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import com.soft_kali.mfoodly.entity.user.AddressBookEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.exeptions.UnauthorizeRequest;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import com.soft_kali.mfoodly.repository.*;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.service.ProductOrderService;
import com.soft_kali.mfoodly.utils.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderImpl implements ProductOrderService {

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    ProductRepositoy productRepositoy;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AddressBookRepository addressBookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    SingleProductOrderRepository singleProductOrderRepository;

    @Override
    public ApiResponse startNewOrder(int cityId, Long addressBookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        AddressBookEntity addressBookEntity = addressBookRepository.findById(addressBookId).orElseThrow(() -> new ResourceNotFountException("Address Book", "id", addressBookId));
        CityEntity cityEntity = cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFountException("City", "id", cityId));
        List<CartEntity> cartEntityList=cartRepository.findAllByUserEntity(userEntity);

        List<ProductOrderEntity> productOrderEntityList=new ArrayList<>();

        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_PENDING_TO_ACCEPT()).get();


        for (int i=0;i<cartEntityList.size();i++){
            CartEntity cartEntity=cartEntityList.get(i);

            ProductOrderEntity productOrderEntity=new ProductOrderEntity();
            productOrderEntity.setCreatedTime(LocalDateTime.now());
            productOrderEntity.setTotalPrice(cartEntity.getTotalPrice());
            productOrderEntity.setUserEntity(userEntity);
            productOrderEntity.setCityEntity(cityEntity);
            productOrderEntity.setOutletName(cartEntity.getOutletEntity());
            productOrderEntity.setAddressBookEntity(addressBookEntity);
            productOrderEntity.setOrderStatusEntity(orderStatusEntity);
            productOrderEntity.setSeller(cartEntity.getOutletEntity().getUserEntity());
            productOrderEntity.setCreatedTime(LocalDateTime.now());

            List<SingleProductOrderEntity> singleProductOrderEntityList = new ArrayList<>();

            for (int i2=0;i2<cartEntity.getSubCartEntityList().size();i2++){
                SubCartEntity subCartEntity=cartEntity.getSubCartEntityList().get(i2);

                SingleProductOrderEntity singleProductOrderEntity=new SingleProductOrderEntity();
                singleProductOrderEntity.setProductId(subCartEntity.productEntity.getProductId());
                singleProductOrderEntity.setMarkedPrice(subCartEntity.productEntity.getMarkedPrice());
                singleProductOrderEntity.setSellingprice(subCartEntity.getProductEntity().getSellingprice());
                singleProductOrderEntityList.add(singleProductOrderEntity);
            }
            List<SingleProductOrderEntity> savedSingleProduct = singleProductOrderRepository.saveAll(singleProductOrderEntityList);

            System.out.println("singleProductOrderRepository " + singleProductOrderEntityList.size());
            System.out.println("getSubCartEntityList " + cartEntity.getSubCartEntityList().size());
            System.out.println("savedSingleProduct " + savedSingleProduct.size());

            productOrderEntity.setSingleProductOrderEntityList(savedSingleProduct);

            productOrderEntityList.add(productOrderEntity);

        }


        productOrderRepository.saveAll(productOrderEntityList);

        return new ApiResponse("Order Successfull",true);
    }


    @Override
    public ApiResponse acceptOrderByOutlet(Long productOrderId, Long outletId) {
        return null;
    }

    @Override
    public ApiResponse cancelOrderByOutlet(Long productOrderId, Long outletId) {
        return null;
    }

    @Override
    public ApiResponse cancelOrderUser(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse sendRequestToPicupBoyOrderByOutlet(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse sendRequestToPicupBoyOrderByPickup(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse acceptOrderByPickup(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse cancelRequestOrderByPickup(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse completeOrderByPickupBoy(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse completeOrderByOutlet(Long productOrderId) {
        return null;
    }

    @Override
    public ApiResponse notifyUserForDelivery(Long productOrderId) {
        return null;
    }

    @Override
    public ProductOrderResponse getAllProductOrderByUser() {
        return null;
    }

    @Override
    public ProductOrderResponse getAllProductOrderByOutletId(Long outletId, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OutletEntity outletEntity=outletRepository.findById(outletId).orElseThrow(() -> new ResourceNotFountException("Outlet", "id", outletId));
        if (outletEntity.getUserEntity().getUserId()!=userEntity.getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }
        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository.findAllByOutletName(outletEntity,pageable);
        List<ProductOrderEntity> productOrderEntityList=productOrderEntityPage.getContent();
        List<ProductOrderDto> productOrderDtoList=productOrderEntityList.stream().map(productOrderEntity ->
                modelMapper.map(productOrderEntity,ProductOrderDto.class)).collect(Collectors.toList());

        ProductOrderResponse productOrderResponse=new ProductOrderResponse();
        productOrderResponse.setContent(productOrderDtoList);
        productOrderResponse.setPageNumber(productOrderEntityPage.getNumber());
        productOrderResponse.setPageSize(productOrderEntityPage.getSize());
        productOrderResponse.setTotalElement(productOrderEntityPage.getTotalElements());
        productOrderResponse.setTotalPages(productOrderEntityPage.getTotalPages());
        productOrderResponse.setLastPage(productOrderEntityPage.isLast());

        return productOrderResponse;
    }


    private void sendNotification() {


    }
}
