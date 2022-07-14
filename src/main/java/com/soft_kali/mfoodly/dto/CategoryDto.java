package com.soft_kali.mfoodly.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;

    @NotEmpty(message = "Category not be Empty !!")
    private String categoryTitle;

    @NotEmpty(message = "Category Description not be Empty !!")
    private String categoryDescription;
}
