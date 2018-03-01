package in.zhiwei.aqi.network.res;

import java.util.List;

/**
 * 根据输入的字母，返回的AQI城市站点的数据
 * Author: gzw48760.
 * Date: 2018/2/11 0011,21:21.
 */

public class SearchRes {

    /**
     * dt : 1.088383ms
     * results : [{"n":["Heze"],"x":1521,"z":0},{"n":["Hebi"],"x":5847,"z":0},{"n":["Heihe"],"x":7919,"z":0},{"n":["Hefei"],"x":1497,"z":0},{"n":["Heyuan"],"x":3513,"z":0},{"n":["Hegang"],"x":7916,"z":0},{"n":["Hengyang"],"x":7905,"z":0},{"n":["Helsinki"],"x":5717,"z":0},{"n":["Hengshui"],"x":1464,"z":0},{"n":["Herstal, Belgium"],"x":3006,"z":0},{"n":["Hengchun, Taiwan"],"x":1645,"z":0},{"n":["Henan'an, Huizhou"],"x":812,"z":0},{"n":["Henggang, Shenzhen"],"x":929,"z":0},{"n":["Hemkade, Amsterdam"],"x":2668,"z":0},{"n":["Heilbronn, Germany"],"x":6160,"z":0},{"n":["Herzogberg, Austria"],"x":2890,"z":0},{"n":["Heidelberg, Germany"],"x":6159,"z":0},{"n":["Heishipo, Guangyuan"],"x":7043,"z":0},{"n":["Hemenkou, Panzhihua"],"x":3607,"z":0},{"n":["Heima group, Dezhou"],"x":616,"z":0}]
     * term : he
     */

    private String dt;
    private String term;
    private List<ResultsBean> results;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * n : ["Heze"]
         * x : 1521
         * z : 0
         */

        private int x;
        private int z;
        private List<String> n;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public List<String> getN() {
            return n;
        }

        public void setN(List<String> n) {
            this.n = n;
        }
    }
}
