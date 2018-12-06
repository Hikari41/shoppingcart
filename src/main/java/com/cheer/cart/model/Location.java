package com.cheer.cart.model;

public class Location {
    private Integer userId;
    private String addressee;
    private String phoneNum;
    private String locName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append(", userId=").append(userId);
        sb.append(", addressee='").append(addressee).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append(", locName='").append(locName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
