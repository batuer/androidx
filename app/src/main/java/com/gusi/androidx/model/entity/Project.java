package com.gusi.androidx.model.entity;

import java.io.Serializable;

/**
 * @author Ylw
 * @since 2019/7/22 23:14
 */
public class Project implements Serializable {
    private String mProjectName;
    private String mOverview;
    private String mNotice;
    private String mPublicResult;
    private String mProgress;
    private String mEnterprise;
    private String mRegisterTime;
    private String mBuildingNum;
    private String mPresaleNum;
    private String mReceiveTime;
    private String mReceiveSite;
    private String mPhone;

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getNotice() {
        return mNotice;
    }

    public void setNotice(String notice) {
        String preNotice = "http://zfyxdj.xa.gov.cn/zfrgdjpt/";
        mNotice = preNotice + notice;
    }

    public String getPublicResult() {
        return mPublicResult;
    }

    public void setPublicResult(String publicResult) {
        String prePublicResult = "http://zfyxdj.xa.gov.cn/zfrgdjpt/";
        String sufPage = "&page";
        mPublicResult = prePublicResult + publicResult + sufPage;
    }

    public String getProgress() {
        return mProgress;
    }

    public void setProgress(String progress) {
        String preProgress = "http://zfyxdj.xa.gov.cn/zfrgdjpt/";
        mProgress = preProgress + progress;
    }

    public String getEnterprise() {
        return mEnterprise;
    }

    public void setEnterprise(String enterprise) {
        mEnterprise = enterprise;
    }

    public String getRegisterTime() {
        return mRegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        mRegisterTime = registerTime;
    }

    public String getBuildingNum() {
        return mBuildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        mBuildingNum = buildingNum;
    }

    public String getPresaleNum() {
        return mPresaleNum;
    }

    public void setPresaleNum(String presaleNum) {
        mPresaleNum = presaleNum;
    }

    public String getReceiveTime() {
        return mReceiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        mReceiveTime = receiveTime;
    }

    public String getReceiveSite() {
        return mReceiveSite;
    }

    public void setReceiveSite(String receiveSite) {
        mReceiveSite = receiveSite;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
