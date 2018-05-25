package fan.com.jd02.Twofragment.Twofragment_listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import fan.com.jd02.R;
import fan.com.jd02.Twofragment.recyitem.OnItemClickListener;
import fan.com.jd02.shangpinxiangqing.ShangpinxqActivity;

public class Two_recyActivity extends AppCompatActivity {

    private RecyclerView two_recyclerview;
    private Two_recycler_Adapter two_recycler_adapter;
    private List<Two_listview.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_two_recy);
        initView();
        yunxing();

    }

    private void yunxing() {
        String psid = getIntent().getStringExtra("pcid");
        RequestParams params = new RequestParams("https://www.zhaoapi.cn/product/getProducts");
        params.addQueryStringParameter("pscid", psid + "");
        //    params.addQueryStringParameter("password","123");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.d("Two_recyActivity", result.toString());
                Gson gson = new Gson();
                Two_listview two_listview = gson.fromJson(result.toString(), Two_listview.class);
                data = two_listview.getData();
                two_recycler_adapter = new Two_recycler_Adapter(Two_recyActivity.this, data);
                two_recyclerview.setLayoutManager(new LinearLayoutManager(Two_recyActivity.this, LinearLayoutManager.VERTICAL, false));
                two_recyclerview.addItemDecoration(new DividerItemDecoration(Two_recyActivity.this, DividerItemDecoration.VERTICAL));
                two_recyclerview.setAdapter(two_recycler_adapter);
                two_recycler_adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Log.d("Two_recyActivity", "点击:" + position);
                        int pid = data.get(position).getPid();
                        Log.d("Two_recyActivity", "pid:" + pid);
                        Intent intent = new Intent(Two_recyActivity.this, ShangpinxqActivity.class);
                        intent.putExtra("pid", pid + "");
                        startActivity(intent);
                    }
                    @Override
                    public void onLongClick(int position) {
                    }
                });
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("Two_recyActivity", "01");
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("Two_recyActivity", "02");
            }

            @Override
            public void onFinished() {
                Log.d("Two_recyActivity", "03");
            }
        });
    }


    private void initView() {
        two_recyclerview = (RecyclerView) findViewById(R.id.two_recyclerview);
    }
}
