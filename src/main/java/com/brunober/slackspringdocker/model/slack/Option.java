
package com.brunober.slackspringdocker.model.slack;


public class Option {

    private Text text;
    private String value;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
