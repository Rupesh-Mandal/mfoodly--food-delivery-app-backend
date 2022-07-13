package com.soft_kali.mfoodly.entity.location;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String name;
    @ManyToOne
    @JoinColumn(name = "districtsId")
    private Districts districts;
}
