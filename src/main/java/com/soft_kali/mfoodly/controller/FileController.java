package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.repository.CategoryRepository;
import com.soft_kali.mfoodly.service.FileService;
import com.soft_kali.mfoodly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${project.image.categories}")
    private String categoryPath;

//    @Value("${project.image}")
//    private String path;

//    @PostMapping("/post/image/upload/{postId}")
//    public ResponseEntity<ProductDto> uploadPostImage(@PathVariable Long productId, @RequestParam("image") MultipartFile file) throws IOException {
//        ProductDto productDto=productService.getProductById(productId);
//
//        String fileName1="post_"+productId+"_user_"+productDto.getOutletEntity().getOutletId();
//
//        String fileName=fileService.uploadImage(path,file,fileName1);
//
//        productDto.setProductImageLink(fileName);
//        ProductDto updatedPostDto=productService.updateProduct(productDto,productId);
//
//        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
//        return null;
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/categories/upload/{categoryId}")
    public ResponseEntity<ApiResponse> uploadPostImage(@PathVariable("categoryId") Long categoryId, @RequestParam("image") MultipartFile files) throws IOException {


        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFountException("Categpry", "id", categoryId));

        String fileName1 = String.valueOf(categoryEntity.getCategoryId());
        fileService.uploadImage(categoryPath, files, fileName1);


        return new ResponseEntity<>(new ApiResponse(fileName1,true), HttpStatus.OK);
    }

    public void deleteCategoryImage(Long categoryId){
        fileService.deleteImage(categoryPath, String.valueOf(categoryId));
    }


//    @GetMapping(value = "/post/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse httpServletResponse) throws IOException {
//        InputStream inputStream = fileService.getResource(path, fileName);
//        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
//    }
//
//    @GetMapping(value = "/profile/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadProfileImage(@PathVariable("fileName") String fileName, HttpServletResponse httpServletResponse) throws IOException {
//        InputStream inputStream = fileService.getResource(path, fileName);
//        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
//    }

    @GetMapping(value = "/categories/image/{categoryId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadCatagoryImage(@PathVariable("categoryId") String fileName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream inputStream = fileService.getResource(categoryPath, fileName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }


}
