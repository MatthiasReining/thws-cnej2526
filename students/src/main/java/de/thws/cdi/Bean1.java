package de.thws.cdi;

import jakarta.enterprise.context.SessionScoped;

@SessionScoped
public class Bean1 {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
