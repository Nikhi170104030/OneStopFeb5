package com.swc.onestop.SmallRecyclers;

import android.graphics.drawable.Drawable;

public class OtherAndCallClass {
    private int typeOfData;
    private Drawable d;
    private String title;
    private String subhead;
    private String phoneNumber;
    private String otherData; // can be mail or website

    public int getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(int typeOfData) {
        this.typeOfData = typeOfData;
    }

    public Drawable getD() {
        return d;
    }

    public void setD(Drawable d) {
        this.d = d;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public OtherAndCallClass(int typeOfData, Drawable d, String title, String subhead, String phoneNumber, String otherData) {

        this.typeOfData = typeOfData;
        this.d = d;
        this.title = title;
        this.subhead = subhead;
        this.phoneNumber = phoneNumber;
        this.otherData = otherData;
    }

    public OtherAndCallClass() {

    }
}
