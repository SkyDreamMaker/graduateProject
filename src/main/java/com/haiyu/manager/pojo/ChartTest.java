package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "chart_test")
public class ChartTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 数据ID
     */
    @Column(name = "value")
    private String value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ChartTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    public ChartTest(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ChartTest() {
    }
}
