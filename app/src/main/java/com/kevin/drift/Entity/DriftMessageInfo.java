package com.kevin.drift.Entity;

/**
 * Created by Benson_Tom on 2016/4/28.
 */
public class DriftMessageInfo {
    private String userIcon;
    private String userName;
    private String userAddress;
    private String driftTime;
    private String driftImg;
    private String driftContent;
    private int driftCommentNums;
    private int driftLikeNums;

    public int getDriftCommentNums() {
        return driftCommentNums;
    }

    public void setDriftCommentNums(int driftCommentNums) {
        this.driftCommentNums = driftCommentNums;
    }

    public String getDriftContent() {
        return driftContent;
    }

    public void setDriftContent(String driftContent) {
        this.driftContent = driftContent;
    }

    public String getDriftImg() {
        return driftImg;
    }

    public void setDriftImg(String driftImg) {
        this.driftImg = driftImg;
    }

    public int getDriftLikeNums() {
        return driftLikeNums;
    }

    public void setDriftLikeNums(int driftLikeNums) {
        this.driftLikeNums = driftLikeNums;
    }

    public String getDriftTime() {
        return driftTime;
    }

    public void setDriftTime(String driftTime) {
        this.driftTime = driftTime;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
