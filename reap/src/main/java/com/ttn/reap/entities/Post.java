package com.ttn.reap.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "sender")
    Employee sender;

    @ManyToOne
    @JoinColumn(name = "reciever")
    Employee receiver;

    String badge;

    Date dateOfCreation;

    String comment;

    Boolean isActive;

    String revokeReason;

    @Transient
    String postDate;

    @Transient
    String postTime;

    public String getPostDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        postDate = dateFormat.format(dateOfCreation);
        return postDate;
    }

    public String getPostTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        postTime = timeFormat.format(dateOfCreation);
        return postTime;
    }
}
