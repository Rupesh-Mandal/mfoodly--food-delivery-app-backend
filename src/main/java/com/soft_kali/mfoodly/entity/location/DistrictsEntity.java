package com.soft_kali.mfoodly.entity.location;

import com.soft_kali.mfoodly.entity.ProductEntity;
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
public class Districts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtsId;
    private String name;


    @OneToMany(mappedBy = "districts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<City> cityList=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "countrytId")
    private Country country;
}
