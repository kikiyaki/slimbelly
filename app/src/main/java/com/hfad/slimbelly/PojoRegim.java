package com.hfad.slimbelly;

public class PojoRegim {
    private String name;
    private String time;
    private int imageId;

    public PojoRegim (String name, String time, int imageId) {
        this.name = name;
        this.time = time;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getImageId() {
        return imageId;
    }
}
