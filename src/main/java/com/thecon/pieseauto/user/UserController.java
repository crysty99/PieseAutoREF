package com.thecon.pieseauto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UserController {

    @Autowired private UserService service;
    //@Autowired private UserRepository repo;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.listAll();
    }

    @GetMapping("/users/{idUser}")
    public User getUser(@PathVariable ("idUser") int id){
        return service.get(id);
    }

    @GetMapping("/users/find")
    public User getUserByNameAndEmail(@RequestParam ("name") String name,@RequestParam("email") String email){
        return service.getUserByNameAndEmail(name, email);
    }

    @GetMapping("/users/purchases")
    public List<User.Purchase> getListOfPurchases(@RequestParam ("idUser") int id){
        return service.get(id).getListOfPurchases();
    }

    @PutMapping("/users/status")
    public ResponseEntity<String> changeActiveStatusForAllUsers(@RequestParam ("status") boolean status) throws UserNotFoundException{
        service.updateStatusForAllUsers(status);
        return ResponseEntity.ok("Status changed for all users in ["+ status +"]!");
    }

    @PutMapping("/users/edit/{idUser}")
    public ResponseEntity<User> editUser(@PathVariable("idUser") int id, @RequestBody User user) throws UserNotFoundException{
        User editUser = service.get(id);

        editUser.setIdUser(user.getIdUser());
        editUser.setName(user.getName());
        editUser.setPassword(user.getPassword());
        editUser.setEmail(user.getEmail());
        editUser.setDateOfBirth(user.getDateOfBirth());
        editUser.setPhoneNumber(user.getPhoneNumber());
        editUser.setAddress(user.getAddress());
        editUser.setProfileImageLocation(user.getProfileImageLocation());
        editUser.setEnabled(user.isEnabled());
        editUser.setRoles(user.getRoles());
        editUser.setListOfPurchases(user.getListOfPurchases());

        service.save(editUser);

        return ResponseEntity.ok(editUser);
    }

    @DeleteMapping("/users/delete/{idUser}")
    public ResponseEntity<User> deleteUser(@PathVariable("idUser") int id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/profile/address/{idUser}")
    public ResponseEntity<User> updateUserAddress(@PathVariable("idUser") int id,@RequestParam("address") String address){
        User editUser = service.get(id);
        editUser.setAddress(address);
        service.save(editUser);

        return ResponseEntity.ok(editUser);
    }

    @PatchMapping("/profile/image/{idUser}")
    public ResponseEntity<User> uploadUserProfileImage(@PathVariable("idUser") int id, @RequestParam("imageFile") MultipartFile imageFile){
        User editUserImage = service.get(id);

        service.updateImage(id,imageFile);
        return ResponseEntity.ok(editUserImage);
    }

    @GetMapping("/users/image/{idUser}")
    public String getUserProfileImageLocation(@PathVariable("idUser") int id){
        return service.getImageLocation(id);
    }

    //TODO login user

}
