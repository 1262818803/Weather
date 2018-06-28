package ui.com.coolweather;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import ui.com.coolweather.register.Mysqlite;


/**
 * Created by asus on 2018/6/6.
 */

public class ZhuActivity extends AppCompatActivity {


    EditText editText3;
    EditText editText4;
    Button button3;
  /*  @BindView(R.id.activity_zhu)
    RelativeLayout activityZhu;*/
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{1,16}$";//验证密码是否有特殊符号或长度不满1位
    private SQLiteDatabase sdb;
    private Mysqlite mys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        ButterKnife.bind(this);
        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        button3=(Button)findViewById(R.id.button3);
        mys = new Mysqlite(this, "zhu_c", null, 1);//使用halper创建数据库
        sdb=mys.getWritableDatabase();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText3.getText().toString().trim();
                String pass = editText4.getText().toString().trim();
                if (name == null || "".equals(name) || pass == null || "".equals(pass)) {
                    Toast.makeText(ZhuActivity.this, "账号与密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String number = editText3.getText().toString();
                    boolean judge = isMobile(number);
                    String pa = editText4.getText().toString();
                    boolean judge1 = isPassword(pa);
                    if (judge == true && judge1 == true) {
                        Toast.makeText(ZhuActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        sdb.execSQL("insert into user_mo(name,pass)values('" + name + "','" + pass + "')");
                        Intent intent = new Intent(ZhuActivity.this,Login.class);
                        startActivity(intent);//启动跳转
                    } else {
                        Toast.makeText(ZhuActivity.this, "手机号码不法与密码不能有特殊符号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    /*
      验证手机格式
     */
    public static boolean isMobile(String number) {

        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
}
