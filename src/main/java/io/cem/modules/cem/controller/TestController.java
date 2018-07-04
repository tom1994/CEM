package io.cem.modules.cem.controller;


import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/cem/test")
@Component("testController")
public class TestController {
    public static Log log=LogFactory.getLog(TestController.class);

    @Autowired
    private ScoreCollectAllService scoreCollectAllService;


    @Autowired
    private ScoreCollectTargetService scoreCollectTargetService;

    @Autowired
    private ScoreCollectLayerService scoreCollectLayerService;

    @Autowired
    private ScoreCollectBusyIdleTimeService scoreCollectBusyIdleTimeService;

    @Autowired
    private ScoreCollectCityService scoreCollectCityService;

    @RequestMapping("/savetargetscores")
    @ResponseBody
    public R saveTargetScores(){ //  http://localhost:8080/cem/test/savetargetscores?serviceType=1
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        String stime ="";
        String ntime ="";
        String fix = "";
        try {

            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            stime = prop.getProperty("stime");
            ntime = prop.getProperty("etime");

            fix = prop.getProperty("queryDayRange");

            stime = DateUtils.format(DateUtils.getPreviousDay(new Date(),Integer.parseInt(fix)));
            ntime = DateUtils.format(DateUtils.getPreviousDay(new Date(),1));

            scoreCollectTargetService.delAll();
            log.info("清空门户排行数据完成..");

            scoreCollectTargetService.saveScore(stime, ntime);
            log.info("门户排行数据导入任务执行完成..");

        }catch (Exception e){
            e.printStackTrace();
        }
        return  R.ok().put("startTime",stime).put("endTime",ntime);

    }

    @RequestMapping("/savemonthcores")
    @ResponseBody
    public R saveMonthScores(){ //  http://localhost:8080/cem/test/savemonthcores
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        String stime ="";
        String ntime ="";
        String fix = "";
        try {

            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            fix = prop.getProperty("queryDayRange2");

            stime = DateUtils.format(DateUtils.getPreviousDay(new Date(),Integer.parseInt(fix)));
            ntime = DateUtils.format(DateUtils.getPreviousDay(new Date(),1));

            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveConnectivityScore");
            scoreCollectAllService.saveConnectivityScore(stime,ntime);
            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveNetworkLayerScore");
            scoreCollectAllService.saveNetworkLayerScore(stime,ntime);
            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveWebPageScore");
            scoreCollectAllService.saveWebPageScore(stime,ntime);
            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveWebVideoScore");
            scoreCollectAllService.saveWebVideoScore(stime,ntime);
            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveDownLoadScore");
            scoreCollectAllService.saveDownLoadScore(stime,ntime);
            log.info("保存最近n个月的汇总数据，开始调用scoreCollectAllService.saveGameScore");
            scoreCollectAllService.saveGameScore(stime,ntime);

            log.info("各应用网络 qoe 分析数据导入任务执行完成..");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  R.ok().put("startTime",stime).put("endTime",ntime);

    }
    @RequestMapping("/savelayerscores")
    @ResponseBody
    public R saveLayerScores(){ //  http://localhost:8080/cem/test/savelayerscores
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        String stime ="";
        String ntime ="";
        String fix = "";
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
           /* stime = prop.getProperty("stime");
            ntime = prop.getProperty("etime");*/

            fix = prop.getProperty("queryDayRange");

            stime = DateUtils.format(DateUtils.getPreviousDay(new Date(),Integer.parseInt(fix)));
            ntime = DateUtils.format(DateUtils.getPreviousDay(new Date(),1));

            scoreCollectLayerService.delAll();
            scoreCollectLayerService.saveScores(stime,ntime);
            System.out.println("test");
            log.info("各应用网络分层质量数据导入任务执行完成..");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  R.ok().put("startTime",stime).put("endTime",ntime);

    }
    @RequestMapping("/savebusyidlescores")
    @ResponseBody
    public R saveBusyIdleScores(){ //  http://localhost:8080/cem/test/savebusyidlescores
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        String stime ="";
        String ntime ="";
        String fix = "";
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            stime = prop.getProperty("stime");
            ntime = prop.getProperty("etime");

            fix = prop.getProperty("queryDayRange");

            stime = DateUtils.format(DateUtils.getDayforBegin(DateUtils.getPreviousDay(new Date(),Integer.parseInt(fix))),DateUtils.DATE_TIME_PATTERN);
            ntime = DateUtils.format(DateUtils.getDayforEnd(DateUtils.getPreviousDay(new Date(),1)),DateUtils.DATE_TIME_PATTERN);

            scoreCollectBusyIdleTimeService.delAll();
            log.info("各应用网络忙闲时历史数据全部清空..");

            for(int i=1;i<7;i++){
                scoreCollectBusyIdleTimeService.saveScore(stime,ntime,i);
            }
            log.info("各应用网络忙闲时数据导入任务执行完成..");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  R.ok().put("startTime",stime).put("endTime",ntime);

    }
    @RequestMapping("/savecityscores")
    @ResponseBody
    public R saveCityScores(){ //  http://localhost:8080/cem/test/savecityscores
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        String stime ="";
        String etime ="";
        String fix ="";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            /*stime = prop.getProperty("stime");
            etime = prop.getProperty("etime");*/
            fix = prop.getProperty("queryDayRange");

            stime = DateUtils.format(DateUtils.getPreviousDay(new Date(),Integer.parseInt(fix)));
            etime = DateUtils.format(DateUtils.getPreviousDay(new Date(),1));

            scoreCollectCityService.delAll();

            scoreCollectCityService.saveScores(stime,etime);

            log.info("各地区平均统计数据导入任务执行完成..");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  R.ok().put("startTime",stime).put("endTime",etime);

    }

}
