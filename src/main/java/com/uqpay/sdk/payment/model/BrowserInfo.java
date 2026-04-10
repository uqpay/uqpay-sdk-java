package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrowserInfo {

    @JsonProperty("accept_header")
    private String acceptHeader;

    @JsonProperty("browser")
    private Browser browser;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("language")
    private String language;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("mobile")
    private Mobile mobile;

    @JsonProperty("screen_color_depth")
    private Integer screenColorDepth;

    @JsonProperty("screen_height")
    private Integer screenHeight;

    @JsonProperty("screen_width")
    private Integer screenWidth;

    @JsonProperty("timezone")
    private String timezone;

    public BrowserInfo() {
    }

    public String getAcceptHeader() { return acceptHeader; }
    public void setAcceptHeader(String acceptHeader) { this.acceptHeader = acceptHeader; }

    public Browser getBrowser() { return browser; }
    public void setBrowser(Browser browser) { this.browser = browser; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Mobile getMobile() { return mobile; }
    public void setMobile(Mobile mobile) { this.mobile = mobile; }

    public Integer getScreenColorDepth() { return screenColorDepth; }
    public void setScreenColorDepth(Integer screenColorDepth) { this.screenColorDepth = screenColorDepth; }

    public Integer getScreenHeight() { return screenHeight; }
    public void setScreenHeight(Integer screenHeight) { this.screenHeight = screenHeight; }

    public Integer getScreenWidth() { return screenWidth; }
    public void setScreenWidth(Integer screenWidth) { this.screenWidth = screenWidth; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Browser {

        @JsonProperty("java_enabled")
        private Boolean javaEnabled;

        @JsonProperty("javascript_enabled")
        private Boolean javascriptEnabled;

        @JsonProperty("user_agent")
        private String userAgent;

        public Browser() {
        }

        public Boolean getJavaEnabled() { return javaEnabled; }
        public void setJavaEnabled(Boolean javaEnabled) { this.javaEnabled = javaEnabled; }

        public Boolean getJavascriptEnabled() { return javascriptEnabled; }
        public void setJavascriptEnabled(Boolean javascriptEnabled) { this.javascriptEnabled = javascriptEnabled; }

        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Location {

        @JsonProperty("lat")
        private String lat;

        @JsonProperty("lon")
        private String lon;

        public Location() {
        }

        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }

        public String getLon() { return lon; }
        public void setLon(String lon) { this.lon = lon; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Mobile {

        @JsonProperty("device_model")
        private String deviceModel;

        @JsonProperty("os_type")
        private String osType;

        @JsonProperty("os_version")
        private String osVersion;

        public Mobile() {
        }

        public String getDeviceModel() { return deviceModel; }
        public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

        public String getOsType() { return osType; }
        public void setOsType(String osType) { this.osType = osType; }

        public String getOsVersion() { return osVersion; }
        public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
    }
}
