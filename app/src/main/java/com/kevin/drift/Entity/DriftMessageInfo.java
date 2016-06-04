package com.kevin.drift.Entity;

/**
 * Created by Benson_Tom on 2016/4/28.
 */
public class DriftMessageInfo {
    private String userID;
    private String driftAddress;
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

    public String getDriftAddress() {
        return driftAddress;
    }

    public void setDriftAddress(String driftAddress) {
        this.driftAddress = driftAddress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "DriftMessageInfo{" +
                "driftAddress='" + driftAddress + '\'' +
                ", userID='" + userID + '\'' +
                ", driftTime='" + driftTime + '\'' +
                ", driftImg='" + driftImg + '\'' +
                ", driftContent='" + driftContent + '\'' +
                ", driftCommentNums=" + driftCommentNums +
                ", driftLikeNums=" + driftLikeNums +
                '}';
    }
}
