package com.thecon.pieseauto.user;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(nullable = false)
    private String password, phoneNumber,address;
    @Column(nullable = false, unique = true)
    private String name,email;
    @Column
    private boolean enabled;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date dateOfBirth;

    @Column
    private String profileImageLocation;

    @Lob
    @Column
    private ArrayList<Purchase> listOfPurchases = new ArrayList<Purchase>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public String getProfileImageLocation() {
        return profileImageLocation;
    }

    public void setProfileImageLocation(String profileImageLocation) {
        this.profileImageLocation = profileImageLocation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<Purchase> getListOfPurchases() {
        return listOfPurchases;
    }

    public void setListOfPurchases(ArrayList<Purchase> listOfPurchases) {
        this.listOfPurchases = listOfPurchases;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", dateOfBirth=" + dateOfBirth +
                ", profileImageLocation='" + profileImageLocation + '\'' +
                ", listOfPurchases=" + listOfPurchases +
                ", roles=" + roles +
                '}';
    }

    public static class Purchase implements Serializable {
        String productName;
        int numberOfProducts;

        public Purchase(String productName, int numberOfProducts) {
            this.productName = productName;
            this.numberOfProducts = numberOfProducts;
        }

        @Override
        public String toString() {
            return "Purchase{" +
                    "productName='" + productName + '\'' +
                    ", numberOfProducts=" + numberOfProducts +
                    '}';
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getNumberOfProducts() {
            return numberOfProducts;
        }

        public void setNumberOfProducts(int numberOfProducts) {
            this.numberOfProducts = numberOfProducts;
        }
    }

}
