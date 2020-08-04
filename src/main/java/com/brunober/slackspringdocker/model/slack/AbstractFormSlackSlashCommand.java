package com.brunober.slackspringdocker.model.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

// workaround for customize x-www-form-urlencoded
public abstract class AbstractFormSlackSlashCommand {

    public void setTeam_id(String teamId) {
        setTeamId(teamId);
    }

    public void setTeam_domain(String teamDomain) {
        setTeamDomain(teamDomain);
    }

    public void setChannel_id(String channelId) {
        setChannelId(channelId);
    }

    public void setChannel_name(String channelName) {
        setChannelName(channelName);
    }

    public void setUser_id(String userId) {
        setUserId(userId);
    }

    public void setUser_name(String userName) {
        setUserName(userName);
    }

    public void setResponse_url(String responseUrl) {
        setResponseUrl(responseUrl);
    }

    public void setEnterprise_id(String enterpriseId) {
        setEnterpriseId(enterpriseId);
    }

    public void setTrigger_id(String trigger_id) {
        setTriggerId(trigger_id);
    }

    abstract void setTeamId(String teamId);
    abstract void setTeamDomain(String teamDomain);
    abstract void setChannelId(String channelId);
    abstract void setChannelName(String channelName);
    abstract void setUserId(String userId);
    abstract void setUserName(String userName);
    abstract void setResponseUrl(String responseUrl);
    abstract void setEnterpriseId(String enterpriseId);
    abstract void setTriggerId(String trigger_id);
}