package com.mlzq.mytao;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.mlzq.mytao.bean.TbkItemBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
import com.mlzq.nubiolib.app.BaseActivity;
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
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_serach)
    TextView mTvSerach;
    @BindView(R.id.list_shop)
    XListView mListShop;
    @BindView(R.id.edt_shopname)
    ClearEditText mEdtShopname;
    BaseAdapters<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean> apater;
    List<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_main);
        ButterKnife.bind(this);
        apater=new BaseAdapters<TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean>(this,list,R.layout.item_main) {
            @Override
            public void convert(BaseViewHolder helper, TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean item, int position) {
                helper.setNetImage(MainActivity.this,R.id.iv_shopimage,item.getPict_url());
            }
        };
        mListShop.setAdapter(apater);
    }

    @OnClick(R.id.tv_serach)
    public void onViewClicked() {
        Map<String, String> map = new HashMap<>();
        map.put("fields", "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
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
                TbkItemBean tbk=new GsonBuilder().serializeNulls().create().fromJson(response+"",TbkItemBean.class);
                for (TbkItemBean.TbkItemGetResponseBean.ResultsBean.NTbkItemBean bean:tbk.getTbk_item_get_response().getResults().getN_tbk_item() ) {
                    list.add(bean);
                }
                apater.notifyDataSetChanged();
            }
        });

    }
}
