package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LlpUpdateAds {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("buttonName")
    @Expose
    private String buttonName;
    @SerializedName("buttonSkip")
    @Expose
    private String buttonSkip;
    @SerializedName("AdsOnOff")
    @Expose
    private Boolean adsOnOff;
    @SerializedName("CustomAdsOnOff")
    @Expose
    private Boolean customAdsOnOff;
    @SerializedName("FullScreenOnOff")
    @Expose
    private Boolean fullScreenOnOff;
    @SerializedName("GoogleBannerAds")
    @Expose
    private String googleBannerAds;
    @SerializedName("GoogleInterAds")
    @Expose
    private String googleInterAds;
    @SerializedName("GoogleNativeAds")
    @Expose
    private String googleNativeAds;
    @SerializedName("GoogleAppOpenAds")
    @Expose
    private String googleAppOpenAds;
    @SerializedName("GoogleRewardAds")
    @Expose
    private String googleRewardAds;
    @SerializedName("GoogleAppIdAds")
    @Expose
    private String googleAppIdAds;
    @SerializedName("GoogleRewardOnOff")
    @Expose
    private Boolean googleRewardOnOff;
    @SerializedName("GoogleBannerOnOff")
    @Expose
    private Boolean googleBannerOnOff;
    @SerializedName("GoogleListNativeOnOff")
    @Expose
    private Boolean googleListNativeOnOff;
    @SerializedName("GoogleMiniNativeOnOff")
    @Expose
    private Boolean googleMiniNativeOnOff;
    @SerializedName("GoogleLargeNativeOnOff")
    @Expose
    private Boolean googleLargeNativeOnOff;
    @SerializedName("GoogleWhichOneNative")
    @Expose
    private Integer googleWhichOneNative;
    @SerializedName("GoogleIntervalCount")
    @Expose
    private Integer googleIntervalCount;
    @SerializedName("GoogleBackInterOnOff")
    @Expose
    private Boolean googleBackInterOnOff;
    @SerializedName("GoogleBackInterIntervalCount")
    @Expose
    private Integer googleBackInterIntervalCount;
    @SerializedName("GoogleExitSplashInterOnOff")
    @Expose
    private Boolean googleExitSplashInterOnOff;
    @SerializedName("GoogleSplashOpenAdsOnOff")
    @Expose
    private Boolean googleSplashOpenAdsOnOff;
    @SerializedName("GoogleNativeTextOnOff")
    @Expose
    private Boolean googleNativeTextOnOff;
    @SerializedName("GoogleNativeText")
    @Expose
    private String googleNativeText;
    @SerializedName("ListNativeWhichOne")
    @Expose
    private Integer listNativeWhichOne;
    @SerializedName("ListNativeAfterCount")
    @Expose
    private Integer listNativeAfterCount;
    @SerializedName("QurekaLink")
    @Expose
    private String qurekaLink;
    @SerializedName("QurekaOnOff")
    @Expose
    private Boolean qurekaOnOff;
    @SerializedName("QurekaAppOpenOnOff")
    @Expose
    private Boolean qurekaAppOpenOnOff;
    @SerializedName("QurekaBannerOnOff")
    @Expose
    private Boolean qurekaBannerOnOff;
    @SerializedName("QurekaInterOnOff")
    @Expose
    private Boolean qurekaInterOnOff;
    @SerializedName("QurekaRewardOnOff")
    @Expose
    private Boolean qurekaRewardOnOff;
    @SerializedName("QurekaListNativeOnOff")
    @Expose
    private Boolean qurekaListNativeOnOff;
    @SerializedName("QurekaMiniNativeOnOff")
    @Expose
    private Boolean qurekaMiniNativeOnOff;
    @SerializedName("QurekaLargeNativeOnOff")
    @Expose
    private Boolean qurekaLargeNativeOnOff;
    @SerializedName("QurekaInterCloseOnOff")
    @Expose
    private Boolean qurekaInterCloseOnOff;
    @SerializedName("QurekaBackInterOnOff")
    @Expose
    private Boolean qurekaBackInterOnOff;
    @SerializedName("HomeNativeBackgroundColorOnOff")
    @Expose
    private Boolean homeNativeBackgroundColorOnOff;
    @SerializedName("NativeBackgroundColor")
    @Expose
    private String nativeBackgroundColor;
    @SerializedName("AllPagesNativeBackgroundOnOff")
    @Expose
    private Boolean allPagesNativeBackgroundOnOff;
    @SerializedName("AllPagesNativeBackgroundCount")
    @Expose
    private Integer allPagesNativeBackgroundCount;
    @SerializedName("PolicyLink")
    @Expose
    private String policyLink;
    @SerializedName("PolicyOnOff")
    @Expose
    private Boolean policyOnOff;
    @SerializedName("LoaderNativeOnOff")
    @Expose
    private Boolean LoaderNativeOnOff;
    @SerializedName("AppOpen")
    @Expose
    private int AppOpen;
    @SerializedName("OneSignalAppId")
    @Expose
    private String oneSignalAppId;
    @SerializedName("ShowDialogBeforeAds")
    @Expose
    private Boolean showDialogBeforeAds;
    @SerializedName("DialogTimeInSec")
    @Expose
    private Double dialogTimeInSec;
    @SerializedName("VpnOnOff")
    @Expose
    private Boolean vpnOnOff;
    @SerializedName("VpnDialog")
    @Expose
    private Boolean vpnDialog;
    @SerializedName("VpnDialogTime")
    @Expose
    private Integer vpnDialogTime;
    @SerializedName("VpnDefaultCountry")
    @Expose
    private VpnDefaultCountry vpnDefaultCountry;
    @SerializedName("VpnListCountry")
    @Expose
    private List<VpnListModel> vpnListCountry = null;
    @SerializedName("VpnUrl")
    @Expose
    private String vpnUrl;
    @SerializedName("VpnCarrierId")
    @Expose
    private String vpnCarrierId;
    @SerializedName("CustomadsData")
    @Expose
    private List<LlpAdApp> customadsData = null;

    public String getGoogleRewardAds() {
        return googleRewardAds;
    }

    public void setGoogleRewardAds(String googleRewardAds) {
        this.googleRewardAds = googleRewardAds;
    }

    public Boolean getGoogleRewardOnOff() {
        return googleRewardOnOff;
    }

    public void setGoogleRewardOnOff(Boolean googleRewardOnOff) {
        this.googleRewardOnOff = googleRewardOnOff;
    }

    public Boolean getQurekaRewardOnOff() {
        return qurekaRewardOnOff;
    }

    public void setQurekaRewardOnOff(Boolean qurekaRewardOnOff) {
        this.qurekaRewardOnOff = qurekaRewardOnOff;
    }

    public Boolean getVpnDialog() {
        return vpnDialog;
    }

    public void setVpnDialog(Boolean vpnDialog) {
        this.vpnDialog = vpnDialog;
    }

    public Boolean getLoaderNativeOnOff() {
        return LoaderNativeOnOff;
    }

    public void setLoaderNativeOnOff(Boolean loaderNativeOnOff) {
        LoaderNativeOnOff = loaderNativeOnOff;
    }

    public int getAppOpen() {
        return AppOpen;
    }

    public void setAppOpen(int appOpen) {
        AppOpen = appOpen;
    }


    public class VpnDefaultCountry {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("flag")
        @Expose
        private String flag;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlag() {
            return flag;
        }
        public void setFlag(String flag) {
            this.flag = flag;
        }

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonSkip() {
        return buttonSkip;
    }

    public void setButtonSkip(String buttonSkip) {
        this.buttonSkip = buttonSkip;
    }

    public Boolean getAdsOnOff() {
        return adsOnOff;
    }

    public void setAdsOnOff(Boolean adsOnOff) {
        this.adsOnOff = adsOnOff;
    }

    public Boolean getCustomAdsOnOff() {
        return customAdsOnOff;
    }

    public void setCustomAdsOnOff(Boolean customAdsOnOff) {
        this.customAdsOnOff = customAdsOnOff;
    }

    public Boolean getFullScreenOnOff() {
        return fullScreenOnOff;
    }

    public void setFullScreenOnOff(Boolean fullScreenOnOff) {
        this.fullScreenOnOff = fullScreenOnOff;
    }

    public String getGoogleBannerAds() {
        return googleBannerAds;
    }

    public void setGoogleBannerAds(String googleBannerAds) {
        this.googleBannerAds = googleBannerAds;
    }

    public String getGoogleInterAds() {
        return googleInterAds;
    }

    public void setGoogleInterAds(String googleInterAds) {
        this.googleInterAds = googleInterAds;
    }

    public String getGoogleNativeAds() {
        return googleNativeAds;
    }

    public void setGoogleNativeAds(String googleNativeAds) {
        this.googleNativeAds = googleNativeAds;
    }

    public String getGoogleAppOpenAds() {
        return googleAppOpenAds;
    }

    public void setGoogleAppOpenAds(String googleAppOpenAds) {
        this.googleAppOpenAds = googleAppOpenAds;
    }

    public String getGoogleAppIdAds() {
        return googleAppIdAds;
    }

    public void setGoogleAppIdAds(String googleAppIdAds) {
        this.googleAppIdAds = googleAppIdAds;
    }

    public Boolean getGoogleBannerOnOff() {
        return googleBannerOnOff;
    }

    public void setGoogleBannerOnOff(Boolean googleBannerOnOff) {
        this.googleBannerOnOff = googleBannerOnOff;
    }

    public Boolean getGoogleListNativeOnOff() {
        return googleListNativeOnOff;
    }

    public void setGoogleListNativeOnOff(Boolean googleListNativeOnOff) {
        this.googleListNativeOnOff = googleListNativeOnOff;
    }

    public Boolean getGoogleMiniNativeOnOff() {
        return googleMiniNativeOnOff;
    }

    public void setGoogleMiniNativeOnOff(Boolean googleMiniNativeOnOff) {
        this.googleMiniNativeOnOff = googleMiniNativeOnOff;
    }

    public Boolean getGoogleLargeNativeOnOff() {
        return googleLargeNativeOnOff;
    }

    public void setGoogleLargeNativeOnOff(Boolean googleLargeNativeOnOff) {
        this.googleLargeNativeOnOff = googleLargeNativeOnOff;
    }

    public Integer getGoogleWhichOneNative() {
        return googleWhichOneNative;
    }

    public void setGoogleWhichOneNative(Integer googleWhichOneNative) {
        this.googleWhichOneNative = googleWhichOneNative;
    }

    public Integer getGoogleIntervalCount() {
        return googleIntervalCount;
    }

    public void setGoogleIntervalCount(Integer googleIntervalCount) {
        this.googleIntervalCount = googleIntervalCount;
    }

    public Boolean getGoogleBackInterOnOff() {
        return googleBackInterOnOff;
    }

    public void setGoogleBackInterOnOff(Boolean googleBackInterOnOff) {
        this.googleBackInterOnOff = googleBackInterOnOff;
    }

    public Integer getGoogleBackInterIntervalCount() {
        return googleBackInterIntervalCount;
    }

    public void setGoogleBackInterIntervalCount(Integer googleBackInterIntervalCount) {
        this.googleBackInterIntervalCount = googleBackInterIntervalCount;
    }

    public Boolean getGoogleExitSplashInterOnOff() {
        return googleExitSplashInterOnOff;
    }

    public void setGoogleExitSplashInterOnOff(Boolean googleExitSplashInterOnOff) {
        this.googleExitSplashInterOnOff = googleExitSplashInterOnOff;
    }

    public Boolean getGoogleSplashOpenAdsOnOff() {
        return googleSplashOpenAdsOnOff;
    }

    public void setGoogleSplashOpenAdsOnOff(Boolean googleSplashOpenAdsOnOff) {
        this.googleSplashOpenAdsOnOff = googleSplashOpenAdsOnOff;
    }

    public Boolean getGoogleNativeTextOnOff() {
        return googleNativeTextOnOff;
    }

    public void setGoogleNativeTextOnOff(Boolean googleNativeTextOnOff) {
        this.googleNativeTextOnOff = googleNativeTextOnOff;
    }

    public String getGoogleNativeText() {
        return googleNativeText;
    }

    public void setGoogleNativeText(String googleNativeText) {
        this.googleNativeText = googleNativeText;
    }

    public Integer getListNativeWhichOne() {
        return listNativeWhichOne;
    }

    public void setListNativeWhichOne(Integer listNativeWhichOne) {
        this.listNativeWhichOne = listNativeWhichOne;
    }

    public Integer getListNativeAfterCount() {
        return listNativeAfterCount;
    }

    public void setListNativeAfterCount(Integer listNativeAfterCount) {
        this.listNativeAfterCount = listNativeAfterCount;
    }

    public String getQurekaLink() {
        return qurekaLink;
    }

    public void setQurekaLink(String qurekaLink) {
        this.qurekaLink = qurekaLink;
    }

    public Boolean getQurekaOnOff() {
        return qurekaOnOff;
    }

    public void setQurekaOnOff(Boolean qurekaOnOff) {
        this.qurekaOnOff = qurekaOnOff;
    }

    public Boolean getQurekaAppOpenOnOff() {
        return qurekaAppOpenOnOff;
    }

    public void setQurekaAppOpenOnOff(Boolean qurekaAppOpenOnOff) {
        this.qurekaAppOpenOnOff = qurekaAppOpenOnOff;
    }

    public Boolean getQurekaBannerOnOff() {
        return qurekaBannerOnOff;
    }

    public void setQurekaBannerOnOff(Boolean qurekaBannerOnOff) {
        this.qurekaBannerOnOff = qurekaBannerOnOff;
    }

    public Boolean getQurekaInterOnOff() {
        return qurekaInterOnOff;
    }

    public void setQurekaInterOnOff(Boolean qurekaInterOnOff) {
        this.qurekaInterOnOff = qurekaInterOnOff;
    }

    public Boolean getQurekaListNativeOnOff() {
        return qurekaListNativeOnOff;
    }

    public void setQurekaListNativeOnOff(Boolean qurekaListNativeOnOff) {
        this.qurekaListNativeOnOff = qurekaListNativeOnOff;
    }

    public Boolean getQurekaMiniNativeOnOff() {
        return qurekaMiniNativeOnOff;
    }

    public void setQurekaMiniNativeOnOff(Boolean qurekaMiniNativeOnOff) {
        this.qurekaMiniNativeOnOff = qurekaMiniNativeOnOff;
    }

    public Boolean getQurekaLargeNativeOnOff() {
        return qurekaLargeNativeOnOff;
    }

    public void setQurekaLargeNativeOnOff(Boolean qurekaLargeNativeOnOff) {
        this.qurekaLargeNativeOnOff = qurekaLargeNativeOnOff;
    }

    public Boolean getQurekaInterCloseOnOff() {
        return qurekaInterCloseOnOff;
    }

    public void setQurekaInterCloseOnOff(Boolean qurekaInterCloseOnOff) {
        this.qurekaInterCloseOnOff = qurekaInterCloseOnOff;
    }

    public Boolean getQurekaBackInterOnOff() {
        return qurekaBackInterOnOff;
    }

    public void setQurekaBackInterOnOff(Boolean qurekaBackInterOnOff) {
        this.qurekaBackInterOnOff = qurekaBackInterOnOff;
    }

    public Boolean getHomeNativeBackgroundColorOnOff() {
        return homeNativeBackgroundColorOnOff;
    }

    public void setHomeNativeBackgroundColorOnOff(Boolean homeNativeBackgroundColorOnOff) {
        this.homeNativeBackgroundColorOnOff = homeNativeBackgroundColorOnOff;
    }

    public String getNativeBackgroundColor() {
        return nativeBackgroundColor;
    }

    public void setNativeBackgroundColor(String nativeBackgroundColor) {
        this.nativeBackgroundColor = nativeBackgroundColor;
    }

    public Boolean getAllPagesNativeBackgroundOnOff() {
        return allPagesNativeBackgroundOnOff;
    }

    public void setAllPagesNativeBackgroundOnOff(Boolean allPagesNativeBackgroundOnOff) {
        this.allPagesNativeBackgroundOnOff = allPagesNativeBackgroundOnOff;
    }

    public Integer getAllPagesNativeBackgroundCount() {
        return allPagesNativeBackgroundCount;
    }

    public void setAllPagesNativeBackgroundCount(Integer allPagesNativeBackgroundCount) {
        this.allPagesNativeBackgroundCount = allPagesNativeBackgroundCount;
    }

    public String getPolicyLink() {
        return policyLink;
    }

    public void setPolicyLink(String policyLink) {
        this.policyLink = policyLink;
    }

    public Boolean getPolicyOnOff() {
        return policyOnOff;
    }

    public void setPolicyOnOff(Boolean policyOnOff) {
        this.policyOnOff = policyOnOff;
    }

    public String getOneSignalAppId() {
        return oneSignalAppId;
    }

    public void setOneSignalAppId(String oneSignalAppId) {
        this.oneSignalAppId = oneSignalAppId;
    }

    public Boolean getShowDialogBeforeAds() {
        return showDialogBeforeAds;
    }

    public void setShowDialogBeforeAds(Boolean showDialogBeforeAds) {
        this.showDialogBeforeAds = showDialogBeforeAds;
    }

    public Double getDialogTimeInSec() {
        return dialogTimeInSec;
    }

    public void setDialogTimeInSec(Double dialogTimeInSec) {
        this.dialogTimeInSec = dialogTimeInSec;
    }

    public Boolean getVpnOnOff() {
        return vpnOnOff;
    }

    public void setVpnOnOff(Boolean vpnOnOff) {
        this.vpnOnOff = vpnOnOff;
    }

    public Integer getVpnDialogTime() {
        return vpnDialogTime;
    }

    public void setVpnDialogTime(Integer vpnDialogTime) {
        this.vpnDialogTime = vpnDialogTime;
    }

    public VpnDefaultCountry getVpnDefaultCountry() {
        return vpnDefaultCountry;
    }

    public void setVpnDefaultCountry(VpnDefaultCountry vpnDefaultCountry) {
        this.vpnDefaultCountry = vpnDefaultCountry;
    }

    public List<VpnListModel> getVpnListCountry() {
        return vpnListCountry;
    }

    public void setVpnListCountry(List<VpnListModel> vpnListCountry) {
        this.vpnListCountry = vpnListCountry;
    }

    public String getVpnUrl() {
        return vpnUrl;
    }

    public void setVpnUrl(String vpnUrl) {
        this.vpnUrl = vpnUrl;
    }

    public String getVpnCarrierId() {
        return vpnCarrierId;
    }

    public void setVpnCarrierId(String vpnCarrierId) {
        this.vpnCarrierId = vpnCarrierId;
    }

    public List<LlpAdApp> getCustomadsData() {
        return customadsData;
    }

    public void setCustomadsData(List<LlpAdApp> customadsData) {
        this.customadsData = customadsData;
    }
}


