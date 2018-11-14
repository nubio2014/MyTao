package com.mlzq.mytao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.mlzq.mytao.Pamera;
import com.mlzq.mytao.R;
import com.mlzq.mytao.activity.ShoppingDetaileActivity;
import com.mlzq.mytao.bean.DaoGou;
import com.mlzq.mytao.bean.TaokoulingBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
import com.mlzq.mytao.http.MyUrl;
import com.mlzq.mytao.utile.AppUtile;
import com.mlzq.nubiolib.Dialog.ToastUtils;
import com.mlzq.nubiolib.app.BaseAdapters;
import com.mlzq.nubiolib.app.BaseViewHolder;
import com.mlzq.nubiolib.utile.FileUtils;
import com.mlzq.nubiolib.widget.ClearEditText;
import com.mlzq.nubiolib.widget.XListView;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    View rootView;
    BaseAdapters<DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean> adapter;
    List<DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean> list = new ArrayList<>();
    @BindView(R.id.edt_shopname)
    ClearEditText mEdtShopname;
    @BindView(R.id.tv_serach)
    TextView mTvSerach;
    @BindView(R.id.list_shop)
    XListView mListShop;
    Unbinder unbinder;

int no=1;
    int page=30;
    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_first, null);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new BaseAdapters<DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean>(getActivity(), list, R.layout.item_main) {
            @Override
            public void convert(BaseViewHolder helper, final DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean item, int position) {
                helper.setNetImage(getActivity(), R.id.iv_shopimage, item.getPict_url());
                helper.setText(R.id.tv_title,item.getTitle());
                helper.setText(R.id.tv_nowprice,"现价："+item.getZk_final_price());
                helper.setText(R.id.tv_volume,"月销量："+item.getVolume());
                helper.setText(R.id.tv_shopname,item.getNick());
                helper.setText(R.id.tv_discount,"优惠券："+item.getCoupon_info());
                helper.setText(R.id.tv_address,"剩余："+item.getCoupon_remain_count());
                helper.getView(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getCoupon_click_url()!=null&&!"".equals(item.getCoupon_click_url())) {
                            getData(item.getTitle(), item.getCoupon_click_url());
                        }else{
                            ToastUtils.showToast(getActivity(),"暂无优惠券");
                        }
                    }
                });
//                helper.setWidgetIsVisiable(R.id.iv_statue,item.getStatus()==1?View.GONE:View.VISIBLE);
            }
        };
        mListShop.setAdapter(adapter);
        MyListListterner listListterner = new MyListListterner();
        mListShop.setXListViewListener(listListterner);
        mListShop.setPullLoadEnable(true);
        mListShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent(getActivity(), ShoppingDetaileActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("data",new GsonBuilder().serializeNulls().create().toJson(list.get(position-1)));
                it.putExtras(bundle);
                startActivity(it);
            }
        });


        return rootView;
    }



    public class MyListListterner implements XListView.IXListViewListener {

        @Override
        public void onRefresh() {
            list.clear();
            no = 1;
            getData(no,mEdtShopname.getText().toString().trim());//数据初始化

            mListShop.setAdapter(adapter);
            loading();
        }

        @Override
        public void onLoadMore() {
            no++;
            getData(no,mEdtShopname.getText().toString().trim());//数据初始化
            adapter.notifyDataSetChanged();
            loading();
        }
    }

    public void loading() {
        mListShop.stopRefresh();
        mListShop.stopLoadMore();
        mListShop.setRefreshTime("刚刚");
    }
    
    
    @OnClick(R.id.tv_serach)
    public void onViewClicked() {

list.clear();
        adapter.notifyDataSetChanged();
        getData(no,mEdtShopname.getText().toString().trim());

    }

    @Override
    public void onResume() {
        super.onResume();
        no=1;
        list.clear();
        getData(no,mEdtShopname.getText().toString().trim());


    }

    private void getData(int no,String keyword){
        Map<String, String> map = new HashMap<>();
        map.put("adzone_id", Pamera.ASZONE_ID+"");
        map.put("method", MyUrl.coupon);
        map.put("page_no",no+"");
        map.put("page_size",page+"");
        if (keyword!=null&&!"".equals(keyword)){
            map.put("q", mEdtShopname.getText().toString().trim());
        }
        MyHttpUtils.doPost(MyHttpUtils.URL, map, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String text = response.body().string();
                Logger.json(text);
                L.e("返回总数据",text);
                return text;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {

                DaoGou tbk=new GsonBuilder().serializeNulls().create().fromJson(response+"",DaoGou.class);
                if (tbk.getTbk_dg_item_coupon_get_response()!=null) {
                    for (DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean bean : tbk.getTbk_dg_item_coupon_get_response().getResults().getTbk_coupon()) {
                        list.add(bean);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void getData(String title,String url){
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "121547671");
        map.put("method","taobao.tbk.tpwd.create");
        map.put("text", title);
        map.put("url",""+url);
//        map.put("ip","");
        MyHttpUtils.doPost(MyHttpUtils.URL, map, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String text = response.body().string();
                Logger.json(text);
                L.e("返回总数据",text);
                return text;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    TaokoulingBean bean = new GsonBuilder().serializeNulls().create().fromJson(response + "", TaokoulingBean.class);
                    FileUtils.copyContext(getActivity(), bean.getTbk_tpwd_create_response().getData().getModel());

                    if (AppUtile.isAppInstalled(getActivity(), Pamera.TAOBAO)) {
                        AppUtile.runApp(Pamera.TAOBAO, getActivity());
                    } else {
                        ToastUtils.showToast(getActivity(), "请先安装淘宝客户端");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(),e.getMessage());
                }

            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
