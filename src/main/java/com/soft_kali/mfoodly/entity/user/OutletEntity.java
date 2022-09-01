package com.soft_kali.mfoodly.entity.user;

import com.soft_kali.mfoodly.entity.location.CityEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;

    @Column(name = "outlet_name")
    private String outletName;
    private String outletDescription;
    private String outletSlogan;
    private String outletBanner;
    private String outletLogo;

    private String outletEmail;
    private String outletPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;



}
