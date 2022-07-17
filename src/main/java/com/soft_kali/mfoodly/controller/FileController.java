package com.soft_kali.mfoodly.controller;

import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.service.FileService;
import com.soft_kali.mfoodly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    ProductService productService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<ProductDto> uploadPostImage(@PathVariable Long productId, @RequestParam("image") MultipartFile file) throws IOException {
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
        return null;
    }

    @GetMapping(value = "/post/image/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream inputStream=fileService.getResource(path,fileName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }


}
