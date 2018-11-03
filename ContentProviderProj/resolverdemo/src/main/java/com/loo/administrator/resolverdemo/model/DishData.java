package com.loo.administrator.resolverdemo.model;

public class DishData {
    private String dish_id;
    private String dish_name;
    private String dish_type;

    public DishData(String dish_id, String dish_name, String dish_type) {
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.dish_type = dish_type;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }
}
