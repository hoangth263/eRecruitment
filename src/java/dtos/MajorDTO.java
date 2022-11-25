/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author ACER
 */
public class MajorDTO {
    private int major_id;
    private String major_name;

    public MajorDTO() {
    }

    public MajorDTO(int major_id, String major_name) {
        this.major_id = major_id;
        this.major_name = major_name;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }
}
