package io.cem.common.utils;

import io.cem.modules.cem.entity.ScoreEntity;

import java.util.ArrayList;
import java.util.List;

public class CalcUtils {
    public static ScoreEntity getAvgScoreEntity(List<ScoreEntity> se){
        if(se==null){
            ScoreEntity rs = new ScoreEntity();
            rs.setScore(0d);
            return rs;
        }else if(se.size()<1){
            ScoreEntity rs = new ScoreEntity();
            rs.setScore(0d);
            return rs;
        }else {
            ScoreEntity s = se.get(0);
            Double all = 0d;
            for(ScoreEntity sef:se){
                all += sef.getScore();
            }
            Double avg = all/se.size();
            s.setScore(Math.rint(avg));
            return s;
        }

    }
    public static void main(String agrs[]){
        List<ScoreEntity> list = new ArrayList<ScoreEntity>();
        ScoreEntity s = new ScoreEntity();
        s.setScore(98.39d);
        ScoreEntity s1 = new ScoreEntity();
        s1.setScore(27d);
        ScoreEntity s2 = new ScoreEntity();
        s2.setScore(99d);
        list.add(s);
        //list.add(s1);
        //list.add(s2);
        System.out.println(CalcUtils.getAvgScoreEntity(list).getScore());

    }
}
