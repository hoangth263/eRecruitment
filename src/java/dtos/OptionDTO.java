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
public class OptionDTO {
    private int op_id;
    private String q_id;
    private String content;
    private boolean isCorrect;

    public OptionDTO() {
    }

    public OptionDTO(int op_id, String q_id, String content, boolean isCorrect) {
        this.op_id = op_id;
        this.q_id = q_id;
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public int getOp_id() {
        return op_id;
    }

    public void setOp_id(int op_id) {
        this.op_id = op_id;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "OptionDTO{" + "op_id=" + op_id + ", q_id=" + q_id + ", content=" + content + ", isCorrect=" + isCorrect + '}';
    }

    
    
}
