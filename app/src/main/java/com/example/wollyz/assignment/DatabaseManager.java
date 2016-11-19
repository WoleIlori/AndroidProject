package com.example.wollyz.assignment;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

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

    public DatabaseManager(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteAssetHelper {

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //setForcedUpgrade();
        }

    }

    public Cursor getAllCountry(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] allCountry =new String[]{COLUMN_COUNTRYID, COLUMN_COUNTRYNAME};
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

    public Cursor getLandmarkByName(String name){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection =new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
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
        return b;
    }


    public Cursor getLandmarksByCountry(String country){
        int countryId;
        countryId = getCountryId(country);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] projection =new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
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
        Log.e("QUERY","query completed");
        return c;

    }

    public int getCountryId(String country){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] Country = new String[]{COLUMN_COUNTRYID, COLUMN_COUNTRYNAME};
        String sqlTables = TABLE_COUNTRY;
        String selection = COLUMN_COUNTRYNAME + "=?";
        String[] selectionArgs = {country};
        int id=0;

        qb.setTables(sqlTables);

        Cursor d = qb.query(db,
                Country,
                selection,
                selectionArgs,//selectionArgs
                null,
                null,
                null);
        if(d.getCount() > 0) {
            d.moveToFirst();
            id = d.getInt(0);
        }
        else{
            Log.e("Cursor", "Result not shown");
        }
        return id;
    }

    public Cursor getAllLandmarks()
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] allLandmark =new String[]{COLUMN_LANDMARKID,COLUMN_LANDMARKNAME,COLUMN_CITY,COLUMN_COUNTRY_ID,COLUMN_DESC};
        String sqlTables = TABLE_LANDMARK;

        qb.setTables(sqlTables);

        Cursor c = qb.query(db,
                allLandmark,
                null,
                null,
                null,
                null,
                null);
        return c;
    }

    public DatabaseManager open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() throws SQLException{
        DBHelper.close();
    }




}
