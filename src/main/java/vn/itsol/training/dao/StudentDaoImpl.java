package vn.itsol.training.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.itsol.training.dto.StudentDto;
import vn.itsol.training.entity.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDaoImpl implements StudentDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Student> findAll() {
    Query<Student> query = sessionFactory.getCurrentSession().createQuery("select s from Student s", Student.class);
    return query.list();
  }

  @Override
  public StudentDto findById(String id) {
    String sql = "Select * From student WHERE id = :p_id";
    Map<String, String> params = new HashMap<>();
    params.put("p_id", id);
    return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(StudentDto.class));
  }

  @Override
  public void save(Student student) {
    sessionFactory.getCurrentSession().save(student);
  }

}
