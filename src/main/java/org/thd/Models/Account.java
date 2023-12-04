package org.thd.Models;

import java.util.Arrays;

public class Account {
    private int id;
    private String username;
    private String password;
    private String role;
    private byte[] picture;
    private String name;
    private int age;
    private String phoneNumber;
    private boolean status;

    public Account() {
    }
    public Account(int id, String username, String password, String role, byte[] picture, String name, int age, String phoneNumber, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.picture = picture;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Account(String username, String password, String role, byte[] picture, String name, int age, String phoneNumber, boolean status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.picture = picture;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
