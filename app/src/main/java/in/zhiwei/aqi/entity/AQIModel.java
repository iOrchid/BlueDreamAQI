package in.zhiwei.aqi.entity;

import java.util.List;

/**
 * Author: gzw48760.
 * Date: 2018/2/5 0005,21:00.
 */

public class AQIModel {

    /**
     * timestamp : 1518311869
     * city : {"name":"北京","url":"http://aqicn.org/city/beijing/cn/","idx":1451,"id":"Beijing","geo":["39.954592","116.468117"]}
     * iaqi : [{"p":"pm25","v":[34,25,151],"i":"北京 pm25 (小颗粒物)  measured by U.S Embassy Beijing Air Quality Monitor (美国驻北京大使馆空气质量监测). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 09:00:00",1,[[0,34],"qaTEzdKaHmHelOaHEjt",-28,"fqlcDEafaQiaedGlODpgv",-34,"npQ",50,44,"hcAfbfabahKNbAAEO",28,"HAlmtCJQ",-76,-34,"eDgFSm",-29,"raieHMriDaLIazp",87,"lDvgLBfMAdCgaBFccEH",-63,"SGJcIPeeHaiHmiLaHqLa",27,"SiTiUiajeQjEDe"]]},{"p":"pm10","v":[58,18,170],"i":"北京 pm10 (可吸入颗粒物)  measured by Beijing Environmental Protection Monitoring Center (北京市环境保护监测中心). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 09:00:00",1,[[0,58],"fFd",-27,"BcsldhGEdeHQnaDKeJAeDFClIAcfchljpos",27,"G",-45,"AoI",30,53,-67,"XHcaghhfbaGBBdDHafEFD",[2,10],"iE",-37,"VH",[2,-1],"dAfGecbjeemJHA",63,-36,29,-37,"R",29,"hjkMlAdJaccgaJCcJCGTKAcFCbGAadfaccdbbCamO",[2,15],[2,-18]]]},{"p":"o3","v":[26,19,35],"i":"北京 o3 (臭氧)  measured by Beijing Environmental Protection Monitoring Center (北京市环境保护监测中心). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 09:00:00",10,[[0,260],"reieTeMviDePU",45,"yinDDMDT",-32,[2,-82],37,"LU",29,36,"ejaEjueuieDaeeH",-52,-53,-155,-52,"DaeaeaieiD",45,36,57,41,"e",[2,57],"e",-49,-134,-49,"PHH",77,"ePDa",73,"e",53,65,"qyiXyqjDIaHiL",-32,"Y",-166,-85,"ae",[2,-4],"aaee",40,"r",32,33,69,44,-40,"aa",-37,"Di",-57,-48,"I",-52,48,65,57,57,-45,"naMev",40,"e",29,"P",32,"D",-32,"uPU"]]},{"p":"no2","v":[4,2,19],"i":"北京 no2 (二氧化氮)  measured by Beijing Environmental Protection Monitoring Center (北京市环境保护监测中心). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 09:00:00",10,[[0,37],"afSfeIafosetjWIRJafaaEo",101,"NI",-32,32,-28,-41,"oWfjaaeDaIEaDaj",55,86,138,54,"kEEtIJD",87,"N",-28,"M",-82,-83,-55,-32,"R",[2,23],50,101,"xSa",55,"IR",-32,"ef",-32,-59,-42,-50,"WIax",-32,"WDaaeaIf",54,69,128,27,"at",32,32,"j",-32,"Ra",-42,"MNI",-64,-46,"sfI",73,41,"D",-37,"Wj",37,"e",-105,-55,-54,41,"jI",32,"SI",-32,"D",-28,-41,28,"a",-28,32,-46,87]]},{"p":"so2","v":[1,1,17],"i":"北京 so2 (二氧化硫)  measured by Beijing Environmental Protection Monitoring Center (北京市环境保护监测中心). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 04:00:00",10,[[0,11],"aaf",[7,-5],"aa",[3,5],"aafzaOaEJaafaaaE",[2,-5],"aafaEaapz",56,"JUlfukEz",31,-71,"Zgkkf",-31,"O",26,"pz",-35,"EKgPYOEafaaEJfakEEafEkaaEJkk",56,"kf",40,"fEEkTJap",26,"KJ",-30,"fafO",-41,"JJTaEaaEaafEfEffaaEafEkOfaEkaaEfa",[2,-10]]]},{"p":"co","v":[3,2,9],"i":"北京 co (一氧化碳)  measured by Beijing Environmental Protection Monitoring Center (北京市环境保护监测中心). Values are converted from µg/m3 to AQI levels using the EPA standard.","h":["2018/02/11 09:00:00",10,[[0,28],"jaIaajsaajIasRI",[3,-27],"asaajasIss",-36,"jRIaIajIj",[2,-18],"aajIjj",27,54,"jI",27,"aIjja",54,-45,-27,-27,"RIIIIas",27,45,45,"jR",-27,-63,-36,"IajR",[5,-9],"aaaa",[3,-9],"aaaajj",63,45,"Ijss",-27,-27,-27,"jaajaRIaIaIjaRRaajRR",[2,-9],"aajjaa",[3,-9],"aa",[2,0],[2,0]]]},{"p":"t","v":[-6,-11,4],"i":"北京  t (温度。) , expressed in degrees celcius and  measured by wunderground.com","h":["2018/02/11 08:00:00",10,[[0,-60],"akaaa",-30,40,"akaJakEfkaaaJaaJaJa",-40,"Tau",[2,20],"kkkauuukkaEfT",-30,"TJ",-40,"EYukJf",35,"kakapEukafOTJ",-30,"T",-30,-30,"uTau",30,"akJuf",35,"kkkkff",220,-240,"aJ",[2,-20],-60,"u",-50,"kk",40,"TaupfkkukaaaaTTYY"]]},{"p":"d","v":[-23,-32,-16],"i":"北京  d (露点)  measured by wunderground.com","h":["2018/02/11 08:00:00",10,[[0,-230],"JkpfJJuaJJaaaOEaaJkaakukaJJaTa",[2,0],"aaaak",-70,"Juuuzf",40,30,"akJaauTuOpaTTaJk",-30,"aJf",65,"uaaka",-30,"a",-50,"kOfaaaaEfaaaTJa",430,-430,30,"f",[2,5],60,"a",-30,"kakTJkkaaJaTJaJakJEf"]]},{"p":"p","v":[1027,1020,1032],"i":"北京  p (空气压力：)  measured by wunderground.com","h":["2018/02/11 08:00:00",10,[[0,10270],"aJaakaakaaaaaaaaaakkkkaJJaaaak",[2,0],"JakJTTTTaJJakJaJJJJaaaakaaaaaaJkaauukkkaJkakfpaakaffJaJaaa",-180,190,"aa",[2,-15],"OaJJaaaakaaaaaaakakuuka"]]},{"p":"h","v":[26,6,38],"i":"北京  h (湿度)  measured by wunderground.com","h":["2018/02/11 08:00:00",10,[[0,260],"Ta",30,"kT",40,-40,"a",-40,"JkaJTTJaJkkfp",50,"uuT",75,45,-60,-50,[2,-40],"JTJaJ",-100,"Tkkuua",80,120,50,60,100,"z",115,"k",-150,150,-105,-75,-40,-60,-90,"a",-50,30,-90,"TJa",150,110,40,110,155,185,"a",-130,-180,60,-170,-90,"a",-30,30,-50,-30,-70,"JTT",-40,"YE",640,-610,40,30,[2,50],440,-100,100,"a",-60,-130,"J",-40,-30,"OO",-30,-60,-40,-70,"JaTa",50,"k",45,75]]},{"p":"w","v":[8,1,12],"i":"北京  w (风)  measured by wunderground.com","h":["2018/02/11 08:00:00",10,[[0,80],"jjRV",-27,"a",27,"oeRRIsqpsR",41,"IjDEaNRH",-52,"Kja",[2,27],"noaizGoNaIm",-43,"aIjaROGIajhPaaaajIjwjj",-49,"aajaIjIjIajIjaasnxaaiaH",-63,89,"IM",[2,13],-71,"nV",[2,0],"aaajDEajwIFgVjaaIa"]]}]
     * aqi : 58
     * time : {"s":{"en":{"ago":"","time":"Updated on Sunday 9:00"},"cn":{"ago":"","time":"更新时间 星期日9:00"},"jp":{"ago":"","time":"日曜日9時に更新"},"es":{"ago":"","time":"Actualizado el domingo 9:00"},"kr":{"ago":"","time":"일요일 9시에 업데이트"},"ru":{"ago":"","time":"Обновлено воскресенье 9:00"},"hk":{"ago":"","time":"更新时间 星期天9:00"},"fr":{"ago":"","time":"Mise à jour le dimanche 9:00"},"pl":{"ago":"","time":"Poprawiony: Niedziela 9:00"},"de":{"ago":"","time":"Aktualisiert am Sonntag um 9"},"pt":{"ago":"","time":"Updated on domingo 9:00"},"vn":{"ago":"","time":"Cập nhật ngày chủ nhật 9"}},"v":"2018-02-11T09:00:00+09:00"}
     * forecast : {"aqi":[{"t":"2018-02-08T15:00:00+00:00","v":[158,174]},{"t":"2018-02-08T18:00:00+00:00","v":[174,250]},{"t":"2018-02-08T21:00:00+00:00","v":[174,250]},{"t":"2018-02-09T00:00:00+00:00","v":[158,174]},{"t":"2018-02-09T03:00:00+00:00","v":[137,158]},{"t":"2018-02-09T06:00:00+00:00","v":[68,89]},{"t":"2018-02-09T09:00:00+00:00","v":[89,137]},{"t":"2018-02-09T12:00:00+00:00","v":[89,137]},{"t":"2018-02-09T15:00:00+00:00","v":[68,89]},{"t":"2018-02-09T18:00:00+00:00","v":[68,89]},{"t":"2018-02-09T21:00:00+00:00","v":[89,137]},{"t":"2018-02-10T00:00:00+00:00","v":[137,158]},{"t":"2018-02-10T03:00:00+00:00","v":[137,158]},{"t":"2018-02-10T06:00:00+00:00","v":[68,89]},{"t":"2018-02-10T09:00:00+00:00","v":[68,89]},{"t":"2018-02-10T12:00:00+00:00","v":[89,137]},{"t":"2018-02-10T15:00:00+00:00","v":[89,137]},{"t":"2018-02-10T18:00:00+00:00","v":[42,68]},{"t":"2018-02-10T21:00:00+00:00","v":[68,89]},{"t":"2018-02-11T00:00:00+00:00","v":[68,89]},{"t":"2018-02-11T03:00:00+00:00","v":[89,137]},{"t":"2018-02-11T06:00:00+00:00","v":[158,174]},{"t":"2018-02-11T09:00:00+00:00","v":[158,174]},{"t":"2018-02-11T12:00:00+00:00","v":[158,174]},{"t":"2018-02-11T15:00:00+00:00","v":[137,158]},{"t":"2018-02-11T18:00:00+00:00","v":[89,137]},{"t":"2018-02-11T21:00:00+00:00","v":[137,158]},{"t":"2018-02-12T00:00:00+00:00","v":[137,158]},{"t":"2018-02-12T03:00:00+00:00","v":[137,158]},{"t":"2018-02-12T06:00:00+00:00","v":[137,158]},{"t":"2018-02-12T09:00:00+00:00","v":[89,137]},{"t":"2018-02-12T12:00:00+00:00","v":[137,158]},{"t":"2018-02-12T15:00:00+00:00","v":[158,174]},{"t":"2018-02-12T18:00:00+00:00","v":[158,174]},{"t":"2018-02-12T21:00:00+00:00","v":[158,174]},{"t":"2018-02-13T00:00:00+00:00","v":[174,250]},{"t":"2018-02-13T03:00:00+00:00","v":[174,250]},{"t":"2018-02-13T06:00:00+00:00","v":[158,174]},{"t":"2018-02-13T09:00:00+00:00","v":[158,174]},{"t":"2018-02-13T12:00:00+00:00","v":[174,250]},{"t":"2018-02-13T15:00:00+00:00","v":[158,174]},{"t":"2018-02-13T18:00:00+00:00","v":[89,137]},{"t":"2018-02-13T21:00:00+00:00","v":[89,137]},{"t":"2018-02-14T00:00:00+00:00","v":[137,158]},{"t":"2018-02-14T03:00:00+00:00","v":[89,137]},{"t":"2018-02-14T06:00:00+00:00","v":[42,68]},{"t":"2018-02-14T09:00:00+00:00","v":[68,89]},{"t":"2018-02-14T12:00:00+00:00","v":[137,158]},{"t":"2018-02-14T15:00:00+00:00","v":[158,174]},{"t":"2018-02-14T18:00:00+00:00","v":[158,174]},{"t":"2018-02-14T21:00:00+00:00","v":[174,250]},{"t":"2018-02-15T00:00:00+00:00","v":[174,250]},{"t":"2018-02-15T03:00:00+00:00","v":[174,250]},{"t":"2018-02-15T06:00:00+00:00","v":[158,174]},{"t":"2018-02-15T09:00:00+00:00","v":[137,158]},{"t":"2018-02-15T12:00:00+00:00","v":[158,174]},{"t":"2018-02-15T15:00:00+00:00","v":[158,174]},{"t":"2018-02-15T18:00:00+00:00","v":[174,250]},{"t":"2018-02-15T21:00:00+00:00","v":[174,250]},{"t":"2018-02-16T00:00:00+00:00","v":[174,250]},{"t":"2018-02-16T03:00:00+00:00","v":[174,250]},{"t":"2018-02-16T06:00:00+00:00","v":[174,250]},{"t":"2018-02-16T09:00:00+00:00","v":[174,250]}],"wind":[{"t":"2018-02-10T02:00:00+00:00","w":[8.7,12.8,302.9],"c":-4},{"t":"2018-02-10T03:00:00+00:00","w":[8.9,13.8,302.2],"c":-3.7},{"t":"2018-02-10T04:00:00+00:00","w":[9,13.6,302.9],"c":-3.2},{"t":"2018-02-10T05:00:00+00:00","w":[8.8,13.3,303.7],"c":-2.7},{"t":"2018-02-10T06:00:00+00:00","w":[7.9,12.5,303.2],"c":-2.5},{"t":"2018-02-10T07:00:00+00:00","w":[7.8,12.2,306.9],"c":-2},{"t":"2018-02-10T08:00:00+00:00","w":[7.5,12.1,309.2],"c":-1.9},{"t":"2018-02-10T09:00:00+00:00","w":[6.8,11.9,310.3],"c":-2},{"t":"2018-02-10T10:00:00+00:00","w":[5.8,11.5,311.7],"c":-2.5},{"t":"2018-02-10T11:00:00+00:00","w":[5.6,11.3,310],"c":-2.8},{"t":"2018-02-10T12:00:00+00:00","w":[3.6,10.3,285.5],"c":-3.3},{"t":"2018-02-10T13:00:00+00:00","w":[3.9,10.3,284.8],"c":-3.4},{"t":"2018-02-10T14:00:00+00:00","w":[4.7,11.1,291.9],"c":-3.6},{"t":"2018-02-10T15:00:00+00:00","w":[4.4,11.1,294],"c":-3.9},{"t":"2018-02-10T16:00:00+00:00","w":[4.2,11.1,295.7],"c":-4.3},{"t":"2018-02-10T17:00:00+00:00","w":[4,10.9,296.9],"c":-4.6},{"t":"2018-02-10T18:00:00+00:00","w":[4.5,9.7,292.4],"c":-4.9},{"t":"2018-02-10T19:00:00+00:00","w":[5,10.1,297.3],"c":-5.1},{"t":"2018-02-10T20:00:00+00:00","w":[5.6,11,295.6],"c":-5.3},{"t":"2018-02-10T21:00:00+00:00","w":[5.8,11.4,294.6],"c":-5.6},{"t":"2018-02-10T22:00:00+00:00","w":[5.8,12,292.1],"c":-6},{"t":"2018-02-10T23:00:00+00:00","w":[6,12.4,291.6],"c":-6.4},{"t":"2018-02-11T00:00:00+00:00","w":[7.9,13.2,303],"c":-6.5},{"t":"2018-02-11T01:00:00+00:00","w":[9,13.7,310.5],"c":-6.1},{"t":"2018-02-11T02:00:00+00:00","w":[9,13.2,313.1],"c":-5.6},{"t":"2018-02-11T03:00:00+00:00","w":[8.7,12.5,314.1],"c":-5},{"t":"2018-02-11T04:00:00+00:00","w":[8.3,11.9,314.6],"c":-4.3},{"t":"2018-02-11T05:00:00+00:00","w":[8,11.8,314],"c":-3.6},{"t":"2018-02-11T06:00:00+00:00","w":[7.9,11.8,313.5],"c":-2.9},{"t":"2018-02-11T07:00:00+00:00","w":[8,11.9,313.5],"c":-2.5},{"t":"2018-02-11T08:00:00+00:00","w":[8,12.1,313.9],"c":-2.3},{"t":"2018-02-11T09:00:00+00:00","w":[7.8,12,314.6],"c":-2.5},{"t":"2018-02-11T10:00:00+00:00","w":[7.4,12.5,315.1],"c":-3},{"t":"2018-02-11T11:00:00+00:00","w":[7.5,12.8,316.9],"c":-3.4},{"t":"2018-02-11T12:00:00+00:00","w":[7.4,13.5,314.8],"c":-3.7},{"t":"2018-02-11T13:00:00+00:00","w":[7.2,13.5,311.7],"c":-4},{"t":"2018-02-11T14:00:00+00:00","w":[6.9,13.1,308.1],"c":-4.2},{"t":"2018-02-11T15:00:00+00:00","w":[6.3,12.8,305.4],"c":-4.5},{"t":"2018-02-11T16:00:00+00:00","w":[5.2,12.6,301.4],"c":-4.7},{"t":"2018-02-11T17:00:00+00:00","w":[4.5,12.4,294.4],"c":-5},{"t":"2018-02-11T18:00:00+00:00","w":[4.2,11.7,285],"c":-5.2},{"t":"2018-02-11T19:00:00+00:00","w":[4,11.3,275.6],"c":-5.4},{"t":"2018-02-11T20:00:00+00:00","w":[3.8,11.1,265.3],"c":-5.6},{"t":"2018-02-11T21:00:00+00:00","w":[3.7,8,257.2],"c":-5.7},{"t":"2018-02-11T22:00:00+00:00","w":[3.4,5.3,250.1],"c":-5.9},{"t":"2018-02-11T23:00:00+00:00","w":[3.3,4.1,244.9],"c":-6.1},{"t":"2018-02-12T00:00:00+00:00","w":[3.3,7,245.7],"c":-5.8},{"t":"2018-02-12T01:00:00+00:00","w":[4,10.8,272.8],"c":-4.1},{"t":"2018-02-12T02:00:00+00:00","w":[6,11.9,300],"c":-2},{"t":"2018-02-12T03:00:00+00:00","w":[6.2,11.6,305],"c":-0.3},{"t":"2018-02-12T04:00:00+00:00","w":[6.2,10.8,304.5],"c":1.4},{"t":"2018-02-12T05:00:00+00:00","w":[5.9,11.2,302.3],"c":2.9},{"t":"2018-02-12T06:00:00+00:00","w":[5.6,10.8,299],"c":4.1},{"t":"2018-02-12T07:00:00+00:00","w":[4.8,10.8,291.2],"c":4.9},{"t":"2018-02-12T08:00:00+00:00","w":[3.3,4.3,255.2],"c":4.5},{"t":"2018-02-12T09:00:00+00:00","w":[3.9,3.9,211.1],"c":3.2},{"t":"2018-02-12T10:00:00+00:00","w":[4.6,6.1,203.2],"c":1.5},{"t":"2018-02-12T11:00:00+00:00","w":[4.6,7.4,212.4],"c":0.9},{"t":"2018-02-12T12:00:00+00:00","w":[3.7,5.1,227],"c":0.5},{"t":"2018-02-12T13:00:00+00:00","w":[2.5,2.5,236.6],"c":0.1},{"t":"2018-02-12T14:00:00+00:00","w":[1.6,1.6,236.3],"c":-0.2},{"t":"2018-02-12T15:00:00+00:00","w":[1.1,1.1,255.5],"c":-0.5},{"t":"2018-02-12T16:00:00+00:00","w":[0.8,0.9,344.7],"c":-0.7},{"t":"2018-02-12T17:00:00+00:00","w":[1.2,1.2,23.5],"c":-0.9},{"t":"2018-02-12T18:00:00+00:00","w":[1.4,1.4,39.7],"c":-1.3},{"t":"2018-02-12T19:00:00+00:00","w":[1.5,1.5,41],"c":-1.7},{"t":"2018-02-12T20:00:00+00:00","w":[1.8,1.8,30.4],"c":-2},{"t":"2018-02-12T21:00:00+00:00","w":[1.9,1.9,32.7],"c":-2.3},{"t":"2018-02-12T22:00:00+00:00","w":[1.4,1.4,50.8],"c":-2.6},{"t":"2018-02-12T23:00:00+00:00","w":[1,1,66.7],"c":-2.9},{"t":"2018-02-13T00:00:00+00:00","w":[1,1,61.2],"c":-2.7},{"t":"2018-02-13T01:00:00+00:00","w":[1.1,1.1,69.4],"c":-1.6},{"t":"2018-02-13T02:00:00+00:00","w":[1.2,1.5,82.9],"c":-0.1},{"t":"2018-02-13T03:00:00+00:00","w":[1.3,1.5,84.7],"c":1.3},{"t":"2018-02-13T04:00:00+00:00","w":[1.3,1.3,93.1],"c":2.5},{"t":"2018-02-13T05:00:00+00:00","w":[1.4,1.4,119.1],"c":3.5},{"t":"2018-02-13T06:00:00+00:00","w":[1.8,1.8,133.9],"c":4.2},{"t":"2018-02-13T07:00:00+00:00","w":[2.2,2.5,142.3],"c":4.7},{"t":"2018-02-13T08:00:00+00:00","w":[2.6,3.1,149.4],"c":4.8},{"t":"2018-02-13T09:00:00+00:00","w":[3,3.6,154.4],"c":4},{"t":"2018-02-13T10:00:00+00:00","w":[3,3,162.6],"c":2.4},{"t":"2018-02-13T11:00:00+00:00","w":[2.1,2.1,173.3],"c":1.7},{"t":"2018-02-13T12:00:00+00:00","w":[0.5,0.5,230.2],"c":1.2},{"t":"2018-02-13T13:00:00+00:00","w":[1.1,1.1,339.9],"c":1.1},{"t":"2018-02-13T14:00:00+00:00","w":[2.3,2.3,332.8],"c":1},{"t":"2018-02-13T15:00:00+00:00","w":[3.5,3.5,332.1],"c":0.8},{"t":"2018-02-13T16:00:00+00:00","w":[4.4,5.6,337.7],"c":0.7},{"t":"2018-02-13T17:00:00+00:00","w":[4.5,6.9,341.6],"c":0.7},{"t":"2018-02-13T18:00:00+00:00","w":[4.3,6.6,345.7],"c":0.4},{"t":"2018-02-13T19:00:00+00:00","w":[3.9,4.9,352.5],"c":0},{"t":"2018-02-13T20:00:00+00:00","w":[3.6,4.6,0.6],"c":-0.3},{"t":"2018-02-13T21:00:00+00:00","w":[3.5,4.4,7.4],"c":-0.6},{"t":"2018-02-13T22:00:00+00:00","w":[3.5,4.3,10.6],"c":-0.8},{"t":"2018-02-13T23:00:00+00:00","w":[3.7,5.3,12],"c":-1.1},{"t":"2018-02-14T00:00:00+00:00","w":[4,6,16],"c":-1},{"t":"2018-02-14T01:00:00+00:00","w":[3.9,5.7,20.4],"c":0},{"t":"2018-02-14T02:00:00+00:00","w":[3.5,5,21.6],"c":1.1},{"t":"2018-02-14T03:00:00+00:00","w":[3.5,5.1,16.9],"c":2.2},{"t":"2018-02-14T04:00:00+00:00","w":[3.4,4.7,12.4],"c":3.1},{"t":"2018-02-14T05:00:00+00:00","w":[2.7,4,16],"c":4},{"t":"2018-02-14T06:00:00+00:00","w":[1.6,2.4,32.6],"c":5.1},{"t":"2018-02-14T07:00:00+00:00","w":[1.4,1.4,96.3],"c":5.5},{"t":"2018-02-14T08:00:00+00:00","w":[2.3,2.3,134.3],"c":5.2},{"t":"2018-02-14T09:00:00+00:00","w":[3.7,4.3,150.6],"c":4.5},{"t":"2018-02-14T10:00:00+00:00","w":[4.3,6.2,154.3],"c":3.1},{"t":"2018-02-14T11:00:00+00:00","w":[3.8,5.3,148],"c":2.1},{"t":"2018-02-14T12:00:00+00:00","w":[3,3.5,133.6],"c":1.2},{"t":"2018-02-14T13:00:00+00:00","w":[2.2,2.2,109.3],"c":0.5},{"t":"2018-02-14T14:00:00+00:00","w":[2,2,94.6],"c":0.2},{"t":"2018-02-14T15:00:00+00:00","w":[2,2,107.3],"c":-0.1},{"t":"2018-02-14T16:00:00+00:00","w":[2,2,128.8],"c":-0.4},{"t":"2018-02-14T17:00:00+00:00","w":[2.1,2.1,141.5],"c":-0.6},{"t":"2018-02-14T18:00:00+00:00","w":[1.7,1.7,144.6],"c":-0.9},{"t":"2018-02-14T19:00:00+00:00","w":[1.2,1.3,133.3],"c":-1.1},{"t":"2018-02-14T20:00:00+00:00","w":[1,1.2,112.1],"c":-1.3},{"t":"2018-02-14T21:00:00+00:00","w":[1.2,1.4,87.8],"c":-1.5},{"t":"2018-02-14T22:00:00+00:00","w":[1.5,1.5,85.9],"c":-1.7},{"t":"2018-02-14T23:00:00+00:00","w":[1.6,1.6,83.4],"c":-2},{"t":"2018-02-15T00:00:00+00:00","w":[1.5,1.5,82.7],"c":-1.9},{"t":"2018-02-15T01:00:00+00:00","w":[1.3,1.6,96],"c":-0.9},{"t":"2018-02-15T02:00:00+00:00","w":[1.3,1.4,125.7],"c":0.3},{"t":"2018-02-15T03:00:00+00:00","w":[1.5,1.7,136.4],"c":1.5},{"t":"2018-02-15T04:00:00+00:00","w":[1.9,2.5,158.9],"c":2.4},{"t":"2018-02-15T05:00:00+00:00","w":[2.4,3.7,182.9],"c":3.3},{"t":"2018-02-15T06:00:00+00:00","w":[2.8,4.6,198.2],"c":4.1},{"t":"2018-02-15T07:00:00+00:00","w":[3,4.8,207.8],"c":4.8},{"t":"2018-02-15T08:00:00+00:00","w":[3.1,4.9,211],"c":5},{"t":"2018-02-15T09:00:00+00:00","w":[2.8,4.9,208.4],"c":4.6},{"t":"2018-02-15T10:00:00+00:00","w":[2.3,2.3,200.4],"c":3.2},{"t":"2018-02-15T11:00:00+00:00","w":[2,2,195.5],"c":2.5},{"t":"2018-02-15T12:00:00+00:00","w":[1.6,1.6,189.2],"c":1.9},{"t":"2018-02-15T13:00:00+00:00","w":[1.5,1.5,171],"c":1.4},{"t":"2018-02-15T14:00:00+00:00","w":[1.5,1.5,152.3],"c":0.9},{"t":"2018-02-15T15:00:00+00:00","w":[1.3,1.3,129.6],"c":0.5},{"t":"2018-02-15T16:00:00+00:00","w":[1.2,1.3,100.9],"c":0.2},{"t":"2018-02-15T17:00:00+00:00","w":[1.3,1.4,76.1],"c":-0.1},{"t":"2018-02-15T18:00:00+00:00","w":[1.5,1.5,57.4],"c":-0.4},{"t":"2018-02-15T21:00:00+00:00","w":[1.6,1.6,42.6],"c":-1.1},{"t":"2018-02-16T00:00:00+00:00","w":[1.4,1.4,45.3],"c":-1},{"t":"2018-02-16T03:00:00+00:00","w":[1.2,1.5,71.5],"c":2.9},{"t":"2018-02-16T06:00:00+00:00","w":[1.5,1.7,124.3],"c":6.1},{"t":"2018-02-16T09:00:00+00:00","w":[0.9,1.4,113.8],"c":5.7},{"t":"2018-02-16T12:00:00+00:00","w":[2,2,13.9],"c":3},{"t":"2018-02-16T15:00:00+00:00","w":[1.7,1.7,56.2],"c":1.1},{"t":"2018-02-16T18:00:00+00:00","w":[2.9,2.9,102.9],"c":0.1},{"t":"2018-02-16T21:00:00+00:00","w":[3.3,7.3,106.4],"c":-1.4},{"t":"2018-02-17T00:00:00+00:00","w":[2.4,5.6,109.4],"c":-1.8},{"t":"2018-02-17T03:00:00+00:00","w":[2.4,3.3,120],"c":0.5},{"t":"2018-02-17T06:00:00+00:00","w":[2.3,3,150.7],"c":3.2},{"t":"2018-02-17T09:00:00+00:00","w":[1.4,2.7,121.2],"c":3.6},{"t":"2018-02-17T12:00:00+00:00","w":[3.1,5,132.2],"c":1.5},{"t":"2018-02-17T15:00:00+00:00","w":[3,4.6,130.8],"c":-0.5},{"t":"2018-02-17T18:00:00+00:00","w":[2.3,3.3,118.9],"c":-1.3},{"t":"2018-02-17T21:00:00+00:00","w":[1.9,2.6,101.2],"c":-2},{"t":"2018-02-18T00:00:00+00:00","w":[1.7,2.7,128.6],"c":-2.2},{"t":"2018-02-18T03:00:00+00:00","w":[1.8,2.4,132.7],"c":0.6},{"t":"2018-02-18T06:00:00+00:00","w":[1.7,2.3,139.6],"c":3.5},{"t":"2018-02-18T09:00:00+00:00","w":[1.8,3.1,135.7],"c":3.8},{"t":"2018-02-18T12:00:00+00:00","w":[3.7,6.2,143.2],"c":1.6},{"t":"2018-02-18T15:00:00+00:00","w":[3.6,5.8,145.4],"c":-0.7},{"t":"2018-02-18T18:00:00+00:00","w":[2.6,3.6,106.1],"c":-1.6},{"t":"2018-02-18T21:00:00+00:00","w":[2.5,3.5,121.7],"c":-2.3},{"t":"2018-02-19T00:00:00+00:00","w":[1.6,2.1,95.7],"c":-2.4},{"t":"2018-02-19T03:00:00+00:00","w":[1.5,1.7,109.4],"c":0},{"t":"2018-02-19T06:00:00+00:00","w":[1.7,2.5,148],"c":1.9},{"t":"2018-02-19T09:00:00+00:00","w":[0.6,1.8,96.9],"c":2.3},{"t":"2018-02-19T12:00:00+00:00","w":[1,1.2,24.6],"c":1.2},{"t":"2018-02-19T15:00:00+00:00","w":[2.9,3.6,11.3],"c":-0.2},{"t":"2018-02-19T18:00:00+00:00","w":[2.1,2.1,337.9],"c":-0.5},{"t":"2018-02-19T21:00:00+00:00","w":[6.7,10.5,323.8],"c":0.2},{"t":"2018-02-20T00:00:00+00:00","w":[5.4,8.2,343.8],"c":-0.7},{"t":"2018-02-20T03:00:00+00:00","w":[4.3,6.2,354.9],"c":1.1},{"t":"2018-02-20T06:00:00+00:00","w":[4,6.4,331.7],"c":3.4},{"t":"2018-02-20T09:00:00+00:00","w":[5.5,7.3,322.3],"c":3.7},{"t":"2018-02-20T12:00:00+00:00","w":[5.3,8.3,342.6],"c":1.3},{"t":"2018-02-20T15:00:00+00:00","w":[4.1,6.7,353.1],"c":-0.4}]}
     * nearest : [{"aqi":"63","vtime":1518307200,"id":"Beijing","name":"北京","curl":"http://aqicn.org/city/beijing/cn/m/","geo":["39.954592","116.468117"]},{"aqi":"-","vtime":1518307200,"id":"Beijing/USembassy","name":"北京美国大使馆","curl":"http://aqicn.org/city/beijing/us-embassy/cn/m/","geo":["39.954592","116.468117"]},{"aqi":"63","vtime":1518307200,"id":"Beijing/%E6%9C%9D%E9%98%B3%E5%86%9C%E5%B1%95%E9%A6%86","name":"朝阳农展馆","curl":"http://aqicn.org/city/beijing/chaoyangnongzhanguan/cn/m/","geo":["39.937","116.461"]},{"aqi":"55","vtime":1518307200,"id":"Beijing/%E4%B8%9C%E5%9B%9B%E7%8E%AF%E5%8C%97%E8%B7%AF","name":"东四环北路","curl":"http://aqicn.org/city/beijing/dongsihuanbeilu/cn/m/","geo":["39.939","116.483"]},{"aqi":"56","vtime":1518307200,"id":"Beijing/%E4%B8%9C%E5%9F%8E%E4%B8%9C%E5%9B%9B","name":"东城东四","curl":"http://aqicn.org/city/beijing/dongchengdongsi/cn/m/","geo":["39.929","116.417"]},{"aqi":"58","vtime":1518307200,"id":"Beijing/%E6%9C%9D%E9%98%B3%E5%A5%A5%E4%BD%93%E4%B8%AD%E5%BF%83","name":"朝阳奥体中心","curl":"http://aqicn.org/city/beijing/chaoyangaotizhongxin/cn/m/","geo":["39.982","116.397"]},{"aqi":"45","vtime":1518307200,"id":"Beijing/%E5%89%8D%E9%97%A8%E4%B8%9C%E5%A4%A7%E8%A1%97","name":"前门东大街","curl":"http://aqicn.org/city/beijing/qianmendongdajie/cn/m/","geo":["39.899","116.395"]},{"aqi":"45","vtime":1518307200,"id":"Beijing/%E4%B8%9C%E5%9F%8E%E5%A4%A9%E5%9D%9B","name":"东城天坛","curl":"http://aqicn.org/city/beijing/dongchengtiantan/cn/m/","geo":["39.886","116.407"]},{"aqi":"55","vtime":1518307200,"id":"Beijing/%E8%A5%BF%E7%9B%B4%E9%97%A8%E5%8C%97%E5%A4%A7%E8%A1%97","name":"西直门北大街","curl":"http://aqicn.org/city/beijing/xizhimenbeidajie/cn/m/","geo":["39.954","116.349"]},{"aqi":"44","vtime":1518307200,"id":"Beijing/%E6%B0%B8%E5%AE%9A%E9%97%A8%E5%86%85%E5%A4%A7%E8%A1%97","name":"永定门内大街","curl":"http://aqicn.org/city/beijing/yongdingmennadajie/cn/m/","geo":["39.876","116.394"]},{"aqi":"52","vtime":1518307200,"id":"Beijing/%E8%A5%BF%E5%9F%8E%E5%AE%98%E5%9B%AD","name":"西城官园","curl":"http://aqicn.org/city/beijing/xichengguanyuan/cn/m/","geo":["39.929","116.339"]},{"aqi":"43","vtime":1518307200,"id":"Beijing/%E8%A5%BF%E5%9F%8E%E4%B8%87%E5%AF%BF%E8%A5%BF%E5%AE%AB","name":"西城万寿西宫","curl":"http://aqicn.org/city/beijing/xichengwanshouxigong/cn/m/","geo":["39.878","116.352"]},{"aqi":"51","vtime":1518307200,"id":"Beijing/%E5%8D%97%E4%B8%89%E7%8E%AF%E8%A5%BF%E8%B7%AF","name":"南三环西路","curl":"http://aqicn.org/city/beijing/nansanhuanxilu/cn/m/","geo":["39.856","116.368"]},{"aqi":"52","vtime":1518307200,"id":"Beijing/%E6%B5%B7%E6%B7%80%E4%B8%87%E6%9F%B3","name":"海淀万柳","curl":"http://aqicn.org/city/beijing/haidianwanliu/cn/m/","geo":["39.987","116.287"]},{"aqi":"45","vtime":1518307200,"id":"Beijing/%E4%BA%A6%E5%BA%84%E5%BC%80%E5%8F%91%E5%8C%BA","name":"亦庄开发区","curl":"http://aqicn.org/city/beijing/yizhuangkaifaqu/cn/m/","geo":["39.795","116.506"]},{"aqi":"67","vtime":1518307200,"id":"Beijing/%E9%80%9A%E5%B7%9E%E6%96%B0%E5%9F%8E","name":"通州新城","curl":"http://aqicn.org/city/beijing/tongzhouxincheng/cn/m/","geo":["39.886","116.663"]},{"aqi":"49","vtime":1518307200,"id":"Beijing/%E4%B8%B0%E5%8F%B0%E8%8A%B1%E5%9B%AD","name":"丰台花园","curl":"http://aqicn.org/city/beijing/fengtaihuayuan/cn/m/","geo":["39.863","116.279"]},{"aqi":"42","vtime":1518307200,"id":"Beijing/%E7%9F%B3%E6%99%AF%E5%B1%B1%E5%8F%A4%E5%9F%8E","name":"石景山古城","curl":"http://aqicn.org/city/beijing/shijingshangucheng/cn/m/","geo":["39.914","116.184"]}]
     * dominentpol : pm10
     */

    private long timestamp;
    private CityBean city;
    private int aqi;
    private TimeBean time;
    private ForecastBean forecast;
    private String dominentpol;
    private List<IaqiBean> iaqi;
    private List<NearestBean> nearest;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }

    public ForecastBean getForecast() {
        return forecast;
    }

    public void setForecast(ForecastBean forecast) {
        this.forecast = forecast;
    }

    public String getDominentpol() {
        return dominentpol;
    }

    public void setDominentpol(String dominentpol) {
        this.dominentpol = dominentpol;
    }

    public List<IaqiBean> getIaqi() {
        return iaqi;
    }

    public void setIaqi(List<IaqiBean> iaqi) {
        this.iaqi = iaqi;
    }

    public List<NearestBean> getNearest() {
        return nearest;
    }

    public void setNearest(List<NearestBean> nearest) {
        this.nearest = nearest;
    }

    public static class CityBean {
        /**
         * name : 北京
         * url : http://aqicn.org/city/beijing/cn/
         * idx : 1451
         * id : Beijing
         * geo : ["39.954592","116.468117"]
         */

        private String name;
        private String url;
        private int idx;
        private String id;
        private List<String> geo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGeo() {
            return geo;
        }

        public void setGeo(List<String> geo) {
            this.geo = geo;
        }
    }

    public static class TimeBean {
        /**
         * s : {"en":{"ago":"","time":"Updated on Sunday 9:00"},"cn":{"ago":"","time":"更新时间 星期日9:00"},"jp":{"ago":"","time":"日曜日9時に更新"},"es":{"ago":"","time":"Actualizado el domingo 9:00"},"kr":{"ago":"","time":"일요일 9시에 업데이트"},"ru":{"ago":"","time":"Обновлено воскресенье 9:00"},"hk":{"ago":"","time":"更新时间 星期天9:00"},"fr":{"ago":"","time":"Mise à jour le dimanche 9:00"},"pl":{"ago":"","time":"Poprawiony: Niedziela 9:00"},"de":{"ago":"","time":"Aktualisiert am Sonntag um 9"},"pt":{"ago":"","time":"Updated on domingo 9:00"},"vn":{"ago":"","time":"Cập nhật ngày chủ nhật 9"}}
         * v : 2018-02-11T09:00:00+09:00
         */

        private SBean s;
        private String v;

        public SBean getS() {
            return s;
        }

        public void setS(SBean s) {
            this.s = s;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public static class SBean {
            /**
             * en : {"ago":"","time":"Updated on Sunday 9:00"}
             * cn : {"ago":"","time":"更新时间 星期日9:00"}
             * jp : {"ago":"","time":"日曜日9時に更新"}
             * es : {"ago":"","time":"Actualizado el domingo 9:00"}
             * kr : {"ago":"","time":"일요일 9시에 업데이트"}
             * ru : {"ago":"","time":"Обновлено воскресенье 9:00"}
             * hk : {"ago":"","time":"更新时间 星期天9:00"}
             * fr : {"ago":"","time":"Mise à jour le dimanche 9:00"}
             * pl : {"ago":"","time":"Poprawiony: Niedziela 9:00"}
             * de : {"ago":"","time":"Aktualisiert am Sonntag um 9"}
             * pt : {"ago":"","time":"Updated on domingo 9:00"}
             * vn : {"ago":"","time":"Cập nhật ngày chủ nhật 9"}
             */

            private EnBean en;
            private CnBean cn;
            private JpBean jp;
            private EsBean es;
            private KrBean kr;
            private RuBean ru;
            private HkBean hk;
            private FrBean fr;
            private PlBean pl;
            private DeBean de;
            private PtBean pt;
            private VnBean vn;

            public EnBean getEn() {
                return en;
            }

            public void setEn(EnBean en) {
                this.en = en;
            }

            public CnBean getCn() {
                return cn;
            }

            public void setCn(CnBean cn) {
                this.cn = cn;
            }

            public JpBean getJp() {
                return jp;
            }

            public void setJp(JpBean jp) {
                this.jp = jp;
            }

            public EsBean getEs() {
                return es;
            }

            public void setEs(EsBean es) {
                this.es = es;
            }

            public KrBean getKr() {
                return kr;
            }

            public void setKr(KrBean kr) {
                this.kr = kr;
            }

            public RuBean getRu() {
                return ru;
            }

            public void setRu(RuBean ru) {
                this.ru = ru;
            }

            public HkBean getHk() {
                return hk;
            }

            public void setHk(HkBean hk) {
                this.hk = hk;
            }

            public FrBean getFr() {
                return fr;
            }

            public void setFr(FrBean fr) {
                this.fr = fr;
            }

            public PlBean getPl() {
                return pl;
            }

            public void setPl(PlBean pl) {
                this.pl = pl;
            }

            public DeBean getDe() {
                return de;
            }

            public void setDe(DeBean de) {
                this.de = de;
            }

            public PtBean getPt() {
                return pt;
            }

            public void setPt(PtBean pt) {
                this.pt = pt;
            }

            public VnBean getVn() {
                return vn;
            }

            public void setVn(VnBean vn) {
                this.vn = vn;
            }

            public static class EnBean {
                /**
                 * ago :
                 * time : Updated on Sunday 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class CnBean {
                /**
                 * ago :
                 * time : 更新时间 星期日9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class JpBean {
                /**
                 * ago :
                 * time : 日曜日9時に更新
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class EsBean {
                /**
                 * ago :
                 * time : Actualizado el domingo 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class KrBean {
                /**
                 * ago :
                 * time : 일요일 9시에 업데이트
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class RuBean {
                /**
                 * ago :
                 * time : Обновлено воскресенье 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class HkBean {
                /**
                 * ago :
                 * time : 更新时间 星期天9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class FrBean {
                /**
                 * ago :
                 * time : Mise à jour le dimanche 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class PlBean {
                /**
                 * ago :
                 * time : Poprawiony: Niedziela 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class DeBean {
                /**
                 * ago :
                 * time : Aktualisiert am Sonntag um 9
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class PtBean {
                /**
                 * ago :
                 * time : Updated on domingo 9:00
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class VnBean {
                /**
                 * ago :
                 * time : Cập nhật ngày chủ nhật 9
                 */

                private String ago;
                private String time;

                public String getAgo() {
                    return ago;
                }

                public void setAgo(String ago) {
                    this.ago = ago;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }

    public static class ForecastBean {
        private List<AqiBean> aqi;
        private List<WindBean> wind;

        public List<AqiBean> getAqi() {
            return aqi;
        }

        public void setAqi(List<AqiBean> aqi) {
            this.aqi = aqi;
        }

        public List<WindBean> getWind() {
            return wind;
        }

        public void setWind(List<WindBean> wind) {
            this.wind = wind;
        }

        public static class AqiBean {
            /**
             * t : 2018-02-08T15:00:00+00:00
             * v : [158,174]
             */

            private String t;
            private List<Integer> v;

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public List<Integer> getV() {
                return v;
            }

            public void setV(List<Integer> v) {
                this.v = v;
            }
        }

        public static class WindBean {
            /**
             * t : 2018-02-10T02:00:00+00:00
             * w : [8.7,12.8,302.9]
             * c : -4.0
             */

            private String t;
            private double c;
            private List<Double> w;

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public double getC() {
                return c;
            }

            public void setC(double c) {
                this.c = c;
            }

            public List<Double> getW() {
                return w;
            }

            public void setW(List<Double> w) {
                this.w = w;
            }
        }
    }

    public static class IaqiBean {
        /**
         * p : pm25
         * v : [34,25,151]
         * i : 北京 pm25 (小颗粒物)  measured by U.S Embassy Beijing Air Quality Monitor (美国驻北京大使馆空气质量监测). Values are converted from µg/m3 to AQI levels using the EPA standard.
         * h : ["2018/02/11 09:00:00",1,[[0,34],"qaTEzdKaHmHelOaHEjt",-28,"fqlcDEafaQiaedGlODpgv",-34,"npQ",50,44,"hcAfbfabahKNbAAEO",28,"HAlmtCJQ",-76,-34,"eDgFSm",-29,"raieHMriDaLIazp",87,"lDvgLBfMAdCgaBFccEH",-63,"SGJcIPeeHaiHmiLaHqLa",27,"SiTiUiajeQjEDe"]]
         */

        private String p;
        private String i;
        private List<Integer> v;
        private List<Object> h;//Java 的数组，必须是同类型数据，所以不得不用Object，如果是python就可以不同类型

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public List<Integer> getV() {
            return v;
        }

        public void setV(List<Integer> v) {
            this.v = v;
        }

        public List<Object> getH() {
            return h;
        }

        public void setH(List<Object> h) {
            this.h = h;
        }
    }

    public static class NearestBean {
        /**
         * aqi : 63
         * vtime : 1518307200
         * id : Beijing
         * name : 北京
         * curl : http://aqicn.org/city/beijing/cn/m/
         * geo : ["39.954592","116.468117"]
         */

        private String aqi;
        private long vtime;
        private String id;
        private String name;
        private String curl;
        private List<String> geo;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public long getVtime() {
            return vtime;
        }

        public void setVtime(long vtime) {
            this.vtime = vtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurl() {
            return curl;
        }

        public void setCurl(String curl) {
            this.curl = curl;
        }

        public List<String> getGeo() {
            return geo;
        }

        public void setGeo(List<String> geo) {
            this.geo = geo;
        }
    }
}
