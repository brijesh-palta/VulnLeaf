package edu.deakin.sit738.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class ProfileController {
  @Autowired JdbcTemplate jdbc;

  @GetMapping("/profile")
  public String profile() { return "profile"; }

  // Deliberately vulnerable: no CSRF token, no referrer check
  @PostMapping("/profile/rename")
  public String rename(@RequestParam String username,
                       @RequestParam String newName,
                       Model model) {
    int updated = jdbc.update("UPDATE users SET full_name='" + newName + "' WHERE username='" + username + "'");
    model.addAttribute("updated", updated);
    model.addAttribute("username", username);
    model.addAttribute("newName", newName);
    return "profile-done";
  }
}
