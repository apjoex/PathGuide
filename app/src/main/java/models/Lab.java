package models;

import java.io.Serializable;

/**
 * Created by AKINDE-PETERS on 6/9/2016.
 */
public class Lab implements Serializable{

    public String name,car_park,bridge,url;

    public Lab(){}

    public Lab(String name,String car_park,String bridge, String url){

        this.name = name;
        this.car_park = car_park;
        this.bridge = bridge;
        this.url = url;


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

    public String getUrl(){
        return this.url;
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

    public void setUrl(String value){
        this.url = value;
    }




    public String toString(){
        return this.name;
    }

}
