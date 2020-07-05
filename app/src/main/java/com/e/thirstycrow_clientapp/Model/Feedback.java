package com.e.thirstycrow_clientapp.Model;

public class Feedback {
    private String subject;
    private String description;
    private String name;
    private String phone;
    private String complaintid;
    public Feedback(){}
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Feedback(String subject, String description, String name, String phone, String complaintid) {
        this.subject = subject;
        this.description = description;
        this.name = name;
        this.phone = phone;
        this.complaintid = complaintid;
    }

    public String getComplaintId() {
        return complaintid;
    }

    public void setComplaintId(String complaintid) {
        this.complaintid = complaintid;
    }
}
