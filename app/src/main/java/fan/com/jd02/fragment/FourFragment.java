package fan.com.jd02.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import fan.com.jd02.R;
import fan.com.jd02.fourfragment.MyExpandAdapter;
import fan.com.jd02.fourfragment.ShoppCarBean;
import fan.com.jd02.fourfragment.mvp.IMainView;
import fan.com.jd02.fourfragment.mvp.ShooppPrenster;

/**
 * Created by fxf on 2018.05.15.
 */

public class FourFragment extends Fragment  implements IMainView, MyExpandAdapter.ModifyGoodsItemNumberListener, MyExpandAdapter.CheckGroupItemListener {
    //默认是false
    private boolean flag;
    //购买商品的总数量
    private int totalNum = 0;
    //购买商品的总价
    private double totalPrice= 0.00;
    private List<ShoppCarBean.DataBean> list;
    /**
     * 返回
     */
    private TextView mBtnBack;
    /**
     * 编辑
     */
    private TextView mBtnEditor;
    private ExpandableListView mExpandList;
    /**
     * 全选
     */
    private CheckBox mBtnCheckAll;
    /**
     * 合计:￥0.00
     */
    private TextView mTvTotalPrice;
    /**
     * 结算(0)
     */
    private TextView mBtnAmount;
    private MyExpandAdapter adapter;
    private ShooppPrenster shooppPrenster;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        initView();

        mExpandList.setGroupIndicator(null);
//https://www.zhaoapi.cn/product/getCarts?uid=13034&token=9927167DCA58A048D9DB3B5B006148FF
        shooppPrenster = new ShooppPrenster(this);
        shooppPrenster.getCarInfo("13034","9927167DCA58A048D9DB3B5B006148FF");

        adapter = new MyExpandAdapter(getActivity());
        mExpandList.setAdapter(adapter);

        adapter.setModifyGoodsItemNumberListener(this);
        //设置商家以及商品是否被选中的监听
        adapter.setCheckGroupItemListener(this);

        mBtnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isChoosedAll(((CheckBox)view).isChecked());
                //计算商品总价
                statisticsPrice();

            }
        });
        mBtnEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!flag){//编辑 -> 完成\
                    flag = true;
                    mBtnEditor.setText("完成");
                    adapter.showDeleteButton(flag);
                }else{
                    flag = false;
                    mBtnEditor.setText("编辑");
                    adapter.showDeleteButton(flag);
                }

            }
        });

        return view;

    }
    private void initView() {
        mBtnBack = (TextView) view.findViewById(R.id.btnBack);
        mBtnEditor = (TextView) view.findViewById(R.id.btnEditor);
        mExpandList = (ExpandableListView) view.findViewById(R.id.expandList);
        mBtnCheckAll = (CheckBox) view.findViewById(R.id.btnCheckAll);
        mTvTotalPrice = (TextView)view. findViewById(R.id.tvTotalPrice);
        mBtnAmount = (TextView) view.findViewById(R.id.btnAmount);
    }
    @Override
    public void onSuccess(List<ShoppCarBean.DataBean> data) {
        this.list=data;
        adapter.setList(list);
        defaultExpand();
    }
    private void defaultExpand() {
        for (int i=0;i<adapter.getGroupCount();i++){
            mExpandList.expandGroup(i);
        }
    }
    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void checkGroupItem(int groupPosition, boolean isChecked) {
        //Toast.makeText(this, "商家", Toast.LENGTH_SHORT).show();
        //商家javabean
        ShoppCarBean.DataBean dataBean = list.get(groupPosition);
        dataBean.setGroupChoosed(isChecked);

        //遍历商家里面的商品，将其置为选中状态
        for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()){
            listBean.setChildChoosed(isChecked);
        }

        //底部结算那个checkbox状态(全选)
        if(isCheckAll()){
            mBtnCheckAll.setChecked(true);
        }else{
            mBtnCheckAll.setChecked(false);
        }

        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算价格
        statisticsPrice();
    }

    @Override
    public void checkChildItem(int groupPosition, int childPosition, boolean isChecked) {
        //Toast.makeText(this, "商品", Toast.LENGTH_SHORT).show();
        //ShoppCarBean.DataBean.ListBean listBean = list.get(groupPosition).getList().get(childPosition);

        ShoppCarBean.DataBean dataBean = list.get(groupPosition);//商家那一层
        List<ShoppCarBean.DataBean.ListBean> listBeans = dataBean.getList();
        ShoppCarBean.DataBean.ListBean listBean = listBeans.get(childPosition);

        //你点击商家的商品条目将其选中状态记录
        listBean.setChildChoosed(isChecked);

        //检测商家里面的每一个商品是否被选中，如果被选中，返回boolean
        boolean result = isGoodsCheckAll(groupPosition);
        if(result){
            dataBean.setGroupChoosed(result);
        }else{
            dataBean.setGroupChoosed(result);
        }

        //底部结算那个checkbox状态(全选)
        if(isCheckAll()){
            mBtnCheckAll.setChecked(true);
        }else{
            mBtnCheckAll.setChecked(false);
        }


        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算总价
        statisticsPrice();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View view) {

        //Toast.makeText(this, "用户点击了商品添加操作", Toast.LENGTH_SHORT).show();

        ShoppCarBean.DataBean.ListBean listBean = list.get(groupPosition).getList().get(childPosition);
        //取出当前的商品数量
        int currentNum = listBean.getNum();
        //商品++
        currentNum++;
        //将商品数量设置javabean上
        listBean.setNum(currentNum);

        //刷新适配器
        adapter.notifyDataSetChanged();


        //计算商品价格
        statisticsPrice();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View view) {
        //Toast.makeText(this, "用户点击了商品减少操作", Toast.LENGTH_SHORT).show();

        ShoppCarBean.DataBean.ListBean listBean = list.get(groupPosition).getList().get(childPosition);
        //取出当前的商品数量
        int currentNum = listBean.getNum();
        //直接结束这个方法
        if(currentNum == 1){
            return;
        }

        currentNum--;
        listBean.setNum(currentNum);
        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算商品价格
        statisticsPrice();

    }


    /**
     * 检测某个商家的商品是否都选中，如果都选中的话，商家CheckBox应该是选中状态
     * @param groupPosition
     * @return
     */
    private boolean isGoodsCheckAll(int groupPosition){
        List<ShoppCarBean.DataBean.ListBean> listBeans = this.list.get(groupPosition).getList();
        //遍历某一个商家的每个商品是否被选中
        for (ShoppCarBean.DataBean.ListBean listBean : listBeans){
            if(listBean.isChildChoosed()){//是选中状态
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    //购物车商品是否全部选中
    private boolean isCheckAll(){

        for (ShoppCarBean.DataBean dataBean : list){
            if(!dataBean.isGroupChoosed()){
                return false;
            }
        }
        return true;
    }

    //全选与反选
    private void isChoosedAll(boolean isChecked){

        for (ShoppCarBean.DataBean dataBean : list){
            dataBean.setGroupChoosed(isChecked);
            for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()){
                listBean.setChildChoosed(isChecked);
            }
        }
        //刷新适配器
        adapter.notifyDataSetChanged();
    }
    /**
     * 计算你所选中的商品总价
     */
    private void statisticsPrice(){
        //初始化值
        totalNum = 0;
        totalPrice = 0.00;
        for (ShoppCarBean.DataBean dataBean : list){

            for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()){
                if(listBean.isChildChoosed()){
                    totalNum++;
                    System.out.println("number : "+totalNum);
                    totalPrice += listBean.getNum()*listBean.getPrice();
                }
            }
        }
        String format = new DecimalFormat("0.00").format(totalPrice);
        //设置文本信息 合计、结算的商品数量
        mTvTotalPrice.setText("合计:￥"+format);
        mBtnAmount.setText("结算("+totalNum+")");
    }
}
