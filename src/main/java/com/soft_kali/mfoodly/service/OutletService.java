package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.model.OutletResponse;
import org.springframework.data.domain.Pageable;

public interface OutletService {

    OutletDto creatNewOutlet(OutletDto outletDto,int cityId);

    OutletResponse getAllOutletByUser(Pageable pageable);

}
