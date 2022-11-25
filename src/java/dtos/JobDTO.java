/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class JobDTO {
    private String job_id;
    private String job_name;
    private int major_id;
    private String major_name;
    private int job_vacancy;
    private String job_description;
    private int level_id;
    private String level_name;
    private String job_skill;
    private boolean save_job;

    public JobDTO() {
    }
    public boolean isSave_job() {
        return save_job;
    }

    public void setSave_job(boolean save_job) {
        this.save_job = save_job;
    }
    

    public String getJob_skill() {
        return job_skill;
    }

    public void setJob_skill(String job_skill) {
        this.job_skill = job_skill;
    }
    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }
    private double salary;
    private Date post_date;
    private CandidateDTO jobid;
    private CandidateDTO jobname;

    public CandidateDTO getJobid() {
        return jobid;
    }

    public void setJobid(CandidateDTO jobid) {
        this.jobid = jobid;
    }

    public CandidateDTO getJobname() {
        return jobname;
    }

    public void setJobname(CandidateDTO jobname) {
        this.jobname = jobname;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public int getJob_vacancy() {
        return job_vacancy;
    }

    public void setJob_vacancy(int job_vacancy) {
        this.job_vacancy = job_vacancy;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

}
