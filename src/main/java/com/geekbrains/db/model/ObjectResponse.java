package com.geekbrains.db.model;

public class ObjectResponse {
    private Object object;
    private String error;

    public boolean ok() {
        return object != null;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ObjectResponse{" +
                "object=" + object +
                ", error='" + error + '\'' +
                '}';
    }
}
