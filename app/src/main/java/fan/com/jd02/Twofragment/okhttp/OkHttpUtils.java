package fan.com.jd02.Twofragment.okhttp;

import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fxf on 2018.04.20.
 */

public class OkHttpUtils {
    private static OkHttpUtils OK_HTTP_UTILS =null ;
    //定义一个Handler
    private static Handler handler = new Handler();
    private OkHttpUtils() {
    }

    /**
     * 得到OkHttpUtils实例对象
     *
     * @return
     */
    public static OkHttpUtils getInstance() {
        if ( null== OK_HTTP_UTILS) {
            synchronized (OkHttpUtils.class) {
                if (null == OK_HTTP_UTILS) {
                    OK_HTTP_UTILS = new OkHttpUtils();
                }
            }
        }
        return OK_HTTP_UTILS;
    }

    /**
     * Get请求
     *
     * @param path             http://www.baidu.com?key=value&key=value
     * @param onFinishListener
     */
    public void doGet(String path, Map<String, String> map, final OnFinishListener onFinishListener) {
        //Http:/www.baidu.com?key=ddd&
        StringBuffer sb = new StringBuffer();
        sb.append(path);
        //判断path是否包含一个
        if (sb.indexOf("?") != -1) {

            //判断"?"是否在最后一个
            if (sb.indexOf("?") != sb.length() - 1) {
                sb.append("&");
            }
        } else {
            sb.append("?");
        }

        //遍历map集合中所有请求参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        //判断get请求路径最后是否包含一个"&"
        if (sb.lastIndexOf("&") != -1) {
            sb.deleteCharAt(sb.length() - 1);
        }


        OkHttpClient okHttpClient = new OkHttpClient();
        //构建请求项
        Request request = new Request.Builder()
                .get()
                .url(sb.toString())
                .build();

        Call call = okHttpClient.newCall(request);
        //execute 子线程  enqueue //
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //请求失败
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器的响应结果
                final String result = response.body().string();
                //请求成功
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //主线程当中执行
                        onFinishListener.onSuccess(result);
                    }
                });
            }
        });
    }

    /**
     * post请求
     *
     * @param path
     * @param map
     * @param onFinishListener
     */
    public void doPost(String path, Map<String, String> map, final OnFinishListener onFinishListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        //构建参数的对象
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合，获取用户的key/value
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        /*FormBody body = new FormBody.Builder()
                .add("mobile",mobile)
                .add("password",password)
                .build();*/
        //构建请求项
        final Request request = new Request.Builder()
                .post(builder.build())
                .url(path)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();//这句话必须放到子线程里
                System.out.println("OkHttUitls 线程名 : " + Thread.currentThread().getName());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onSuccess(result);
                    }
                });

            }
        });
    }

    /**
     * 上传文件
     *
     * @param path             上传文件路径
     * @param params           上传的参数
     * @param onFinishListener 监听
     */
    public void upLoadFile(String path, Map<String, Object> params, final OnFinishListener onFinishListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设设置类型 以表单的形式提交
        builder.setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object object = entry.getValue();
            if (!(object instanceof File)) {
                builder.addFormDataPart(entry.getKey(), object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart(entry.getKey(), file.getName().trim(),
                        RequestBody.create(MediaType.parse("img/png"), file));
                //img/png -> application/octet-stream
            }
        }
        Request request = new Request.Builder()
                .post(builder.build())
                .url(path)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //判断服务器是否响应成功
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onFinishListener.onSuccess(result);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //服务器响应失败
                            onFinishListener.onFailed(response.message());
                        }
                    });
                }
            }
        });
    }
}
