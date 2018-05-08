package io.cem.modules.cem.entity;

import java.util.List;

public class ChartEntity {
    private String name;
    private List<Double> data;

    public void setName(String name) {
        this.name = name;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public List<Double> getData() {
        return data;
    }
}
