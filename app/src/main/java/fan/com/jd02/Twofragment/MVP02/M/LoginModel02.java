package fan.com.jd02.Twofragment.MVP02.M;

import android.os.Handler;

import java.io.IOException;

import fan.com.jd02.Twofragment.MVP02.P.ILoginPresenter02;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by fxf on 2018.04.20.
 */

public class LoginModel02  implements ILoginModel02{

    private static Handler handler = new Handler();
    @Override
    public void login02(String cid, final ILoginPresenter02 iLoginPresenter02) {
        String url = "https://www.zhaoapi.cn/product/getProductCatagory?cid="+cid;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {//子线程
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginPresenter02.onFailed02(e.getMessage());
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
                            iLoginPresenter02.onSccuess02(result);
                        }
                    });
                }
            }
        });



    }
}
