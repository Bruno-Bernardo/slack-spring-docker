
package com.brunober.slackspringdocker.model.jira;


import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String expand;
    private String self;
    private String key;
    @ManyToOne
    private Fields fields;
    private String week;

    public Issue() {
    }

    public Issue(String key) {
        this.key = key;
        this.week = new SimpleDateFormat("w").format(new java.util.Date());
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
