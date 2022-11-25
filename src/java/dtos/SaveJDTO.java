/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author DELL
 */
public class SaveJDTO {
    private String job_id;
    private String email;
    public SaveJDTO(String job_id, String email) {
        this.job_id = job_id;
        this.email = email;
    }

    public SaveJDTO() {
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
