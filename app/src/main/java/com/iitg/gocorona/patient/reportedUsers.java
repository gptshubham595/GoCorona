package com.iitg.gocorona.patient;

public class reportedUsers {
    public String reported,contact,location;

    public reportedUsers() {

    }
    public reportedUsers(String foodQuery, String contact,String location) {
        this.reported = foodQuery;
        this.contact = contact;
        this.location=location;

    }
    public reportedUsers(String foodQuery) {
        this.reported = foodQuery;
    }

    public String getReported() {
        return reported;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String url) {
        this.contact = url;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String image) {
        this.location = image;
    }


}
