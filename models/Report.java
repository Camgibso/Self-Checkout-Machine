package com.scs.models;

public class Report {
    private String text;

    public Report(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
