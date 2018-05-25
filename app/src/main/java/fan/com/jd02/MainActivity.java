package fan.com.jd02;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hjm.bottomtabbar.BottomTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import fan.com.jd02.fragment.FiveFragment;
import fan.com.jd02.fragment.FourFragment;
import fan.com.jd02.fragment.OneFragment;
import fan.com.jd02.fragment.ThreeFragment;
import fan.com.jd02.fragment.TwoFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottom_tab_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bottom_tab_bar.init(getSupportFragmentManager())
              //  .setImgSize(50,50)
                .setFontSize(15)
                .setTabPadding(4,6,10)
                .setChangeColor(Color.RED,Color.DKGRAY)
                .addTabItem("首页",null, OneFragment.class)
                .addTabItem("分类",null, TwoFragment.class)
                .addTabItem("发现",null, ThreeFragment.class)
                .addTabItem("购物车",null, FourFragment.class)
                .addTabItem("我的",null, FiveFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {

                    }
                });
    }
}
