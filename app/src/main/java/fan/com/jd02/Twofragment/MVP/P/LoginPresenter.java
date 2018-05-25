package fan.com.jd02.Twofragment.MVP.P;

import com.google.gson.Gson;

import java.util.List;

import fan.com.jd02.Twofragment.MVP.M.LoginModel;
import fan.com.jd02.Twofragment.MVP.V.IMainView;
import fan.com.jd02.Twofragment.bean.Userbean;


/**
 * Created by fxf on 2018.04.20.
 */

public class LoginPresenter implements ILoginPresenter {
    private IMainView iMainView;
    private LoginModel loginModel;
    public LoginPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        loginModel = new LoginModel();
    }

    @Override
    public void login() {
        loginModel.login( this);
    }

    @Override
    public void onFailed(String msg) {
        if ( null== iMainView) {
            return;
        }
        iMainView.onFailed(msg);
    }

    @Override
    public void onSccuess(Object o) {
        //判空处理
        if ( null== iMainView) {
            return;
        }
        //iMainView.onSuccess(o);
        Gson gson = new Gson();
        Userbean userbean = gson.fromJson(o.toString(), Userbean.class);
        String code = userbean.getCode();


        if ("0".equals(code)) {
            List<Userbean.DataBean> data = userbean.getData();
            iMainView.onSuccess(data);
        } else {
            iMainView.onFailed(userbean.getMsg());
        }
    }

    @Override
    public void onDestory() {
        if (null != iMainView) {
            iMainView = null;
        }
    }
}
