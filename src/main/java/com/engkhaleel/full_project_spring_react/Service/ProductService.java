package com.engkhaleel.full_project_spring_react.Service;

import com.engkhaleel.full_project_spring_react.Model.Product;
import com.engkhaleel.full_project_spring_react.Repo.RepoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    private RepoProduct repoProduct;

    public List<Product> getProduct() {
        return repoProduct.findAll();
    }

    public Product getProductById(int id) {
        /* if product exist already have id returned ! but if not return -1
        (-1) value is check inside a controller to return 404 not found
        -> but if return a value > 0 will return 200 ok
         */
        return repoProduct.findById(id).orElse(new Product(-1));
    }

    // in this JPA context save and update doing the same function ..
    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return repoProduct.save(product);
    }

    // to delete product by id \\
    public void deleteProduct(int id) {
        repoProduct.deleteById(id);
    }


    public List<Product> searchProduct(String keyword) {
        // this method not built-In JPA you need to write a some queries ...
        return repoProduct.searchProduct(keyword);
    }
}
