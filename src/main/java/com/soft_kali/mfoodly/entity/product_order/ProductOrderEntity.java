package com.soft_kali.mfoodly.entity.product_order;

import com.soft_kali.mfoodly.entity.user.AddressBookEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.PickupBoyUserDetails;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ProductOrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOrderId;

    private LocalDateTime createdTime;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;

    @ManyToOne
    @JoinColumn(name = "outletId")
    private OutletEntity outletName;

    @ManyToOne
    @JoinColumn(name = "pickupBoyUserDetailsId")
    private PickupBoyUserDetails pickupBoyUserDetails;


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "delivery_address",
            joinColumns = @JoinColumn(name = "productOrderId", referencedColumnName = "productOrderId"),
            inverseJoinColumns = @JoinColumn(name = "addressBookId", referencedColumnName = "addressBookId"))
    private AddressBookEntity addressBookEntity;


    @OneToMany(mappedBy = "ProductOrderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SingleProductOrderEntity> singleProductOrderEntityList = new ArrayList<>();

}
