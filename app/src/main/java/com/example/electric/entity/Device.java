package com.example.electric.entity;

import java.math.BigInteger;

public class Device {
    private int isOFF;
    private String deviceModelPicture;
    private String activateTime;
    private String deviceCode;
    private BigInteger deviceId;
    private String deviceName;
    private String deviceCreateTime;

    public int getIsOFF() {
        return isOFF;
    }

    public void setIsOFF(int isOFF) {
        this.isOFF = isOFF;
    }

    public String getDeviceModelPicture() {
        return deviceModelPicture;
    }

    public void setDeviceModelPicture(String deviceModelPicture) {
        this.deviceModelPicture = deviceModelPicture;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCreateTime() {
        return deviceCreateTime;
    }

    public void setDeviceCreateTime(String deviceCreateTime) {
        this.deviceCreateTime = deviceCreateTime;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    private int deviceStatus;


}
