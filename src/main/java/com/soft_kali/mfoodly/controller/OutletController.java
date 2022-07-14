package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.OutletDto;
import com.soft_kali.mfoodly.model.OutletResponse;
import com.soft_kali.mfoodly.service.OutletService;
import com.soft_kali.mfoodly.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/outlet")
public class OutletController {

    @Autowired
    OutletService outletService;


    @PreAuthorize("hasRole('OUTLET')")
    @PostMapping("/cityId/{cityId}/create")
    public ResponseEntity<OutletDto> createNewOutlet(@RequestBody OutletDto outletDto, @PathVariable int cityId){
        OutletDto createdOutlet=outletService.creatNewOutlet(outletDto,cityId);
        return new ResponseEntity<>(createdOutlet, HttpStatus.CREATED);
    }



    @PreAuthorize("hasRole('OUTLET')")
    @GetMapping("/")
    public ResponseEntity<OutletResponse> getAllOutletByUser(
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam(value = "sortBy") Optional<String> sortBy,
            @RequestParam(value = "sortDir") Optional<Sort.Direction> sort){

        Pageable pageable = PageRequest.of(pageNumber.orElse(AppConstants.PAGE_NUMBBER),
                pageSize.orElse(AppConstants.PAGE_SIZE), sort.orElse(AppConstants.sort),
                sortBy.orElse(AppConstants.SORT_BY_FOR_POST));


        OutletResponse outletResponse=outletService.getAllOutletByUser(pageable);
        return new ResponseEntity<>(outletResponse, HttpStatus.CREATED);
    }


}
