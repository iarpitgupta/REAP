package com.ttn.reap.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Badges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String badgeName;

    Integer value;
}
