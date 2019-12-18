package me.nice.customview.bean;

public class MainView {

    private String name;
    private String image;
    private int id;

    public MainView(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public MainView(String name, String image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
