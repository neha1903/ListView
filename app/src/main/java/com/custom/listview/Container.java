package com.custom.listview;

import android.graphics.Bitmap;

public class Container {

    private int id;
    private String remark;
    private Bitmap photo;
    private Boolean remarkCheck;
    private int listItemPosition = -1;


    Container(int id, Bitmap photo){
        this.id = id;
        remark = "";
        remarkCheck = false;
        this.photo = photo;
    }

    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }

    public Boolean getRemarkCheck() {
        return remarkCheck;
    }

    public void setRemarkCheck(Boolean remarkCheck) {
        this.remarkCheck = remarkCheck;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
