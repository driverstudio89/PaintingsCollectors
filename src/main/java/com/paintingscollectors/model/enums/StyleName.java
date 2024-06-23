package com.paintingscollectors.model.enums;

public enum StyleName {

    IMPRESSIONISM ("Impressionism"),
    ABSTRACT ("Abstract"),
    EXPRESSIONISM ("Expressionism"),
    SURREALISM ("Surrealism"),
    REALISM ("Realism");

    private final String value;

    private StyleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }



}
