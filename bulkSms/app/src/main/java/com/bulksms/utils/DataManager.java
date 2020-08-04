package com.bulksms.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bulksms.models.Contact;
import com.bulksms.utils.BulkSmsDbContract.ContactEntry;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;
    private DbOpenHelper dbHelper;
    private List<Contact> contacts = new ArrayList<Contact>();

    public DataManager(){}

    public DataManager(DbOpenHelper dbHelper){this.dbHelper = dbHelper;}

    public static DataManager getInstance(){
        if(ourInstance == null){
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public Cursor getContacts(){
        Cursor contactsCursor = null;
        String[] contactColumns = {
                ContactEntry._ID,
                ContactEntry.COLUMN_CONTACT_NAME,
                ContactEntry.COLUMN_CONTACT_PHONE
        };
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        contactsCursor = db.query(
                ContactEntry.TABLE_NAME,
                contactColumns,
                null, null, null, null, null
        );

        return contactsCursor;
    }

    public void insertContact(String name, String phone){
        ContentValues values = new ContentValues();
        values.put(ContactEntry.COLUMN_CONTACT_NAME, name);
        values.put(ContactEntry.COLUMN_CONTACT_PHONE, phone);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(ContactEntry.TABLE_NAME, null, values);
    }
}
