package com.soft_kali.mfoodly.entity.cart;

import com.soft_kali.mfoodly.entity.product.ProductEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
public class SubCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long subCartId;

    public int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    public ProductEntity productEntity;

}
