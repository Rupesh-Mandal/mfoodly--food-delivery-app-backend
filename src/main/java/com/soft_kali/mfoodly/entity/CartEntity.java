package com.soft_kali.mfoodly.entity;

import com.soft_kali.mfoodly.entity.location.CityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = "outletId")
    private OutletEntity outletEntity;




    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "subCartId", referencedColumnName = "subCartId"))
    List<SubCartEntity> subCartEntityList = new ArrayList<>();


}
