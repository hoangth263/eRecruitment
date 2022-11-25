/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author Thien's
 */
public class UserCvDTO {
    private int id;
    private String email;
    private String can_cv;
    private Date date;

    public UserCvDTO() {
    }

    public UserCvDTO(String email, String can_cv, Date date) {
        this.email = email;
        this.can_cv = can_cv;
        this.date = date;
    }
    public UserCvDTO(int id, String email, String can_cv, Date date) {
        this.id = id;
        this.email = email;
        this.can_cv = can_cv;
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

    public String getCan_cv() {
        return can_cv;
    }

    public void setCan_cv(String can_cv) {
        this.can_cv = can_cv;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
