package com.thecon.pieseauto;

import com.thecon.pieseauto.product.Product;
import com.thecon.pieseauto.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
    @Autowired private ProductRepository repo;

    @Test
    public void testAddNewProduct(){
        Product product = new Product();
        product.setProductName("piesaTest2");
        product.setProductDescription("despre piesa test2");
        product.setStock(1);

        Product savedProduct = repo.save(product);

        Assertions.assertNotNull(savedProduct);
    }

    @Test
    public void testListAll(){
        Iterable<Product> products = repo.findAll();
        Assertions.assertNotNull(products);

        for (Product product : products){
            System.out.println(product);
        }
    }

    @Test
    public void testUpdate(){
        int idPiesa = 1;
        Optional<Product> optionalProduct = repo.findById(idPiesa);
        Product product = optionalProduct.get();
        product.setProductName("PiesaUpdateTest");
        repo.save(product);

        Product updatedProduct = repo.findById(idPiesa).get();
        Assertions.assertEquals(updatedProduct.getProductName(),"PiesaUpdateTest");
    }

    @Test
    public void testGet(){
        int idPiesa = 1;
        Optional<Product> optionalProduct = repo.findById(idPiesa);
        Assertions.assertTrue(optionalProduct.isPresent());
        System.out.println(optionalProduct.get());
    }

    //@Test
    public void testDelete(){
        int idPiesa = 1;
        repo.deleteById(idPiesa);

        Optional<Product> optionalProduct = repo.findById(idPiesa);
        Assertions.assertFalse(optionalProduct.isPresent());
    }
    @Test
    public void testBuy(){
        int idPiesa = 3;
        Optional<Product> optionalProduct = repo.findById(idPiesa);
        Product product = optionalProduct.get();
        product.setStock(product.getStock()-1);
        repo.save(product);

        Assertions.assertEquals(optionalProduct.get().getStock(),38);
    }

    @Test
    public void testBuyList(){
        int idPiesa1 = 1;
        int idPiesa2 = 3;
        int newStock;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(idPiesa1);
        ids.add(idPiesa2);

        Product product;
        Iterable<Product> iterableProduct = repo.findAllById(ids);
        Iterator<Product> it = iterableProduct.iterator();
        while(it.hasNext()){
            product = it.next();
            if(product.getStock() > 0){
                newStock = product.getStock()-1;
                product.setStock(newStock);
                repo.save(product);
                System.out.println(product.getProductName() + " " + product.getStock());
                Assertions.assertEquals(product.getStock(),newStock);
            } else {
                System.out.println(product.getProductName() + " out of stock");
                Assertions.assertEquals(product.getStock(),0);
            }
        }
    }
}
