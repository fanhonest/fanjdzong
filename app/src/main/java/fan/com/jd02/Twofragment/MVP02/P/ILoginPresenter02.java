package fan.com.jd02.Twofragment.MVP02.P;

/**
 * Created by fxf on 2018.04.20.
 */

public interface ILoginPresenter02 {
    void login02(String cid);
    void onFailed02(String msg);
    void onSccuess02(Object o);
    //销毁的方法
    void onDestory02();
}
