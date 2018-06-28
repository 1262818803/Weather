package ui.com.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ui.com.coolweather.register.Mysqlite;
import ui.com.coolweather.register.St;

public class Login extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    Button button;
    TextView button2;
    /*    @BindView(R.id.activity_main)
        RelativeLayout activityMain;*/
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//验证密码是否有特殊符号或长度不满6位
    private SQLiteDatabase w;
    private SQLiteDatabase r;
    private Mysqlite mys;
    private List<St> mdata;
    private String name;
    private String pass;
    public SharedPreferences pre;
    public SharedPreferences.Editor editor;
    public CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        button=(Button)findViewById(R.id.button);
        button2=(TextView)findViewById(R.id.button2);
        pre= PreferenceManager.getDefaultSharedPreferences(this);
        mys = new Mysqlite(this, "zhu_c", null, 1);//使用halper创建数据库
        r=mys.getReadableDatabase();
        w=mys.getWritableDatabase();
        mdata=new ArrayList<St>();
        Cursor query = r.rawQuery("select * from user_mo", null);
        while(query.moveToNext()){
            int index1 = query.getColumnIndex("name");
            int index2 = query.getColumnIndex("pass");
            name = query.getString(index1);
            pass = query.getString(index2);
            mdata.add(new St(0, name, pass));
        }
        remember=(CheckBox)findViewById(R.id.remember);
        boolean isRemember=pre.getBoolean("rememberPassword",false);
        if(isRemember){
            String account=pre.getString("name","");
            String password=pre.getString("password","");
            editText.setText(account);
            editText2.setText(password);
            remember.setChecked(true);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = editText.getText().toString().trim();
                String pass1 = editText2.getText().toString().trim();
                if (name1.equals(name)&&pass1.equals(pass)){
                    editor=pre.edit();
                    if(remember.isChecked()){
                        editor.putBoolean("rememberPassword",true);
                        editor.putString("name",name);
                        editor.putString("password",pass);
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, UserActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,"账号与密码输入不正确",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this, ZhuActivity.class);
                startActivity(intent1);
            }
        });
    }
}
