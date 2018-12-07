package `in`.zhiwei.aqi.network.res

import com.google.gson.annotations.SerializedName

/**
 * https://api.waqi.info/mapq/nearest?from=1451
 * 根据网络ip，查看距离当前ip最近的AQI站点
 * Author: gzw48760.
 * Date: 2018/2/11 0011,21:17.
 */

class NearestRes {

    /**
     * d : [{"nlo":"Suzhou","nna":"苏州","t":1518350400,"pol":"pm25","x":"1489","v":"160","u":"Suzhou","key":"_Cy6tysgvBQA","d":1.1,"geo":[31.298886,120.585316]}]
     * g : {"ip":"58.211.111.35","city":"Suzhou","country":"China","names":{"de":"Suzhou","en":"Suzhou","es":"Suzhou","fr":"Suzhou","ja":"蘇州市","pt-BR":"Suzhou","ru":"Сучжоу","zh-CN":"苏州"},"iso2":"CN","geo":[31.3041,120.5954],"distance":648.3678843782851}
     */

    var g: GBean? = null
    var d: List<DBean>? = null

    class GBean {
        /**
         * ip : 58.211.111.35
         * city : Suzhou
         * country : China
         * names : {"de":"Suzhou","en":"Suzhou","es":"Suzhou","fr":"Suzhou","ja":"蘇州市","pt-BR":"Suzhou","ru":"Сучжоу","zh-CN":"苏州"}
         * iso2 : CN
         * geo : [31.3041,120.5954]
         * distance : 648.3678843782851
         */

        var ip: String? = null
        var city: String? = null
        var country: String? = null
        var names: NamesBean? = null
        var iso2: String? = null
        var distance: Double = 0.toDouble()
        var geo: List<Double>? = null

        class NamesBean {
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

            var de: String? = null
            var en: String? = null
            var es: String? = null
            var fr: String? = null
            var ja: String? = null
            @SerializedName("pt-BR")
            var ptBR: String? = null
            var ru: String? = null
            @SerializedName("zh-CN")
            var zhCN: String? = null
        }
    }

    class DBean {
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

        var nlo: String? = null
        var nna: String? = null
        var t: Long = 0
        var pol: String? = null
        var x: String? = null
        var v: String? = null
        var u: String? = null
        var key: String? = null
        var d: Double = 0.toDouble()
        var geo: List<Double>? = null
    }
}
