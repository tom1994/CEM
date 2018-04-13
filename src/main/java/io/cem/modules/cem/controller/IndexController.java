package io.cem.modules.cem.controller;

import com.alibaba.druid.support.json.JSONUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ScoreCollectCityEntity;
import io.cem.modules.cem.entity.ScoreCollectDayEntity;
import io.cem.modules.cem.entity.ScoreCollectEntity;
import io.cem.modules.cem.entity.ScoreCollectTargetEntity;
import io.cem.modules.cem.service.IndexHistogramViewService;
import io.cem.modules.cem.service.ScoreCollectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/cem/index")
public class IndexController {
    public static Log log=LogFactory.getLog(IndexController.class);
    @Autowired
    private ScoreCollectService scoreCollectService;


    @RequestMapping("/qoeview")
    @ResponseBody
    public R getQoEView(@RequestParam String serviceType){
        Date[] dateParam = getQueryTime();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("serviceType",serviceType);
        List<ScoreCollectEntity> scoreCollects = scoreCollectService.list(p);
        //return "{\"id\":\"0\"1,\"name\":\"dder\"}";
        return  R.ok().put("scoreCollects",scoreCollects);

    }

    @RequestMapping("/layerqoeview")
    @ResponseBody
    public R getLayerQoEView(String serviceType,String accessLayer){// test http://localhost:8080/cem/index/layerqoeview?serviceType=20&accessLayer=1000
        log.info("1111$$$$$$$$$$$$$accessLayer:"+accessLayer);
        Date[] dateParam = getQueryTime();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("serviceType",serviceType);
        p.put("accessLayer",accessLayer);
        List<ScoreCollectEntity> scoreCollects = scoreCollectService.list(p);
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    @RequestMapping("/cityrankingview")
    @ResponseBody
    public R getCityRankingView(){// http://localhost:8080/cem/index/cityrankingview
        Date[] dateParam = getQueryTime();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        List<ScoreCollectCityEntity> scoreCollects = scoreCollectService.getCityRanking(p);
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    @RequestMapping("/dayscoresview")
    @ResponseBody
    public R getDayScoresView(@RequestParam String interval){// http://localhost:8080/cem/index/dayscoresview?interval=2
        Date[] dateParam = getQueryTime();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("interval",interval);
        List<ScoreCollectDayEntity> scoreCollects = scoreCollectService.getDayScores(p);
        return  R.ok().put("scoreCollects",scoreCollects);

    }
    @RequestMapping("/targerview")
    @ResponseBody
    public R getTargerScoresView(@RequestParam String serviceType){ //  http://localhost:8080/cem/index/targerview?serviceType=20
        Date[] dateParam = getQueryTime();
        Date startTime = dateParam[0];
        Date endTime = dateParam[1];
        Map p = new LinkedHashMap<String,Object>();
        p.put("startTime",startTime);
        p.put("endTime",endTime);
        p.put("serviceType",serviceType);
        List<ScoreCollectTargetEntity> scoreCollects = scoreCollectService.getTargerScores(p);
        return  R.ok().put("scoreCollects",scoreCollects);

    }

    private Date[] getQueryTime(){
        Date[] rs = new Date[2];
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse("2017-12-1");
            Date end = dateFormat.parse("2018-3-31");
            rs[0] = start;
            rs[1] = end;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  rs;

    }

}
