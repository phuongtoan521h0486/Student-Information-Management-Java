package org.thd.Models;

import java.util.Arrays;

public class Student {
    private String studentId;
    private String email;
    private String name;
    private boolean gender;
    private String major;
    private byte[] picture;

    public Student() {
    }

    public Student(String studentId, String email, String name, boolean gender, String major, byte[] picture) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.picture = picture;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", major='" + major + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }
}
