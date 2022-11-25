package dtos;

import java.util.Date;

/**
 *
 * @author Thien
 */
public class InterviewingDTO {
    private int id;
    private String inter_id;
    private String inter_name;
    private String can_id;
    private String can_name;
    private String can_cv;
    private Date date;
    private String location;
    private int isStatus;
    private String status;
    private String comment;
    private int score;
    private CandidateDTO canid;

    public InterviewingDTO() {
    }
    
    

    public InterviewingDTO(int id, String inter_id, String inter_name, String can_id, String can_name, Date date, String location, int score) {
        this.id = id;
        this.inter_id = inter_id;
        this.inter_name = inter_name;
        this.can_id = can_id;
        this.can_name = can_name;
        this.date = date;
        this.location = location;
        this.score = score;
    }
    public InterviewingDTO(int id, String inter_id, String inter_name, String can_id, String can_name, String can_cv, Date date, String location, int isStatus, String status, String comment, int score) {
        this.id = id;
        this.inter_id = inter_id;
        this.inter_name = inter_name;
        this.can_id = can_id;
        this.can_name = can_name;
        this.can_cv = can_cv;
        this.date = date;
        this.location = location;
        this.isStatus = isStatus;
        this.status = status;
        this.comment = comment;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInter_id() {
        return inter_id;
    }

    public void setInter_id(String inter_id) {
        this.inter_id = inter_id;
    }

    public String getInter_name() {
        return inter_name;
    }

    public void setInter_name(String inter_name) {
        this.inter_name = inter_name;
    }

    public String getCan_id() {
        return can_id;
    }

    public void setCan_id(String can_id) {
        this.can_id = can_id;
    }

    public String getCan_name() {
        return can_name;
    }

    public void setCan_name(String can_name) {
        this.can_name = can_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCan_cv() {
        return can_cv;
    }

    public void setCan_cv(String can_cv) {
        this.can_cv = can_cv;
    }
    public int getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(int isStatus) {
        this.isStatus = isStatus;
    }
    
}
