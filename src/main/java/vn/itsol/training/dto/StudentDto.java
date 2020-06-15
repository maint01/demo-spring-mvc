package vn.itsol.training.dto;


public class StudentDto {
  private Long id;
  private String name;
  private String phone;
  private String address;
  private String email;

  public StudentDto() {
  }

  public StudentDto(Long id, String name, String phone, String address, String email) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.email = email;
  }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public Long getId() {
    return id;
  }

  public String getName() {
        return name;
    }
}
