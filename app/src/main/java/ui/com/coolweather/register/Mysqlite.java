package ui.com.coolweather.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2018/6/6.
 */

public class Mysqlite extends SQLiteOpenHelper{
    private String sql="create table if not exists user_mo(_id Integer primary key autoincrement,name varchar(30) not null,pass varchar(50) not null)";

    public Mysqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
