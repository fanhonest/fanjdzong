package fan.com.jd02.fivefragment;

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
import fan.com.jd02.R;
import fan.com.jd02.fivefragment.bean.RegBean;
import fan.com.jd02.fivefragment.mvp.p.RegPresenter;
import fan.com.jd02.fivefragment.mvp.v.MyView;

public class RegActivity extends AppCompatActivity  implements MyView.RegView{

    @BindView(R.id.e2_ditText)
    EditText regTel;
    @BindView(R.id.e2_ditText2)
    EditText regPwd;
    @BindView(R.id.zhucebutton)
    Button zhucebutton;
    private RegPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        //调用p层
        presenter = new RegPresenter(this);
    }
    @OnClick(R.id.zhucebutton)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.zhucebutton:
                if (!TextUtils.isEmpty(regTel.getText().toString()) && !TextUtils.isEmpty(regPwd.getText().toString())) {

                    if (regTel.getText().toString().length() < 6){
                        Toast.makeText(this, "密码长度不能小于 6 位", Toast.LENGTH_SHORT).show();
                    }else {
                        // mvp请求注册的接口
                        presenter.getData(regTel.getText().toString(),regPwd.getText().toString());
                    }

                } else {
                    Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void failed(int code) {
        Toast.makeText(this, "注册失败！请检查网络"+ code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sucess(RegBean regBean) {
        Toast.makeText(this, "注册结果：      "+regBean.getMsg(), Toast.LENGTH_SHORT).show();

        if(regBean.getMsg().equals("注册成功")){
            try {
                Thread.sleep(2000);
                Toast.makeText(this, "即将跳转到登录页面", Toast.LENGTH_SHORT).show();
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
