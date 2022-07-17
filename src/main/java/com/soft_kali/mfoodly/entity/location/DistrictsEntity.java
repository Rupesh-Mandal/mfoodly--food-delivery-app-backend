package com.soft_kali.mfoodly.entity.location;

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
public class DistrictsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int districtsId;
    private String name;


    @ManyToOne
    @JoinColumn(name = "countrytId")
    private CountryEntity countryEntity;


}
