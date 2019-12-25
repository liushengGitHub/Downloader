package liusheng.download.manhuadui.bean;

import java.util.List;

public class ConfigJson {

    private List<DoMainListBean> doMainList;

    public List<DoMainListBean> getDoMainList() {
        return doMainList;
    }

    public void setDoMainList(List<DoMainListBean> doMainList) {
        this.doMainList = doMainList;
    }

    public static class DoMainListBean {
        /**
         * name : 自动选择
         * domain : ["https://mhcdn.manhuazj.com"]
         */

        private String name;
        private List<String> domain;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getDomain() {
            return domain;
        }

        public void setDomain(List<String> domain) {
            this.domain = domain;
        }
    }
}
