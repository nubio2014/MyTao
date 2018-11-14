package com.mlzq.mytao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.mlzq.mytao.R;
import com.mlzq.mytao.bean.TbkItemBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
import com.mlzq.nubiolib.app.BaseAdapters;
import com.mlzq.nubiolib.app.BaseViewHolder;
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
public class SearchFragment extends Fragment {


    View rootView;
    BaseAdapters<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean> apater;
    List<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean> list = new ArrayList<>();
    @BindView(R.id.edt_shopname)
    ClearEditText mEdtShopname;
    @BindView(R.id.tv_serach)
    TextView mTvSerach;
    @BindView(R.id.list_shop)
    XListView mListShop;
    Unbinder unbinder;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_search, null);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        apater = new BaseAdapters<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean>(getActivity(), list, R.layout.item_main) {
            @Override
            public void convert(BaseViewHolder helper, TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean item, int position) {
                helper.setNetImage(getActivity(), R.id.iv_shopimage, item.getPict_url());
                helper.setText(R.id.tv_title,item.getTitle());
                helper.setText(R.id.tv_nowprice,"原价："+item.getReserve_price());
                helper.setText(R.id.tv_volume,"月销量："+item.getVolume());
                helper.setText(R.id.tv_shopname,item.getNick());
                helper.setText(R.id.tv_discount,"折扣价："+item.getZk_final_price());
                helper.setText(R.id.tv_address,item.getProvcity());

            }
        };
        mListShop.setAdapter(apater);

        return rootView;
    }
    @OnClick(R.id.tv_serach)
    public void onViewClicked() {

list.clear();
        apater.notifyDataSetChanged();
        getData();

    }


    private void getData(){
        Map<String, String> map = new HashMap<>();
        map.put("fields", "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,coupon_click_url");
        map.put("method", "taobao.tbk.item.get");
        map.put("q", mEdtShopname.getText().toString().trim());
        map.put("page_no","1");
        map.put("page_size","30");
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
                    TbkItemBean tbk = new GsonBuilder().serializeNulls().create().fromJson(response + "", TbkItemBean.class);
                    for (TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean bean : tbk.getTbk_item_get_response().getResults().getN_tbk_item()) {
                        list.add(bean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                apater.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
