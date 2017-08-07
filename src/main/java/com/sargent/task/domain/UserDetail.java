package com.sargent.task.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "user_info")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDetail {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String name;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column()
    private String location;

    @Column()
    String text;

    public UserDetail() {
    }

    public UserDetail(String name, String location, String text) {
        this.name = name;
        this.location = location;
        this.text = text;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDetail {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
