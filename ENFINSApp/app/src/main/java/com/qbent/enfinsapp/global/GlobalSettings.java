package com.qbent.enfinsapp.global;

public enum GlobalSettings {
    API_BASE_URL("http://148.72.209.27:8888/api/external/");

    private String key;

    public String getKey() {
        return key;
    }

    GlobalSettings (String key) {
        this.key = key;
    }
}
