package com.kevin.drift.Utils;


/**
 * Created by Benson_Tom on 2016/6/1.
 * 自定义Gson中的复杂数据解析的实体类
 */
public class JsonBean {
    public String messageInfo;
    public User user;

   public static class User{
       private String id;
       private String userAccount;
       private String password;
       private String username;
       private String userIcon;
       private String userEmail;
       private String userIntroduce;
       private String userFansNumbers;
       private String userFocusNumbers;

       public String getId() {
           return id;
       }

       public void setId(String id) {
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

       public String getUserFansNumbers() {
           return userFansNumbers;
       }

       public void setUserFansNumbers(String userFansNumbers) {
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
   }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
