package `in`.zhiwei.aqi.network.res

/**
 * 根据输入的字母，返回的AQI城市站点的数据
 * Author: gzw48760.
 * Date: 2018/2/11 0011,21:21.
 */

class SearchRes {

    /**
     * dt : 1.088383ms
     * results : [{"n":["Heze"],"x":1521,"z":0},{"n":["Hebi"],"x":5847,"z":0},{"n":["Heihe"],"x":7919,"z":0},{"n":["Hefei"],"x":1497,"z":0},{"n":["Heyuan"],"x":3513,"z":0},{"n":["Hegang"],"x":7916,"z":0},{"n":["Hengyang"],"x":7905,"z":0},{"n":["Helsinki"],"x":5717,"z":0},{"n":["Hengshui"],"x":1464,"z":0},{"n":["Herstal, Belgium"],"x":3006,"z":0},{"n":["Hengchun, Taiwan"],"x":1645,"z":0},{"n":["Henan'an, Huizhou"],"x":812,"z":0},{"n":["Henggang, Shenzhen"],"x":929,"z":0},{"n":["Hemkade, Amsterdam"],"x":2668,"z":0},{"n":["Heilbronn, Germany"],"x":6160,"z":0},{"n":["Herzogberg, Austria"],"x":2890,"z":0},{"n":["Heidelberg, Germany"],"x":6159,"z":0},{"n":["Heishipo, Guangyuan"],"x":7043,"z":0},{"n":["Hemenkou, Panzhihua"],"x":3607,"z":0},{"n":["Heima group, Dezhou"],"x":616,"z":0}]
     * term : he
     */

    var dt: String? = null
    var term: String? = null
    var results: List<ResultsBean>? = null

    class ResultsBean {
        /**
         * n : ["Heze"]
         * x : 1521
         * z : 0
         */

        var x: Int = 0
        var z: Int = 0
        var n: List<String>? = null
    }
}
