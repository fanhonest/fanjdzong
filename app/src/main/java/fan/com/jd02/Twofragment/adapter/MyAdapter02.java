package fan.com.jd02.Twofragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fan.com.jd02.R;
import fan.com.jd02.Twofragment.Twofragment_listview.Two_recyActivity;
import fan.com.jd02.Twofragment.bean.ZiUserbean;
import fan.com.jd02.Twofragment.recyitem.OnItemClickListener;

/**
 * Created by fxf on 2018.04.20.
 */

public class MyAdapter02 extends RecyclerView.Adapter<MyAdapter02.ViewHolder> {

    private Context context;
    List<ZiUserbean.DataBean> data;

    public MyAdapter02(Context conext, List<ZiUserbean.DataBean> data) {
        this.context = conext;
        this.data = data;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item02, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.item02text.setText(data.get(position).getName());
        List<ZiUserbean.DataBean.ListBean> list = data.get(position).getList();
        MyAdapter03 myAdapter03 = new MyAdapter03(context, list);
        holder.item02_recy.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        holder.item02_recy.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        holder.item02_recy.setAdapter(myAdapter03);

        myAdapter03.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position01) {
                Toast.makeText(context, data.get(position).getList().get(position01).getName(), Toast.LENGTH_SHORT).show();
                //https://www.zhaoapi.cn/product/getProductDetail?pid=1
                int pscid = data.get(position).getList().get(position01).getPscid();
                Intent intent = new Intent(context, Two_recyActivity.class);
                intent.putExtra("pcid", pscid+"");
                context.startActivity(intent);


            }

            @Override
            public void onLongClick(int position) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item02text;
        private final RecyclerView item02_recy;

        public ViewHolder(View itemView) {
            super(itemView);
            item02text = itemView.findViewById(R.id.item02_text);
            item02_recy = itemView.findViewById(R.id.item02_rexyclerview);

        }
    }
}