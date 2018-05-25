package fan.com.jd02.Twofragment.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import fan.com.jd02.R;
import fan.com.jd02.Twofragment.bean.ZiUserbean;
import fan.com.jd02.Twofragment.recyitem.OnItemClickListener;

/**
 * Created by fxf on 2018.04.20.
 */

public class MyAdapter03 extends RecyclerView.Adapter<MyAdapter03.ViewHolder> {

    private Context context;
    List<ZiUserbean.DataBean.ListBean> data;

    public MyAdapter03(Context conext, List<ZiUserbean.DataBean.ListBean> data) {
        this.context = conext;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item02_02, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }


    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.item02text.setText(data.get(position).getName());
        Uri uri = Uri.parse(data.get(position).getIcon());
        holder.simpleDraweeView.setImageURI(uri);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item02text;
        private final SimpleDraweeView simpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            item02text = itemView.findViewById(R.id.item02_02_text);
            simpleDraweeView = itemView.findViewById(R.id.item02_02_sim);

        }
    }
}