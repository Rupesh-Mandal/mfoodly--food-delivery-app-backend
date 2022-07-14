package com.soft_kali.mfoodly.entity;

import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}
