package com.ttn.reap.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "employee_received_star")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeReceivedStar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    Employee employee;


    Integer gold;

    Integer silver;

    Integer bronze;

    Integer pointsRedeemed;

    Integer totalPoints;

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints() {
        this.totalPoints = ((gold * 30) + (silver * 20) + (bronze * 10));
    }
}
