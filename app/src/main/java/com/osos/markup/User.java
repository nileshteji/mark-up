package com.osos.markup;

public class User {
    String Email;
    String Category;
    String Name;
    User(String Email,String Category,String Name){
        this.Email=Email;
        this.Category=Category;
        this.Name=Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
