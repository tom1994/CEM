package io.cem.modules.cem.controller;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.cem.common.utils.PageUtils;
import io.cem.common.utils.excel.ExcelUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
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
        System.out.println(probedata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        List<ScoreEntity> scoreList = new ArrayList<>();
        int service = Integer.parseInt(map.get("service").toString());
        if (map.get("probe_id") != null) {
            int probeId = Integer.parseInt(map.get("probe_id").toString());
            List<ProbeEntity> probeList = probeService.queryProbeByLayer(probeId);

            String dateStr = map.get("ava_start").toString();
            String dateStr2 = map.get("ava_terminal").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            int dateDifferent = 0;
            try {
                Date date2 = format.parse(dateStr2);
                Date date = format.parse(dateStr);
                dateDifferent = recordHourPingService.differentDays(date, date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < probeList.size(); i++) {
                map.put("probe_id", probeList.get(i).getId());
                if (dateDifferent > 5) {
                    if (service == 1) {
                        List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
                        List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
                        scoreList.addAll(recordHourPingService.calculateServiceDate1(pingList, tracertList));
                    } else if (service == 2) {
                        List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
                        List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
                        List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
                        List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
                        List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
                        scoreList.addAll(recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList));
                    } else if (service == 3) {
                        List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
                        scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
                    } else if (service == 4) {
                        List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
                        List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
                        scoreList.addAll(recordHourWebDownloadService.calculateService4(webDownloadList, ftpList));
                    } else if (service == 5) {
                        List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
                        scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
                    } else if (service == 6) {
                        List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
                        scoreList.addAll(recordHourGameService.calculateService6(gameList));
                    } else {
                    }
                }
                //查询小时表
                else {
                    if (service == 1) {
                        List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
                        List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
                        scoreList.addAll(recordHourPingService.calculateServiceDate1(pingList, tracertList));
                    } else if (service == 2) {
                        List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
                        List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
                        List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
                        List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
                        List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
                        scoreList.addAll(recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList));
                    } else if (service == 3) {
                        List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
                        scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
                    } else if (service == 4) {
                        List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
                        List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
                        scoreList.addAll(recordHourWebDownloadService.calculateService4(webDownloadList, ftpList));
                    } else if (service == 5) {
                        List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
                        scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
                    } else if (service == 6) {
                        List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
                        scoreList.addAll(recordHourGameService.calculateService6(gameList));
                    } else {
                    }
                }
            }
        } else {
            String dateStr = map.get("ava_start").toString();
            String dateStr2 = map.get("ava_terminal").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            int dateDifferent = 0;
            try {
                Date date2 = format.parse(dateStr2);
                Date date = format.parse(dateStr);
                dateDifferent = recordHourPingService.differentDays(date, date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateDifferent>5){
                if (service==1){
                    List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
                    List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
                    scoreList = recordHourPingService.calculateServiceDate1(pingList, tracertList);
                }
                else if (service==2){
                    List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
                    List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
                    List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
                    List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
                    List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
                    scoreList = recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList);
                }
                else if (service==3){
                    List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
                    scoreList = recordHourWebPageService.calculateService3(webPageList);
                }
                else if (service==4){
                    List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
                    List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
                    scoreList = recordHourWebDownloadService.calculateServiceDate4(webDownloadList, ftpList);
                }
                else if (service==5){
                    List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
                    scoreList = recordHourWebVideoService.calculateService5(videoList);
                }
                else if (service==6){
                    List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
                    scoreList = recordHourGameService.calculateService6(gameList);
                }
                else {}
            }
            //查询小时表
            else {
                if (service == 1) {
                    List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
                    List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
                    scoreList = recordHourPingService.calculateServiceDate1(pingList, tracertList);
                } else if (service == 2) {
                    List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
                    List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
                    List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
                    List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
                    List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
                    scoreList = recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList);
                } else if (service == 3) {
                    List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
                    scoreList = recordHourWebPageService.calculateService3(webPageList);
                } else if (service == 4) {
                    List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
                    List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
                    scoreList = recordHourWebDownloadService.calculateServiceDate4(webDownloadList, ftpList);
                } else if (service == 5) {
                    List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
                    scoreList = recordHourWebVideoService.calculateService5(videoList);
                } else if (service == 6) {
                    List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
                    scoreList = recordHourGameService.calculateService6(gameList);
                } else {
                }
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
