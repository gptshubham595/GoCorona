package com.iitg.gocorona.patient;

public class reportedUsers {
    public String Reported,contact,location;

    public reportedUsers() {

    }
    public reportedUsers(String foodQuery, String contact,String location) {
        this.Reported = foodQuery;
        this.contact = contact;
        this.location=location;

    }
    public reportedUsers(String foodQuery) {
        this.Reported = foodQuery;
    }

    public String getReported() {
        return Reported;
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
