package fan.com.jd02.fourfragment.mvp;

import java.util.List;

import fan.com.jd02.fourfragment.ShoppCarBean;

/**
 * Created by fxf on 2018.05.24.
 */

public interface IShooppPrenster {
    void onSuccess(List<ShoppCarBean.DataBean> data);
    void onFailed(String msg);
    void onDestory();
    void getCarInfo(String uid,String token);
}
