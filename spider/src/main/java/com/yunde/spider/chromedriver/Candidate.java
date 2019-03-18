package com.yunde.spider.chromedriver;

/**
 * @author laisy
 * @date 2019/3/18
 * @description
 */
public class Candidate {
    private String name;
    private String mobile;
    private String jobTitle;

    public Candidate(String name, String mobile, String jobTitle) {
        this.name = name;
        this.mobile = mobile;
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}