package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.EvaluationEntity;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static io.cem.modules.cem.entity.ScoreEntity.sortDescStringMethod;
import static io.cem.modules.cem.entity.ScoreEntity.sortStringMethod;

/**
 */
@RestController
@EnableAsync
@RequestMapping("recordhourping")
public class RecordHourPingController {
    @Autowired
    private RecordHourPingService recordHourPingService;
    @Autowired
    private RecordHourTracertService recordHourTracertService;
    @Autowired
    private RecordHourSlaService recordHourSlaService;
    @Autowired
    private RecordHourDnsService recordHourDnsService;
    @Autowired
    private RecordHourDhcpService recordHourDhcpService;
    @Autowired
    private RecordHourPppoeService recordHourPppoeService;
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    @Autowired
    private RecordHourWebPageService recordHourWebPageService;
    @Autowired
    private RecordHourWebDownloadService recordHourWebDownloadService;
    @Autowired
    private RecordHourFtpService recordHourFtpService;
    @Autowired
    private RecordHourWebVideoService recordHourWebVideoService;
    @Autowired
    private RecordHourGameService recordHourGameService;
    @Autowired
    private ProbeService probeService;

    /**
     * 质量排名界面计算分
     */
    /**
     * miao进行性能优化，增加异步处理机制
     */
    @RequestMapping("/list")
    @RequiresPermissions("recordhourping:list")
    public R list(String probedata, Integer page, Integer limit, String order) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
        System.out.println(probedata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
//        int service = Integer.parseInt(map.get("service").toString());
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);
            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ScoreEntity> scoreList=new ArrayList<>();

        if(dateDifferent==0){
            scoreList = recordHourRadiusService.calculateHourScore(map);
        }else if(dateDifferent==1){
            scoreList = recordHourRadiusService.calculateDayHourScore(map);
        }else{
            scoreList = recordHourRadiusService.calculateDayScore(map);
        }

		/*int total = 0;
        if(page==null) {              *//*没有传入page,则取全部值*//*
            map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			//total = scoreList.size();
		}*/



        if (map.get("target_id") == null) {
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setTargetName("");
            }
        } else {
        }

        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = scoreList.size();
        }
     //   Collections.sort(scoreList,scoreComparator);
        if (order.equals("asc")) {
            sortStringMethod(scoreList);
        }else if(order.equals("desc")){
            sortDescStringMethod(scoreList);
        }

        int start = (page-1)*limit;
        int end;
        if((page*limit-1)<scoreList.size()){
            end = page*limit-1;
        }else{end = scoreList.size()-1;}

        List<ScoreEntity> newList = new ArrayList<>();
        for(int i=start;i<=end;i++){
            newList.add(i-start,scoreList.get(i));
        }


        PageUtils pageUtil = new PageUtils(newList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * ZTY用于质量评分界面计算分
     */
    @RequestMapping("/qualityList")
    @RequiresPermissions("recordhourping:qualityList")
    public R qualityList(String probedata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EvaluationEntity score;
        if(dateDifferent>5){
            score = recordHourFtpService.calculateHourQualityScore(map);
        }else{
            score = recordHourFtpService.calculateDayQualityScore(map);
        }


        return R.ok().put("score", score);

    }

    /**
     * ZTY用于绘制网络连通性图
     */
    @RequestMapping("/connection")
    @RequiresPermissions("recordhourping:connection")
    public R connectionImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);
            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ScoreEntity> scoreList;
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.connectionDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.connectionHourChart(map);
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * ZTY用于绘制网络层质量图
     */
    @RequestMapping("/quality")
    @RequiresPermissions("recordhourping:quality")
    public R qualityImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        System.out.println(chartdata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ScoreEntity> scoreList;
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.qualityDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.qualityHourChart(map);
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * ZTY用于绘制网页浏览图
     */
    @RequestMapping("/page")
    @RequiresPermissions("recordhourping:page")
    public R pageImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        System.out.println(chartdata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ScoreEntity> scoreList = new ArrayList<>();
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.pageDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.pageHourChart(map);
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * ZTY用于绘制文件下载类图
     */
    @RequestMapping("/download")
    @RequiresPermissions("recordhourping:download")
    public R downloadImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        System.out.println(chartdata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ScoreEntity> scoreList;
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.downloadDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.downloadHourChart(map);
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * ZTY用于绘制在线视频类图
     */
    @RequestMapping("/video")
    @RequiresPermissions("recordhourping:video")
    public R videoImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        System.out.println(chartdata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ScoreEntity> scoreList;
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.videoDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.videoHourChart(map);
        }
        System.out.println(scoreList);
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * ZTY用于绘制游戏类图
     */
    @RequestMapping("/game")
    @RequiresPermissions("recordhourping:game")
    public R gameImage(String chartdata) throws ExecutionException, InterruptedException {
        //查询列表数据
        Map<String, Object> map = new HashMap<>();
        JSONObject chartdata_jsonobject = JSONObject.parseObject(chartdata);
        System.out.println(chartdata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        String dateStr = map.get("ava_start").toString();
        String dateStr2 = map.get("ava_terminal").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int dateDifferent = 0;
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            dateDifferent = recordHourPingService.differentDays(date, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ScoreEntity> scoreList;
        if (dateDifferent > 5) {
            //查询天表
            scoreList = recordHourDhcpService.gameDayChart(map);
        } else {
            //查询小时表
            scoreList = recordHourDhcpService.gameHourChart(map);
        }
        System.out.println(scoreList);
        return R.ok().put("scoreList", scoreList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("recordhourping:info")
    public R info(@PathVariable("id") Integer id) {
        RecordHourPingEntity recordHourPing = recordHourPingService.queryObject(id);

        return R.ok().put("recordHourPing", recordHourPing);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("recordhourping:save")
    public R save(@RequestBody RecordHourPingEntity recordHourPing) {
        recordHourPingService.save(recordHourPing);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("recordhourping:update")
    public R update(@RequestBody RecordHourPingEntity recordHourPing) {
        recordHourPingService.update(recordHourPing);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("recordhourping:delete")
    public R delete(@RequestBody Integer[] ids) {
        recordHourPingService.deleteBatch(ids);

        return R.ok();
    }


}
