package com.myapp.geetikabhawan.ui.ourteam;

public class MemberData {

    private String name, post, image, key;

    public MemberData() {
    }

    public MemberData(String name, String post, String image, String key) {
        this.name = name;
        this.post = post;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
