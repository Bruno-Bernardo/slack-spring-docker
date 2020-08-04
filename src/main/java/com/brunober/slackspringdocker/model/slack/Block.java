
package com.brunober.slackspringdocker.model.slack;

import com.brunober.slackspringdocker.model.jira.Issue;
import com.brunober.slackspringdocker.model.jira.Jira;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Block {

    private String type = "section";
    private Text text;
    private Accessory accessory;
    private List<Element> elements;
    private List<Field> fields;

    public Block() {
    }

    public Block(String type, Text text, Accessory accessory, List<Element> elements, List<Field> fields) {
        this.type = type;
        this.text = text;
        this.accessory = accessory;
        this.elements = elements;
        this.fields = fields;
    }

    public Block(List<Field> fields) {
        this.fields = fields;
    }

    public Block(String type) {
        this.type = type;
    }

    public Block(Text text) {
        this.text = text;
    }

    public Block(Issue issue) {
        this.text = new Text("mrkdwn", "<https://healthchess.atlassian.net/browse/" + issue.getKey() + "|" + issue.getKey() + "> - " + issue.getFields().getStatus().getName());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
