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
public class QuestionDTO {
    private String q_id;
    private String questiontxt;
    private int major_id;

    public QuestionDTO() {
    }

    public QuestionDTO(String q_id, String questiontxt, int major_id) {
        this.q_id = q_id;
        this.questiontxt = questiontxt;
        this.major_id = major_id;
    }

    

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQuestiontxt() {
        return questiontxt;
    }

    public void setQuestiontxt(String questiontxt) {
        this.questiontxt = questiontxt;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" + "q_id=" + q_id + ", questiontxt=" + questiontxt + ", major_id=" + major_id + '}';
    }
    
    
    
}
