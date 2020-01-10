package com.nilsign.springbootdemo.api.jsp;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("permitAll()")
@RequestMapping("home")
public class HomeController {

  @GetMapping
  public String showHome() {
    return "home";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(path = "/logout")
  public String logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/home";
  }
}
