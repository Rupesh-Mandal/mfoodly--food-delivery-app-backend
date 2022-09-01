package com.soft_kali.mfoodly.dto.user;

import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PickupBoyUserDetailsDto {

    private Long pickupBoyUserDetailsId;
    private UserDto pickupBoyUser;
    private OutletDto outletEntity;

}
