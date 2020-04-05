package com.iitg.gocorona.query;

public class find_suspect_class {
    String name,image,post,id,text;
    Integer reposrted;

    public find_suspect_class(String name, String image, String post, String id, String text, Integer reposrted) {
        this.name = name;
        this.image = image;
        this.post = post;
        this.id = id;
        this.text = text;
        this.reposrted = reposrted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getReposrted() {
        return reposrted;
    }

    public void setReposrted(Integer reposrted) {
        this.reposrted = reposrted;
    }
}
