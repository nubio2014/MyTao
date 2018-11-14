package com.mlzq.mytao.bean;

import java.util.List;

/**
 * Created by Dev on 2018/10/16.
 * desc :
 */

public class ShopDetailedBean {


    /**
     * tbk_item_info_get_response : {"results":{"n_tbk_item":[{"cat_leaf_name":"其他电焊/切割设备","cat_name":"五金/工具","item_url":"https://item.taobao.com/item.htm?id=576661572026","material_lib_type":"","nick":"王氏shopping","num_iid":576661572026,"pict_url":"https://img.alicdn.com/bao/uploaded/i2/2588702759/O1CN011WFfCVjqQq7ouJR_!!2588702759.jpg","provcity":"广东 揭阳","reserve_price":"10.2","seller_id":2588702759,"small_images":{"string":["https://img.alicdn.com/i4/2588702759/O1CN011WFfCW9eNusdYsy_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCRA1QxC2S7v_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCYQGZJk6cqP_!!2588702759.jpg"]},"title":"氩弧焊机QQ-150A氩弧焊枪配件瓷嘴 瓷咀 喷嘴保护套","user_type":0,"volume":0,"zk_final_price":"10.2"}]},"request_id":"4jk6wrahr6ek"}
     */

    private TbkItemInfoGetResponseBean tbk_item_info_get_response;

    public TbkItemInfoGetResponseBean getTbk_item_info_get_response() {
        return tbk_item_info_get_response;
    }

    public void setTbk_item_info_get_response(TbkItemInfoGetResponseBean tbk_item_info_get_response) {
        this.tbk_item_info_get_response = tbk_item_info_get_response;
    }

    public static class TbkItemInfoGetResponseBean {
        /**
         * results : {"n_tbk_item":[{"cat_leaf_name":"其他电焊/切割设备","cat_name":"五金/工具","item_url":"https://item.taobao.com/item.htm?id=576661572026","material_lib_type":"","nick":"王氏shopping","num_iid":576661572026,"pict_url":"https://img.alicdn.com/bao/uploaded/i2/2588702759/O1CN011WFfCVjqQq7ouJR_!!2588702759.jpg","provcity":"广东 揭阳","reserve_price":"10.2","seller_id":2588702759,"small_images":{"string":["https://img.alicdn.com/i4/2588702759/O1CN011WFfCW9eNusdYsy_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCRA1QxC2S7v_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCYQGZJk6cqP_!!2588702759.jpg"]},"title":"氩弧焊机QQ-150A氩弧焊枪配件瓷嘴 瓷咀 喷嘴保护套","user_type":0,"volume":0,"zk_final_price":"10.2"}]}
         * request_id : 4jk6wrahr6ek
         */

        private ResultsBean results;
        private String request_id;

        public ResultsBean getResults() {
            return results;
        }

        public void setResults(ResultsBean results) {
            this.results = results;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultsBean {
            private List<NTbkItemBean> n_tbk_item;

            public List<NTbkItemBean> getN_tbk_item() {
                return n_tbk_item;
            }

            public void setN_tbk_item(List<NTbkItemBean> n_tbk_item) {
                this.n_tbk_item = n_tbk_item;
            }

            public static class NTbkItemBean {
                /**
                 * cat_leaf_name : 其他电焊/切割设备
                 * cat_name : 五金/工具
                 * item_url : https://item.taobao.com/item.htm?id=576661572026
                 * material_lib_type :
                 * nick : 王氏shopping
                 * num_iid : 576661572026
                 * pict_url : https://img.alicdn.com/bao/uploaded/i2/2588702759/O1CN011WFfCVjqQq7ouJR_!!2588702759.jpg
                 * provcity : 广东 揭阳
                 * reserve_price : 10.2
                 * seller_id : 2588702759
                 * small_images : {"string":["https://img.alicdn.com/i4/2588702759/O1CN011WFfCW9eNusdYsy_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCRA1QxC2S7v_!!2588702759.jpg","https://img.alicdn.com/i3/2588702759/O1CN011WFfCYQGZJk6cqP_!!2588702759.jpg"]}
                 * title : 氩弧焊机QQ-150A氩弧焊枪配件瓷嘴 瓷咀 喷嘴保护套
                 * user_type : 0
                 * volume : 0
                 * zk_final_price : 10.2
                 */

                private String cat_leaf_name;
                private String cat_name;
                private String item_url;
                private String material_lib_type;
                private String nick;
                private long num_iid;
                private String pict_url;
                private String provcity;
                private String reserve_price;
                private long seller_id;
                private SmallImagesBean small_images;
                private String title;
                private int user_type;
                private int volume;
                private String zk_final_price;

                public String getCat_leaf_name() {
                    return cat_leaf_name;
                }

                public void setCat_leaf_name(String cat_leaf_name) {
                    this.cat_leaf_name = cat_leaf_name;
                }

                public String getCat_name() {
                    return cat_name;
                }

                public void setCat_name(String cat_name) {
                    this.cat_name = cat_name;
                }

                public String getItem_url() {
                    return item_url;
                }

                public void setItem_url(String item_url) {
                    this.item_url = item_url;
                }

                public String getMaterial_lib_type() {
                    return material_lib_type;
                }

                public void setMaterial_lib_type(String material_lib_type) {
                    this.material_lib_type = material_lib_type;
                }

                public String getNick() {
                    return nick;
                }

                public void setNick(String nick) {
                    this.nick = nick;
                }

                public long getNum_iid() {
                    return num_iid;
                }

                public void setNum_iid(long num_iid) {
                    this.num_iid = num_iid;
                }

                public String getPict_url() {
                    return pict_url;
                }

                public void setPict_url(String pict_url) {
                    this.pict_url = pict_url;
                }

                public String getProvcity() {
                    return provcity;
                }

                public void setProvcity(String provcity) {
                    this.provcity = provcity;
                }

                public String getReserve_price() {
                    return reserve_price;
                }

                public void setReserve_price(String reserve_price) {
                    this.reserve_price = reserve_price;
                }

                public long getSeller_id() {
                    return seller_id;
                }

                public void setSeller_id(long seller_id) {
                    this.seller_id = seller_id;
                }

                public SmallImagesBean getSmall_images() {
                    return small_images;
                }

                public void setSmall_images(SmallImagesBean small_images) {
                    this.small_images = small_images;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getUser_type() {
                    return user_type;
                }

                public void setUser_type(int user_type) {
                    this.user_type = user_type;
                }

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }

                public String getZk_final_price() {
                    return zk_final_price;
                }

                public void setZk_final_price(String zk_final_price) {
                    this.zk_final_price = zk_final_price;
                }

                public static class SmallImagesBean {
                    private List<String> string;

                    public List<String> getString() {
                        return string;
                    }

                    public void setString(List<String> string) {
                        this.string = string;
                    }
                }
            }
        }
    }
}
