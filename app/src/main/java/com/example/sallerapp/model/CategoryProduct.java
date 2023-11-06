package com.example.sallerapp.model;

public class CategoryProduct {
    private String nameCategory;
    private String note;

    public CategoryProduct(String nameCategory, String note) {
        this.nameCategory = nameCategory;
        this.note = note;
    }
    public CategoryProduct() {

    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
