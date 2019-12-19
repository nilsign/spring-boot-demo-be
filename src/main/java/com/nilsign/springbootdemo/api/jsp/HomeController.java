package com.nilsign.springbootdemo.api.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("home")
public class HomeController {

  @GetMapping
  @RolesAllowed({"GLOBALADMIN", "ADMIN", "SELLER", "SUPPORT", "BUYER"})
  public String showHome() {
    return "home";
  }

  @GetMapping(path = "/logout")
  @RolesAllowed({"GLOBALADMIN", "ADMIN", "SELLER", "SUPPORT", "BUYER"})
  public String logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/home";
  }
}
