package com.soft_kali.mfoodly.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Role {

    @Id
    private Long roleId;
    private String name;


}
