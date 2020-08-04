package com.brunober.slackspringdocker.utils;

import com.brunober.slackspringdocker.model.slack.Block;
import com.brunober.slackspringdocker.model.slack.Message;
import com.brunober.slackspringdocker.model.slack.Text;

import java.util.Arrays;

public class SlackUtils {
    public static Block getBlockDivider() {
        return new Block("divider");
    }

    public static Message getWaitingMessage() {
        return new Message(Arrays.asList(new Block(new Text("plain_text", "Processing... Please wait."))), null);
    }
}
