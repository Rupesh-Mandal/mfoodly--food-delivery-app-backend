package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.admin.AdminDashboardData;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.dto.location.CountryDto;
import com.soft_kali.mfoodly.dto.location.DistrictsDto;
import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.location.CountryEntity;
import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.role.Role;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.exeptions.BadRequest;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.jwt.JwtTokenHelper;
import com.soft_kali.mfoodly.model.*;
import com.soft_kali.mfoodly.repository.*;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.repository.location.CountryRepository;
import com.soft_kali.mfoodly.repository.location.DistrictsRepository;
import com.soft_kali.mfoodly.service.AdminService;
import com.soft_kali.mfoodly.service.FileService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.soft_kali.mfoodly.utils.AppConstants.admin;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    FileService fileService;

    @Autowired
    ProductRepositoy productRepositoy;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    DistrictsRepository districtsRepository;

    @Autowired
    CityRepository cityRepository;

    @Value("${project.image.categories}")
    private String categoryPath;
    @Override
    public ApiResponse login(JwtAuthRequest request) throws Exception {
        if (!request.getPhoneNumber().trim().equals(admin)) {
            throw new BadRequest("You are not admin");
        }
        String token;
        try {
            authenticate(request.getPhoneNumber(), request.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getPhoneNumber());
            token = jwtTokenHelper.generateToken(userDetails);

        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }

        return new ApiResponse(token, true);

    }

    @Override
    public AdminDashboardData getDashboardData() {
        Role ROLE_CUSTOMER = roleRepository.findById(AppConstants.ROLE_CUSTOMER).get();
        Role ROLE_OUTLET = roleRepository.findById(AppConstants.ROLE_SELLER).get();

        AdminDashboardData adminDashboardData = new AdminDashboardData();
        long totalCustomer = userRepository.countAllByRoles(ROLE_CUSTOMER);
        long totalSeller = userRepository.countAllByRoles(ROLE_OUTLET);
        long totalOutlet = outletRepository.count();
        long totalCategories = categoryRepository.count();

        Pageable pageable = PageRequest.of(AppConstants.PAGE_NUMBBER,
               AppConstants.PAGE_SIZE, AppConstants.sort,
                AppConstants.SORT_BY_FOR_PRODUCT_ORDER);

        Pageable pageableForUser = PageRequest.of(AppConstants.PAGE_NUMBBER,
               AppConstants.PAGE_SIZE, AppConstants.sort,
                AppConstants.SORT_BY_FOR_USER);

        List<ProductOrderEntity> productOrderEntityList = productOrderRepository.findAll(pageable).getContent();
        List<UserEntity> customerList = userRepository.findAllByRoles(ROLE_CUSTOMER,pageableForUser).getContent();
        List<UserEntity> sellerList = userRepository.findAllByRoles(ROLE_OUTLET,pageableForUser).getContent();

        List<ProductOrderDto> productOrderDtoList=productOrderEntityList.stream().map(productOrderEntity -> modelMapper.map(productOrderEntity,ProductOrderDto.class)).collect(Collectors.toList());
        List<UserDto> customerListDto=customerList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());
        List<UserDto> sellerListDto=sellerList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());

        adminDashboardData.setTotalCustomer(totalCustomer);
        adminDashboardData.setTotalSeller(totalSeller);
        adminDashboardData.setTotalOutlet(totalOutlet);
        adminDashboardData.setTotalCategories(totalCategories);


        adminDashboardData.setProductOrderEntityList(productOrderDtoList);
        adminDashboardData.setCustomerList(customerListDto);
        adminDashboardData.setSellerList(sellerListDto);

        return adminDashboardData;
    }

    @Override
    public CategoriesResponse getAllProductCategories(Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage=categoryRepository.findAll(pageable);
        List<CategoryEntity> categoryEntityList=categoryEntityPage.getContent();

        List<CategoryDto> categoryDtoList=categoryEntityList.stream().map(categoryEntity ->
                modelMapper.map(categoryEntity,CategoryDto.class)).collect(Collectors.toList());


        CategoriesResponse categoriesResponse=new CategoriesResponse();
        categoriesResponse.setContent(categoryDtoList);
        categoriesResponse.setPageNumber(categoryEntityPage.getNumber());
        categoriesResponse.setPageSize(categoryEntityPage.getSize());
        categoriesResponse.setTotalElement(categoryEntityPage.getTotalElements());
        categoriesResponse.setTotalPages(categoryEntityPage.getTotalPages());
        categoriesResponse.setLastPage(categoryEntityPage.isLast());
        return categoriesResponse;
    }

    @Override
    public CategoryDto upLoadCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity=modelMapper.map(categoryDto,CategoryEntity.class);
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);

        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public ApiResponse deleteCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFountException("Categpry", "id", categoryId));
        categoryRepository.delete(categoryEntity);
//        new FileController().deleteCategoryImage(categoryId);
        try {
            fileService.deleteImage(categoryPath, String.valueOf(categoryId));
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ApiResponse("Category Successfully Deleted",true);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFountException("Categpry", "id", categoryId));
        categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);

        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoriesResponse getSearchProductCategories(String search_query, Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage=categoryRepository.findAll(search_query,pageable);
        List<CategoryEntity> categoryEntityList=categoryEntityPage.getContent();

        List<CategoryDto> categoryDtoList=categoryEntityList.stream().map(categoryEntity ->
                modelMapper.map(categoryEntity,CategoryDto.class)).collect(Collectors.toList());


        CategoriesResponse categoriesResponse=new CategoriesResponse();
        categoriesResponse.setContent(categoryDtoList);
        categoriesResponse.setPageNumber(categoryEntityPage.getNumber());
        categoriesResponse.setPageSize(categoryEntityPage.getSize());
        categoriesResponse.setTotalElement(categoryEntityPage.getTotalElements());
        categoriesResponse.setTotalPages(categoryEntityPage.getTotalPages());
        categoriesResponse.setLastPage(categoryEntityPage.isLast());
        return categoriesResponse;
    }

    @Override
    public UserResponse getCustomer(Pageable pageable) {
        Role ROLE_CUSTOMER = roleRepository.findById(AppConstants.ROLE_CUSTOMER).get();

        Page<UserEntity> page=userRepository.findAllByRoles(ROLE_CUSTOMER,pageable);
        List<UserEntity> userEntityList=page.getContent();
        List<UserDto> customerListDto=userEntityList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse=new UserResponse();

        userResponse.setContent(customerListDto);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElement(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());

        return userResponse;
    }

    @Override
    public UserResponse getSeller(Pageable pageable) {
        Role ROLE_OUTLET = roleRepository.findById(AppConstants.ROLE_SELLER).get();

        Page<UserEntity> page=userRepository.findAllByRoles(ROLE_OUTLET,pageable);
        List<UserEntity> userEntityList=page.getContent();
        List<UserDto> customerListDto=userEntityList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse=new UserResponse();

        userResponse.setContent(customerListDto);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElement(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());

        return userResponse;
    }

    @Override
    public ProductOrderResponse getProductOrders(Pageable pageable) {
        Page<ProductOrderEntity> page = productOrderRepository.findAll(pageable);
        List<ProductOrderEntity> productOrderEntityList=page.getContent();

        List<ProductOrderDto> productOrderDtoList=productOrderEntityList.stream().map(productOrderEntity -> modelMapper.map(productOrderEntity,ProductOrderDto.class)).collect(Collectors.toList());

        ProductOrderResponse productOrderResponse=new ProductOrderResponse();

        productOrderResponse.setContent(productOrderDtoList);
        productOrderResponse.setPageNumber(page.getNumber());
        productOrderResponse.setPageSize(page.getSize());
        productOrderResponse.setTotalElement(page.getTotalElements());
        productOrderResponse.setTotalPages(page.getTotalPages());
        productOrderResponse.setLastPage(page.isLast());
        return productOrderResponse;
    }

    @Override
    public UserResponse getSearchCutomer(String search_query, Pageable pageable) {
        Role ROLE_CUSTOMER = roleRepository.findById(AppConstants.ROLE_CUSTOMER).get();

        Page<UserEntity> page=userRepository.findAllByNameContainingAndRoles(search_query,ROLE_CUSTOMER,pageable);
        List<UserEntity> userEntityList=page.getContent();
        List<UserDto> customerListDto=userEntityList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse=new UserResponse();

        userResponse.setContent(customerListDto);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElement(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());

        return userResponse;    }

    @Override
    public UserResponse getSearchseller(String search_query, Pageable pageable) {
        Role ROLE_OUTLET = roleRepository.findById(AppConstants.ROLE_SELLER).get();

        Page<UserEntity> page=userRepository.findAllByNameContainingAndRoles(search_query,ROLE_OUTLET,pageable);
        List<UserEntity> userEntityList=page.getContent();
        List<UserDto> customerListDto=userEntityList.stream().map(userEntity -> modelMapper.map(userEntity,UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse=new UserResponse();

        userResponse.setContent(customerListDto);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElement(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());

        return userResponse;
    }

    @Override
    public ProductOrderResponse getSearchProductOrders(String search_query, Pageable pageable) {
        Page<ProductOrderEntity> page = productOrderRepository.findAll(search_query,pageable);
        List<ProductOrderEntity> productOrderEntityList=page.getContent();

        List<ProductOrderDto> productOrderDtoList=productOrderEntityList.stream().map(productOrderEntity -> modelMapper.map(productOrderEntity,ProductOrderDto.class)).collect(Collectors.toList());

        ProductOrderResponse productOrderResponse=new ProductOrderResponse();

        productOrderResponse.setContent(productOrderDtoList);
        productOrderResponse.setPageNumber(page.getNumber());
        productOrderResponse.setPageSize(page.getSize());
        productOrderResponse.setTotalElement(page.getTotalElements());
        productOrderResponse.setTotalPages(page.getTotalPages());
        productOrderResponse.setLastPage(page.isLast());
        return productOrderResponse;
    }

    @Override
    public OutletResponse getSearchOutlet(String search_query, Pageable pageable) {
        Page<OutletEntity> outletEntityPage=outletRepository.findAll(search_query,pageable);
        List<OutletEntity> outletEntityList=outletEntityPage.getContent();

        List<OutletDto> outletDtoList=outletEntityList.stream().map(outletEntity -> modelMapper.map(outletEntity,OutletDto.class)).collect(Collectors.toList());

        OutletResponse outletResponse=new OutletResponse();
        outletResponse.setContent(outletDtoList);
        outletResponse.setPageNumber(outletEntityPage.getNumber());
        outletResponse.setPageSize(outletEntityPage.getSize());
        outletResponse.setTotalElement(outletEntityPage.getTotalElements());
        outletResponse.setTotalPages(outletEntityPage.getTotalPages());
        outletResponse.setLastPage(outletEntityPage.isLast());

        return outletResponse;
    }

    @Override
    public OutletResponse getOutlet(Pageable pageable) {
        Page<OutletEntity> outletEntityPage=outletRepository.findAll(pageable);
        List<OutletEntity> outletEntityList=outletEntityPage.getContent();

        List<OutletDto> outletDtoList=outletEntityList.stream().map(outletEntity -> modelMapper.map(outletEntity,OutletDto.class)).collect(Collectors.toList());

        OutletResponse outletResponse=new OutletResponse();
        outletResponse.setContent(outletDtoList);
        outletResponse.setPageNumber(outletEntityPage.getNumber());
        outletResponse.setPageSize(outletEntityPage.getSize());
        outletResponse.setTotalElement(outletEntityPage.getTotalElements());
        outletResponse.setTotalPages(outletEntityPage.getTotalPages());
        outletResponse.setLastPage(outletEntityPage.isLast());

        return outletResponse;
    }

    @Override
    public ProductResponse getSearchProduct(String search_query, Pageable pageable) {
        Page<ProductEntity> productEntityPage=productRepositoy.findAll(search_query,pageable);
        List<ProductEntity> productEntityList=productEntityPage.getContent();

        List<ProductDto> productDtoList=productEntityList.stream().map(productEntity ->
                modelMapper.map(productEntity,ProductDto.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDtoList);
        productResponse.setPageNumber(productEntityPage.getNumber());
        productResponse.setPageSize(productEntityPage.getSize());
        productResponse.setTotalElement(productEntityPage.getTotalElements());
        productResponse.setTotalPages(productEntityPage.getTotalPages());
        productResponse.setLastPage(productEntityPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProduct(Pageable pageable) {
        Page<ProductEntity> productEntityPage=productRepositoy.findAll(pageable);
        List<ProductEntity> productEntityList=productEntityPage.getContent();

        List<ProductDto> productDtoList=productEntityList.stream().map(productEntity ->
                modelMapper.map(productEntity,ProductDto.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDtoList);
        productResponse.setPageNumber(productEntityPage.getNumber());
        productResponse.setPageSize(productEntityPage.getSize());
        productResponse.setTotalElement(productEntityPage.getTotalElements());
        productResponse.setTotalPages(productEntityPage.getTotalPages());
        productResponse.setLastPage(productEntityPage.isLast());

        return productResponse;
    }

    @Override
    public List<CountryDto> getAllCountry() {
        List<CountryEntity> countryEntities=countryRepository.findAll();
        List<CountryDto> countryDtoList=countryEntities.stream().map(countryEntity ->
                modelMapper.map(countryEntity,CountryDto.class)).collect(Collectors.toList());
        return countryDtoList;
    }

    @Override
    public List<DistrictsDto> getAllDistrictByCountry(int countrytId) {
        CountryEntity countryEntity=countryRepository.findById(countrytId).get();
        List<DistrictsEntity> districtsEntityList=districtsRepository.findAllByCountryEntity(countryEntity);

        List<DistrictsDto> districtsDtoList=districtsEntityList.stream().map(districtsEntity ->
                modelMapper.map(districtsEntity,DistrictsDto.class)).collect(Collectors.toList());

        return districtsDtoList;
    }

    @Override
    public List<CityDto> getAllCityByDistrict(int districtsId) {
        DistrictsEntity districtsEntity=districtsRepository.findById(districtsId).get();
        List<CityEntity> cityEntityList=cityRepository.findAllByDistrictsEntity(districtsEntity);

        List<CityDto> cityDtoList=cityEntityList.stream().map(cityEntity ->
                modelMapper.map(cityEntity,CityDto.class)).collect(Collectors.toList());

        return cityDtoList;
    }

    @Override
    public DistrictsDto addDistrict(int countrytId, DistrictsDto districtsDto) {
        CountryEntity countryEntity=countryRepository.findById(countrytId).orElseThrow(() ->
                new ResourceNotFountException("Country", "id", countrytId));

        DistrictsEntity districtsEntity=modelMapper.map(districtsDto,DistrictsEntity.class);
        districtsEntity.setCountryEntity(countryEntity);

        DistrictsDto newDistrictDto=modelMapper.map((districtsRepository.save(districtsEntity)),DistrictsDto.class);

        return newDistrictDto;
    }

    @Override
    public CityDto addCity(int districtsId, CityDto cityDto) {
        DistrictsEntity districtsEntity=districtsRepository.findById(districtsId).orElseThrow(()->
                new ResourceNotFountException("District", "id", districtsId));

        CityEntity cityEntity=modelMapper.map(cityDto,CityEntity.class);
        cityEntity.setDistrictsEntity(districtsEntity);

        CityDto newCityDto=modelMapper.map((cityRepository.save(cityEntity)),CityDto.class);

        return newCityDto;
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
