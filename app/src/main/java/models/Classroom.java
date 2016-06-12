package models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AKINDE-PETERS on 6/8/2016.
 */
public class Classroom implements Serializable {

    public String name,car_park,bridge;

    public Classroom(){}

    public Classroom(String name,String car_park,String bridge){

        this.name = name;
        this.car_park = car_park;
        this.bridge = bridge;

    }


    //// getters ////

    public String getName(){
        return this.name;
    }

    public String getCar_park(){
        return this.car_park;
    }

    public String getBridge(){
        return this.bridge;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("car_park", car_park);
        result.put("bridge", bridge);


        return result;
    }

    //// setters ////

    public void setName(String value){
        this.name = value;
    }

    public void setCar_park(String value){
        this.car_park = value;
    }

    public void setBridge(String value){
        this.bridge = value;
    }



    public String toString(){
        return this.name;
    }

}

