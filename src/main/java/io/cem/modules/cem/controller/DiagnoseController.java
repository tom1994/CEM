package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("diagnose")
public class DiagnoseController {
    @Autowired
    private ProbeService probeService;
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

    /**
     * Miao 周期故障诊断分数计算
     */
    @RequestMapping("/list")
    @RequiresPermissions("recordhourping:list")
    public R list(String probedata, Integer page, Integer limit) throws Exception {
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

        List<ScoreEntity> scoreList = new ArrayList<>();
        List<ProbeEntity> probeList = new ArrayList<>();
        if (map.get("probe_id") != null && !map.get("probe_id").equals("")) {
            int probeId = Integer.parseInt(map.get("probe_id").toString());
            probeList = probeService.queryProbeByLayer(probeId);
            int start=0;
            for (int i = 0; i < probeList.size(); i++) {
                map.put("probe_id", probeList.get(i).getId());
                List<ScoreEntity> list= new ArrayList<>();
                if (dateDifferent > 5) {
                    list=recordHourRadiusService.diagnoseDay(map);
                } else if (dateDifferent>=3) {
                    list=recordHourRadiusService.diagnoseDayHour(map);
                } else {
                    list=recordHourRadiusService.diagnoseHour(map);
                }
                scoreList.addAll(start,list);
                start=list.size();
                System.out.println(scoreList);
            }
        } else if(map.get("county_id") != null && !map.get("county_id").equals("")) {
            if (dateDifferent > 5) {
                scoreList=recordHourRadiusService.diagnoseDay(map);
            } else if (dateDifferent>=3) {
                scoreList= recordHourRadiusService.diagnoseDayHour(map);
            } else {
                scoreList=recordHourRadiusService.diagnoseHour(map);
            }

        }else if(map.get("city_id") != null && !map.get("city_id").equals("")){
            if (dateDifferent > 5) {
                scoreList=recordHourRadiusService.diagnoseDay(map);
            } else if (dateDifferent>=3) {
                scoreList= recordHourRadiusService.diagnoseDayHour(map);
            } else {
                scoreList=recordHourRadiusService.diagnoseHour(map);
            }
        } else{
            if (dateDifferent > 5) {
                scoreList=recordHourRadiusService.diagnoseDay(map);
            } else if (dateDifferent>=3) {
                scoreList= recordHourRadiusService.diagnoseDayHour(map);
            } else {
                scoreList=recordHourRadiusService.diagnoseHour(map);
            }
        }



    //    recordHourDhcpService.combination(map,scoreList);

        if (map.get("target_id") == null) {
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setTargetName("");
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
        //List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
        PageUtils pageUtil = new PageUtils(scoreList, total, limit, page);
        return R.ok().put("page", pageUtil);
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

}
