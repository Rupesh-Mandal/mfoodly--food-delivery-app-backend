package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.model.OutletResponse;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.repository.OutletRepository;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.service.OutletService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutletServiceImpl implements OutletService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    CityRepository cityRepository;
    @Override
    public OutletDto creatNewOutlet(OutletDto outletDto,int cityId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);
        CityEntity cityEntity=cityRepository.findById(cityId).orElseThrow(()-> new ResourceNotFountException("City","id",cityId));

        OutletEntity outletEntity=modelMapper.map(outletDto,OutletEntity.class);
        outletEntity.setUserEntity(userEntity);
        outletEntity.setCityEntity(cityEntity);

        OutletEntity createdOutletEntity=outletRepository.save(outletEntity);
        createdOutletEntity.setOutletBanner(createdOutletEntity.getOutletId()+ AppConstants._outlet_banner);
        createdOutletEntity.setOutletLogo(createdOutletEntity.getOutletId()+ AppConstants._outlet_logo);

        OutletEntity finalOutlet=outletRepository.save(createdOutletEntity);

        OutletDto createdOutletDto=modelMapper.map(finalOutlet,OutletDto.class);


        return createdOutletDto;
    }

    @Override
    public OutletResponse getAllOutletByUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        Page<OutletEntity> outletEntityPage=outletRepository.findAllByUserEntity(userEntity,pageable);
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
}
