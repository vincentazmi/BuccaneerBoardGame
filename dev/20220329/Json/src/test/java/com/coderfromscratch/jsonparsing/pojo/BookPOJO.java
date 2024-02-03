package com.coderfromscratch.jsonparsing.pojo;

import java.time.LocalDate;

public class BookPOJO {

    private String title;
    private  boolean inPrint;
    private String publishDate;

    public BookPOJO(String title, boolean inPrint, String publishDate) {
        this.title = title;
        this.inPrint = inPrint;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInPrint() {
        return inPrint;
    }

    public void setInPrint(boolean inPrint) {
        this.inPrint = inPrint;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
