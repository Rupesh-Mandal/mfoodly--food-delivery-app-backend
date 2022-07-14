package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.dto.AddressBookDto;
import com.soft_kali.mfoodly.entity.AddressBookEntity;
import com.soft_kali.mfoodly.entity.UserEntity;
import com.soft_kali.mfoodly.model.AddressBookResponse;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.repository.AddressBookRepository;
import com.soft_kali.mfoodly.service.AddressBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookRepository addressBookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse addNewAddress(AddressBookDto addressBookDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        AddressBookEntity addressBookEntity=modelMapper.map(addressBookDto,AddressBookEntity.class);
        addressBookEntity.setUserEntity(userEntity);
        addressBookRepository.save(addressBookEntity);

        return new ApiResponse("Address Successfully Added",true);
    }

    @Override
    public AddressBookResponse getAllAddressByUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity =  modelMapper.map(authentication.getPrincipal(),UserEntity.class);

        Page<AddressBookEntity> addressBookEntityPage=addressBookRepository.findAllByUserEntity(userEntity,pageable);
        List<AddressBookEntity> addressBookEntityList=addressBookEntityPage.getContent();

        List<AddressBookDto> addressBookDtoList=addressBookEntityList.stream().map(addressBookEntity ->
                modelMapper.map(addressBookEntity,AddressBookDto.class)).collect(Collectors.toList());

        AddressBookResponse addressBookResponse=new AddressBookResponse();
        addressBookResponse.setContent(addressBookDtoList);
        addressBookResponse.setPageNumber(addressBookEntityPage.getNumber());
        addressBookResponse.setPageSize(addressBookEntityPage.getSize());
        addressBookResponse.setTotalElement(addressBookEntityPage.getTotalElements());
        addressBookResponse.setTotalPages(addressBookEntityPage.getTotalPages());
        addressBookResponse.setLastPage(addressBookEntityPage.isLast());

        return addressBookResponse;
    }
}
