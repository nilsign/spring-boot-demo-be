package com.nilsign.springbootdemo.api.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class OAuth2LogoutController {

  @GetMapping(path = "auth/logout")
  public String logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }
}
