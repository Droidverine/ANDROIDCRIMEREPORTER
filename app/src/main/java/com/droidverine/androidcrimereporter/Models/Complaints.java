package com.droidverine.androidcrimereporter.Models;

/**
 * Created by DELL on 07-03-2018.
 */

public class Complaints {
    String name,location,url,status,otherinfo,contact;

    public Complaints(String name, String location, String url, String status, String otherinfo, String contact) {
        this.name = name;
        this.location = location;
        this.url = url;
        this.status = status;
        this.otherinfo = otherinfo;
        this.contact = contact;
    }

    public Complaints() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
