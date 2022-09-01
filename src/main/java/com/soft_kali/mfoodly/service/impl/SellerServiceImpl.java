package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.dto.seller.SellerDashboardData;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.role.Role;
import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.exeptions.BadRequest;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.jwt.JwtTokenHelper;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.JwtAuthRequest;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import com.soft_kali.mfoodly.repository.*;
import com.soft_kali.mfoodly.service.NotificationService;
import com.soft_kali.mfoodly.service.SellerService;
import com.soft_kali.mfoodly.utils.AppConstants;
import com.soft_kali.mfoodly.utils.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.soft_kali.mfoodly.utils.AppConstants.ROLE_SELLER;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    ProductRepositoy productRepositoy;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    JwtTokenHelper jwtTokenHelper;


    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse login(JwtAuthRequest request) {
        String token;
        Role role=roleRepository.findById(ROLE_SELLER).get();
        try {
            authenticate(request.getPhoneNumber(), request.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getPhoneNumber());
            token = jwtTokenHelper.generateToken(userDetails);

            UserEntity userEntity=userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(()->
                    new ResourceNotFountException("user", "phone number", userDetails.getUsername()));

            if (!userEntity.getRoles().contains(role)){
                throw new BadRequest("You are not Seller");
            }

        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }

        return new ApiResponse(token, true);
    }

    @Override
    public UserDto getUSerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);
        UserDto userDto=modelMapper.map(userEntity,UserDto.class);
        return userDto;
    }

    @Override
    public SellerDashboardData getDashboardData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OrderStatus status=new OrderStatus();

        OrderStatusEntity STATUS_PENDING_TO_ACCEPT=orderStatusRepository.findById(status.getSTATUS_PENDING_TO_ACCEPT()).get();
        OrderStatusEntity STATUS_WATING_TO_ORDER_READY=orderStatusRepository.findById(status.getSTATUS_WATING_TO_ORDER_READY()).get();


        Pageable pageable = PageRequest.of(AppConstants.PAGE_NUMBBER,
                AppConstants.PAGE_SIZE, AppConstants.sort,
                AppConstants.SORT_BY_FOR_PRODUCT_ORDER);

        Page<ProductOrderEntity> pendingProductOrderEntityPage=productOrderRepository
                .findAllBySellerAndOrderStatusEntity(userEntity,STATUS_PENDING_TO_ACCEPT,pageable);

        List<ProductOrderEntity> pendingProductOrderEntityList=pendingProductOrderEntityPage.getContent();
        List<ProductOrderDto> productOrderDtoList=pendingProductOrderEntityList.stream().map(productOrderEntity -> modelMapper.map(productOrderEntity,ProductOrderDto.class)).collect(Collectors.toList());



        SellerDashboardData sellerDashboardData=new SellerDashboardData();
        sellerDashboardData.setProductOrderDtoList(productOrderDtoList);
        sellerDashboardData.setTotalPendingOrder(pendingProductOrderEntityPage.getTotalElements());
        sellerDashboardData.setTotalStartedOrder(productOrderRepository.countBySellerAndOrderStatusEntity(userEntity,
                STATUS_WATING_TO_ORDER_READY));
        sellerDashboardData.setTotalProduct(productRepositoy.countByUserEntity(userEntity));
        sellerDashboardData.setTotalOutlet(outletRepository.countByUserEntity(userEntity));

        return sellerDashboardData;
    }

    @Override
    public ApiResponse cancelOrder(Long productOrderId) {
        ProductOrderEntity productOrderEntity=productOrderRepository.findById(productOrderId).orElseThrow(() -> new ResourceNotFountException("productOrderEntity", "id", productOrderId));
        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_ORDER_CANCELLED_BY_SELLER()).get();
        productOrderEntity.setOrderStatusEntity(orderStatusEntity);

        productOrderRepository.save(productOrderEntity);
        notificationService.orderCancelledBySeller(productOrderEntity);

        return new ApiResponse("Order Successfully Cancelled",true);
    }

    @Override
    public ApiResponse acceptOrder(Long productOrderId) {
        ProductOrderEntity productOrderEntity=productOrderRepository.findById(productOrderId).orElseThrow(() -> new ResourceNotFountException("productOrderEntity", "id", productOrderId));
        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_WATING_TO_ORDER_READY()).get();
        productOrderEntity.setOrderStatusEntity(orderStatusEntity);

        productOrderRepository.save(productOrderEntity);
        notificationService.orderAcceptedBySeller(productOrderEntity);

        return new ApiResponse("Order Successfully Accepted",true);
    }

    @Override
    public ProductOrderResponse getAllPendingOrder(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_PENDING_TO_ACCEPT()).get();

        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository.findAllBySellerAndOrderStatusEntity(userEntity,orderStatusEntity,pageable);
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

    @Override
    public ProductOrderResponse getAllStartedOrder(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_WATING_TO_ORDER_READY()).get();
        OrderStatusEntity orderStatusEntity2=orderStatusRepository.findById(new OrderStatus().getSTATUS_WATING_ACCEPT_BY_PICKUPBOY()).get();
        OrderStatusEntity orderStatusEntity3=orderStatusRepository.findById(new OrderStatus().getSTATUS_ORDER_STARTED_TO_DELIVERED()).get();

        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository
                .findAllBySellerAndOrderStatusEntityAndOrderStatusEntityAndOrderStatusEntity(userEntity,
                        orderStatusEntity,orderStatusEntity2,orderStatusEntity3,pageable);
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

    @Override
    public ProductOrderResponse getAllCancelledOrder(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_ORDER_CANCELLED_BY_SELLER()).get();
        OrderStatusEntity orderStatusEntity2=orderStatusRepository.findById(new OrderStatus().getSTATUS_ORDER_CANCELLED_BY_USER()).get();

        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository
                .findAllBySellerAndOrderStatusEntityAndOrderStatusEntity(userEntity,orderStatusEntity,orderStatusEntity2,pageable);
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

    @Override
    public ProductOrderResponse getAllFailedOrder(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        OrderStatusEntity orderStatusEntity=orderStatusRepository.findById(new OrderStatus().getSTATUS_ORDER_FAILD_TO_DELIVERED()).get();

        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository.findAllBySellerAndOrderStatusEntity(userEntity,orderStatusEntity,pageable);
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

    @Override
    public ProductOrderResponse getAllOrderHistory(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = modelMapper.map(authentication.getPrincipal(), UserEntity.class);

        Page<ProductOrderEntity> productOrderEntityPage=productOrderRepository.findAllBySeller(userEntity, pageable);
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


    private void authenticate(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new Exception("Invalid username or password");
        }


    }
}
