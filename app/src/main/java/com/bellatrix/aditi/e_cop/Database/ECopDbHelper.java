package com.bellatrix.aditi.e_cop.Database;

/**
 * Created by Aditi on 07-04-2018.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bellatrix.aditi.e_cop.Database.Contract.*;

public class ECopDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "ECop.db";
    private static final int DATABASE_VERSION = 11;

    public ECopDbHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USER_TABLE="CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                UserEntry.COLUMN_ADHAAR_NUMBER + " TEXT PRIMARY KEY, " +
                UserEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL," +
                UserEntry.COLUMN_LAST_NAME + " TEXT," +
                UserEntry.COLUMN_EMAIL + " TEXT NOT NULL," +
                UserEntry.COLUMN_CONTACT_NUMBER + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_COP_TABLE="CREATE TABLE " + CopEntry.TABLE_NAME + " (" +
                CopEntry.COLUMN_COP_ID + " TEXT PRIMARY KEY, " +
                CopEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL," +
                CopEntry.COLUMN_LAST_NAME + " TEXT," +
                CopEntry.COLUMN_RANK + " TEXT NOT NULL," +
                CopEntry.COLUMN_POST + " TEXT NOT NULL," +
                CopEntry.COLUMN_STATION_POSTED + " TEXT" +"); ";

        final String SQL_CREATE_COURT_TABLE="CREATE TABLE " + CourtEntry.TABLE_NAME + " (" +
                CourtEntry.COLUMN_COURT_ID + " TEXT PRIMARY KEY, " +
                CourtEntry.COLUMN_TYPE + " TEXT NOT NULL," +
                CourtEntry.COLUMN_CITY + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_CREDENTIALS_TABLE="CREATE TABLE " + CredentialsEntry.TABLE_NAME + " (" +
                CredentialsEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
                CredentialsEntry.COLUMN_PASSWORD + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_COMPLAIN_TABLE="CREATE TABLE " + ComplainEntry.TABLE_NAME + " (" +
                ComplainEntry.COLUMN_COMPLAIN_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ComplainEntry.COLUMN_USER + " TEXT NOT NULL, " +
                ComplainEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                ComplainEntry.COLUMN_VICTIM + " TEXT," +
                ComplainEntry.COLUMN_FILED_DATE + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_FIR_TABLE="CREATE TABLE " + FIREntry.TABLE_NAME + " (" +
                FIREntry.COLUMN_FIR_NUMBER + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIREntry.COLUMN_USER + " TEXT NOT NULL, " +
                FIREntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                FIREntry.COLUMN_VICTIM + " TEXT," +
                FIREntry.COLUMN_ACCUSED + " TEXT," +
                FIREntry.COLUMN_TYPE + " TEXT NOT NULL," +
                FIREntry.COLUMN_LODGED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" +"); ";

        final String SQL_CREATE_CASE_TABLE="CREATE TABLE " + CasesEntry.TABLE_NAME + " (" +
                CasesEntry.COLUMN_CASE_NUMBER + " TEXT PRIMARY KEY, " +
                CasesEntry.COLUMN_FIR_NUMBER + " TEXT NOT NULL," +
                CasesEntry.COLUMN_COURT_ID + " TEXT NOT NULL," +
                CasesEntry.COLUMN_DESCRIPTION+ " TEXT NOT NULL, " +
                CasesEntry.COLUMN_LAWYER + " TEXT NOT NULL," +
                CasesEntry.COLUMN_PROSECUTOR + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_UPDATES_TABLE="CREATE TABLE " + UpdatesEntry.TABLE_NAME + " (" +
                UpdatesEntry.COLUMN_UPDATE_NUMBER + " TEXT NOT NULL, " +
                UpdatesEntry.COLUMN_CASE_NUMBER + " TEXT NOT NULL," +
                UpdatesEntry.COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                UpdatesEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                "PRIMARY KEY("+UpdatesEntry.COLUMN_CASE_NUMBER+","+UpdatesEntry.COLUMN_UPDATE_NUMBER+")); ";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COP_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COURT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CREDENTIALS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COMPLAIN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FIR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CASE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_UPDATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ Contract.ComplainEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.CopEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.CredentialsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.FIREntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.CasesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.UpdatesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.CourtEntry.TABLE_NAME);

        onCreate(db);
    }
}