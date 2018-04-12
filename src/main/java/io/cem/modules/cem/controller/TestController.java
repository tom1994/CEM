package io.cem.modules.cem.controller;

import io.cem.common.utils.R;
import io.cem.modules.cem.service.IndexHistogramViewService;
import io.cem.modules.cem.service.IndexLineViewService;
import io.cem.modules.cem.service.IndexRadaViewService;
import io.cem.modules.cem.service.IndexRankingViewService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/cem/test")
public class TestController {
    public static Log log=LogFactory.getLog(IndexController.class);
    @Autowired
    private IndexHistogramViewService indexHistogramViewService;
    @Autowired
    private IndexLineViewService indexLineViewService;
    @Autowired
    private IndexRadaViewService indexRadaViewService;
    @Autowired
    private IndexRankingViewService indexRankingViewService;

    @RequestMapping("/savescores")
    @ResponseBody
    public R saveScores(@RequestParam String serviceType){ //  http://localhost:8080/cem/test/savescores?serviceType=1
        List<Map<String,String>> dateList = new LinkedList<Map<String,String>>();
        Map<String,String> p1 = new LinkedHashMap<String,String>();
        p1.put("startTime","2017-11-1");
        p1.put("endTime","2017-11-30");
        dateList.add(p1);
        Map<String,String> p2 = new LinkedHashMap<String,String>();
        p2.put("startTime","2017-12-1");
        p2.put("endTime","2017-12-31");
        dateList.add(p2);
        Map<String,String> p3 = new LinkedHashMap<String,String>();
        p3.put("startTime","2018-1-1");
        p3.put("endTime","2018-1-31");
        dateList.add(p3);
        Map<String,String> p4 = new LinkedHashMap<String,String>();
        p4.put("startTime","2018-2-1");
        p4.put("endTime","2018-2-28");
        dateList.add(p4);
        Map<String,String> p5 = new LinkedHashMap<String,String>();
        p5.put("startTime","2018-3-1");
        p5.put("endTime","2018-3-31");
        dateList.add(p5);
        String startDate = "2018-3-1";
        String endDate = "2018-3-31";
        //log.info("==========dateList=="+dateList);

        //indexLineViewService.saveConnectivityScore(dateList,"1000");
        //indexLineViewService.saveDownLoadScore(dateList,"1000");
        //indexLineViewService.saveGameScore(dateList,"1000");
        //indexLineViewService.saveWebVideoScore(dateList,"1000");
        //indexLineViewService.saveWebPageScore(dateList,"1000");

        //indexRadaViewService.saveConnectivityScore(startDate,endDate,1);
        //indexRadaViewService.saveDownLoadScore(startDate,endDate,2);
        //indexRadaViewService.saveGameScore(startDate,endDate,1);
        //indexRadaViewService.saveWebPageScore(startDate,endDate,2);
        //indexRadaViewService.saveWebVideoScore(startDate,endDate,1);

        //indexRankingViewService.saveConnectivityScore(startDate,endDate,1);
        try {
            indexRankingViewService.saveDownLoadScore(startDate, endDate, 2);
            indexRankingViewService.saveGameScore(startDate, endDate, 1);
            indexRankingViewService.saveWebPageScore(startDate, endDate, 2);
            indexRankingViewService.saveWebVideoScore(startDate, endDate, 1);
        }catch (InterruptedException i){

        }catch(ExecutionException e){

        }
        return  R.ok().put("msg","test Ranking success ");

    }
}
