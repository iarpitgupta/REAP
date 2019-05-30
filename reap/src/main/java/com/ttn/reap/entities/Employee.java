package com.ttn.reap.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 30, message = "First Name must have 2 to 30 characters")
    String firstName;

    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 30, message = "First Name must have 2 to 30 characters")
    String lastName;

    @Transient
    String fullName;

    @Email(message = "Email should not start with special character or number and must end with @tothenew.com")
    String email;

    String password;

    String profilePic;

    Integer gold;

    Integer silver;

    Integer bronze;

    @Temporal(TemporalType.DATE)
    Date createdOn;

    Boolean active;

    String resetToken;


    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "employee")
    List<EmployeeOrder> order = new ArrayList<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }


}
