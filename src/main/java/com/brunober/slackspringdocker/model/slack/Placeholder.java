
package com.brunober.slackspringdocker.model.slack;


public class Placeholder {

    private String type;
    private String text;
    private Boolean emoji;

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
