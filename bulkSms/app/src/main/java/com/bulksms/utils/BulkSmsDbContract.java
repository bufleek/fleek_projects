package com.bulksms.utils;

import android.provider.BaseColumns;

public final class BulkSmsDbContract {
    private BulkSmsDbContract(){}

    public static final class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_CONTACT_NAME = "name";
        public static final String COLUMN_CONTACT_PHONE = "phone";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_CONTACT_PHONE + " TEXT NOT NULL)";
    }
}
