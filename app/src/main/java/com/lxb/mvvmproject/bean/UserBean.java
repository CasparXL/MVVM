package com.lxb.mvvmproject.bean;

/**
 * "浪小白" 创建 2019/10/22.
 * 界面名称以及功能:
 */
public class UserBean {
    /**
     * imgUrl : http://120.79.210.114:9006/donghui_app/IMG/2018110703264920181107152649[0].jpg
     * isUse : 0
     * name : 张三
     * registerType : 1
     * userId : 10003
     * account : 18682199291
     */

    private String imgUrl;
    private int isUse;
    private String name;
    private int registerType;
    private int userId;
    private String account;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
