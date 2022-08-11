package com.example.samplebatch.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * User model
 */
@Entity
public class UserInfo {
  @Id
  private long id;

  private String name;

  private String department;

  private long salary;

  private LocalDate time;

  public UserInfo(){
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  public LocalDate getTime() {
    return time;
  }

  public void setTime(LocalDate time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", department='" + department + '\'' +
        ", salary=" + salary +
        ", time=" + time +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserInfo userInfo = (UserInfo) o;
    return id == userInfo.id && salary == userInfo.salary && Objects.equals(name, userInfo.name) && Objects.equals(department, userInfo.department) && Objects.equals(time, userInfo.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, department, salary, time);
  }
}