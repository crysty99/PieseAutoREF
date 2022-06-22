package com.thecon.pieseauto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

    @GetMapping("/home")
    public String showHomePage() {

        return "index";
    }

    @GetMapping("/account")
    public String showAccountPage() {

        return "accountDetails";
    }

}
