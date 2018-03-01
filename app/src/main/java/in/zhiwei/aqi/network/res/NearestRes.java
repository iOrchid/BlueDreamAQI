package in.zhiwei.aqi.network.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * https://api.waqi.info/mapq/nearest?from=1451
 * 根据网络ip，查看距离当前ip最近的AQI站点
 * Author: gzw48760.
 * Date: 2018/2/11 0011,21:17.
 */

public class NearestRes {

    /**
     * d : [{"nlo":"Suzhou","nna":"苏州","t":1518350400,"pol":"pm25","x":"1489","v":"160","u":"Suzhou","key":"_Cy6tysgvBQA","d":1.1,"geo":[31.298886,120.585316]}]
     * g : {"ip":"58.211.111.35","city":"Suzhou","country":"China","names":{"de":"Suzhou","en":"Suzhou","es":"Suzhou","fr":"Suzhou","ja":"蘇州市","pt-BR":"Suzhou","ru":"Сучжоу","zh-CN":"苏州"},"iso2":"CN","geo":[31.3041,120.5954],"distance":648.3678843782851}
     */

    private GBean g;
    private List<DBean> d;

    public GBean getG() {
        return g;
    }

    public void setG(GBean g) {
        this.g = g;
    }

    public List<DBean> getD() {
        return d;
    }

    public void setD(List<DBean> d) {
        this.d = d;
    }

    public static class GBean {
        /**
         * ip : 58.211.111.35
         * city : Suzhou
         * country : China
         * names : {"de":"Suzhou","en":"Suzhou","es":"Suzhou","fr":"Suzhou","ja":"蘇州市","pt-BR":"Suzhou","ru":"Сучжоу","zh-CN":"苏州"}
         * iso2 : CN
         * geo : [31.3041,120.5954]
         * distance : 648.3678843782851
         */

        private String ip;
        private String city;
        private String country;
        private NamesBean names;
        private String iso2;
        private double distance;
        private List<Double> geo;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public NamesBean getNames() {
            return names;
        }

        public void setNames(NamesBean names) {
            this.names = names;
        }

        public String getIso2() {
            return iso2;
        }

        public void setIso2(String iso2) {
            this.iso2 = iso2;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public List<Double> getGeo() {
            return geo;
        }

        public void setGeo(List<Double> geo) {
            this.geo = geo;
        }

        public static class NamesBean {
            /**
             * de : Suzhou
             * en : Suzhou
             * es : Suzhou
             * fr : Suzhou
             * ja : 蘇州市
             * pt-BR : Suzhou
             * ru : Сучжоу
             * zh-CN : 苏州
             */

            private String de;
            private String en;
            private String es;
            private String fr;
            private String ja;
            @SerializedName("pt-BR")
            private String ptBR;
            private String ru;
            @SerializedName("zh-CN")
            private String zhCN;

            public String getDe() {
                return de;
            }

            public void setDe(String de) {
                this.de = de;
            }

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public String getEs() {
                return es;
            }

            public void setEs(String es) {
                this.es = es;
            }

            public String getFr() {
                return fr;
            }

            public void setFr(String fr) {
                this.fr = fr;
            }

            public String getJa() {
                return ja;
            }

            public void setJa(String ja) {
                this.ja = ja;
            }

            public String getPtBR() {
                return ptBR;
            }

            public void setPtBR(String ptBR) {
                this.ptBR = ptBR;
            }

            public String getRu() {
                return ru;
            }

            public void setRu(String ru) {
                this.ru = ru;
            }

            public String getZhCN() {
                return zhCN;
            }

            public void setZhCN(String zhCN) {
                this.zhCN = zhCN;
            }
        }
    }

    public static class DBean {
        /**
         * nlo : Suzhou
         * nna : 苏州
         * t : 1518350400
         * pol : pm25
         * x : 1489
         * v : 160
         * u : Suzhou
         * key : _Cy6tysgvBQA
         * d : 1.1
         * geo : [31.298886,120.585316]
         */

        private String nlo;
        private String nna;
        private long t;
        private String pol;
        private String x;
        private String v;
        private String u;
        private String key;
        private double d;
        private List<Double> geo;

        public String getNlo() {
            return nlo;
        }

        public void setNlo(String nlo) {
            this.nlo = nlo;
        }

        public String getNna() {
            return nna;
        }

        public void setNna(String nna) {
            this.nna = nna;
        }

        public long getT() {
            return t;
        }

        public void setT(long t) {
            this.t = t;
        }

        public String getPol() {
            return pol;
        }

        public void setPol(String pol) {
            this.pol = pol;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public double getD() {
            return d;
        }

        public void setD(double d) {
            this.d = d;
        }

        public List<Double> getGeo() {
            return geo;
        }

        public void setGeo(List<Double> geo) {
            this.geo = geo;
        }
    }
}
