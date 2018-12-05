![Logo](./images/aqi.svg)

## :star:BlueDreamAQI:partly_sunny:

[![jetpack](https://img.shields.io/badge/志威-梦之蓝AQI-brightgreen.svg)](https://github.com/zhiwei1990/BlueDreamAQI) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21) [![apache2.0](https://img.shields.io/badge/license-LGPL-brightgreen.svg)](./LICENSE) [![Build Status](https://travis-ci.org/zhiwei1990/BlueDreamAQI.svg?branch=master)](https://travis-ci.org/zhiwei1990/BlueDreamAQI)![sonar cloud](https://sonarcloud.io/api/project_badges/measure?project=zhiwei1990_BlueDreamAQI&metric=alert_status) ![code lines](https://sonarcloud.io/api/project_badges/measure?project=zhiwei1990_BlueDreamAQI&metric=ncloc)![reliability](https://sonarcloud.io/api/project_badges/measure?project=zhiwei1990_BlueDreamAQI&metric=reliability_rating)[![version](https://img.shields.io/github/release/zhiwei1990/BlueDreamAQI.svg)](https://github.com/zhiwei1990/BlueDreamAQI/releases) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![HitCount](http://hits.dwyl.io/zhiwei1990/BlueDreamAQI.svg)](http://hits.dwyl.io/zhiwei1990/BlueDreamAQI)

#### 一、项目简介[^1]

> 梦之蓝AQI，一款旨在技术研究与学习,预报pm25等空气质量指数预报的Android版App。



> 前言：
>
> 1. 作为一个技术小白，动手实践才是我们快速提升能力的一种方式。多数时候，总是太多的理由和接口，慵懒，让我们变得平庸无为。
> 2. 作者曾在北京工作过一年，作为帝都，北京拥有着及其丰富的各种资源，真心喜爱这个城市，然而除了那经常光顾的雾霾。在北京的一年里，呼吸感染，让我光顾医院的次数，超过之前十年的次数总和。对于`雾霾`二字，深有感触。
> 3. 回到南方工作之后，发现即使aqi质量严重，却发现少有人戴口罩，做防护，而根据相关医学论文报告，pm2.5的增加，会明显增加肺癌的发病率，高达十几、二十几倍。而多数我们，却并未意识到这种严重。
>
> **温水里的青蛙**，可以这么说吧，作为一个普通的技术小白，只希望我们每个人，都能够关注健康，《我不是药神》的感触，健康，比什么都重要，真的.

#### 二、体验下载[Apk](https://raw.githubusercontent.com/zhiwei1990/BlueDreamAQI/master/release/aqi.apk)

[![get form baiduAppStore](./images/baiduAppStore.png)](http://shouji.baidu.com/software/23722654.html)[![get from HuaweiAppStore](./images/huawei_appstore.png)](http://app.hicloud.com/app/C100221047)[![get from HuaweiAppStore](./images/wandoujia.png)](http://www.wandoujia.com/apps-in.zhiwei.aqi)

`手机扫码下载`

![二维码](./images/QR_aqi.png)

#### 三、App截图

![北京AQI图1](/images/img_aqi_beijing.png)![北京AQI图2](./images/img_aqi_nearby.png)![关于开发者](./images/img_aqi_about.png)![搜索界面](./images/img_search_city.png)

#### 四、开发计划

- [x] ~~基础功能实现，App升级、简单分享~~
- [x] ~~Splash、widget、aqi地图、城市选择、 转场动画~~
- [ ] ~~热修复接入，反馈~~，设置，前台进程、X5Client
- [ ]  pm25.com ，加入aqi资讯 吸入/沉淀量检测，生活指南/指数，作为2.0 beta版开发
- [ ] 接入pm25.in数据源，Air Visual 数据源,提供不同的UI风格
- [ ] AQI预报、温馨（语音）提示、推送、社交化分享
- [ ] 优化&新技术的引入，NDK、Kotlin、flutter框架实现
- [ ] 自建python服务器，提供api，以及用户账户，上传每日城市图片

#### 五、开源库的引用

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

####  六、关于作者 [![jianshu](./images/jianshu.png)](https://www.jianshu.com/u/72294e6848c0)[![github](./images/github.png)](https://github.com/zhiwei1990)[![csdn](./images/csdn.png)](https://blog.csdn.net/zhiwei9001)

> 作者本人只是一个，就职于某`知名`(@_@ 有名字的)互联网公司的，技术小白一枚，对于编程有着某种兴趣和热爱，然技术确实渣渣，好读书、不求甚解~~

`人之为学有难易乎、为之，则难者亦易矣；不为，则易者亦难矣。`

#### 七、关注及反馈

> 由于个人才疏学浅，技能有限，App的开发与设计中难免有许多问题，恳请各位朋友，不吝赐教，可以提交`issues`反馈`Bug`、`建议`或`意见`。
>
> 如若感觉项目有那么点意义和价值，可以给个`star`:smile:

[![Github stars](https://img.shields.io/github/stars/zhiwei1990/BlueDreamAQI.svg?style=social&label=star)](https://github.com/zhiwei1990/BlueDreamAQI)[![Github followers](https://img.shields.io/github/followers/zhiwei1990.svg?style=social&label=follow)](https://github.com/zhiwei1990/BlueDreamAQI)[![Github issues](https://img.shields.io/github/issues/zhiwei1990/BlueDreamAQI.svg?style=social&label=issues)](https://github.com/zhiwei1990/BlueDreamAQI)

[^1]: 项目开发环境为AndroidStudio `3.2.1`、Jdk8+、Windows10



