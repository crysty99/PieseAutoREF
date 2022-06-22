package com.thecon.pieseauto.user;

import com.thecon.pieseauto.product.Product;
import com.thecon.pieseauto.product.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
        return repo.findById(id).get().getListOfPurchases();
    }

    @PutMapping("/users/status")
    public ResponseEntity<String> changeActiveStatusForAllUsers(@RequestParam ("status") boolean status) throws UserNotFoundException{
        repo.updateStatusForAllUsers(status);
        return ResponseEntity.ok("Status changed for all users in ["+ status +"]!");
    }

    @PutMapping("/users/edit/{idUser}")
    public ResponseEntity<User> editUser(@PathVariable("idUser") int id, @RequestBody User user) throws UserNotFoundException{
        Optional<User> editUser = Optional.ofNullable(repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" + id)));

        editUser.get().setIdUser(user.getIdUser());
        editUser.get().setName(user.getName());
        editUser.get().setPassword(user.getPassword());
        editUser.get().setEmail(user.getEmail());
        editUser.get().setDateOfBirth(user.getDateOfBirth());
        editUser.get().setPhoneNumber(user.getPhoneNumber());
        editUser.get().setAddress(user.getAddress());
        editUser.get().setProfileImage(user.getProfileImage());
        editUser.get().setEnabled(user.isEnabled());
        editUser.get().setRoles(user.getRoles());
        editUser.get().setListOfPurchases(user.getListOfPurchases());

        repo.save(editUser.get());

        return ResponseEntity.ok(editUser.get());
    }

    @DeleteMapping("/users/delete/{idUser}")
    public ResponseEntity<User> deleteUser(@PathVariable("idUser") int id){
        Optional<User> user = Optional.ofNullable(repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" + id)));
        repo.deleteUserRoleByIdUser(id);
        repo.deleteUserByIdUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //TODO updateUserAddress

    //TODO uploadUserProfileImage

    //TODO getUserProfileImageLocation

    //TODO login user


/*



    @GetMapping("/users/editImage/{idUser}")
    public String updateProfileImage(@PathVariable("idUser") int id, Model model, RedirectAttributes ra){
        try {
            byte[] img = service.get(id).getProfileImage();
            model.addAttribute("image", img);
            model.addAttribute("title", "Edit user (ID: "+ id +")");
            return "userFormImage";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/editAddress/{idUser}")
    public String updateUserAddress(@PathVariable("idUser") int id, Model model, RedirectAttributes ra){
        try {
            String address = service.get(id).getAddress();
            int ID = service.get(id).getIdUser();
            model.addAttribute("address", address);
            model.addAttribute("ID", ID);
            model.addAttribute("title", "Edit user (ID: "+ id +")");
            return "userFormAddress";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("displayImage/{idUser}")
    @ResponseBody
    void showImage(@PathVariable("idUser") int id, HttpServletResponse response)
            throws IOException, UserNotFoundException {
        User user = service.get(id);
        response.setContentType("image/png");
        response.getOutputStream().write(user.getProfileImage());
        response.getOutputStream().close();
    }
*/
}
