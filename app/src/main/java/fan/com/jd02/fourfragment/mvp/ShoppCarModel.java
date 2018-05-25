package fan.com.jd02.fourfragment.mvp;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;

import fan.com.jd02.fourfragment.ShoppCarBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fxf on 2018.05.24.
 */

public class ShoppCarModel implements IShoppCarModel {
    private Handler handler = new Handler();

    @Override
    public void getCarInfo(String uid, String token,final IShooppPrenster iShooppPrenster) {
        OkHttpClient okHttpClient = new OkHttpClient();
   //     https://www.zhaoapi.cn/product/getCarts?uid=13034&token=9927167DCA58A048D9DB3B5B006148FF
        Request request = new Request.Builder()
                .get()
                .url("https://www.zhaoapi.cn/product/getCarts?uid=" + uid +"&token="+token+ "&source=android")
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iShooppPrenster.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    final ShoppCarBean shoppCarBean = gson.fromJson(str, ShoppCarBean.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            iShooppPrenster.onSuccess(shoppCarBean.getData());

                        }
                    });
                }
            }
        });
    }
}
