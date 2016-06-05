package com.kevin.drift.Entity;

/**
 * Created by Benson_Tom on 2016/5/26.
 * 用户的实体类
 */
public class User {
    private int id;
    private String userAccount;
    private String password;
    private String username;
    private String userIcon;
    private String userEmail;
    private String userIntroduce;
    private String userFansNumbers;
    private String userFocusNumbers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFansNumber() {
        return userFansNumbers;
    }

    public void setUserFansNumber(String userFansNumbers) {
        this.userFansNumbers = userFansNumbers;
    }

    public String getUserFocusNumbers() {
        return userFocusNumbers;
    }

    public void setUserFocusNumbers(String userFocusNumbers) {
        this.userFocusNumbers = userFocusNumbers;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userAccount='" + userAccount + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userIntroduce='" + userIntroduce + '\'' +
                ", userFansNumbers='" + userFansNumbers + '\'' +
                ", userFocusNumbers='" + userFocusNumbers + '\'' +
                '}';
    }
}
