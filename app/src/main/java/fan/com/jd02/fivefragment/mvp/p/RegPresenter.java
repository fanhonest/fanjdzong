package fan.com.jd02.fivefragment.mvp.p;


import fan.com.jd02.fivefragment.bean.RegBean;
import fan.com.jd02.fivefragment.mvp.m.ModelCallBack;
import fan.com.jd02.fivefragment.mvp.m.RegModel;
import fan.com.jd02.fivefragment.mvp.v.MyView;

/**
 * Created by fxf on 2018.05.23.
 */

public class RegPresenter {
    RegModel regModel = new RegModel();
    MyView.RegView regView;
    public RegPresenter(MyView.RegView regView) {
        this.regView = regView;
    }

    public void getData(String tel, String pwd) {
        regModel.getRegData(tel,pwd, new ModelCallBack.RegCallBack() {

            @Override
            public void success(RegBean regBean) {
                regView.sucess(regBean);
                System.out.println("注册p数据："+regBean.toString());
            }

            @Override
            public void failed(Throwable code) {
                System.out.println("注册p错误："+code);
            }
        });
    }
}
