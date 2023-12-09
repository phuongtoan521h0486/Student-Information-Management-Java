package org.thd.Models;

import java.util.Date;

public class Certificate {
    private int certificateId;
    private String title;
    private Double score;
    private Date issuedDate;
    private Date expirationDate;
    private String studentId;

    public Certificate() {
    }

    public Certificate(int certificateId, String title, Double score, Date issuedDate, Date expirationDate, String studentId) {
        this.certificateId = certificateId;
        this.title = title;
        this.score = score;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.studentId = studentId;
    }

    public Certificate(String title, Double score, Date issuedDate, Date expirationDate, String studentId) {
        this.title = title;
        this.score = score;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.studentId = studentId;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "certificateId=" + certificateId +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", issuedDate=" + issuedDate +
                ", expirationDate=" + expirationDate +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
