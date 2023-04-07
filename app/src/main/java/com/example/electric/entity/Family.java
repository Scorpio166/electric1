package com.example.electric.entity;

public class Family {
    private int isRoomOwner;
    private String roomAddress;
    private int roomId;
    private String roomName;

    public int getIsRoomOwner() {
        return isRoomOwner;
    }

    public void setIsRoomOwner(int isRoomOwner) {
        this.isRoomOwner = isRoomOwner;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
