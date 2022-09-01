package com.soft_kali.mfoodly.entity.product_order;

import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
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
    @JoinColumn(name = "sellerId")
    private UserEntity seller;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;

    @ManyToOne
    @JoinColumn(name = "outletId")
    private OutletEntity outletName;

    @ManyToOne
    @JoinColumn(name = "pickupBoyUserDetailsId")
    private PickupBoyUserDetails pickupBoyUserDetails;


    @ManyToOne
    @JoinColumn(name = "orderStatusId")
    private OrderStatusEntity orderStatusEntity;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "delivery_address",
            joinColumns = @JoinColumn(name = "productOrderId", referencedColumnName = "productOrderId"),
            inverseJoinColumns = @JoinColumn(name = "addressBookId", referencedColumnName = "addressBookId"))
    private AddressBookEntity addressBookEntity;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "productOrderId", referencedColumnName = "productOrderId"),
            inverseJoinColumns = @JoinColumn(name = "singleProductOrderId", referencedColumnName = "singleProductOrderId"))
    private List<SingleProductOrderEntity> singleProductOrderEntityList = new ArrayList<>();

}
