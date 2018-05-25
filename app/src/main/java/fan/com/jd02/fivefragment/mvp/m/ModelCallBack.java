package fan.com.jd02.fivefragment.mvp.m;


import fan.com.jd02.fivefragment.bean.LoginBean;
import fan.com.jd02.fivefragment.bean.PersonInfoBean;
import fan.com.jd02.fivefragment.bean.RegBean;

/**
 * Created by fxf on 2018.05.23.
 */

public interface ModelCallBack {
    public interface LoginCallBack{
        //登录时，数据获取成功的方法，返回一个值表示登陆成功
        public void success(LoginBean loginBean);
        //登录时，数据获取失败的方法，返回一个int值响应码表示登陆失败
        public void failed(Throwable code);
    }

    public interface RegCallBack {
        //注册时，数据获取成功的方法，返回一个值表示登陆成功
        public void success(RegBean regBean);
        //注册时，数据获取失败的方法，返回一个int值响应码表示登陆失败
        public void failed(Throwable code);
    }

    //个人中心
    public interface PersonCallBack{
        void success(PersonInfoBean personBean);
        void failed(Throwable code);
    }

}
