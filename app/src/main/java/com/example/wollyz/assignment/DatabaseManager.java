package com.example.wollyz.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Wollyz on 12/11/2016.
 */
public class DatabaseManager {
    public static final String TABLE_COUNTRY = "Country";
    public static final String COLUMN_COUNTRYID = "_id";
    public static final String COLUMN_COUNTRYNAME = "country_name";

    public static final String TABLE_LANDMARK = "Landmark";
    public static final String COLUMN_LANDMARKID = "_id";
    public static final String COLUMN_LANDMARKNAME = "landmark_name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY_ID = "country_id";
    public static final String COLUMN_DESC = "description";

    public static final String TABLE_LIST = "List";
    public static final String COLUMN_lANDMARK_ID = "_id";
    public static final String COLUMN_VISITSTATUS = "visit_status";
    public static final String COLUMN_VISITDATE = "visit_date";

    public static final String DATABASE_NAME = "myDb";
    public static final int DATABASE_VERSION = 1;

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteAssetHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    }

    public Cursor getAllCountry() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] allCountry = new String[]{COLUMN_COUNTRYID, COLUMN_COUNTRYNAME};
        String sqlTables = TABLE_COUNTRY;

        qb.setTables(sqlTables);

        Cursor c = qb.query(db,
                allCountry,
                null,
                null,
                null,
                null,
                null);
        return c;
    }

    public String getLandmarkByName(String name) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection = new String[]{COLUMN_LANDMARKID, COLUMN_LANDMARKNAME, COLUMN_CITY, COLUMN_COUNTRY_ID, COLUMN_DESC};
        String selection = COLUMN_LANDMARKNAME + " = ?";
        String[] selectionArgs = {name};
        String sqlTables = TABLE_LANDMARK;

        qb.setTables(sqlTables);

        Cursor b = qb.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(b.getCount()> 0)
        {
            b.moveToFirst();
        }
        else
        {
            Log.e("Cursor", "Result not shown");
        }
        return b.getString(1);
    }


    //return the names of landmark that are in the country passed as an argument
    public Cursor getLandmarksByCountry(String country) {
        int countryId;

        //implementing join- get id of country and select landmarks that contain the id of that country
        countryId = getCountryId(country);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection = new String[]{COLUMN_LANDMARKID, COLUMN_LANDMARKNAME, COLUMN_CITY, COLUMN_COUNTRY_ID, COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;
        String selection = COLUMN_COUNTRY_ID + "=?";
        String[] selectionArgs = {String.valueOf(countryId)};

        qb.setTables(sqlTables);
        Cursor c = qb.query(db,
                projection,
                selection,
                selectionArgs,//selectionArgs
                null,
                null,
                null);
        return c;

    }

    //get id of Country from Country Table
    private int getCountryId(String country) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] Country = new String[]{COLUMN_COUNTRYID, COLUMN_COUNTRYNAME};
        String sqlTables = TABLE_COUNTRY;
        String selection = COLUMN_COUNTRYNAME + "=?";
        String[] selectionArgs = {country};
        int id = 0;

        qb.setTables(sqlTables);

        Cursor d = qb.query(db,
                Country,
                selection,
                selectionArgs,//selectionArgs
                null,
                null,
                null);
        if (d.getCount() > 0) {
            d.moveToFirst();
            id = d.getInt(0);
        } else {
            Log.e("Cursor", "Result not shown");
        }
        return id;
    }

    public DatabaseManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() throws SQLException {
        DBHelper.close();
    }


    //add Landmark to List Table
    public void addLandmarkToList(String name) {
        //get landmark id
        int lId = getLandmarkId(name);
        String status = "N";

        //put id into list  and status
        ContentValues values = new ContentValues();
        values.put(COLUMN_lANDMARK_ID, lId);
        values.put(COLUMN_VISITSTATUS, status);
        long newRowId = db.insert(TABLE_LIST, null, values);


    }

    //return id of selected landmark
    private int getLandmarkId(String name)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] Landmark = new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;
        String selection = COLUMN_LANDMARKNAME + "=?";
        String[] selectionArgs = {name};

        qb.setTables(sqlTables);

        Cursor d = qb.query(db,
                Landmark,
                selection,
                selectionArgs,//selectionArgs
                null,
                null,
                null);
        d.moveToFirst();
        return d.getInt(0);
    }


    //display landmarks that are not visited from ListTable
    public String[] getLandmarksNotVisited() {

        //get ids of landmarks in list table
        int[] landmarkIds = getLandmarksNotVisitedId();
        String[] landmarknames = new String[landmarkIds.length];
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection = new String[]{COLUMN_LANDMARKID, COLUMN_LANDMARKNAME, COLUMN_CITY, COLUMN_COUNTRY_ID, COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;
        qb.setTables(sqlTables);

        //get the name of each landmark and add to array
        for(int i=0; i< landmarknames.length; i++)
        {
            Cursor d = qb.query(db,
                    projection,
                    COLUMN_LANDMARKID + " = " + String.valueOf(landmarkIds[i]),
                    null,//selectionArgs
                    null,
                    null,
                    null);
            d.moveToFirst();
            landmarknames[i] = d.getString(1);
        }
        return landmarknames;

    }

    //return ids of landmarks not visited from ListTable
    //i.e. Landmarks whose visit_status is N
    private int[] getLandmarksNotVisitedId()
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] listLandmark = new String[]{COLUMN_lANDMARK_ID,COLUMN_VISITSTATUS,COLUMN_VISITDATE};
        String sqlTables = TABLE_LIST;
        String where = COLUMN_VISITSTATUS + "=?";
        String[] whereArgs = {"N"};
        qb.setTables(sqlTables);
        Cursor d = qb.query(db,
                listLandmark,
                where,
                whereArgs,
                null,
                null,
                null);

        int[]listIds = new int[d.getCount()];
        int i = 0;
        d.moveToFirst();
        while(!d.isAfterLast()){
            listIds[i] = d.getInt(0);
            i++;
            d.moveToNext();
        }
        return listIds;
    }


    //return description of landmarks
    public Cursor getLandmarkContents(String landmark){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] Landmark = new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;
        String selection = COLUMN_LANDMARKNAME + "=?";
        String[] selectionArgs = {landmark};

        qb.setTables(sqlTables);

        return qb.query(db,
                Landmark,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    //return names of landmarks visited
    //i.e. Landmarks whose visit_status is Y
    public String[] getLandmarksVisited() {

        //get ids of landmarks in list table
        int[] landmarkIds = getLandmarksVisitedId();
        String[] landmarknames = new String[landmarkIds.length];
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection = new String[]{COLUMN_LANDMARKID, COLUMN_LANDMARKNAME, COLUMN_CITY, COLUMN_COUNTRY_ID, COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;
        qb.setTables(sqlTables);

        for(int i=0; i< landmarknames.length; i++)
        {
            Cursor d = qb.query(db,
                    projection,
                    COLUMN_LANDMARKID + " = " + String.valueOf(landmarkIds[i]),
                    null,//selectionArgs
                    null,
                    null,
                    null);
            d.moveToFirst();
            landmarknames[i] = d.getString(1);
        }
        return landmarknames;

    }

    //return the ids of visited landmarks from ListTable
    private int[] getLandmarksVisitedId()
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] listLandmark = new String[]{COLUMN_lANDMARK_ID,COLUMN_VISITSTATUS,COLUMN_VISITDATE};
        String sqlTables = TABLE_LIST;
        String where = COLUMN_VISITSTATUS + "=?";
        String[] whereArgs = {"Y"};
        qb.setTables(sqlTables);
        Cursor d = qb.query(db,
                listLandmark,
                where,
                whereArgs,
                null,
                null,
                null);

        int[]listIds = new int[d.getCount()];
        int i = 0;
        d.moveToFirst();
        while(!d.isAfterLast()){
            listIds[i] = d.getInt(0);
            i++;
            d.moveToNext();
        }
        Log.e("Query","Update Completed");
        return listIds;
    }

    //update visit_status of the landmark passed
    public void updateList(String name){
        int Lid = getLandmarkId(name);
        ContentValues val = new ContentValues();
        val.put(COLUMN_VISITSTATUS, "Y");

        //update row where the id is equal to landmarkid
        String where = COLUMN_lANDMARK_ID + " LIKE ? ";
        String[] whereArgs = {String.valueOf(Lid)};

        int count = db.update(
                TABLE_LIST,
                val,
                where,
                whereArgs);
    }

    //delete landmark from user's list
    public void deleteLandmark(String name){
        //get Id of landmark
        int lId = getLandmarkId(name);

        String where = COLUMN_lANDMARK_ID + " LIKE ? ";
        String[] whereArgs = {String.valueOf(lId)};
        db.delete(TABLE_LIST, where, whereArgs);

    }

    public Cursor getAllLandmarks()
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] Landmark = new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;

        qb.setTables(sqlTables);

        return qb.query(db,
                Landmark,
                null,
                null,
                null,
                null,
                null);
    }

}
