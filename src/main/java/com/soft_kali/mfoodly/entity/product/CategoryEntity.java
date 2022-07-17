package com.soft_kali.mfoodly.entity.product;

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
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    private String categoryTitle;
    private String categoryDescription;


}
