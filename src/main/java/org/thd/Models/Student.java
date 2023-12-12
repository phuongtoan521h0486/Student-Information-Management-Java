package org.thd.Models;

import java.util.Arrays;

public class Student {
    private String studentId;
    private String email;
    private String name;
    private boolean gender;
    private String major;
    private double gpa;
    private int trainingPoint;
    private byte[] picture;

    public Student() {
    }

    public Student(String studentId, String email, String name, boolean gender, String major, double gpa, int trainingPoint, byte[] picture) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.gpa = gpa;
        this.trainingPoint = trainingPoint;
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

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getTrainingPoint() {
        return trainingPoint;
    }

    public void setTrainingPoint(int trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean isGpaA () {
        if (this.gpa >= 8.5 && this.gpa <= 10) {
            return true;
        }
        return false;
    }

    public boolean isGpaB() {
        if (this.gpa >= 7.0 && this.gpa <= 8.4) {
            return true;
        }
        return false;
    }

    public boolean isGpaC() {
        if (this.gpa >= 5.5 && this.gpa <= 6.9) {
            return true;
        }
        return false;
    }

    public boolean isGpaD() {
        if (this.gpa >= 4.0 && this.gpa <= 5.4) {
            return true;
        }
        return false;
    }

    public boolean isGpaF() {
        if (this.gpa < 4.0) {
            return true;
        }
        return false;
    }

    public boolean isExcellent() {
        if (this.trainingPoint >= 90 && this.trainingPoint <= 100) {
            return true;
        }
        return false;
    }

    public boolean isVeryGood() {
        if (this.trainingPoint >= 80 && this.trainingPoint < 90) {
            return true;
        }
        return false;
    }

    public boolean isGood() {
        if (this.trainingPoint >= 65 && this.trainingPoint < 80) {
            return true;
        }
        return false;
    }

    public boolean isAverage() {
        if (this.trainingPoint >= 50 && this.trainingPoint < 65) {
            return true;
        }
        return false;
    }

    public boolean isBelowAverage() {
        if (this.trainingPoint >= 35 && this.trainingPoint < 50) {
            return true;
        }
        return false;
    }

    public boolean isWeek() {
        if (this.trainingPoint < 35) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", major='" + major + '\'' +
                ", gpa=" + gpa +
                ", trainingPoint=" + trainingPoint +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }
}
