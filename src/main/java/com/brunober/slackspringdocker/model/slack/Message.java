
package com.brunober.slackspringdocker.model.slack;

import com.brunober.slackspringdocker.model.jira.Issue;
import com.brunober.slackspringdocker.utils.SlackUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private List<Block> blocks = null;
    @JsonProperty("response_type")
    private String responseType;

    public Message() {
    }

    public Message(List<Block> blocks, String responseType) {
        this.blocks = blocks;
        this.responseType = responseType;
    }

    public Message MessageFromIssues(List<Issue> issues, String responseType) {
        this.blocks = new ArrayList<>();
        this.responseType = responseType;

        this.blocks.add(new Block(new Text("mrkdwn", "*Issues of the week - " + new SimpleDateFormat("w").format(new java.util.Date()) + "*")));
        this.blocks.add(SlackUtils.getBlockDivider());
        this.blocks.add(new Block(new Text("mrkdwn", ":fire: *Urgent*")));
        this.blocks.add(SlackUtils.getBlockDivider());

        if (issues != null && !issues.isEmpty()) {
            for (Issue issue : issues) {
                this.blocks.add(new Block(issue));
            }
        }

        return this;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
