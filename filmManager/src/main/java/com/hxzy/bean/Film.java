package com.hxzy.bean;

import com.hxzy.util.CommonUtil;

import java.time.LocalDateTime;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public class Film {
    private int fId;
    private String chineseName;
    private String englishName;
    private String category;
    private String area;
    private double duration;
    private LocalDateTime release;

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public LocalDateTime getRelease() {
        return release;
    }

    public void setRelease(LocalDateTime release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "Film{" +
                "fId=" + fId +
                ", chineseName='" + chineseName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", category='" + category + '\'' +
                ", area='" + area + '\'' +
                ", duration=" + duration +
                ", release=" + CommonUtil.localDateTime2String(this.release,CommonUtil.PATTERN1) +
                '}';
    }
}
