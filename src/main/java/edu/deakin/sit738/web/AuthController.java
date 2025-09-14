package edu.deakin.sit738.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
  @Autowired JdbcTemplate jdbc;

  @GetMapping("/login")
  public String loginPage() { return "login"; }

  // Deliberately vulnerable: dynamic SQL string concatenation
  @PostMapping("/login")
  public String doLogin(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
    String sql = "SELECT COUNT(*) FROM users WHERE username='"
               + username + "' AND password_plain='" + password + "'";
    Integer count = jdbc.queryForObject(sql, Integer.class);
    model.addAttribute("sql", sql);
    model.addAttribute("ok", count != null && count > 0);
    return "login-result";
  }
}
