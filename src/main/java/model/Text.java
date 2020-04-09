package model;

import controller.utils.TextType;

public class Text {
    String id;
    String link;
    String content;
    String attributes;
    TextType type;

    public Text(String link, String content, String attributes, TextType type) {
        this.link = link;
        this.content = content;
        this.attributes = attributes;
        this.type = type;
    }

    public Text(String id, String link, String content, String attributes, TextType type) {
        this.id = id;
        this.link = link;
        this.content = content;
        this.attributes = attributes;
        this.type = type;
    }

    public Text(String link, String content, String attributes) {
        this.link = link;
        this.content = content;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public String getAttributes() {
        return attributes;
    }

    public TextType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Text{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", attributes='" + attributes + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getJsonFormat() {
        return "{" +
                "\"link\":\"" + link + "\"" +
                ", \"attributes\":\"" + getJsonFriendly(attributes) + "\"" +
                ", \"content\":\"" + getJsonFriendly(content) + "\"" +
                "}";

    }

    private String getJsonFriendly(String original) {
        return original.replaceAll("\"", "\\\"")
                .replaceAll("\b", "\\b")
                .replaceAll("\f", "\\f")
                .replaceAll("\n", "\\n")
                .replaceAll("\r", "\\r")
                .replaceAll("\t", "\\t")
                .replaceAll("\"", "")
                .replaceAll(":", "")
                .replaceAll(";", "")
                .replaceAll(",", "")
                .replaceAll("'", "")
                .replaceAll("“", "")
                .replaceAll("\\)", "")
                .replaceAll("\\(", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
    }

}
