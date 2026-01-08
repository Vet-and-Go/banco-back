package grupo4.banco_back.persistence.dao.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class ClientJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "login")
  String login;
  @Column(name = "password")
  String password;
  @Column(name = "name")
  String name;
  @Column(name = "firstSurname")
  String firstSurname;
  @Column(name = "secondSurname")
  String secondSurname;
  @Column(name = "dni")
  String dni;
  @Column(name = "apiToken")
  String apiToken;

  public ClientJpaEntity() {
  }

  public ClientJpaEntity(Long id, String login, String password, String name, String firstSurname, String secondSurname,
      String dni, String apiToken) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.name = name;
    this.firstSurname = firstSurname;
    this.secondSurname = secondSurname;
    this.dni = dni;
    this.apiToken = apiToken;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstSurname() {
    return firstSurname;
  }

  public void setFirstSurname(String firstSurname) {
    this.firstSurname = firstSurname;
  }

  public String getSecondSurname() {
    return secondSurname;
  }

  public void setSecondSurname(String secondSurname) {
    this.secondSurname = secondSurname;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ClientJpaEntity other)) {
      return false;
    }
    return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }
}
