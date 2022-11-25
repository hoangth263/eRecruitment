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
public class ExamDTO {
    private String exam_id;
    private String exam_name;
    private int major_id;

    public ExamDTO() {
    }

    public ExamDTO(String exam_id, String exam_name, int major_id) {
        this.exam_id = exam_id;
        this.exam_name = exam_name;
        this.major_id = major_id;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

}
