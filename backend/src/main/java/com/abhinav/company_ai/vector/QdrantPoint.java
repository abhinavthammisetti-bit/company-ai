package com.abhinav.company_ai.vector;

import java.util.List;
import java.util.Map;

public class QdrantPoint {

    private String id;
    private List<Float> vector;
    private Map<String, Object> payload;

    public QdrantPoint() {
    }

    public QdrantPoint(String id,
                       List<Float> vector,
                       Map<String, Object> payload) {

        this.id = id;
        this.vector = vector;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public List<Float> getVector() {
        return vector;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }
}