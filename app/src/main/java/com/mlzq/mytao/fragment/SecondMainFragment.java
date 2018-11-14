package com.mlzq.mytao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.GsonBuilder;
import com.mlzq.mytao.Pamera;
import com.mlzq.mytao.R;
import com.mlzq.mytao.bean.FavoriteBean;
import com.mlzq.mytao.bean.TaokoulingBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
import com.mlzq.mytao.utile.AppUtile;
import com.mlzq.nubiolib.Dialog.ToastUtils;
import com.mlzq.nubiolib.utile.FileUtils;
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
 * A simple {@link Fragment} subclass.
 */
public class SecondMainFragment extends Fragment {


    View rootView;
    Unbinder unbinder;
    @BindView(R.id.title_layout)
    SlidingTabLayout mTitleLayout;
    @BindView(R.id.vp_repair)
    ViewPager mVpRepair;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private  List<String> mTitles=new ArrayList();


    int no = 1;
    int page = 30;

    public SecondMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_secondcontrol, null);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        getData();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("fields", "favorites_title,favorites_id,type");
        map.put("method", "taobao.tbk.uatm.favorites.get");
        map.put("page_no", "1");
        map.put("page_size", "100");
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
                    mTitles.clear();;
                    FavoriteBean tbk = new GsonBuilder().serializeNulls().create().fromJson(response + "", FavoriteBean.class);
                    if (tbk.getTbk_uatm_favorites_get_response() != null) {
                        for (FavoriteBean.TbkUatmFavoritesGetResponseBean.ResultsBean.TbkFavoritesBean bean : tbk.getTbk_uatm_favorites_get_response().getResults().getTbk_favorites()) {
                            mTitles.add(bean.getFavorites_title());
                            mFragments.add(SecondSimpleCardFragment.getInstance(bean));
                        }
                        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),mTitles.toArray(new String[mTitles.size()]),mFragments);
                        mVpRepair.setAdapter(mAdapter);
                        mTitleLayout.setViewPager(mVpRepair,mTitles.toArray(new String[mTitles.size()]));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getData(String title, String url) {
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
