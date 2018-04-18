package io.cem.modules.cem.service.impl;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.LinkedList;


@Service
public class IndexTaskServiceImpl implements IndexTaskService {
    @Autowired
    private IndexHistogramViewService indexHistogramViewService;
    @Autowired
    private IndexLineViewService indexLineViewService;
    @Autowired
    private IndexMapViewService indexMapViewService;
    @Autowired
    private IndexRadaViewService indexRadaViewService;
    @Autowired
    private IndexRankingViewService indexRankingViewService;

    public void run(){
        try {

            List<Map<String,Object>> paramList = new LinkedList<Map<String,Object>>();
            InputStream in =new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath())) ;
            Properties prop = new Properties();
            prop.load(in);
            List<Date> mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryMouthRange")));

            String startDate = DateUtils.format(DateUtils.setStartEndDay(mouths.get(0),0));
            String endDate = DateUtils.format(DateUtils.setStartEndDay(mouths.get(mouths.size()-1),1));;

            indexHistogramViewService.saveConnectivityScore(mouths);
            indexHistogramViewService.saveDownLoadScore(mouths);
            indexHistogramViewService.saveGameScore(mouths);
            indexHistogramViewService.saveNetworkLayerScore(mouths);
            indexHistogramViewService.saveWebPageScore(mouths);
            indexHistogramViewService.saveWebVideoScore(mouths);


            indexLineViewService.saveConnectivityScore(mouths,"2000");
            indexLineViewService.saveDownLoadScore(mouths,"2000");
            indexLineViewService.saveGameScore(mouths,"2000");
            indexLineViewService.saveNetworkLayerScore(mouths,"2000");
            indexLineViewService.saveWebPageScore(mouths,"2000");
            indexLineViewService.saveWebVideoScore(mouths,"2000");



            indexMapViewService.saveScore(startDate,endDate,1);

            indexRadaViewService.saveWebVideoScore(startDate,endDate,1);
            indexRadaViewService.saveWebPageScore(startDate,endDate,1);
            indexRadaViewService.saveGameScore(startDate,endDate,1);
            indexRadaViewService.saveDownLoadScore(startDate,endDate,1);
            indexRadaViewService.saveConnectivityScore(startDate,endDate,1);
            indexRadaViewService.saveNetworkLayerScore(startDate,endDate,1);

            indexRankingViewService.saveDownLoadScore(startDate,endDate,1);
            indexRankingViewService.saveGameScore(startDate,endDate,1);
            indexRankingViewService.saveWebPageScore(startDate,endDate,1);
            indexRankingViewService.saveWebVideoScore(startDate,endDate,1);
            indexRankingViewService.saveConnectivityScore(startDate,endDate,1);


        } catch (Exception e) {
            e.printStackTrace();
        }
//        Date startDate = DateUtils.setStartEndDay(mouths.get(0),0);
//        Date endDate = DateUtils.setStartEndDay(mouths.get(mouths.size()-1),1);

    }


}
