package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.RecordHourDhcpService;
import io.cem.modules.cem.service.RecordHourPingService;
import io.cem.modules.cem.service.RecordHourRadiusService;
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
    private RecordHourDhcpService recordHourDhcpService;
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;

    /**
     * 探针排名
     * @param probedata
     * @param page
     * @param limit
     * @param order
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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

        if (map.get("target_id") == null) {
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setTargetName("");
                scoreList.get(i).setTargetId(-1);
            }
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
     * 质量评分-网络连通性
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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

        if(dateDifferent>5){
            scoreList = recordHourDhcpService.connectionDayChart(map);
        }else if(dateDifferent<=5 && dateDifferent>=2){
            scoreList = recordHourDhcpService.connectionHourChart(map);
        } else{
            scoreList = recordHourDhcpService.connectionDayHourChart(map);
        }
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * 质量评分-网络层质量
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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

        if(dateDifferent>5){
            scoreList = recordHourDhcpService.qualityDayChart(map);
        }else if(dateDifferent<=5 && dateDifferent>=2){
            scoreList = recordHourDhcpService.qualityHourChart(map);
        } else{
            scoreList = recordHourDhcpService.qualityDayHourChart(map);
        }
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * 质量评分-网页浏览
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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
            scoreList = recordHourDhcpService.pageDayChart(map);
        } else {
            scoreList = recordHourDhcpService.pageHourChart(map);
        }
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * 质量评分-文件下载
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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
        if(dateDifferent>5){
            scoreList = recordHourDhcpService.downloadDayChart(map);
        }else if(dateDifferent<=5 && dateDifferent>=2){
            scoreList = recordHourDhcpService.downloadHourChart(map);
        } else{
            scoreList = recordHourDhcpService.downloadDayHourChart(map);
        }
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * 质量评分-在线视频
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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
            scoreList = recordHourDhcpService.videoDayChart(map);
        } else {
            scoreList = recordHourDhcpService.videoHourChart(map);
        }
        System.out.println(scoreList);
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
        return R.ok().put("scoreList", scoreList);
    }

    /**
     * 质量评分-网络游戏
     * @param chartdata
     * @return R
     * @throws ExecutionException
     * @throws InterruptedException
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
            scoreList = recordHourDhcpService.gameDayChart(map);
        } else {
            scoreList = recordHourDhcpService.gameHourChart(map);
        }
        System.out.println(scoreList);
        if(map.get("probe_id")==null){
            for(int i=0;i<scoreList.size();i++){
                scoreList.get(i).setProbeName("所有探针");
            }
        }
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
