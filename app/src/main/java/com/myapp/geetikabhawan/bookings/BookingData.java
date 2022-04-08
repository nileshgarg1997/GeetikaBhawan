package com.myapp.geetikabhawan.bookings;

public class BookingData {

    private String name,mobile,category,bookingDate,image,key;

    public BookingData() {
    }

    public BookingData(String name, String mobile, String category, String bookingDate, String image, String key) {
        this.name = name;
        this.mobile = mobile;
        this.category = category;
        this.bookingDate = bookingDate;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
