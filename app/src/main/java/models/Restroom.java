package models;

import java.io.Serializable;

/**
 * Created by AKINDE-PETERS on 6/9/2016.
 */
public class Restroom implements Serializable{

    public String name,car_park,bridge;

    public Restroom(){}

    public Restroom(String name,String car_park,String bridge){

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
