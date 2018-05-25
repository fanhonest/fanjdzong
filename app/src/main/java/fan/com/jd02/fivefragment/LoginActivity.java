package fan.com.jd02.fivefragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fan.com.jd02.MainActivity;
import fan.com.jd02.R;
import fan.com.jd02.fivefragment.bean.LoginBean;
import fan.com.jd02.fivefragment.mvp.p.LoginPresenter;
import fan.com.jd02.fivefragment.mvp.v.MyView;

public class LoginActivity extends AppCompatActivity implements MyView.LoginView {

    @BindView(R.id.editText)
    EditText tel;
    @BindView(R.id.editText2)
    EditText pwd;
    @BindView(R.id.button)
    Button btn;
    @BindView(R.id.button2)
    Button reg;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //调用p层
        presenter = new LoginPresenter(this);
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  pref.edit().clear().commit();


                Toast.makeText(LoginActivity.this, "不会", Toast.LENGTH_SHORT).show();
                SharedPreferences pref =getSharedPreferences("date", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("name", "");

                editor.putInt("uid", 0);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void success(LoginBean loginBean) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        SharedPreferences pref =getSharedPreferences("date", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", loginBean.getData().getMobile());
        int uid = loginBean.getData().getUid();
        editor.putInt("uid", uid);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        // finish();

    }

    @Override
    public void failed(int code) {
        Toast.makeText(this, "登录失败！请检查登录信息" + code, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                if (!TextUtils.isEmpty(tel.getText().toString()) && !TextUtils.isEmpty(pwd.getText().toString())) {
                    //如果都不为空,请求接口
                    presenter.getData(tel.getText().toString(), pwd.getText().toString());
                } else {
                    Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button2:
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                break;
        }
    }
}
