package com.soft_kali.mfoodly.entity;

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
public class SubCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subCartId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

}
