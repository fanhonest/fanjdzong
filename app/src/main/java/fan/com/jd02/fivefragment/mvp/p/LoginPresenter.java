package fan.com.jd02.fivefragment.mvp.p;


import fan.com.jd02.fivefragment.bean.LoginBean;
import fan.com.jd02.fivefragment.mvp.m.LoginModel;
import fan.com.jd02.fivefragment.mvp.m.ModelCallBack;
import fan.com.jd02.fivefragment.mvp.v.MyView;

/**
 * Created by fxf on 2018.05.23.
 */

public class LoginPresenter {
    LoginModel loginModel = new LoginModel();
    MyView.LoginView loginView;
    public LoginPresenter(MyView.LoginView loginView) {
        this.loginView = loginView;
    }

    public void getData(String tel, String pwd) {
        loginModel.getLoginData(tel,pwd, new ModelCallBack.LoginCallBack() {
            @Override
            public void success(LoginBean dengluBean) {
                loginView.success(dengluBean);
                System.out.println("登录p数据："+dengluBean.toString());
            }

            @Override
            public void failed(Throwable code) {
                System.out.println("登录p错误："+code);
            }
        });
    }
}
