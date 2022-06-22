package com.thecon.pieseauto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Value("${uploadDir}")
    private String uploadFolder;
    @Autowired private UserRepository repo;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return repo.findAll();
    }

    @GetMapping("/users/{idUser}")
    public Optional<User> getUser(@PathVariable ("idUser") int id){
        return repo.findById(id);
    }

    @GetMapping("/users/find")
    public Optional<User> getUserByNameAndEmail(@RequestParam ("name") String name,@RequestParam("email") String email){
        return Optional.ofNullable(repo.getUserByNameAndEmail(name, email));
    }

    @GetMapping("/users/purchases")
    public List<User.Purchase> getListOfPurchases(@RequestParam ("idUser") int id){
        List<User.Purchase> purchases = repo.findById(id).get().getListOfPurchases();
        return purchases;
    }

    @PutMapping("/users/status")
    public ResponseEntity<String> changeActiveStatusForAllUsers(@RequestParam ("status") boolean status) throws UserNotFoundException{
        repo.updateStatusForAllUsers(status);
        return ResponseEntity.ok("Status changed for all users in ["+ status +"]!");
    }

    //TODO editUser

    //TODO deleteUser

    //TODO updateUserAddress

    //TODO uploadUserProfileImage

    //TODO getUserProfileImageLocation

    //TODO login user

}
