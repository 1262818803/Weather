package ui.com.coolweather.Note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class NotesSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_NOTE="note";
    public static final String TABLE_NAME_DEL="note_del";
    public static final String NOTE_ID="_id";
    public static final String NOTE_CONTENT="content";
    public static final String NOTE_DATE="date";
    public NotesSQLiteOpenHelper(Context context) {
        super(context,"note",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TABLE_NAME_NOTE+"("+NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NOTE_CONTENT+" TEXT NOT NULL DEFAULT\"\"," +
                NOTE_DATE+" TEXT NOT NULL DEFAULT\"\""+")";
        Log.i("SQL",sql);
        db.execSQL(sql);
        String del_sql="CREATE TABLE "+TABLE_NAME_DEL+"("+NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NOTE_CONTENT+" TEXT NOT NULL DEFAULT\"\"," +
                NOTE_DATE+" TEXT NOT NULL DEFAULT\"\""+")";
        Log.i("SQL",del_sql);
        db.execSQL(del_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}