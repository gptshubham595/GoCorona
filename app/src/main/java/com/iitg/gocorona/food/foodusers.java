package com.iitg.gocorona.food;

public  class foodusers {
    public  String foodQuery,contact,location;
    public foodusers(){

    }
    public foodusers(String foodQuery, String contact,String location) {
        this.foodQuery = foodQuery;
        this.contact = contact;
        this.location=location;

    }

    public String getFoodQuery() {
        return foodQuery;
    }

    public void setFoodQuery(String heading) {
        this.foodQuery = heading;
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
