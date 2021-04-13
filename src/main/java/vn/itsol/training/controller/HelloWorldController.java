package vn.itsol.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.itsol.training.dto.StudentDto;
import vn.itsol.training.service.StudentService;
import vn.itsol.training.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
//@RestController
public class HelloWorldController {

  @Autowired
  private StudentService studentService;

    @RequestMapping(value = "/")
    public String init() {
        return "init";
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/language")
    public String language() {
        return "language";
    }

    @GetMapping(value = "/student")
    public String findAll(Model model) {
      List<StudentDto> list = studentService.findAll();
        model.addAttribute("listData", list);
        return "student";
    }

    @GetMapping("/student/{id}/detail")
    public String findById(@PathVariable("id") String id, Model model) {
      StudentDto data = studentService.findById(id);
        model.addAttribute("data", data);
        return "detail";
    }

    @GetMapping("/new")
    public String saveInit(Model model) {
      model.addAttribute("student", new StudentDto());
        return "save";
    }

  @PostMapping("/new")
  public String saveDo(@ModelAttribute("student") StudentDto dto) {
    studentService.save(dto);
    return "redirect:/student";
  }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadInit() {

        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadDo(@RequestParam(name = "file") MultipartFile file) {
        try {
          studentService.uploadFile(file);
            return "viewFile";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "upload";
        }
    }

    @RequestMapping(value = "/download-file")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response) {
      String directory = StringUtils.FOLDER_UPLOAD;
      Path path = Paths.get(directory, "da_nang.jpg");
        if (Files.exists(path)) {
            response.setContentType(StringUtils.CONTENT_TYPE_IMAGE);
            response.addHeader("Content-Disposition", "attachment; filename=da_nang.jpg");
            try {
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

  // =========================== REST =======================//
  @RequestMapping(value = "/student-rest")
  public @ResponseBody
  List<StudentDto> findAllRest() {
    return studentService.findAll();
  }

  @RequestMapping(value = "/save-rest", method = RequestMethod.POST)
  public @ResponseBody
  void saveDoRest(@RequestBody StudentDto dto) {
    studentService.save(dto);
  }

}
