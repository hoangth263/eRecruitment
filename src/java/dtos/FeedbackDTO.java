/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class FeedbackDTO {

    private int id;
    private String email;
    private String subject;
    private String detail;
    private Date date;

    public FeedbackDTO() {
    }

    public FeedbackDTO(int id, String email, String subject, String detail, Date date) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.detail = detail;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   
}