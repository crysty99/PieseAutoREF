package com.thecon.pieseauto.product;

import javax.persistence.*;

@Entity
@Table(name = "piesa")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPiesa;
    @Column(nullable = false)
    private String productName,productDescription;
    @Column(nullable = false)
    private int stock;

    public int getIdPiesa() {
        return idPiesa;
    }

    public void setIdPiesa(int idPiesa) {
        this.idPiesa = idPiesa;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idPiesa=" + idPiesa +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", stock=" + stock +
                '}';
    }
}