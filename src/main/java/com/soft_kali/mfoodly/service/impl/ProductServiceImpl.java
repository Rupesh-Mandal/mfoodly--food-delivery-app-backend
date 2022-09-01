package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.product.CategoryDto;
import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.exeptions.UnauthorizeRequest;
import com.soft_kali.mfoodly.model.ProductResponse;
import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.repository.CategoryRepository;
import com.soft_kali.mfoodly.repository.OutletRepository;
import com.soft_kali.mfoodly.repository.ProductRepositoy;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.service.ProductService;
import com.soft_kali.mfoodly.utils.AppConstants;
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
public class ProductServiceImpl implements ProductService {


    @Autowired
    OutletRepository outletRepository;

    @Autowired
    ProductRepositoy productRepositoy;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductResponse getAllProductByUserDefaultLocation(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        CityEntity cityEntity=userEntity.getCityEntity();

        Page<ProductEntity> productEntityPage=productRepositoy.findAllByCityEntity(cityEntity,pageable);
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
    public ProductResponse getAllProductByCity(int cityId,Pageable pageable) {

        CityEntity cityEntity=cityRepository.findById(cityId).orElseThrow(()-> new ResourceNotFountException("City","id",cityId));

        Page<ProductEntity> productEntityPage=productRepositoy.findAllByCityEntity(cityEntity,pageable);
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
    public ProductResponse getAllProductByOutlet(Pageable pageable,Long outletId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        OutletEntity outletEntity=outletRepository.findById(outletId).orElseThrow(()-> new ResourceNotFountException("Outlet","id",outletId));

        if (outletEntity.getUserEntity().getUserId()!=userEntity.getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }

        Page<ProductEntity> productEntityPage=productRepositoy.findAllByOutletName(outletEntity,pageable);
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
    public ProductResponse getAllProductByCagegory(Long categoryId,Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);
        CityEntity cityEntity=userEntity.getCityEntity();

        CategoryEntity categoryEntity=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFountException("Categpry","id",categoryId));

        Page<ProductEntity> productEntityPage=productRepositoy.findAllByCityEntityAndCategoryEntitiesIsLike(cityEntity,categoryEntity,pageable);
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
    public ProductDto addProduct(ProductDto productDto,Long outletId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        OutletEntity outletEntity=outletRepository.findById(outletId).orElseThrow(()-> new ResourceNotFountException("Outlet","id",outletId));

        if (userEntity.getUserId()!=outletEntity.getUserEntity().getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }

        ProductEntity productEntity=modelMapper.map(productDto,ProductEntity.class);
        productEntity.setCreatedTime(LocalDateTime.now());
        productEntity.setCityEntity(outletEntity.getCityEntity());
        productEntity.setOutletName(outletEntity);
        productEntity.setUserEntity(outletEntity.getUserEntity());

        List<CategoryEntity> categoryEntityList=new ArrayList<>();
        List<CategoryDto> categoryDtoList=productDto.getCategoryEntities();
        for (int i=0; i<categoryDtoList.size();i++){
             Long categoryId=categoryDtoList.get(i).getCategoryId();
            CategoryEntity categoryEntity=categoryRepository.findById(categoryId).orElseThrow(
                    ()-> new ResourceNotFountException("Category","id",categoryId));

            categoryEntityList.add(categoryEntity);
        }

        productEntity.setCategoryEntities(categoryEntityList);
        ProductEntity createdProduct=productRepositoy.save(productEntity);
        createdProduct.setProductImageLink(createdProduct.getProductId()+AppConstants._product_image);

        ProductEntity finalProduct=productRepositoy.save(createdProduct);

        return modelMapper.map(finalProduct,ProductDto.class);
    }

    @Override
    public ProductDto UpdateProduct(ProductDto productDto, Long productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        ProductEntity productEntity=productRepositoy.findById(productId).orElseThrow(()-> new ResourceNotFountException("Product","id",productId));
        OutletEntity outletEntity=productEntity.getOutletName();

        if (userEntity.getUserId()!=outletEntity.getUserEntity().getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }

        productEntity.setProductName(productDto.getProductName());
        productEntity.setProductDescription(productDto.getProductDescription());
        productEntity.setSellingprice(productDto.getSellingprice());
        productEntity.setMarkedPrice(productDto.getMarkedPrice());

        ProductEntity createdProduct=productRepositoy.save(productEntity);


        return modelMapper.map(createdProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        ProductEntity productEntity=productRepositoy.findById(productId).orElseThrow(()-> new ResourceNotFountException("Product","id",productId));
        OutletEntity outletEntity=productEntity.getOutletName();

        if (userEntity.getUserId()!=outletEntity.getUserEntity().getUserId()){
            throw  new UnauthorizeRequest("Invalid Request");
        }

        productRepositoy.delete(productEntity);

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryEntity> categoryEntityList=categoryRepository.findAll();

        List<CategoryDto> categoryDtoList=categoryEntityList.stream().map(categoryEntity ->
                modelMapper.map(categoryEntity,CategoryDto.class)).collect(Collectors.toList());

        return categoryDtoList;
    }
}
