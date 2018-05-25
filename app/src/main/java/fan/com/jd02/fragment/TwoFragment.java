package fan.com.jd02.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fan.com.jd02.R;
import fan.com.jd02.Twofragment.MVP.P.LoginPresenter;
import fan.com.jd02.Twofragment.MVP.V.IMainView;
import fan.com.jd02.Twofragment.MVP02.P.LoginPresenter02;
import fan.com.jd02.Twofragment.MVP02.V.IMainView02;
import fan.com.jd02.Twofragment.adapter.MyAdapter;
import fan.com.jd02.Twofragment.adapter.MyAdapter02;
import fan.com.jd02.Twofragment.bean.Userbean;
import fan.com.jd02.Twofragment.bean.ZiUserbean;
import fan.com.jd02.Twofragment.recyitem.OnItemClickListener;

/**
 * Created by fxf on 2018.04.25.
 */

public class TwoFragment extends Fragment implements IMainView,IMainView02 {


    @BindView(R.id.Recycler01)
    RecyclerView Recycler01;
    @BindView(R.id.Recycler02)
    RecyclerView Recycler02;
    private View view;
    private Unbinder unbinder;
    private LoginPresenter loginPresenter;
    private MyAdapter myAdapter;
    private int cid;
    private LoginPresenter02 loginPresenter02;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.twofragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        loginPresenter02 = new LoginPresenter02(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.login();
        loginPresenter02.login02(1 + "");
        return view;

    }

    @Override
    public void onSuccess(final List<Userbean.DataBean> data) {
        //data01.addAll(data);
        Log.d("MainActivity", "data:" + data.toString());
        //  Log.d("MainActivity", data01.toString());
        myAdapter = new MyAdapter(getActivity(), data);
        Recycler01.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        Recycler01.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        Recycler01.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

                cid = data.get(position).getCid();
                Log.d("MainActivity", "cid:" + cid);
                   Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                loginPresenter02.login02(cid + "");
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFailed02(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess02(List<ZiUserbean.DataBean> data) {
        MyAdapter02 myAdapter02 = new MyAdapter02(getActivity(), data);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        Recycler02.setLayoutManager(layoutmanager);
        Recycler02.setAdapter(myAdapter02);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //解绑，防止内存泄露
        loginPresenter.onDestory();
        loginPresenter02.onDestory02();
    }
}