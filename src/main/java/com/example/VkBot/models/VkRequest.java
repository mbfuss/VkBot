package com.example.VkBot.models;

public class VkRequest {
    private String type;
    private VkMessageObject object;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VkMessageObject getObject() {
        return object;
    }

    public void setObject(VkMessageObject object) {
        this.object = object;
    }
}
