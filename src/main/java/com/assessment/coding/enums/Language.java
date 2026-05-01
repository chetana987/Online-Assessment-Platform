package com.assessment.coding.enums;

public enum Language {
    JAVA("java"),
    PYTHON("python"),
    CPP("cpp");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}