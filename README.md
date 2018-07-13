## BlueDreamAQI

> 前言：
>
> 1. 作为一个技术小白，动手实践才是我们快速提升能力的一种方式。多数时候，总是太多的理由和接口，慵懒，让我们变得平庸无为。
> 2. 作者曾在北京工作过一年，作为帝都，北京拥有着及其丰富的各种资源，真心喜爱这个城市，然而除了那经常光顾的雾霾。在北京的一年里，呼吸感染，让我光顾医院的次数，超过之前十年的次数总和。对于`雾霾`二字，深有感触。
> 3. 回到南方工作之后，发现即使aqi质量严重，却发现少有人戴口罩，做防护，而根据相关医学论文报告，pm2.5的增加，会明显增加肺癌的发病率，高达十几、二十几倍。而多数我们，却并未意识到这种严重。
>
> **温水里的青蛙**，可以这么说吧，作为一个普通的技术小白，只希望我们每个人，都能够关注健康，《我不是药神》的感触，健康，比什么都重要，真的。

梦之蓝AQI，一款旨在技术研究与学习,预报pm25等空气质量指数预报的Android版App。

[![get form baiduAppStore](./images/baiduAppStore.png)](http://shouji.baidu.com/software/23722654.html)[![get from HuaweiAppStore](./images/huawei_appstore.png)](http://app.hicloud.com/app/C100221047)[![get from HuaweiAppStore](./images/wandoujia.png)](http://www.wandoujia.com/apps-in.zhiwei.aqi)

#### 开发环境

- JDK1.8
- Android SDK
- AndroidStudio 3.0+

#### App截图

![北京AQI图1](/images/img_aqi_beijing.png)![北京AQI图2](./images/img_aqi_nearby.png)![关于开发者](./images/img_aqi_about.png)![搜索界面](./images/img_search_city.png)

#### 开发计划

- [x] ~~基础功能实现，App升级、简单分享~~
- [x] ~~Splash、widget、aqi地图、城市选择、 转场动画~~
- [ ] ~~热修复接入，反馈~~，设置，前台进程、X5Client
- [ ]  pm25.com ，加入aqi资讯 吸入/沉淀量检测，生活指南/指数，作为2.0 beta版开发
- [ ] 接入pm25.in数据源，Air Visual 数据源,提供不同的UI风格
- [ ] AQI预报、温馨（语音）提示、推送、社交化分享
- [ ] 优化&新技术的引入，NDK、Kotlin、flutter框架实现
- [ ] 自建python服务器，提供api，以及用户账户，上传每日城市图片

#### 开源库的引用

-  [butterknife](https://github.com/JakeWharton/butterknife)
- [okhttp3](https://github.com/square/okhttp)
- [retrofit2](https://github.com/square/retrofit)
- [gson](https://github.com/google/gson)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [leakcanary](https://github.com/square/leakcanary)
- [jsoup](https://github.com/jhy/jsoup)
- [Android UtilCode](https://github.com/Blankj/AndroidUtilCode)
- [lottie-android](https://github.com/airbnb/lottie-android)
- [TinyPinyin](https://github.com/promeG/TinyPinyin)

目前项目中使用了以上开源库，在此表示感谢！

#### 贡献支持

> 或许我们都只是个开发技术小白，但是我们坚持学习前进，欢迎各位朋友**fork**、**star**本项目，让我们一同学习，一同进步！

**License:**

> The Blue Dream AQI software suite is licensed under the terms of the Apache license.
>
> See [LGPL](./LICENSE,"开源协议") for more information.

