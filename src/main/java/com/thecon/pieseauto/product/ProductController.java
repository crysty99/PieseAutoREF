package com.thecon.pieseauto.product;

import com.thecon.pieseauto.user.User;
import com.thecon.pieseauto.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired private ProductService service;
    @Autowired private UserService serviceUser;

    @GetMapping("/products")
    public List<Product> getProductList(){
        return service.listAll();
    }

    @PostMapping("/products/add")
    public Product addProduct(@RequestBody Product product) {
        service.save(product);
        return product;
    }

    @PutMapping("/products/update/{idPiesa}")
    public ResponseEntity<Product> updateProduct(@PathVariable("idPiesa") int id, @RequestBody Product product) {
            Product updateProduct = service.get(id);
            updateProduct.setProductName(product.getProductName());
            updateProduct.setProductDescription(product.getProductDescription());
            updateProduct.setStock(product.getStock());
            service.save(updateProduct);
            return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/products/delete/{idPiesa}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("idPiesa") int id){
       service.delete(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/buy/{idPiesa}")
    public ResponseEntity<Product> buyProduct(@RequestParam(name = "idUser") int idUser,@PathVariable("idPiesa") int idPiesa,@RequestParam(name = "numberOfPurchases")int nrPurchases,@RequestBody Product product){

        Product buyProduct = service.get(idPiesa);

        if(buyProduct.getStock() >= nrPurchases) {
            User user = serviceUser.get(idUser);

            String productName = product.getProductName();
            ArrayList<User.Purchase> purchases;
            if(user.getListOfPurchases() == null){
                purchases = new ArrayList<>();
            }else{
                purchases = user.getListOfPurchases();
            }

            buyProduct.setStock(product.getStock() - nrPurchases);
            purchases.add(new User.Purchase(productName,nrPurchases));
            System.out.println(purchases);
            user.setListOfPurchases(purchases);

            serviceUser.save(user);
            service.save(buyProduct);

            return ResponseEntity.ok(buyProduct);
        } throw new RuntimeException("Not enough in Stock");
    }
}
