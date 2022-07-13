package com.rupesh_mandal.blog_app_backend.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFountException extends RuntimeException {
    String resourceName;
    String fieldName;
    long fieldValu;

    String fielName;

    public ResourceNotFountException(String resourceName, String fieldName, long fieldValu) {
        super(resourceName+" not found with "+fieldName+" : "+fieldValu);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValu = fieldValu;
    }

    public ResourceNotFountException(String resourceName, String fieldName, String fielName) {
        super(resourceName+" not found with "+fieldName+" : "+fielName);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fielName = fielName;
    }
}
