package com.kevin.drift.Fragment;

/**
 * Created by Benson_Tom on 2016/4/3.
 */
public class SortModel {

    /**
     * 联系人实体类
     * username是ListView中用户的名称
     * userFirstLetter是用户名的首字母
     */
    private String username;
    private String userFirstLetter;

    public String getUserFirstLetter() {
        return userFirstLetter;
    }

    public void setUserFirstLetter(String userFirstLetter) {
        this.userFirstLetter = userFirstLetter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
