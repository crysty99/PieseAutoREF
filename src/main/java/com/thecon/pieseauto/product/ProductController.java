package com.thecon.pieseauto.product;

import com.thecon.pieseauto.user.User;
import com.thecon.pieseauto.user.UserNotFoundException;
import com.thecon.pieseauto.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired private ProductRepository repo;
    @Autowired private UserRepository repoUser;

    @GetMapping("/products")
    public List<Product> getProductList(){
        return (List<Product>) repo.findAll();
    }

    @PostMapping("/products/add")
    public Product addProduct(@RequestBody Product product) {
        repo.save(product);
        return product;
    }

    @PutMapping("/products/update/{idPiesa}")
    public ResponseEntity<Product> updateProduct(@PathVariable("idPiesa") int id, @RequestBody Product product) {
            Optional<Product> updateProduct = Optional.ofNullable(repo.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + id)));
            updateProduct.get().setProductName(product.getProductName());
            updateProduct.get().setProductDescription(product.getProductDescription());
            updateProduct.get().setStock(product.getStock());
            repo.save(updateProduct.get());
            return ResponseEntity.ok(updateProduct.get());
    }

    @DeleteMapping("/products/delete/{idPiesa}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("idPiesa") int id){
       Optional<Product> product = Optional.ofNullable(repo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + id)));
       repo.delete(product.get());
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/buy/{idPiesa}")
    public ResponseEntity<Product> buyProduct(@RequestParam(name = "idUser") int idUser,@PathVariable("idPiesa") int idPiesa,@RequestParam(name = "numberOfPurchases")int nrPurchases,@RequestBody Product product){

        Optional<Product> buyProduct = Optional.ofNullable(repo.findById(idPiesa)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + idPiesa)));

        if(buyProduct.get().getStock() >= nrPurchases) {
            Optional<User> user = Optional.ofNullable(repoUser.findById(idUser)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id:" + idUser)));

            String productName = product.getProductName();
            ArrayList<User.Purchase> purchases;
            if(user.get().getListOfPurchases() == null){
                purchases = new ArrayList<>();
            }else{
                purchases = user.get().getListOfPurchases();
            }

            buyProduct.get().setStock(product.getStock() - nrPurchases);
            purchases.add(new User.Purchase(productName,nrPurchases));
            System.out.println(purchases);
            user.get().setListOfPurchases(purchases);

            repoUser.save(user.get());
            repo.save(buyProduct.get());

            return ResponseEntity.ok(buyProduct.get());
        } throw new RuntimeException("Not enough in Stock");
    }
}
