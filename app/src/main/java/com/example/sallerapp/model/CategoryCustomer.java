package com.example.sallerapp.model;

public class CategoryCustomer {
    private String nameCategory;
    private String note;

    public CategoryCustomer(String nameCategory, String note) {
        this.nameCategory = nameCategory;
        this.note = note;
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

    public CategoryCustomer() {

    }
}
