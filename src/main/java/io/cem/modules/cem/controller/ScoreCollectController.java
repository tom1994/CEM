package io.cem.modules.cem.controller;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
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
@RequestMapping("/cem/collect")
public class ScoreCollectController {
    public static Log log=LogFactory.getLog(ScoreCollectController.class);
    @Autowired
    private ScoreCollectTargetService scoreCollectTargetService;
    @Autowired
    private ScoreCollectAllService scoreCollectAllService;
    @Autowired
    private ScoreCollectLayerService scoreCollectLayerService;
    @Autowired
    ScoreCollectBusyIdleTimeService scoreCollectBusyIdleTimeService;
    @Autowired
    ScoreCollectCityService scoreCollectCityService;
    //显示门户排行
    @RequestMapping("/targetview")
    @ResponseBody
    public R targetView(String serviceType){// test http://localhost:8080/cem/collect/targetview
        Date[] p = getQueryDay();
        List<ScoreCollectTargetEntity> scoreCollects = new ArrayList<ScoreCollectTargetEntity>();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("startDate",DateUtils.format(p[1]));
        pm.put("endDate",DateUtils.format(p[0]));
        pm.put("startTime","00:00:00");
        pm.put("endTime","23:59:59");
        pm.put("serviceType",serviceType);
        log.info("门户排行，传入的查询时间参数 startDate="+pm.get("startDate"));
        log.info("门户排行，传入的查询时间参数 endDate="+pm.get("endDate"));
        scoreCollects = scoreCollectTargetService.getScores(pm);
        log.info("门户排行，计算结果："+scoreCollects);
        return  R.ok().put("scoreCollects",scoreCollects).put("startDate",pm.get("startDate")).put("endDate",pm.get("endDate"));

    }
    //显示n个月的柱图
    @RequestMapping("/qoeview")
    @ResponseBody
    public R getQoEView(String serviceType){// test http://localhost:8080/cem/collect/qoeview
        List<Date> p = getQueryMouth();
        List<ScoreCollectAllEntity> scoreCollects = new ArrayList<ScoreCollectAllEntity>();
        for(Date d:p){
            Map<String,Object> pm = new LinkedHashMap<String,Object>();
            pm.put("startDate",DateUtils.setStartEndDay(d,1));
            pm.put("endDate",DateUtils.setStartEndDay(d,0));
            pm.put("startTime","00:00:00");
            pm.put("endTime","23:59:59");
            pm.put("serviceType",serviceType);
            log.info("getQoEView 开始时间："+pm.get("startDate"));
            log.info("getQoEView 结束时间："+pm.get("endDate"));
            scoreCollects.addAll(scoreCollectAllService.getScores(pm));
        }
        log.info("各应用网络QOE分析测计算结果:"+scoreCollects);
        //return "{\"id\":\"0\"1,\"name\":\"dder\"}";
        return  R.ok().put("scoreCollects",scoreCollects).put("queryDate",p);

    }
    //显示n天的层级曲线图
    @RequestMapping("/layerqoeview")
    @ResponseBody
    public R getLayerQoEView(String serviceType){
        List<Date> p = getQueryDays();
        List<ScoreCollectLayerEntity> scoreCollects = new ArrayList<ScoreCollectLayerEntity>();
        Map<String,Object> layersParams = new LinkedHashMap<String, Object>();

        for(Date d:p){
            Map<String,Object> pm = new LinkedHashMap<String,Object>();
            pm.put("startDate",DateUtils.setStartEndDay(d,1));
            pm.put("endDate",DateUtils.setStartEndDay(d,0));
            pm.put("startTime","00:00:00");
            pm.put("endTime","23:59:59");
            pm.put("serviceType",serviceType);
            log.info("/layerqoeview 层级 查询参数，开始时间："+pm.get("startDate"));
            log.info("/layerqoeview 层级 查询参数，结束时间："+pm.get("endDate"));
            scoreCollects.addAll(scoreCollectLayerService.getScores(pm));
        }
        log.info("网络分层评测计算结果1:"+scoreCollects);
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    //显示忙时闲时雷达图
    @RequestMapping("/dayscoresview")
    @ResponseBody
    public R getDayScoresView(@RequestParam String interval){// http://localhost:8080/cem/index/dayscoresview?interval=2
        Date[] d = getQueryDay();

        Map pm = new LinkedHashMap<String,Object>();
        //pm.put("startDate",DateUtils.format(d[1]));
        //pm.put("endDate",DateUtils.format(d[0]));
        pm.put("interval",interval);
        //log.info("忙闲时，传入的查询时间参数 startDate="+pm.get("startDate"));
        //log.info("忙闲时，传入的查询时间参数 endDate="+pm.get("endDate"));
        List<ScoreCollectDayEntity> scoreCollects = scoreCollectBusyIdleTimeService.getScores(pm);
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
        p.put("endTime",endTime);123456
        List<ScoreCollectCityEntity> scoreCollects = scoreCollectService.getCityRanking(p);*/
        Date[] p = getQueryDay();
        Map<String,Object> pm = new LinkedHashMap<String,Object>();
        pm.put("startDate",p[1]);
        pm.put("endDate",p[0]);
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
        log.info("各地区平均感知计算结果:"+json);
        return  R.ok().put("scoreCollects",json);

    }
    //返回一组连续日期对象，单位为月
    @RequestMapping("/queryrange")
    @ResponseBody
    public R getQueryRange(@RequestParam String serviceType){ //  http://localhost:8080/cem/index/queryrange?serviceType=0
        Properties prop = getProperties("chart.properties");
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
    private List<Date> getQueryDays(){
        Properties prop = getProperties("chart.properties");
        return DateUtils.getLastNumberDays(new Date(),Integer.parseInt(prop.getProperty("queryDayRange2")));
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
    private List<Date> getQueryMouth(){
        Properties prop = getProperties("chart.properties");
        List<Date> mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryMouthRange")));
        return  mouths;

    }
    private Properties getProperties(String fileName){
        Properties prop = null;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource(fileName).getPath()));
            prop = new Properties();
            prop.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] s){
        Date[] d = new ScoreCollectController().getQueryDay();
        System.out.println(d[0]);
        System.out.println(d[1]);

    }

}
