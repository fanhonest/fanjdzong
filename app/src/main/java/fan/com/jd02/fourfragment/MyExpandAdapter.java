package fan.com.jd02.fourfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fan.com.jd02.R;

/**
 * Created by fxf on 2018.05.24.
 */

public class MyExpandAdapter extends BaseExpandableListAdapter {
    private List<ShoppCarBean.DataBean> data;
    private Context context;
    private ModifyGoodsItemNumberListener modifyGoodsItemNumberListener;
    private CheckGroupItemListener checkGroupItemListener;
    //接收是否处于编辑状态的一个boolean值
    private boolean isEditor;

    //商家以及商品是否被选中的一个监听
    public void setCheckGroupItemListener(CheckGroupItemListener checkGroupItemListener){
        this.checkGroupItemListener = checkGroupItemListener;
    }

    //设置商品的加减监听
    public void setModifyGoodsItemNumberListener(ModifyGoodsItemNumberListener modifyGoodsItemNumberListener){
        this.modifyGoodsItemNumberListener = modifyGoodsItemNumberListener;
    }

    //是否显示删除按钮
    public void showDeleteButton(boolean isEditor){
        this.isEditor = isEditor;
        //刷新适配器
        notifyDataSetChanged();
    }

    public MyExpandAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<ShoppCarBean.DataBean> data){
        this.data=data;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return data !=null?data.size() :0;
    }

    @Override
    public int getChildrenCount(int i) {
        return data!=null&&data.get(i).getList()!=null?data.get(i).getList().size() :0;
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.layout_group_item,viewGroup,false);
        }
        CheckBox ck_group_choosed = view.findViewById(R.id.ck_group_choosed);
        ck_group_choosed.setText(data.get(groupPosition).getSellerName());

        if(data.get(groupPosition).isGroupChoosed()){
            ck_group_choosed.setChecked(true);
        }else{
            ck_group_choosed.setChecked(false);
        }

        //ck_group_choosed.setChan
        ck_group_choosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGroupItemListener.checkGroupItem(groupPosition,((CheckBox)view).isChecked());
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {

        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.layout_child_item,viewGroup,false);

        }
        //商品选择
        CheckBox ck_child_choosed = view.findViewById(R.id.ck_child_choose);
        //商品图片
        ImageView iv_show_pic = view.findViewById(R.id.iv_show_pic);
        //商品主标题
        TextView tv_commodity_name = view.findViewById(R.id.tv_commodity_name);
        //商品副标题
        TextView tv_commodity_attr = view.findViewById(R.id.tv_commodity_attr);
        //商品价格
        TextView tv_commodity_price = view.findViewById(R.id.tv_commodity_price);
        //商品数量
        TextView tv_commodity_num = view.findViewById(R.id.tv_commodity_num);
        //商品减
        TextView iv_sub = view.findViewById(R.id.iv_sub);
        //商品加减中的数量变化
        final TextView tv_commodity_show_num = view.findViewById(R.id.tv_commodity_show_num);
        //商品加
        TextView iv_add = view.findViewById(R.id.iv_add);
        //删除按钮
        Button btn_commodity_delete = view.findViewById(R.id.btn_commodity_delete);

        //设置文本信息
        tv_commodity_name.setText(data.get(i).getList().get(i1).getTitle());
        tv_commodity_attr.setText(data.get(i).getList().get(i1).getSubhead());
        tv_commodity_price.setText("￥"+data.get(i).getList().get(i1).getPrice());
        tv_commodity_num.setText("x"+data.get(i).getList().get(i1).getNum());
        tv_commodity_show_num.setText(data.get(i).getList().get(i1).getNum()+"");

        //分割图片地址
        String images = data.get(i).getList().get(i1).getImages();

        String[] urls = images.split("\\|");

        //加载商品图片
        Glide.with(context)
                .load(urls[0])
                .crossFade()
                .into(iv_show_pic);

//商品加
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyGoodsItemNumberListener.doIncrease(i,i1,tv_commodity_show_num);
            }
        });

        //设置商品加减的按钮
        //商品减
        iv_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyGoodsItemNumberListener.doDecrease(i,i1,tv_commodity_show_num);

            }
        });

        //商品复选框是否被选中
        ck_child_choosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isChecked false  true
                checkGroupItemListener.checkChildItem(i,i1,((CheckBox)view).isChecked());
            }
        });

        //处理商品的选中状态
        if(data.get(i).getList().get(i1).isChildChoosed()){
            ck_child_choosed.setChecked(true);
        }else{
            ck_child_choosed.setChecked(false);
        }

        //控制删除按钮的隐藏与显示
        if(isEditor){
            btn_commodity_delete.setVisibility(View.VISIBLE);
        }else{
            btn_commodity_delete.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    public interface CheckGroupItemListener{
        //商家的条目的复选框监听
        void checkGroupItem(int groupPosition, boolean isChecked);
        //商品的
        void checkChildItem(int groupPosition, int childPosition, boolean isChecked);

    }

    /**
     * 商品加减接口
     */
    public interface ModifyGoodsItemNumberListener{

        //商品添加操作
        void doIncrease(int groupPosition, int childPosition, View view);
        //商品减少操作
        void doDecrease(int groupPosition, int childPosition, View view);

    }
}