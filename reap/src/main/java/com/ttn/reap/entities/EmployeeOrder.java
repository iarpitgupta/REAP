package com.ttn.reap.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ElementCollection
    List<String> items = new ArrayList<>();

    @ElementCollection
    List<String> imageUrl = new ArrayList<>();

    @JsonManagedReference
    @ManyToOne
    Employee employee;

    Integer totalPrice;

    Integer quantity;

    Date dateOfOrder;

    @Transient
    String orderDate;

    @Transient
    String orderTime;

    public String getOrderDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        orderDate = dateFormat.format(dateOfOrder);
        return orderDate;
    }

    public String getOrderTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        orderTime = timeFormat.format(dateOfOrder);
        return orderTime;
    }
}
