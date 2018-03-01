package in.zhiwei.aqi.network.res;

/**
 * 检查升级的json对象类
 * Author: gzw48760.
 * Date: 2018/2/28 0028,20:32.
 */

public class UpgradeRes {

    /**
     * appName : 梦之蓝AQI
     * forceUpgrade : true
     * updateUrl : http://zhiwei.net.cn/apk/aqi.apk
     * upgradeInfo : V2.0版本更新，你就不想试一试么 @_@,~_~
     * versionNum : 1
     */

    private String appName;
    private boolean forceUpgrade;
    private String updateUrl;
    private String upgradeInfo;
    private int versionNum;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }
}
