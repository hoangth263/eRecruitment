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
public class QADTO {

    private String qId;
    private String aId;
    private String email;
    private String detail;
    private Date datetime;

    public QADTO() {
    }

    public QADTO(String qId, String email, String detail, Date datetime) {
        this.qId = qId;
        this.email = email;
        this.detail = detail;
        this.datetime = datetime;
    }

    public QADTO(String qId, String aId, String email, String detail, Date datetime) {
        this.qId = qId;
        this.aId = aId;
        this.email = email;
        this.detail = detail;
        this.datetime = datetime;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

}
