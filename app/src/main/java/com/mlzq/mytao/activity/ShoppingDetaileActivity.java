package com.mlzq.mytao.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.mlzq.mytao.Pamera;
import com.mlzq.mytao.R;
import com.mlzq.mytao.bean.DaoGou;
import com.mlzq.mytao.bean.TaokoulingBean;
import com.mlzq.mytao.http.L;
import com.mlzq.mytao.http.MyHttpUtils;
import com.mlzq.mytao.utile.AppUtile;
import com.mlzq.nubiolib.Dialog.ToastUtils;
import com.mlzq.nubiolib.app.BaseActivity;
import com.mlzq.nubiolib.utile.FileUtils;
import com.mlzq.nubiolib.utile.GlideImageLoader;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * ( 淘宝客商品详情（简版） )
 */

public class ShoppingDetaileActivity extends BaseActivity {


    String data;
    @BindView(R.id.first_banner)
    Banner mFirstBanner;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_now_price)
    TextView mTvNowPrice;
    @BindView(R.id.tv_old_price)
    TextView mTvOldPrice;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_jump)
    TextView mTvJump;
    DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean bean;
    private Context con=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_shopping_detaile);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        data=  bundle.getString("data");
        L.json(data);
        L.e(data);
        bean=new GsonBuilder().serializeNulls().create().fromJson(data,DaoGou.TbkDgItemCouponGetResponseBean.ResultsBean.TbkCouponBean.class);
        mFirstBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mFirstBanner.setImageLoader(new GlideImageLoader());
        mFirstBanner.setBannerAnimation(Transformer.DepthPage);
        mFirstBanner.isAutoPlay(true);
        mFirstBanner.setDelayTime(3000);
        mFirstBanner.setIndicatorGravity(BannerConfig.CENTER);
        mFirstBanner.setImages(bean.getSmall_images().getString());
        //  firstBanner.setBannerTitles(Virtualdata.bannerTitle());//
        mFirstBanner.start();
        mTvTitle.setText(bean.getTitle());
        mTvOldPrice.setText(bean.getCoupon_info());
        mTvNowPrice.setText("￥："+bean.getZk_final_price());
        mTvCount.setText("月销售"+bean.getVolume());



    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void getData(){
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "121547671");
        map.put("method","taobao.tbk.tpwd.create");
        map.put("text", bean.getTitle());
        map.put("url",""+bean.getCoupon_click_url());
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
                    FileUtils.copyContext(ShoppingDetaileActivity.this, bean.getTbk_tpwd_create_response().getData().getModel());

                    if (AppUtile.isAppInstalled(ShoppingDetaileActivity.this, Pamera.TAOBAO)) {
                        AppUtile.runApp(Pamera.TAOBAO, con);
                    } else {
                        ToastUtils.showToast(con, "请先安装淘宝客户端");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtils.showToast(con,e.getMessage());
                }

            }
        });
    }
    @OnClick(R.id.tv_jump)
    public void onViewClicked() {

        if (bean.getCoupon_click_url()!=null&&!"".equals(bean.getCoupon_click_url())) {
            getData();
        }else{
            ToastUtils.showToast(this,"暂无优惠券");
        }

    }
}
