package com.haiyu.manager.pojo;


import lombok.Data;

@Data
public class ChartRequest {

    private String temperature;

    private String humilevel;

    private String regTime;


    public ChartRequest(String temperature, String humilevel, String regTime) {
        this.temperature = temperature;
        this.humilevel = humilevel;
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "ChartRequest{" +
                "temperature='" + temperature + '\'' +
                ", humilevel='" + humilevel + '\'' +
                ", regTime='" + regTime + '\'' +
                '}';
    }
}
