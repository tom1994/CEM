package io.cem.common.utils.excel;

/**
 * 用于测试的Entity类
 * Created by tomxie on 2017/4/6 12:41.
 */
public class Student {
    /**
     * id
     */
    private Integer id;
    /**
     * 学号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学院
     */
    private String age;
    /**
     * 成绩
     */
    private float score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%f", no, name, age, score);
    }
}

