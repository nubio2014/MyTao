package com.mlzq.mytao.fragment;

import android.annotation.SuppressLint;
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
import com.mlzq.mytao.bean.FavoriteBean;
import com.mlzq.mytao.bean.TaokoulingBean;
import com.mlzq.mytao.bean.XuanPinKuBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
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
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;


/**
 * taobao.tbk.uatm.favorites.item.get( 获取淘宝联盟选品库的宝贝信息 )
 */

@SuppressLint("ValidFragment")
public class SecondSimpleCardFragment extends Fragment {
    View rootView;
    @BindView(R.id.edt_shopname)
    ClearEditText mEdtShopname;
    @BindView(R.id.tv_serach)
    TextView mTvSerach;
    @BindView(R.id.list_shop)
    XListView mListShop;
    Unbinder unbinder;
    @BindView(R.id.tv_none)
    TextView mTvNone;
    private String mTitle;
    FavoriteBean.TbkUatmFavoritesGetResponseBean.ResultsBean.TbkFavoritesBean bean;
    BaseAdapters<XuanPinKuBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> adapter;
    List<XuanPinKuBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> list = new ArrayList<>();
    int no = 1;
    int page = 30;


    public static SecondSimpleCardFragment getInstance(FavoriteBean.TbkUatmFavoritesGetResponseBean.ResultsBean.TbkFavoritesBean bean) {
        SecondSimpleCardFragment sf = new SecondSimpleCardFragment();
        sf.bean = bean;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_second, null);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        list.clear();

        no = 1;
        getData(no);


        adapter = new BaseAdapters<XuanPinKuBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean>(getActivity(), list, R.layout.item_main) {
            @Override
            public void convert(BaseViewHolder helper, final XuanPinKuBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean item, int position) {
                helper.setNetImage(getActivity(), R.id.iv_shopimage, item.getPict_url());
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_nowprice, "现价：" + item.getZk_final_price());
                helper.setText(R.id.tv_volume, "月销量：" + item.getVolume());
                helper.setText(R.id.tv_shopname, item.getNick());
                helper.setText(R.id.tv_discount, "优惠券：" + item.getCoupon_info());
                helper.setText(R.id.tv_address, "剩余：" + item.getCoupon_remain_count());
                helper.getView(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (item.getCoupon_click_url()!=null&&!"".equals(item.getCoupon_click_url())) {
                            getYouHuiQuanData(item.getTitle(), item.getCoupon_click_url());
                        }else{
                            ToastUtils.showToast(getActivity(),"暂无优惠券");
                        }


                    }
                });
                helper.setWidgetIsVisiable(R.id.iv_statue,item.getStatus()==1?View.GONE:View.VISIBLE);
            }
        };
        mListShop.setAdapter(adapter);
        MyListListterner listListterner = new MyListListterner();
        mListShop.setXListViewListener(listListterner);
        mListShop.setPullLoadEnable(true);
        mListShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), ShoppingDetaileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("data", new GsonBuilder().serializeNulls().create().toJson(list.get(position - 1)));
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
            getData(no);//数据初始化

            mListShop.setAdapter(adapter);
            loading();
        }

        @Override
        public void onLoadMore() {
            no++;
            getData(no);//数据初始化
            adapter.notifyDataSetChanged();
            loading();
        }
    }

    public void loading() {
        mListShop.stopRefresh();
        mListShop.stopLoadMore();
        mListShop.setRefreshTime("刚刚");
    }


    private void getData(int no) {
        Map<String, String> map = new HashMap<>();
        map.put("adzone_id", Pamera.ASZONE_ID + "");
        map.put("method", "taobao.tbk.uatm.favorites.item.get");
        map.put("page_no", no + "");
        map.put("page_size", page + "");
        map.put("favorites_id", bean.getFavorites_id() + "");
        map.put("fields", "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,coupon_click_url,coupon_total_count,coupon_info");
        MyHttpUtils.doPost(MyHttpUtils.URL, map, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String text = response.body().string();
                Logger.json(text);
                L.e("返回总数据", text);
                return text;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    XuanPinKuBean tbk = new GsonBuilder().serializeNulls().create().fromJson(response + "", XuanPinKuBean.class);
                    if (tbk.getTbk_uatm_favorites_item_get_response() != null) {
                        for (XuanPinKuBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean bean : tbk.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item()) {
                            list.add(bean);
                        }
                        adapter.notifyDataSetChanged();
                    }
                   mTvNone.setVisibility(list.size()>0?View.GONE:View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getYouHuiQuanData(String title, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "121547671");
        map.put("method", "taobao.tbk.tpwd.create");
        map.put("text", title);
        map.put("url", "" + url);
//        map.put("ip","");
        MyHttpUtils.doPost(MyHttpUtils.URL, map, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String text = response.body().string();
                Logger.json(text);
                L.e("返回总数据", text);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), e.getMessage());
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