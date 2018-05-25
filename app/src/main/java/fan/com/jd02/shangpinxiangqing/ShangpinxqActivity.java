package fan.com.jd02.shangpinxiangqing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fan.com.jd02.R;
import fan.com.jd02.shangpinxiangqing.MVP.SpXqBean;
import fan.com.jd02.shangpinxiangqing.MVP.SpxqP;
import fan.com.jd02.shangpinxiangqing.MVP.SpxqinterV;
import fan.com.jd02.shangpinxiangqing.fengxing.User;
import fan.com.jd02.shouye.ashouyemian.BannerImageLoad;
import fan.com.jd02.shouye.daBanner.DaBannerActivity;
import okhttp3.ResponseBody;

public class ShangpinxqActivity extends AppCompatActivity implements SpxqinterV {

    @BindView(R.id.shangpin_banner)
    Banner shangpin_banner;
    @BindView(R.id.shangpin_text01)
    TextView shangpin_text01;
    @BindView(R.id.shangpin_text02)
    TextView shangpin_text02;
    @BindView(R.id.shangpin_text03)
    TextView shangpin_text03;
    @BindView(R.id.shangpin_jiarugouwuche)
    TextView shangpin_jiarugouwuche;

    @BindView(R.id.new_login_btn)
    Button mNewLoginBtn;
    @BindView(R.id.new_login_close)
    Button mNewLoginClose;
    @BindView(R.id.new_login_shareqq)
    Button mNewLoginShareqq;
    @BindView(R.id.new_login_shareqzone)
    Button mNewLoginShareqzone;

    private SpxqP spxqP;
    private UserInfo mInfo;
    public static Tencent mTencent;
    public static String mAppid = "1106062414";
    private String detailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpinxq);

        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");

        Log.d("ShangpinxqActivity", pid);
        ButterKnife.bind(this);
        // https://www.zhaoapi.cn/product/getProductDetail
        spxqP = new SpxqP();
        spxqP.attachView(this);
        spxqP.getData(ApiSpxq.SPXQAPI, pid);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }

    }

    @OnClick({R.id.shangpin_jiarugouwuche, R.id.new_login_btn, R.id.new_login_close, R.id.new_login_shareqq, R.id.new_login_shareqzone})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.shangpin_jiarugouwuche:

                //  https://www.zhaoapi.cn/product/addCart?uid=13034&pid=63&token=9927167DCA58A048D9DB3B5B006148FF
                RequestParams params = new RequestParams("https://www.zhaoapi.cn/product/addCart");
                params.addQueryStringParameter("uid", "13034");
                params.addQueryStringParameter("pid", "62");
                params.addQueryStringParameter("token", "9927167DCA58A048D9DB3B5B006148FF");
                params.addQueryStringParameter("source", "android");

                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //解析result

                        Toast.makeText(ShangpinxqActivity.this, "架构成功", Toast.LENGTH_SHORT).show();
                    }

                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });


                break;
            case R.id.new_login_btn:
                onClickLogin();
                break;
            case R.id.new_login_close:
                mTencent.logout(this);//注销登录
                break;
            case R.id.new_login_shareqq:
                onClickShare();
                break;
            case R.id.new_login_shareqzone:
                shareToQQzone();
                break;
        }
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            Log.d("ShangpinxqActivity", string.toString());
            final ArrayList<String> list = new ArrayList<>();
            SpXqBean spXqBean = new Gson().fromJson(string, SpXqBean.class);
            String subhead = spXqBean.getData().getSubhead();
            String title = spXqBean.getData().getTitle();
            shangpin_text01.setText(subhead);
            shangpin_text02.setText(title);
            detailUrl = spXqBean.getData().getDetailUrl();

            shangpin_text03.setText(spXqBean.getData().getCreatetime());
            String images = spXqBean.getData().getImages();
            Log.d("ShangpinxqActivity", "images" + images);
            if (images.indexOf("|") != -1) {
                System.out.println("包含该字符串");
                String result = images.substring(0, images.indexOf("|"));
                Log.d("MyAdapter", result);
                //加载图片  url=result
                list.add(result);

            } else {
                //加载图片  url=iamges
                list.add(images);
            }

            //设置图片加载器
            shangpin_banner.setImageLoader(new BannerImageLoad());
            //设置含有图片路径的集合
            shangpin_banner.setImages(list);
            //设置延迟时间
            shangpin_banner.setDelayTime(3000);
            //是否自动轮播
            shangpin_banner.isAutoPlay(true);
            //开启
            shangpin_banner.start();

            shangpin_banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(ShangpinxqActivity.this, DaBannerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", list);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (spxqP == null) {
            spxqP.dettachView();
        }
    }


    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
            Log.i("lkei", values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    /***
     * QQ平台返回返回数据个体 loginListener的values
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(ShangpinxqActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(ShangpinxqActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(ShangpinxqActivity.this, "登录成功", Toast.LENGTH_LONG).show();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }

    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     *
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    Gson gson = new Gson();
                    User user = gson.fromJson(response.toString(), User.class);
                    if (user != null) {
//                            tv_name.setText("昵称：" + user.getNickname() + "  性别:" + user.getGender() + "  地址：" + user.getProvince() + user.getCity());
//                            tv_content.setText("头像路径：" + user.getFigureurl_qq_2());
                        // Picasso.with(ShangpinxqActivity.this).load(response.getString("figureurl_qq_2")).into(imageView);
                        Toast.makeText(ShangpinxqActivity.this, "youshuju", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    };

    //qq分享
    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");

        //分享网址链接
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                detailUrl);
        //分享中的图片
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "fxf");
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(ShangpinxqActivity.this, params, new BaseUiListener1());
    }

    //回调接口  (成功和失败的相关操作)
    private class BaseUiListener1 implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete(response);
        }

        protected void doComplete(Object values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }

    @SuppressWarnings("unused")
    private void shareToQQzone() {
        try {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                    QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "切切歆语");
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "sss");
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    detailUrl);
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                    QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            Tencent mTencent = Tencent.createInstance("1106062414",
                    ShangpinxqActivity.this);
            mTencent.shareToQzone(ShangpinxqActivity.this, params,
                    new BaseUiListener1());
        } catch (Exception e) {
        }
    }


}
