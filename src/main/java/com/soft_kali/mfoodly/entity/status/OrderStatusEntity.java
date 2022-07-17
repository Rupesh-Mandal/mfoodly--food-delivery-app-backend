package com.soft_kali.mfoodly.entity.status;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderStatusId;

    String name;
    String statusColorCode;


}
