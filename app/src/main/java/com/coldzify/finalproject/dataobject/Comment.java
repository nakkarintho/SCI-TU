package com.coldzify.finalproject.dataobject;

import android.support.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Comment implements Comparable<Comment>{
    private String reportID;
    private String comment;
    private String commenter;
    private String imagefilename;
    private Timestamp timestamp;

    public Comment(){}
    public Comment(String reportID, String comment, String imagefilename, String commenter) {
        this.reportID = reportID;
        this.comment = comment;
        this.imagefilename = imagefilename;
        this.commenter = commenter;
        this.timestamp = new Timestamp(new Date());

    }

    public Comment(String reportID, String comment, String imagefilename, String commenter, Timestamp timestamp) {
        this.reportID = reportID;
        this.comment = comment;
        this.imagefilename = imagefilename;
        this.commenter = commenter;
        this.timestamp = timestamp;
    }

    public String getReportID() {
        return reportID;
    }

    public String getComment() {
        return comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public String getImagefilename() { return imagefilename; }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "reportID='" + reportID + '\'' +
                ", comment='" + comment + '\'' +
                ", imagefilename=" + imagefilename +
                ", commenter='" + commenter + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(@NonNull Comment comment) {

        return timestamp.compareTo(comment.getTimestamp());
    }
}
