/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import dtos.JobDTO;

/**
 *
 * @author Thien's
 */
public class CandidateDTO {

    private String id;
    private String jobId;
    private int majorId;
    private String email;
    private String name;
    private String cv;
    private String phone;
    private int isStatus;
    private float score;
    private String status;
    private InterviewingDTO interid;
    private JobDTO jobname;

    public CandidateDTO() {
    }


    public CandidateDTO(String id, JobDTO jobname, String cv, float score, int isStatus) {
        this.id = id;
        this.jobname = jobname;
        this.cv = cv;
        this.score = score;
        this.isStatus = isStatus;
    }

    public CandidateDTO(String id, JobDTO jobname, String cv, String email, float score, int isStatus) {
        this.id = id;
        this.jobname = jobname;
        this.cv = cv;
        this.email = email;
        this.score = score;
        this.isStatus = isStatus;
    }

    public CandidateDTO(String id, JobDTO jobname, String cv, String email, float score, InterviewingDTO interid, int isStatus) {
        this.id = id;
        this.jobname = jobname;
        this.cv = cv;
        this.email = email;
        this.score = score;
        this.interid = interid;
        this.isStatus = isStatus;
    }

    public CandidateDTO(String id, JobDTO jobname, String cv, float score, InterviewingDTO interid, int isStatus) {
        this.id = id;
        this.jobname = jobname;
        this.cv = cv;
        this.score = score;
        this.interid = interid;
        this.isStatus = isStatus;
    }

    public CandidateDTO(String id, String jobId, int majorId, String email, String name, String cv, String phone, int isStatus, float score, String status) {
        this.id = id;
        this.jobId = jobId;
        this.majorId = majorId;
        this.email = email;
        this.name = name;
        this.cv = cv;
        this.phone = phone;
        this.isStatus = isStatus;
        this.score = score;
        this.status = status;
    }

    public InterviewingDTO getInterid() {
        return interid;
    }

    public void setInterid(InterviewingDTO interid) {
        this.interid = interid;
    }

    public JobDTO getJobname() {
        return jobname;
    }

    public void setJobname(JobDTO jobname) {
        this.jobname = jobname;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public int getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(int isStatus) {
        this.isStatus = isStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
