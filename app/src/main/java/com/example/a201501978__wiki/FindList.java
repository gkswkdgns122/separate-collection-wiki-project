package com.example.a201501978__wiki;

public class FindList {
    private String name;
    private String category;
    private String information;


    public FindList(String name, String category, String information){
        this.name = name;
        this.category = category;
        this.information = information;
    }


    public String getName()
    {
        return this.name;
    }

    public String getCategory()
    {
        return this.category;
    }

    public String getInformation()
    {
        return this.information;
    }
}
