package com.rupesh_mandal.blog_app_backend.entity;

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
    private int roleId;
    private String name;


}
