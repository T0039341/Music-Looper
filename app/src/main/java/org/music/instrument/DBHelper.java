package org.music.instrument;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import org.music.instrument.listeners.OnDatabaseChangedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by GAGAN.
 */
public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    private static final String LOG_TAG = "DBHelper";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;

    public static final String DATABASE_NAME = "saved_recordings.db";
    private static final int DATABASE_VERSION = 1;

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_recordings";

        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_RECORDING_NAME = "recording_name";
        public static final String COLUMN_NAME_RECORDING_FILE_PATH = "file_path";
        public static final String COLUMN_NAME_RECORDING_LENGTH = "length";
        public static final String COLUMN_NAME_TIME_ADDED = "time_added";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperItem.TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_RECORDING_NAME + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_RECORDING_LENGTH + " INTEGER " + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_TIME_ADDED + " INTEGER " + ")";

    @SuppressWarnings("unused")
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBHelperItem.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseChangedListener = listener;
    }

    /**
     * Get user recording based on user.
     * @param username
     * @return
     */
    public ArrayList<RecordingItem> getUserRecording(String username) {
        ArrayList<RecordingItem> recordingItems = new ArrayList<>();
        String[] projection = {
                DBHelperItem._ID,
                DBHelperItem.COLUMN_NAME_RECORDING_NAME,
                DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH,
                DBHelperItem.COLUMN_NAME_RECORDING_LENGTH,
                DBHelperItem.COLUMN_NAME_TIME_ADDED
        };

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(DBHelperItem.TABLE_NAME, projection, DBHelperItem.COLUMN_NAME_USER_NAME + "=?",
                new String[]{String.valueOf(username)}, null, null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                RecordingItem item = new RecordingItem();
                item.setId(cursor.getInt(cursor.getColumnIndex(DBHelperItem._ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_NAME)));
                item.setFilePath(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH)));
                item.setLength(cursor.getInt(cursor.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH)));
                item.setTime(cursor.getLong(cursor.getColumnIndex(DBHelperItem.COLUMN_NAME_TIME_ADDED)));
                recordingItems.add(item);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return recordingItems;
    }

    /**
     * Remove recording from database.
     * @param name
     */
    public void removeItemWithId(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = {String.valueOf(name)};
        db.delete(DBHelperItem.TABLE_NAME, DBHelperItem.COLUMN_NAME_RECORDING_NAME + "=?", whereArgs);
    }

    /**
     * Get the total number of recordings.
     * @param username
     * @return
     */
    public int getCount(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DBHelperItem._ID};
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, DBHelperItem.COLUMN_NAME_USER_NAME + "=?",
                new String[]{String.valueOf(username)}, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    public Context getContext() {
        return mContext;
    }

    public class RecordingComparator implements Comparator<RecordingItem> {
        public int compare(RecordingItem item1, RecordingItem item2) {
            Long o1 = item1.getTime();
            Long o2 = item2.getTime();
            return o2.compareTo(o1);
        }
    }

    /**
     * Insert the recording row in the databasae.
     *
     * @param username
     * @param recordingName
     * @param filePath
     * @param length
     * @return
     */
    public long addRecording(String username, String recordingName, String filePath, long length) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_USER_NAME, username);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, recordingName);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, filePath);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, length);
        cv.put(DBHelperItem.COLUMN_NAME_TIME_ADDED, System.currentTimeMillis());
        long rowId = db.insert(DBHelperItem.TABLE_NAME, null, cv);

        if (mOnDatabaseChangedListener != null) {
            mOnDatabaseChangedListener.onNewDatabaseEntryAdded();
        }

        return rowId;
    }
}
