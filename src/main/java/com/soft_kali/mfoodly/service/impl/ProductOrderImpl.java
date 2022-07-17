package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.dto.product_order.SingleProductOrderDto;
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
import com.soft_kali.mfoodly.repository.AddressBookRepository;
import com.soft_kali.mfoodly.repository.OutletRepository;
import com.soft_kali.mfoodly.repository.ProductOrderRepository;
import com.soft_kali.mfoodly.repository.ProductRepositoy;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.service.ProductOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    public ApiResponse startNewOrder(ProductOrderDto productOrderDto, int cityId, Long addressBookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        AddressBookEntity addressBookEntity = addressBookRepository.findById(addressBookId).orElseThrow(() -> new ResourceNotFountException("Address Book", "id", addressBookId));
        CityEntity cityEntity = cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFountException("City", "id", cityId));

        List<SingleProductOrderDto> singleProductOrderDtoList = productOrderDto.getSingleProductOrderEntityList();
        if (singleProductOrderDtoList.size() == 0) {
            throw new ResourceNotFountException("Product is empty", "null", 0);
        }
        List<ProductOrderEntity> productOrderEntityList = new ArrayList<>();

        for (int i = 0; i < singleProductOrderDtoList.size(); i++) {
            SingleProductOrderDto singleProductOrderDto = singleProductOrderDtoList.get(i);
            ProductEntity productEntity = productRepositoy.findById(singleProductOrderDto.getProductId()).orElseThrow(() -> new ResourceNotFountException("Product", "id", singleProductOrderDto.getProductId()));
            OutletEntity outletEntity = productEntity.getOutletName();

            singleProductOrderDto.setProductId(productEntity.getProductId());
            singleProductOrderDto.setProductImageLink(productEntity.getProductImageLink());
            singleProductOrderDto.setMarkedPrice(productEntity.getMarkedPrice());
            singleProductOrderDto.setSellingprice(productEntity.getSellingprice());

            for (int i2 = 0; i2 < productOrderEntityList.size(); i2++) {
                OutletEntity outletEntity2 = productOrderEntityList.get(i2).getOutletName();
                if (outletEntity.getOutletId() == outletEntity2.getOutletId()) {
                    ProductOrderEntity productOrderEntity = productOrderEntityList.get(i2);

                    productOrderEntity.setUserEntity(userEntity);
                    productOrderEntity.setCityEntity(cityEntity);
                    productOrderEntity.setAddressBookEntity(addressBookEntity);
                    productOrderEntity.setOutletName(outletEntity2);

                    SingleProductOrderEntity singleProductOrderEntity = modelMapper.map(singleProductOrderDto, SingleProductOrderEntity.class);
                    List<SingleProductOrderEntity> singleProductOrderEntityList = productOrderEntity.getSingleProductOrderEntityList();
                    singleProductOrderEntityList.add(singleProductOrderEntity);

                    productOrderEntity.setSingleProductOrderEntityList(singleProductOrderEntityList);
                    productOrderEntityList.add(i2, productOrderEntity);
                } else {
                    ProductOrderEntity productOrderEntity = productOrderEntityList.get(i2);

                    productOrderEntity.setUserEntity(userEntity);
                    productOrderEntity.setCityEntity(cityEntity);
                    productOrderEntity.setAddressBookEntity(addressBookEntity);
                    productOrderEntity.setOutletName(outletEntity2);

                    SingleProductOrderEntity singleProductOrderEntity = modelMapper.map(singleProductOrderDto, SingleProductOrderEntity.class);
                    List<SingleProductOrderEntity> singleProductOrderEntityList = new ArrayList<>();
                    singleProductOrderEntityList.add(singleProductOrderEntity);

                    productOrderEntity.setSingleProductOrderEntityList(singleProductOrderEntityList);
                    productOrderEntityList.add(productOrderEntity);
                    sendNotification();
                }
            }
            if (productOrderEntityList.size() == 0) {
                ProductOrderEntity productOrderEntity = new ProductOrderEntity();

                OutletEntity outletEntity2 = productEntity.getOutletName();

                productOrderEntity.setUserEntity(userEntity);
                productOrderEntity.setCityEntity(cityEntity);
                productOrderEntity.setAddressBookEntity(addressBookEntity);
                productOrderEntity.setOutletName(outletEntity2);

                SingleProductOrderEntity singleProductOrderEntity = modelMapper.map(singleProductOrderDto, SingleProductOrderEntity.class);
                List<SingleProductOrderEntity> singleProductOrderEntityList = new ArrayList<>();
                singleProductOrderEntityList.add(singleProductOrderEntity);

                productOrderEntity.setSingleProductOrderEntityList(singleProductOrderEntityList);
                productOrderEntityList.add(productOrderEntity);
                sendNotification();
            }

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
