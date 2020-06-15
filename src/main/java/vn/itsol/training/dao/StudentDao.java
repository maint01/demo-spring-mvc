package vn.itsol.training.dao;

import vn.itsol.training.dto.StudentDto;
import vn.itsol.training.entity.Student;

import java.util.List;

public interface StudentDao {

  List<Student> findAll();

  StudentDto findById(String id);

  void save(Student student);
}
