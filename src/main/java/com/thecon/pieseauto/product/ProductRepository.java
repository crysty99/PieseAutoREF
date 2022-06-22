package com.thecon.pieseauto.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

   public Long countByIdPiesa(Integer id);

}
