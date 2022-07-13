package com.rupesh_mandal.blog_app_backend.payloads;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;

    @NotEmpty(message = "Category not be Empty !!")
    private String categoryTitle;

    @NotEmpty(message = "Category Description not be Empty !!")
    private String categoryDescription;
}
