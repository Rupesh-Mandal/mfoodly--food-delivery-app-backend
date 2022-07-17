package com.soft_kali.mfoodly.entity.user;

import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class AddressBookEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressBookId;

    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddressPhoneNumber;


    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;


    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}
