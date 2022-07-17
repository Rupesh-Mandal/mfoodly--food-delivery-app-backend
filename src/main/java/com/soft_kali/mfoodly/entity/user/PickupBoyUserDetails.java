package com.soft_kali.mfoodly.entity.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class PickupBoyUserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pickupBoyUserDetailsId;


    @ManyToOne
    @JoinColumn(name = "pickupBoyUserId", referencedColumnName = "userId")
    private UserEntity pickupBoyUser;


    @ManyToOne
    @JoinColumn(name = "outletId")
    private OutletEntity outletEntity;


}
