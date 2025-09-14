package edu.deakin.sit738.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ReportController {
  @Autowired JdbcTemplate jdbc;

  @GetMapping("/report/top-spend")
  public String topSpend(Model model) {
    // Deliberately broad report: exposes email + totals to any visitor
    String sql =
      "SELECT u.full_name, u.email, SUM(o.amount) AS total " +
      "FROM users u JOIN orders o ON u.id=o.user_id " +
      "GROUP BY u.id, u.full_name, u.email " +
      "ORDER BY total DESC";
    List<Map<String,Object>> rows = jdbc.queryForList(sql);
    model.addAttribute("rows", rows);
    model.addAttribute("sql", sql);
    return "report";
  }
}
