package fan.com.jd02.Twofragment.MVP.P;

/**
 * Created by fxf on 2018.04.20.
 */

public interface ILoginPresenter {
    void login();
    void onFailed(String msg);
    void onSccuess(Object o);
    //销毁的方法
    void onDestory();
}
