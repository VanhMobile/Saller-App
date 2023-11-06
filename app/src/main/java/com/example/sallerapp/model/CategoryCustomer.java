package com.example.sallerapp.model;

public class CategoryCustomer {
    private String idCategory;
    private String nameCategory;
    private String note;

    public CategoryCustomer(String idCategory, String nameCategory, String note) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.note = note;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
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
