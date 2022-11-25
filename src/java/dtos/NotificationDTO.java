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
public class NotificationDTO {
    private int nId;
    private String email;
    private String title;
    private String content;
    private String linkTitle;
    private String link;
    private Date date;
    private String timeAgo;
    private boolean isRead;

    public NotificationDTO() {
    }

    public NotificationDTO(int nId, String email, String title, String content, String linkTitle, String link, Date date, String timeAgo, boolean isRead) {
        this.nId = nId;
        this.email = email;
        this.title = title;
        this.content = content;
        this.linkTitle = linkTitle;
        this.link = link;
        this.date = date;
        this.timeAgo = timeAgo;
        this.isRead = isRead;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    
}
