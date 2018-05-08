package io.cem.modules.cem.controller;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.dao.LayerDao;
import io.cem.modules.cem.dao.ScoreCollectLayerDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/cem/index")
public class IndexController {
    public static Log log=LogFactory.getLog(IndexController.class);
    @Autowired
    private ScoreCollectAllService scoreCollectAllService;
    @Autowired
    private ScoreCollectService scoreCollectService;
    @Autowired
    private LayerDao layerDao;
    @Autowired
    private ScoreCollectLayerService scoreCollectLayerService;
    @Autowired
    private ScoreCollectBusyIdleTimeService scoreCollectBusyIdleTimeService;
    @Autowired
    ScoreCollectTargetService scoreCollectTargetService;
    @Autowired
    ScoreCollectCityService scoreCollectCityService;


//显示门户排行
    @RequestMapping("/targetview")
    @ResponseBody
    public R targetView(String serviceType){// test http://localhost:8080/cem/index/targetview
        Date[] p = getQueryDay();
        List<ScoreCollectTargetEntity> scoreCollects = new ArrayList<ScoreCollectTargetEntity>();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("startDate",p[1]);
        pm.put("startDate",p[0]);
        pm.put("startTime","00:00:00");
        pm.put("endTime","23:00:00");
        pm.put("serviceType",serviceType);
        scoreCollects = scoreCollectTargetService.getScores(pm);
        log.info("门户排行，计算结果："+scoreCollects);
        return  R.ok().put("scoreCollects",scoreCollects);

    }


//显示n个月的柱图
    @RequestMapping("/qoeview")
    @ResponseBody
    public R getQoEView(String serviceType){// test http://localhost:8080/cem/index/layerqoeview?serviceType=0
        List<Date> p = getQueryMouth();
        List<ScoreCollectAllEntity> scoreCollects = new ArrayList<ScoreCollectAllEntity>();
        for(Date d:p){
            Map<String,Object> pm = new LinkedHashMap<String,Object>();
            pm.put("stime",DateUtils.setStartEndDay(d,1));
            pm.put("etime",DateUtils.setStartEndDay(d,0));
            pm.put("serviceType",serviceType);
            scoreCollects.addAll(scoreCollectAllService.getQoeScores(pm));
        }
        log.info("各应用网络QOE分析测计算结果:"+scoreCollects);
        //return "{\"id\":\"0\"1,\"name\":\"dder\"}";
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    //显示n天的层级曲线图
    @RequestMapping("/layerqoeview")
    @ResponseBody
    public R getLayerQoEView(String serviceType,String accessLayer){
        List<Date> p = getQueryDays();
        List<ScoreCollectLayerEntity> scoreCollects = new ArrayList<ScoreCollectLayerEntity>();
        Map<String,Object> layersParams = new LinkedHashMap<String, Object>();
        List<LayerEntity> layers = layerDao.queryList(layersParams);
        for(Date d:p){
            Map<String,Object> pm = new LinkedHashMap<String,Object>();
            pm.put("stime",DateUtils.setStartEndDay(d,1));
            pm.put("etime",DateUtils.setStartEndDay(d,0));
            pm.put("serviceType",serviceType);
            scoreCollects.addAll(scoreCollectLayerService.getScores(pm));
        }
        log.info("网络分层评测计算结果:"+scoreCollects);
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    /*public R getLayerQoEView(String serviceType,String accessLayer){// test http://localhost:8080/cem/index/layerqoeview?serviceType=20&accessLayer=1000
        List<Date> p = getQueryDays();
        //List<ScoreCollectAllEntity> scoreCollects = new ArrayList<ScoreCollectAllEntity>();
        List<ChartEntity> scoreCollectstest = new ArrayList<ChartEntity>();
        Map<String,Object> params = new LinkedHashMap<String, Object>();
        List<LayerEntity> layers = layerDao.queryList(params);
        for(LayerEntity l : layers){
            List<Double> dataValue = new ArrayList<Double>();
            for(Date d:p){
                Map<String,Object> pm = new LinkedHashMap<String,Object>();
                pm.put("stime",DateUtils.getDayforBegin(d));
                pm.put("etime",DateUtils.getDayforEnd(d));
                pm.put("serviceType",serviceType);
                pm.put("accessLayer",l.getLayerTag());
                List<ScoreCollectAllEntity> t = scoreCollectAllService.getQoeScores(pm);
                if(t.size()>0){
                    dataValue.add(t.get(0).getScore());
                }
            }
            ChartEntity e = new ChartEntity();
            e.setName(l.getLayerName());
            e.setData(dataValue);
            scoreCollectstest.add(e);
        }
        return  R.ok().put("scoreCollects",scoreCollectstest);

    }*/
    //显示忙时闲时雷达图
    @RequestMapping("/dayscoresview")
    @ResponseBody
    public R getDayScoresView(@RequestParam String interval){// http://localhost:8080/cem/index/dayscoresview?interval=2
        Date[] dateParam = getQueryDay();
        Date startTime = dateParam[1];
        Date endTime = dateParam[0];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("interval",interval);
        List<ScoreCollectDayEntity> scoreCollects = scoreCollectBusyIdleTimeService.getScores(p);
        log.info("各应用感知（忙闲时）计算结果:"+scoreCollects);
        return  R.ok().put("scoreCollects",scoreCollects);

    }

//显示地图数据
    @RequestMapping("/cityrankingview")
    @ResponseBody
    public R getCityRankingView(){// http://localhost:8080/cem/index/cityrankingview
        /*Date[] dateParam = getQueryMouth();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        List<ScoreCollectCityEntity> scoreCollects = scoreCollectService.getCityRanking(p);*/
        Date[] p = getQueryDay();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("stime",p[1]);
        pm.put("etime",p[0]);
        pm.put("qtype",1);
        List<ScoreCollectCityEntity> scoreCollects = scoreCollectCityService.getScores(pm);
        List<Map<String,Object>> json = new ArrayList<Map<String,Object>>();
        for(ScoreCollectCityEntity sce:scoreCollects){
            Map<String,Object> rs = new HashMap<String, Object>();
            rs.put("name",sce.getCityName());
            rs.put("value",sce.getScore());
            json.add(rs);
        }
        pm.put("qtype",0);
        List<ScoreCollectCityEntity> scoreCollects2 = scoreCollectCityService.getScores(pm);

        for(ScoreCollectCityEntity sce:scoreCollects2){
            Map<String,Object> rs = new HashMap<String, Object>();
            rs.put("name",sce.getAreaName());
            rs.put("value",sce.getScore());
            json.add(rs);
        }
        log.info("各地区下半场感知计算结果:"+json);
        return  R.ok().put("scoreCollects",json);

    }

    /*@RequestMapping("/targerview")
    @ResponseBody
    public R getTargerScoresView(String serviceType){

        Date[] p = getQueryDay();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("stime",p[1]);
        pm.put("etime",p[0]);
        pm.put("serviceType",serviceType);
        List<ScoreCollectAllEntity> scoreCollects = scoreCollectAllService.getSeniorityScores(pm);
        return  R.ok().put("scoreCollects",scoreCollects);


    }*/
    //返回门户排行饼图
    @RequestMapping("/targerpieview")
    @ResponseBody
    public R getTargerScoresPieView(String serviceType){ //  http://localhost:8080/cem/index/targerpieview?serviceType=0
        /*Date[] dateParam = getQueryMouth();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("serviceType",serviceType);
        List<ScoreCollectTargetEntity> scoreCollects = scoreCollectService.getTargerScores(p);*/
        Date[] p = getQueryDay();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("stime",p[1]);
        pm.put("etime",p[0]);
        pm.put("serviceType",serviceType);
        List<ScoreCollectTargetEntity> scoreCollects = scoreCollectTargetService.getScores(pm);
        List<Map<String,Object>> json = new ArrayList<Map<String,Object>>();
        for(ScoreCollectTargetEntity sce:scoreCollects){
            Map<String,Object> rs = new HashMap<String, Object>();
            rs.put("name",sce.getTarget());
            rs.put("y",sce.getScore());
            json.add(rs);
        }
        return  R.ok().put("scoreCollects",json);


    }
    //返回一组连续日期对象，单位为月
    @RequestMapping("/queryrange")
    @ResponseBody
    public R getQueryRange(@RequestParam String serviceType){ //  http://localhost:8080/cem/index/queryrange?serviceType=0
        InputStream pf = IndexController.class.getClassLoader().getResourceAsStream("chart.properties");
        Properties prop = new Properties();
        try {
            prop.load(pf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Date> mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryMouthRange")));
        List<Map<String,String>> mouthsView = new ArrayList<Map<String,String>>();
        for(Date d:mouths){
            Map<String,String> dateMap = new HashMap<String,String>();
            dateMap.put("mouthDay",DateUtils.format(d,"yyyy-MM"));
            mouthsView.add(dateMap);
        }
        //Collections.reverse(mouthsView);
        return  R.ok().put("mouths",mouthsView);

    }
    //返回一组连续日期对象，单位为天
    @RequestMapping("/querydayrange")
    @ResponseBody
    public R getQueryDayRange(){
        InputStream pf = IndexController.class.getClassLoader().getResourceAsStream("chart.properties");
        Properties prop = new Properties();
        try {
            prop.load(pf);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<Date> mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryDayRange2")));
//        List<Map<String,String>> mouthsView = new ArrayList<Map<String,String>>();
//        for(Date d:mouths){
//            Map<String,String> dateMap = new HashMap<String,String>();
//            dateMap.put("mouthDay",DateUtils.format(d,"yyyy-MM"));
//            mouthsView.add(dateMap);
//        }
//        Collections.reverse(mouthsView);
        List<Date> days = DateUtils.getLastNumberDays(new Date(),Integer.parseInt(prop.getProperty("queryDayRange2")));
        List<Map<String,String>> daysView = new ArrayList<Map<String,String>>();
        for(Date d:days){
            Map<String,String> dateMap = new HashMap<String,String>();
            dateMap.put("day",DateUtils.format(d,"MM-dd"));
            daysView.add(dateMap);
        }
        Collections.reverse(daysView);

        return  R.ok().put("days",daysView);
    }
    private List<Date> getQueryMouth(){
        List<Date> mouths = new ArrayList<Date>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryMouthRange")));

        }catch (Exception e){
            e.printStackTrace();
        }

        return  mouths;

    }

    private List<Date> getQueryDays(){
        List<Date> days = new ArrayList<Date>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            days = DateUtils.getLastNumberDays(new Date(),Integer.parseInt(prop.getProperty("queryDayRange2")));

        }catch (Exception e){
            e.printStackTrace();
        }

        return  days;

    }

    private Date[] getQueryDay(){
        Date[] rs = new Date[2];
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            Date now = new Date();
            rs[0] = now;
            rs[1] = DateUtils.getLastDay(now,Integer.parseInt(prop.getProperty("queryDayRange2")));

        }catch (Exception e){
            e.printStackTrace();
        }

        return  rs;

    }

    public static void main(String[] s){
        List<Double> data1 = new ArrayList<Double>();
        data1.add(89d);
        data1.add(88d);
        data1.add(97d);
        data1.add(95d);
        data1.add(82d);
        List<Double> data2 = new ArrayList<Double>();
        data2.add(89d);
        data2.add(99d);
        data2.add(97d);
        data2.add(95d);
        data2.add(82d);
        List<Double> data3 = new ArrayList<Double>();
        data3.add(99d);
        data3.add(95d);
        data3.add(91d);
        data3.add(85d);
        data3.add(86d);
        List<ChartEntity> scoreCollectstest = new ArrayList<ChartEntity>();
        ChartEntity ce = new ChartEntity();
        ce.setName("layweb1");
        ce.setData(data1);
        scoreCollectstest.add(ce);
        ChartEntity ce2 = new ChartEntity();
        ce2.setName("layweb2");
        ce2.setData(data2);
        scoreCollectstest.add(ce2);
        ChartEntity ce3 = new ChartEntity();
        ce3.setName("layweb3");
        ce3.setData(data3);
        scoreCollectstest.add(ce3);
        for(ChartEntity t:scoreCollectstest){
            System.out.println(t.getName());
            List<Double> data = t.getData();
            for(Double v:data){
                System.out.println("====data:"+v);
            }
        }

    }

}
