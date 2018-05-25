package fan.com.jd02.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamlive.upmarqueeview.UPMarqueeView;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fan.com.jd02.R;
import fan.com.jd02.appquanju.OnItemClickListener;
import fan.com.jd02.shangpinxiangqing.ShangpinxqActivity;
import fan.com.jd02.shouye.MVP.ShouyePP;
import fan.com.jd02.shouye.MVP.ShouyeV;
import fan.com.jd02.shouye.ashouyemian.Api;
import fan.com.jd02.shouye.ashouyemian.BannerImageLoad;
import fan.com.jd02.shouye.ashouyemian.RvRecommendAdapter;
import fan.com.jd02.shouye.ashouyemian.ShouyeBean;
import fan.com.jd02.shouye.daBanner.DaBannerActivity;
import fan.com.jd02.sousuo.SousuoActivity;
import okhttp3.ResponseBody;

/**
 * Created by fxf on 2018.05.15.
 */

public class OneFragment extends Fragment implements ShouyeV {
    @BindView(R.id.one_banner)
    Banner one_banner;
    @BindView(R.id.one_recycler)
    RecyclerView one_recycler;
    @BindView(R.id.shaomiao)
    TextView shaomiao;
    @BindView(R.id.one_edit)
    EditText one_edit;
    @BindView(R.id.xiaoxi)
    TextView xiaoxi;
    private Unbinder unbinder;
    private ShouyePP shouyePP;
    private View view;
    private final static int REQUEST_CODE = 9999;

    private UPMarqueeView upview1;
    List<String> data = new ArrayList<>();
    List<View> views = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);

        shouyePP = new ShouyePP();
        shouyePP.attachView(this);
        shouyePP.getData(Api.SHOUYE2, 1 + "");
        //运动时权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        }
        upview1 = (UPMarqueeView) view.findViewById(R.id.upview1);
        initdata();
        initView();
        return view;

    }
    /**
     * 初始化界面程序
     */
    private void initView() {
        setView();
        upview1.setViews(views);
        /**
         * 设置item_view的监听
         */
        upview1.setOnItemClickListener(new UPMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(getActivity(), "你点击了第几个items" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);

            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), position + "你点击了" + data.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), position + "你点击了" + data.get(position + 1).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            //进行对控件赋值
            tv1.setText(data.get(i).toString());
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).toString());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        data = new ArrayList<>();
        data.add("家人给2岁孩子喝这个，孩子智力倒退10岁!!!");
        data.add("iPhone8最感人变化成真，必须买买买买!!!!");
        data.add("简直是白菜价！日本玩家33万甩卖15万张游戏王卡");
        data.add("iPhone7价格曝光了！看完感觉我的腰子有点疼...");
        data.add("主人内疚逃命时没带够，回废墟狂挖30小时！");
//        data.add("竟不是小米乐视！看水抢了骁龙821首发了！！！");

    }


    /**
     * 处理二维码扫描结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            ShouyeBean shouyeBean = new Gson().fromJson(string, ShouyeBean.class);
            final List<ShouyeBean.DataBean> data = shouyeBean.getData();
            Log.d("OneFragment", data.toString());
            final ArrayList<String> list01 = new ArrayList<>();
            int size = data.size();
            for (int i = 0; i < size; i++) {
                list01.add(data.get(i).getIcon() + "");
            }
            //设置图片加载器
            one_banner.setImageLoader(new BannerImageLoad());
            //设置含有图片路径的集合
            one_banner.setImages(list01);
            //设置延迟时间
            one_banner.setDelayTime(3000);
            //是否自动轮播
            one_banner.isAutoPlay(true);
            //开启
            one_banner.start();

            one_banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(getActivity(), DaBannerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", list01);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            ShouyeBean.TuijianBean tuijian = shouyeBean.getTuijian();
            final List<ShouyeBean.TuijianBean.ListBean> list = tuijian.getList();
            RvRecommendAdapter adater = new RvRecommendAdapter(getActivity(), list);

            one_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            one_recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            one_recycler.setAdapter(adater);
            adater.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ShangpinxqActivity.class);
                    intent.putExtra("pid", list.get(position).getPid() + "");
                    int pid = list.get(position).getPid();
                    Log.d("AAAAA", "pid:" + pid);
                    startActivity(intent);

                }

                @Override
                public void onLongItemClick(int position) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (shouyePP == null) {
            shouyePP.dettachView();
        }
    }

    @OnClick({R.id.shaomiao, R.id.one_edit, R.id.xiaoxi})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.shaomiao:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.one_edit:
                Intent intent1 = new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent1);
                break;
            case R.id.xiaoxi:
                break;
        }
    }
}
