package ui.com.coolweather.Note;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ui.com.coolweather.R;

public class Add_note extends AppCompatActivity {
    ImageView bt_c, bt_s;
    TextView tv_time;
    EditText tv_date;
    NotesSQLiteOpenHelper sh;
    SQLiteDatabase dbread;
    public static int ENTER_STATE = 0;
    public static String last_content;
    public static int id;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        bt_c = (ImageView) findViewById(R.id.cancel);
        bt_s = (ImageView) findViewById(R.id.save);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_date = (EditText) findViewById(R.id.et_date);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(date);
        tv_time.setText(dateString);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );

        sh = new NotesSQLiteOpenHelper(this);
        dbread = sh.getReadableDatabase();
        Bundle bundle = this.getIntent().getExtras();
        last_content = bundle.getString("info");
        Log.i("last_content", last_content);
        tv_date.setText(last_content);
        bt_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = tv_date.getText().toString();
                Log.d("content", content);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = sdf.format(date);
                String sql;
                String sql_count = "SELECT COUNT(*) FROM note";
                SQLiteStatement st = dbread.compileStatement(sql_count);
                long count = st.simpleQueryForLong();
                Log.d("count", count + "");
                Log.d("ENTER_STATE", ENTER_STATE + "");

                if (ENTER_STATE == 0) {
                    if (!content.equals("")) {
                        sql = "insert into " + NotesSQLiteOpenHelper.TABLE_NAME_NOTE +
                                " values(" + count + "," + "'" + content + "'" + "," + "'" + dateString + "')";
                        Log.d("LOG", sql);
                        dbread.execSQL(sql);
                    }
                } else {
                    Log.d("执行命令", "执行了该函数");
                    String updatesql = "update note set content='"
                            + content + "' where_id=" + id;
                    dbread.execSQL(updatesql);

                }
                Intent data = new Intent();
                setResult(2, data);
                finish();
            }
        });

        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
