package fan.com.jd02.Twofragment.Twofragment_listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fan.com.jd02.R;
import fan.com.jd02.Twofragment.recyitem.OnItemClickListener;


/**
 * Created by fxf on 2018.03.30.
 */

public class Two_recycler_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<Two_listview.DataBean> data;

    public Two_recycler_Adapter(Context context, List<Two_listview.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder
        //View view = View.inflate(context,R.layout.item,null);
        View view = LayoutInflater.from(context).inflate(R.layout.two_ercycler_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_top.setText(data.get(position).getSubhead() + "");
        myViewHolder.tv_bom.setText(data.get(position).getTitle() + "");
        String images = data.get(position).getImages();
        String substring = images.substring(0, images.indexOf("|"));

        Picasso.with(context).load(substring).into(((MyViewHolder) holder).sdv_miao);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // private final SimpleDraweeView sdv_miao;
        private final TextView tv_top;
        private final TextView tv_bom;
        private final ImageView sdv_miao;

        public MyViewHolder(View itemView) {
            super(itemView);
            sdv_miao = itemView.findViewById(R.id.two_recycler_item);
            tv_top = itemView.findViewById(R.id.two_recycler_text_item01);
            tv_bom = itemView.findViewById(R.id.two_recycler_text_item02);
        }
    }
}