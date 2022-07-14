package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.AddressBookDto;
import com.soft_kali.mfoodly.model.AddressBookResponse;
import com.soft_kali.mfoodly.model.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface AddressBookService {
    ApiResponse addNewAddress(AddressBookDto addressBookDto);
    AddressBookResponse getAllAddressByUser(Pageable pageable);

}
