package com.aplana.ivan.learnview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 1 on 10.05.2017.
 */

public class TestEntity {
    public String name;
    public String language;
    public String value;

    // конструктор
    public TestEntity() {

    }

    public String getName() {
        return name;
    }

    public TestEntity JsonToObj(String jsonInString){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(jsonInString, TestEntity.class);
    }
    public String ObjToJson (TestEntity clss){
        Gson gson = new Gson();
        return gson.toJson(clss);
    }
}
