package com.ksbm.ontu.foundation.model;

public class BookMarkModel {

    String name; String password; String id; boolean isselect = false;

//    public BookMarkModel(String name, String password, String id) {
//        this.name = name;
//        this.password = password;
//        this.id = id;
//    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public boolean getisselect() {
        return isselect;
    }

    public void setisselect(boolean isselect) {
        this.isselect = isselect;
    }

}
