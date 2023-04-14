package com.example.electric.entity;

public class RoomUser {
    private int isRoomOwner;
    private String createTime;
    private String avatar;
    private String userName;
    private int userId;

    public int getIsRoomOwner() {
        return isRoomOwner;
    }

    public void setIsRoomOwner(int isRoomOwner) {
        this.isRoomOwner = isRoomOwner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
