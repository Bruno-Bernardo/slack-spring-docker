package com.brunober.slackspringdocker.model.slack;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {

    private String type;
    private String text;
    private Boolean emoji;

    public Field() {
    }

    public Field(String type, String text, Boolean emoji) {
        this.type = type;
        this.text = text;
        this.emoji = emoji;
    }

    public Field(String text) {
        this.type = "mrkdwn";
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getEmoji() {
        return emoji;
    }

    public void setEmoji(Boolean emoji) {
        this.emoji = emoji;
    }
}
