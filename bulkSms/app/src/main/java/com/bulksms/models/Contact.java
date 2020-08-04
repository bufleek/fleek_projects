package com.bulksms.models;

public class Contact {
    private int id;
    private String name;
    private String phone;

    public Contact(){}

    public Contact(int id, String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public void setName(String name){this.name = name;}
    public void setPhone(String phone){this.phone = phone;}
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPhone(){return this.phone;}

    public String toString(){return name + "|" + phone;}
}
