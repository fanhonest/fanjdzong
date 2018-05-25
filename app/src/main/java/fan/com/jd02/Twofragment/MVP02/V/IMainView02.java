package fan.com.jd02.Twofragment.MVP02.V;


import java.util.List;

import fan.com.jd02.Twofragment.bean.ZiUserbean;

/**
 * Created by fxf on 2018.04.20.
 */

public interface IMainView02 {
    void onFailed02(String msg);
    void onSuccess02(List<ZiUserbean.DataBean> data);
}
