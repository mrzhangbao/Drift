package com.kevin.drift.Entity;

/**
 * Created by Benson_Tom on 2016/6/4.
 * 手机联系人的实体类
 */
public class MobileContacts {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String userFirstLetter;

    public String getUserFirstLetter() {
        return userFirstLetter;
    }

    public void setUserFirstLetter(String userFirstLetter) {
        this.userFirstLetter = userFirstLetter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "MobileContacts{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
