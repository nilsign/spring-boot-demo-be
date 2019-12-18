package com.nilsign.springbootdemo.api.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

  @GetMapping("home")
  public String showHome() {
    return "home";
  }

  @GetMapping(path = "home/logout")
  public String logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return "/";
  }
}
