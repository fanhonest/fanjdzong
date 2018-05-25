package fan.com.jd02.fivefragment;

import java.util.Map;

import fan.com.jd02.fivefragment.bean.LoginBean;
import fan.com.jd02.fivefragment.bean.PersonInfoBean;
import fan.com.jd02.fivefragment.bean.RegBean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by fxf on 2018.05.23.
 */

public interface GetDataInterface {
    //注册的接口
    //https://www.zhaoapi.cn/user/reg?mobile=18631090582&password=888888
    @FormUrlEncoded
    @POST("/user/reg")
    Observable<RegBean> reg(@FieldMap Map<String, String> map);

    //登录的接口
    //https://www.zhaoapi.cn/user/login?mobile=18631090582&password=888888
    @FormUrlEncoded
    @POST("/user/login")
    Observable<LoginBean> login(@FieldMap Map<String, String> map);

    //个人中心接口：
    //https://www.zhaoapi.cn/user/getUserInfo?uid=100
    @GET("/user/getUserInfo")
    Observable<PersonInfoBean> person(@Query("uid") int uid);
}
