
package com.brunober.slackspringdocker.model.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Accessory {

    private String type;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("alt_text")
    private String altText;
    private Placeholder placeholder;
    private List<Option> options = null;
    private Text text;
    private String value;

    @JsonProperty("initial_date")
    private String initialDate;

    public Accessory() {
    }

    public Accessory(String type, String imageUrl, String altText) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.altText = altText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Placeholder getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Placeholder placeholder) {
        this.placeholder = placeholder;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

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

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }
}
