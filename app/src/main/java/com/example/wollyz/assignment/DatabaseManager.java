package com.example.wollyz.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

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


    public Cursor getLandmarksByCountry(String country) {
        int countryId;
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


    //display landmarks from List
    public String[] getListLandmark() {

        //get ids of landmarks in list table
        int[] landmarkIds = getLandmarksById();
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
        Log.e("Query","Insert Completed");
        return landmarknames;

    }

    //return all the ids of landmarks in ListTable
    private int[] getLandmarksById()
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] listLandmark = new String[]{COLUMN_lANDMARK_ID,COLUMN_VISITSTATUS,COLUMN_VISITDATE};
        String sqlTables = TABLE_LIST;
        qb.setTables(sqlTables);
        Cursor d = qb.query(db,
                listLandmark,
                null,
                null,
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

    public void addLandmarkToVisit(String name){
        int Lid = getLandmarkId(name);

        //update status of landmark
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
