package com.soft_kali.mfoodly.entity.cart;

import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long cartId;

    @ManyToOne
    @JoinColumn(name = "userId")
    public UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = "outletId")
    public OutletEntity outletEntity;




    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "subCartId", referencedColumnName = "subCartId"))
    public List<SubCartEntity> subCartEntityList = new ArrayList<>();


}
