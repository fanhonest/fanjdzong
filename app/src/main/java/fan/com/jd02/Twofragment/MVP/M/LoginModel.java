package fan.com.jd02.Twofragment.MVP.M;

import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fan.com.jd02.Twofragment.MVP.P.ILoginPresenter;
import fan.com.jd02.Twofragment.okhttp.OkHttpUtils;
import fan.com.jd02.Twofragment.okhttp.OnFinishListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by fxf on 2018.04.20.
 */

public class LoginModel implements ILoginModel, OnFinishListener {
    private static Handler handler = new Handler();

    @Override
    public void login(final ILoginPresenter iLoginPresenter) {
        OkHttpUtils instance = OkHttpUtils.getInstance();
        Map<String, String> map = new HashMap<>();
        instance.doGet("https://www.zhaoapi.cn/product/getCatagory", map, this);
        String url = "https://www.zhaoapi.cn/product/getCatagory";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginPresenter.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String result = response.body().string();//JSON
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLoginPresenter.onSccuess(result);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void onSuccess(Object obj) {

    }
}
