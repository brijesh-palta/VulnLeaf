package edu.deakin.sit738.web;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileOutputStream;

@Controller
public class UploadController {

  private final File uploadDir = new File("/tmp/vuln-uploads");

  @GetMapping("/upload")
  public String uploadPage() { return "upload"; }

  // Deliberately no validation on type/size/name
  @PostMapping("/upload")
  public String doUpload(@RequestParam("file") MultipartFile file, Model model) throws Exception {
    if (!uploadDir.exists()) uploadDir.mkdirs();
    File out = new File(uploadDir, file.getOriginalFilename());
    try (FileOutputStream fos = new FileOutputStream(out)) {
      FileCopyUtils.copy(file.getBytes(), fos);
    }
    model.addAttribute("path", out.getAbsolutePath());
    return "upload-done";
  }
}
