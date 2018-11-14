//package com.mlzq.mytao.http;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//import com.google.gson.TypeAdapterFactory;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonToken;
//import com.google.gson.stream.JsonWriter;
//import com.umeng.message.PushAgent;
//import com.zdd.wlb.mlzq.ToastUtils;
//import com.zdd.wlb.mlzq.base.AppManager;
//import com.zdd.wlb.mlzq.utile.ShowOne;
//import com.zdd.wlb.ui.login.LoginActivity;
//import com.zhy.http.okhttp.callback.Callback;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * Created by Dev on 2017/9/11.
// */
//
//public abstract class DataBack extends Callback<DataBase>{
//
//    Context context;
//
//    public DataBack(Context context) {
//        this.context = context;
//    }
////    public DataBack() {
////    }
//
//    @Override
//    public DataBase parseNetworkResponse(Response response, int id) throws Exception {
//        DataBase bean = new DataBase();
//try {
//    String text = response.body().string();
//    L.d("请求页面："+context.getClass()+"   返回数据:   "+text);
//    JSONObject jsonObject = new JSONObject(text);
//
//     L.json(text);//打印Log
//    if (jsonObject.has("errorcode")) {
//        bean.setErrorcode(jsonObject.getInt("errorcode"));
//    }
//    if (jsonObject.has("errormsg")) {
//        bean.setErrormsg(jsonObject.optString("errormsg").toString());
//    }
//    if (jsonObject.has("datetime")) {
//        bean.setDatetime(jsonObject.getString("datetime"));
//    }
//    if (jsonObject.has("ticket")) {
//        bean.setTicket(jsonObject.getString("ticket"));
//    }
//    if (jsonObject.has("total")) {
//        bean.setTotal(jsonObject.getInt("total"));
//    }
//    if (jsonObject.has("data")) {
//        JSONObject object = jsonObject.optJSONObject("data");
//        if (object == null) {
//            JSONArray array = jsonObject.optJSONArray("data");
//            if (array != null) {
//                bean.setData(array.toString());
//            } else {
//                if (!jsonObject.getString("data").equals("")){
//                    bean.setData(jsonObject.getString("data"));
//                }else{
//                    bean.setData("{}");
//                }
//               // TbkItemBean.setData("{}");
//            }
//        } else {
//            bean.setData(object.toString());
//        }
//
//    }
//}catch (Exception e){
//    e.printStackTrace();
//}
//
//
//        return bean;
//    }
//    private void showToast(String res){
//        if(!TextUtils.isEmpty(res)){
//            Toast.makeText(context, res, Toast.LENGTH_LONG).show();
//        }
//    }
//    @Override
//    public void onError(Call call, Exception e, int id) {
//
//    }
//
//    @Override
//    public void onResponse(final DataBase bean, int id) {
//        try {
//            if (bean.getErrorcode()==0) {
//                onSuccess(bean);
//            }else if(bean.getErrorcode()==44404){
//              //  UpdateApp.getServiceVersion(context, 2, "物城宝");
//                new ShowOne(context) {
//                    @Override
//                    public String setMessages() {
//                        return bean.getErrormsg();
//                    }
//
//                    @Override
//                    public void ButtonOnClick() {
//                        AppManager.getAppManager().finishAllActivity();
//                        AppManager.getAppManager().onAppExit(context);
//                    }
//                };
//
//             //   ToastUtils.showToast(context,TbkItemBean.getErrormsg());
//            }
//            else if (bean.getErrorcode()==1101){
//                SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.remove("phone");
//                editor.remove("equipment");
//                editor.remove("token");
//                editor.remove("houseinfo");
//                editor.remove("TenantName");
//                editor.commit();
//                User.TOKEN = null;
//                User.TenantId = 0;
//                User.TenantName = "";
//                User.CustomerId = null;
//                User.FloorId = null;
//                User.Equipment = null;//设备号
//                User.UserPhone = null;//手机号
//                User.Housename = null;//楼栋名
//                User.Identity = "";//业主
//                User.Name = "";//姓名
//                User.Address = "";
//                PushAgent mPushAgent = PushAgent.getInstance(context);
//                if (mPushAgent.getRegistrationId() != null) {
//                    Map<String, String> map = new HashMap<>();
//                    MyHttpUtils.doPost(context, "http://" + MyUrl.Ip + ":8095/bind?usrname=0&devToken=" + mPushAgent.getRegistrationId() + "&from=android", map, new DataCallBack(context) {
//                        @Override
//                        public void onSuccess(DataBase response) {
//                            com.zdd.wlb.mlzq.http.L.e(response.getData() + "");
//
//                        }
//                    });
//                }
//                Intent it=new Intent(context, LoginActivity.class);
//                context.startActivity(it);
//                ToastUtils.showToast(context,bean.getErrormsg());
//               AppManager.getAppManager().finishActivity(context.getClass());
//            //    ShowDialog.showUniteDialog(context,TbkItemBean.getErrormsg());
//            }
////            else if (TbkItemBean.getErrorcode()==50000){
////                // showToast(TbkItemBean.getErrormsg());
////                SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
////                SharedPreferences.Editor editor = sp.edit();
////                editor.remove("phone");
////                editor.remove("equipment");
////                editor.remove("token");
////                editor.remove("houseinfo");
////                editor.commit();
////                User.TOKEN = null;
////                User.TenantId = 0;
////                User.TenantName = "";
////                User.CustomerId = null;
////                User.FloorId = null;
////                User.Equipment = null;//设备号
////                User.UserPhone = null;//手机号
////                User.Housename = null;//楼栋名
////                User.Identity = "";//业主
////                User.Name = "";//姓名
////                User.Address = "";
////                ToastUtils.showToast(context,TbkItemBean.getErrormsg());
////                Intent it=new Intent(context, LoginActivity.class);
////                context.startActivity(it);
////                AppManager.getAppManager().finishActivity(context.getClass());
////                //    ShowDialog.showUniteDialog(context,TbkItemBean.getErrormsg());
////            }
//            else{
//                onFile(bean);
//            }
//            Log.e("返回Data数据",bean.getData()+"");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public abstract void onSuccess(DataBase response);
//    public abstract void onFile(DataBase response);
//
//    public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
//        @SuppressWarnings("unchecked")
//
//        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
//            Class<T> rawType = (Class<T>) type.getRawType();
//            if (rawType != String.class) {
//                return null;
//            }
//            return (TypeAdapter<T>) new StringNullAdapter();
//        }
//    }
//    public class StringNullAdapter extends TypeAdapter<String> {
//        @Override
//        public String read(JsonReader reader) throws IOException {
//            // TODO Auto-generated method stub
//            if (reader.peek() == JsonToken.NULL) {
//                reader.nextNull();
//                return "";
//            }
//            return reader.nextString();
//        }
//        @Override
//        public void write(JsonWriter writer, String value) throws IOException {
//            // TODO Auto-generated method stub
//            if (value == null) {
//                writer.nullValue();
//                return;
//            }
//            writer.value(value);
//        }
//    }
//
//}
