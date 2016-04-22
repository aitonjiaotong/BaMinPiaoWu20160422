package bamin.com.kepiao.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shane on 2016/2/27.
 */
public class MySqLite extends SQLiteOpenHelper {

    public MySqLite(Context context, int version) {
        super(context, "text.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE setout (id INTEGER PRIMARY KEY AUTOINCREMENT,addr_name VARCHAR(20))";
        db.execSQL(sql);
        String sql_arrive = "CREATE TABLE arrive (id INTEGER PRIMARY KEY AUTOINCREMENT,addr_name VARCHAR(20))";
        db.execSQL(sql_arrive);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
