package com.mlzq.mytao.bean;

/**
 * Created by Dev on 2018/10/17.
 * desc :
 */

public class TaokoulingBean {

    /**
     * tbk_tpwd_create_response : {"data":{"model":"￥AADPOKFz￥"}}
     */

    private TbkTpwdCreateResponseBean tbk_tpwd_create_response;

    public TbkTpwdCreateResponseBean getTbk_tpwd_create_response() {
        return tbk_tpwd_create_response;
    }

    public void setTbk_tpwd_create_response(TbkTpwdCreateResponseBean tbk_tpwd_create_response) {
        this.tbk_tpwd_create_response = tbk_tpwd_create_response;
    }

    public static class TbkTpwdCreateResponseBean {
        /**
         * data : {"model":"￥AADPOKFz￥"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * model : ￥AADPOKFz￥
             */

            private String model;

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }
        }
    }
}
