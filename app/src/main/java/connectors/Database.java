package connectors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import models.Classroom;
import models.Lab;
import models.Office;
import models.Restroom;

/**
 * Created by AKINDE-PETERS on 6/8/2016.
 */
public class Database extends SQLiteOpenHelper {

    static String DATABASE_NAME = "local_db";
    static int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory)null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE classrooms (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,car_park TEXT,bridge TEXT,url TEXT);");
        db.execSQL("CREATE TABLE offices (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,car_park TEXT,bridge TEXT,url TEXT);");
        db.execSQL("CREATE TABLE labs (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,car_park TEXT,bridge TEXT,url TEXT);");
        db.execSQL("CREATE TABLE restrooms (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,car_park TEXT,bridge TEXT,url TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void saveClassroom(ArrayList <Classroom> classrooms){
        for(int g = 0;g< classrooms.size();g++){
            String query = " SELECT * FROM classrooms WHERE name = '"+classrooms.get(g).getName()+"'";
            Cursor cursor = getReadableDatabase().rawQuery(query,null);

            ContentValues cv = new ContentValues();
            cv.put("name", classrooms.get(g).getName());
            cv.put("car_park", classrooms.get(g).getCar_park());
            cv.put("bridge", classrooms.get(g).getBridge());
            cv.put("url", classrooms.get(g).getUrl());
            if(cursor.getCount() + 0 == 0){
                getWritableDatabase().insert("classrooms","name", cv);
            }else{
                String[] args={classrooms.get(g).getName()};
                getWritableDatabase().update("classrooms",cv,"name=?", args);
            }
            cursor.close();
        }
    }

    public ArrayList <Classroom> getClassrooms(){
        ArrayList <Classroom> classroom = new ArrayList<Classroom>();
        String query = " SELECT * FROM classrooms";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            classroom.add(new Classroom(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
                    ));
        }
        close();
        return classroom;
    }

    public ArrayList <Classroom> getSearchClassrooms(String code){
        ArrayList <Classroom> classroom = new ArrayList<Classroom>();
        String query = " SELECT * FROM classrooms WHERE name LIKE '%"+code+"%'";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            classroom.add(new Classroom(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return classroom;
    }

    public void saveOffice(ArrayList <Office> offices){
        for(int g = 0;g< offices.size();g++){
            String query = " SELECT * FROM offices WHERE name = '"+offices.get(g).getName()+"'";
            Cursor cursor = getReadableDatabase().rawQuery(query,null);

            ContentValues cv = new ContentValues();
            cv.put("name", offices.get(g).getName());
            cv.put("car_park", offices.get(g).getCar_park());
            cv.put("bridge", offices.get(g).getBridge());
            cv.put("url", offices.get(g).getUrl());
            if(cursor.getCount() + 0 == 0){
                getWritableDatabase().insert("offices","name", cv);
            }else{
                String[] args={offices.get(g).getName()};
                getWritableDatabase().update("offices",cv,"name=?", args);
            }
            cursor.close();
        }
    }

    public ArrayList <Office> getOffices(){
        ArrayList <Office> office = new ArrayList<Office>();
        String query = " SELECT * FROM offices";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            office.add(new Office(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return office;
    }

    public ArrayList <Office> getSearchOffices(String code){
        ArrayList <Office> office = new ArrayList<Office>();
        String query = " SELECT * FROM offices WHERE name LIKE '%"+code+"%'";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            office.add(new Office(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return office;
    }

    public void saveLab(ArrayList <Lab> labs){
        for(int g = 0;g< labs.size();g++){
            String query = " SELECT * FROM labs WHERE name = '"+labs.get(g).getName()+"'";
            Cursor cursor = getReadableDatabase().rawQuery(query,null);

            ContentValues cv = new ContentValues();
            cv.put("name", labs.get(g).getName());
            cv.put("car_park", labs.get(g).getCar_park());
            cv.put("bridge", labs.get(g).getBridge());
            cv.put("url", labs.get(g).getUrl());
            if(cursor.getCount() + 0 == 0){
                getWritableDatabase().insert("labs","name", cv);
            }else{
                String[] args={labs.get(g).getName()};
                getWritableDatabase().update("labs",cv,"name=?", args);
            }
            cursor.close();
        }
    }

    public ArrayList <Lab> getLabs(){
        ArrayList <Lab> lab = new ArrayList<Lab>();
        String query = " SELECT * FROM labs";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            lab.add(new Lab(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return lab;
    }

    public ArrayList <Lab> getSearchLabs(String code){
        ArrayList <Lab> lab = new ArrayList<Lab>();
        String query = " SELECT * FROM labs WHERE name LIKE '%"+code+"%'";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            lab.add(new Lab(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return lab;
    }



    public void saveRestroom(ArrayList <Restroom> restrooms){
        for(int g = 0;g< restrooms.size();g++){
            String query = " SELECT * FROM restrooms WHERE name = '"+restrooms.get(g).getName()+"'";
            Cursor cursor = getReadableDatabase().rawQuery(query,null);

            ContentValues cv = new ContentValues();
            cv.put("name", restrooms.get(g).getName());
            cv.put("car_park", restrooms.get(g).getCar_park());
            cv.put("bridge", restrooms.get(g).getBridge());
            cv.put("url", restrooms.get(g).getUrl());
            if(cursor.getCount() + 0 == 0){
                getWritableDatabase().insert("restrooms","name", cv);
            }else{
                String[] args={restrooms.get(g).getName()};
                getWritableDatabase().update("restrooms",cv,"name=?", args);
            }
            cursor.close();
        }
    }



    public ArrayList <Restroom> getRestrooms(){
        ArrayList <Restroom> restroom = new ArrayList<Restroom>();
        String query = " SELECT * FROM restrooms";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            restroom.add(new Restroom(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return restroom;
    }

    public ArrayList <Restroom> getSearchRestrooms(String code){
        ArrayList <Restroom> restroom = new ArrayList<Restroom>();
        String query = " SELECT * FROM restrooms WHERE name = '"+code+"'";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            restroom.add(new Restroom(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("car_park")),
                    cursor.getString(cursor.getColumnIndex("bridge")),
                    cursor.getString(cursor.getColumnIndex("url"))
            ));
        }
        close();
        return restroom;
    }



    public void removeClasses() {
        String query = "DELETE FROM classrooms";
        getWritableDatabase().execSQL(query);
    }

    public void removeRestrooms() {
        String query = "DELETE FROM restrooms";
        getWritableDatabase().execSQL(query);
    }

    public void removeOffices() {
        String query = "DELETE FROM offices";
        getWritableDatabase().execSQL(query);
    }

    public void removeLabs() {
        String query = "DELETE FROM labs";
        getWritableDatabase().execSQL(query);
    }

}
