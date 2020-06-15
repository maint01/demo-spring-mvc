package vn.itsol.training.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.itsol.training.dao.StudentDao;
import vn.itsol.training.dto.StudentDto;
import vn.itsol.training.entity.Student;
import vn.itsol.training.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("all")
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentDao studentDao;

  @Autowired
  private ModelMapper modelMapper;


  @Override
  public List<StudentDto> findAll() {
    return studentDao.findAll()
      .stream()
      .map(student -> modelMapper.map(student, StudentDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public StudentDto findById(String id) {
    return studentDao.findById(id);
  }

    @Override
    public boolean uploadFile(MultipartFile file) {
        try {
            File newFile = new File(StringUtils.FOLDER_UPLOAD + file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException fileNotFound) {
            return false;
        }
        return true;
    }

  @Override
  public void save(StudentDto studentInformationDto) {
    Student student = new Student();

    student.setName(studentInformationDto.getName());
    student.setPhone(studentInformationDto.getPhone());
    student.setAddress(studentInformationDto.getAddress());
    student.setEmail(studentInformationDto.getEmail());

    studentDao.save(student);
  }

}
