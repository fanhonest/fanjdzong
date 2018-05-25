package fan.com.jd02.Twofragment.MVP02.P;

import com.google.gson.Gson;

import java.util.List;

import fan.com.jd02.Twofragment.MVP02.M.LoginModel02;
import fan.com.jd02.Twofragment.MVP02.V.IMainView02;
import fan.com.jd02.Twofragment.bean.ZiUserbean;


/**
 * Created by fxf on 2018.04.20.
 */

public class LoginPresenter02  implements ILoginPresenter02 {
    private IMainView02 iMainView02;
    private LoginModel02 loginModel02;
    public LoginPresenter02(IMainView02 iMainView02) {
        this.iMainView02 = iMainView02;
        loginModel02 = new LoginModel02();
    }
    @Override
    public void login02(String cid) {
        loginModel02.login02(cid, this);
    }

    @Override
    public void onFailed02(String msg) {
        if (null == iMainView02) {
            return;
        }
        iMainView02.onFailed02(msg);
    }

    @Override
    public void onSccuess02(Object o) {
        //判空处理
        if ( null== iMainView02) {
            return;
        }
        //iMainView.onSuccess(o);
        Gson gson = new Gson();
        ZiUserbean ziUserbean = gson.fromJson(o.toString(), ZiUserbean.class);
        String code = ziUserbean.getCode();
        List<ZiUserbean.DataBean> data = ziUserbean.getData();
        if ("0".equals(code)) {
            iMainView02.onSuccess02(data);
        } else {
            iMainView02.onFailed02(ziUserbean.getMsg());
        }
    }

    @Override
    public void onDestory02() {
        if (null != iMainView02) {
            iMainView02 =null ;
        }
    }
}
