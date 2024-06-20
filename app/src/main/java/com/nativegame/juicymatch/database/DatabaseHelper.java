package com.nativegame.juicymatch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nativegame.juicymatch.item.Item;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int INIT_COIN = 100;

    private static final String DATABASE_NAME = "game_data.db";
    private static final int DATABASE_VERSION = 1;

    private static final String STAR_TABLE_NAME = "STAR_TABLE";
    private static final String STAR_TABLE_COLUMN_LEVEL_NAME = "Level";
    private static final String STAR_TABLE_COLUMN_STAR_COUNT = "Star";

    private static final String ITEM_TABLE_NAME = "ITEM_TABLE";
    private static final String ITEM_TABLE_COLUMN_ITEM_NAME = "Name";
    private static final String ITEM_TABLE_COLUMN_ITEM_COUNT = "Number";

    private static DatabaseHelper INSTANCE;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseHelper(context);
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStarTableStatement = "CREATE TABLE " + STAR_TABLE_NAME + " ("
                + STAR_TABLE_COLUMN_LEVEL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STAR_TABLE_COLUMN_STAR_COUNT + " INTEGER"
                + ")";

        String createItemTableStatement = "CREATE TABLE " + ITEM_TABLE_NAME + " ("
                + ITEM_TABLE_COLUMN_ITEM_NAME + " TEXT PRIMARY KEY, "
                + ITEM_TABLE_COLUMN_ITEM_COUNT + " INTEGER"
                + ")";

        sqLiteDatabase.execSQL(createStarTableStatement);
        sqLiteDatabase.execSQL(createItemTableStatement);
        initItem(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private void initItem(SQLiteDatabase sqLiteDatabase) {
        ContentValues coinValues = new ContentValues();
        coinValues.put(ITEM_TABLE_COLUMN_ITEM_NAME, Item.COIN);
        coinValues.put(ITEM_TABLE_COLUMN_ITEM_COUNT, INIT_COIN);

        ContentValues hammerValues = new ContentValues();
        hammerValues.put(ITEM_TABLE_COLUMN_ITEM_NAME, Item.HAMMER);
        hammerValues.put(ITEM_TABLE_COLUMN_ITEM_COUNT, 0);

        ContentValues bombValues = new ContentValues();
        bombValues.put(ITEM_TABLE_COLUMN_ITEM_NAME, Item.BOMB);
        bombValues.put(ITEM_TABLE_COLUMN_ITEM_COUNT, 0);

        ContentValues gloveValues = new ContentValues();
        gloveValues.put(ITEM_TABLE_COLUMN_ITEM_NAME, Item.GLOVE);
        gloveValues.put(ITEM_TABLE_COLUMN_ITEM_COUNT, 0);

        sqLiteDatabase.insert(ITEM_TABLE_NAME, null, coinValues);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, null, hammerValues);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, null, bombValues);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, null, gloveValues);
    }

    public boolean updateItemCount(String name, int count) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_TABLE_COLUMN_ITEM_COUNT, count);

        int id = db.update(ITEM_TABLE_NAME, values,
                ITEM_TABLE_COLUMN_ITEM_NAME + " = ? ",
                new String[]{name});

        db.close();

        if (id == -1) {
            return false;
        }
        return true;
    }

    public int getItemCount(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ITEM_TABLE_NAME,
                new String[]{ITEM_TABLE_COLUMN_ITEM_COUNT},
                ITEM_TABLE_COLUMN_ITEM_NAME + " =? ",
                new String[]{name},
                null, null, null, null);

        int number = -1;
        if (cursor.moveToFirst()) {
            number = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return number;
    }

    public boolean insertLevelStar(int star) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STAR_TABLE_COLUMN_STAR_COUNT, star);

        long id = db.insert(STAR_TABLE_NAME, null, values);

        db.close();

        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateLevelStar(int levelID, int star) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STAR_TABLE_COLUMN_STAR_COUNT, star);

        int id = db.update(STAR_TABLE_NAME, values,
                STAR_TABLE_COLUMN_LEVEL_NAME + " = ? ",
                new String[]{String.valueOf(levelID)});

        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int getLevelStar(int levelID) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(STAR_TABLE_NAME,
                new String[]{STAR_TABLE_COLUMN_STAR_COUNT},
                STAR_TABLE_COLUMN_LEVEL_NAME + " =? ",
                new String[]{String.valueOf(levelID)},
                null, null, null, null);

        int star = -1;
        if (cursor.moveToFirst()) {
            star = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return star;
    }

    public List<Integer> getAllLevelStars() {
        List<Integer> stars = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + STAR_TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                stars.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return stars;
    }

}
