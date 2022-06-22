package com.thecon.pieseauto.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class UserController {

    @Value("${uploadDir}")
    private String uploadFolder;
    @Autowired private UserService service;

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/users")
    public String showUserList(Model model){
        ArrayList<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
/*
    @RequestMapping(value = "/account")
    @ResponseBody
    public String getIdPrincipal(Model model){
        Authentication  authentication;
        //int id = authentication.getPrincipal().getName();
        model.addAttribute("userId", id);
        return "account";
    }
*/
    @GetMapping("/users/new") //not used yet
    public String showNewForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("title", "Add new user");
        return "userForm";
    }

    @GetMapping("/users/edit/{idUser}")
    public String showEditForm(@PathVariable("idUser") int id, Model model, RedirectAttributes ra){
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("title", "Edit user (ID: "+ id +")");
            return "userForm";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }

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

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","User saved successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{idUser}")
    public String deleteUser(@PathVariable("idUser") int id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "User (ID: "+id+") deleted successfully!");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }
    @PostMapping("/account/save")
    public String updateUserAccount(User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","Account updated successfully!");
        return "redirect:/account";
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

}
