package ui.com.coolweather;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.com.coolweather.Note.Add_note;
import ui.com.coolweather.Note.MyAdapter;
import ui.com.coolweather.Note.NotesSQLiteOpenHelper;

public class UserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    ImageButton IB;
    ListView lv;
    int count = 1;
    MyAdapter adapter;
    NotesSQLiteOpenHelper sh;
    SQLiteDatabase dbread;
    List<Map<String, Object>> datelist;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        imageView=(ImageView)findViewById(R.id.imageView);
        init();
        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_note.ENTER_STATE = 0;
                Intent intent = new Intent(UserActivity.this, Add_note.class);
                Bundle bundle = new Bundle();
                bundle.putString("info", "");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        sh = new NotesSQLiteOpenHelper(this);
        dbread = sh.getReadableDatabase();
        //调用自定义方法，初始化页面（填充数据），更新页面
        RefreshNoteList();
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserActivity.this,WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void RefreshNoteList() {
        int size = datelist.size();
        if (size > 0) {
            //删除原来的数据，添加新的数据
            datelist.removeAll(datelist);
            //当数据发生变化时，通知需要更新Listview 显示
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);
        }
        datelist = getDate();
        //创建Myadapter适配器
        adapter = new MyAdapter(this, datelist);
        lv.setAdapter(adapter);
    }

    private List<Map<String, Object>> getDate() {
        Cursor curser = dbread.query("note", null, "content!=\"\"", null, null, null, null);
        while (curser.moveToNext()) {
            String name = curser.getString(curser.getColumnIndex("content"));
            String date = curser.getString(curser.getColumnIndex("date"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tv_content", name);
            map.put("tv_date", date);
            datelist.add(map);
        }
        curser.close();
        return datelist;
    }

    //点击之后，跳转到编辑页面
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
       Add_note.ENTER_STATE = 0;
        String content = lv.getItemAtPosition(arg2) + "";
        String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));
        Log.d("CONTENT", content1);
        Cursor c = dbread.query("note", null, "content=" + "'" + content1 + "'", null
                , null, null, null);
        while (c.moveToNext()) {
            String No = c.getString(c.getColumnIndex("_id"));
            Log.d("TEXT", No);
            Intent intent1 = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("info", content1);
           Add_note.id = Integer.parseInt(No);
            intent1.putExtras(bundle);
            intent1.setClass(UserActivity.this, Add_note.class);
            startActivityForResult(intent1, 1);
        }
    }

    //长按后，弹出对话框，是否删除数据
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        final int n = arg2;
        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除该标签");
        builder.setMessage("确定删除吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content = lv.getItemAtPosition(n) + "";
                String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));
                Cursor c = dbread.query("note", null, "content=" + "'" + content1 + "'", null
                        , null, null, null);
                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex("_id"));;
                    String sql_del = "update note set content='' where _id=" + id;
                    dbread.execSQL(sql_del);
                    RefreshNoteList();
                }
            }

        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
        return  true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            RefreshNoteList();
        }
    }
    public void init() {
        IB = (ImageButton) findViewById(R.id.Ib);
        lv = (ListView) findViewById(R.id.lv_write);
        datelist = new ArrayList<Map<String, Object>>();

    }
}
