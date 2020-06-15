package vn.itsol.training.service;

import org.springframework.web.multipart.MultipartFile;
import vn.itsol.training.dto.StudentDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface StudentService {

  List<StudentDto> findAll();

  StudentDto findById(String id);

  boolean uploadFile(MultipartFile file) throws FileNotFoundException;

  void save(StudentDto studentInformationDto);
}
