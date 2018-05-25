package fan.com.jd02.fivefragment.mvp.m;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fan.com.jd02.fivefragment.GetDataInterface;
import fan.com.jd02.fivefragment.bean.LoginBean;
import fan.com.jd02.fivefragment.retrofit.LoggingInterceptor;
import fan.com.jd02.fivefragment.retrofit.RetrofitUnitl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by fxf on 2018.05.23.
 */

public class LoginModel {

    //https://www.zhaoapi.cn/user/login?mobile=18631090582&password=888888
    public void getLoginData(final String tel, String pwd, final ModelCallBack.LoginCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000,TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        //使用Retrofit结合RxJava，okhttp封装类的单例模式
        Map<String,String> map = new HashMap<>();
        map.put("mobile",tel);
        map.put("password",pwd);

        RetrofitUnitl.getInstance("https://www.zhaoapi.cn",ok)
                .setCreate(GetDataInterface.class)
                .login(map)
                .subscribeOn(Schedulers.io())               //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())  //最后在主线程中执行

                //进行事件的订阅，使用Consumer实现
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        //请求成功时返回数据
                        callBack.success(loginBean);
                        System.out.println("m登录信息：" + loginBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.failed(throwable);
                    }
                });
    }
}
