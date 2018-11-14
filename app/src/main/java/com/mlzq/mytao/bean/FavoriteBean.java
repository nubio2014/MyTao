package com.mlzq.mytao.bean;

import java.util.List;

/**
 * Created by Dev on 2018/10/24.
 * desc : 选品库列表
 */

public class FavoriteBean {


    /**
     * tbk_uatm_favorites_get_response : {"results":{"tbk_favorites":[{"favorites_id":18742521,"favorites_title":"家居","type":1},{"favorites_id":18742518,"favorites_title":"女装","type":1},{"favorites_id":18700565,"favorites_title":"数码","type":1}]},"total_results":3,"request_id":"1463e49zro2w6"}
     */

    private TbkUatmFavoritesGetResponseBean tbk_uatm_favorites_get_response;

    public TbkUatmFavoritesGetResponseBean getTbk_uatm_favorites_get_response() {
        return tbk_uatm_favorites_get_response;
    }

    public void setTbk_uatm_favorites_get_response(TbkUatmFavoritesGetResponseBean tbk_uatm_favorites_get_response) {
        this.tbk_uatm_favorites_get_response = tbk_uatm_favorites_get_response;
    }

    public static class TbkUatmFavoritesGetResponseBean {
        /**
         * results : {"tbk_favorites":[{"favorites_id":18742521,"favorites_title":"家居","type":1},{"favorites_id":18742518,"favorites_title":"女装","type":1},{"favorites_id":18700565,"favorites_title":"数码","type":1}]}
         * total_results : 3
         * request_id : 1463e49zro2w6
         */

        private ResultsBean results;
        private int total_results;
        private String request_id;

        public ResultsBean getResults() {
            return results;
        }

        public void setResults(ResultsBean results) {
            this.results = results;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultsBean {
            private List<TbkFavoritesBean> tbk_favorites;

            public List<TbkFavoritesBean> getTbk_favorites() {
                return tbk_favorites;
            }

            public void setTbk_favorites(List<TbkFavoritesBean> tbk_favorites) {
                this.tbk_favorites = tbk_favorites;
            }

            public static class TbkFavoritesBean {
                /**
                 * favorites_id : 18742521
                 * favorites_title : 家居
                 * type : 1
                 */

                private int favorites_id;
                private String favorites_title;
                private int type;

                public int getFavorites_id() {
                    return favorites_id;
                }

                public void setFavorites_id(int favorites_id) {
                    this.favorites_id = favorites_id;
                }

                public String getFavorites_title() {
                    return favorites_title;
                }

                public void setFavorites_title(String favorites_title) {
                    this.favorites_title = favorites_title;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
