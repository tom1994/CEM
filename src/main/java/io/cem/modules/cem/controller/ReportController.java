package io.cem.modules.cem.controller;


import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    public RecordDhcpService recordDhcpService;
    @Autowired
    public RecordDnsService recordDnsService;
    @Autowired
    public RecordFtpService recordFtpService;
    @Autowired
    public RecordGameService recordGameService;
    @Autowired
    public RecordPingService recordPingService;
    @Autowired
    public RecordPppoeService recordPppoeService;
    @Autowired
    public RecordRadiusService recordRadiusService;
    @Autowired
    public RecordSlaService recordSlaService;
    @Autowired
    public RecordTracertService recordTracertService;
    @Autowired
    public RecordWebDownloadService recordWebDownloadService;
    @Autowired
    public RecordWebPageService recordWebPageService;
    @Autowired
    public RecordWebVideoService recordWebVideoService;

    @RequestMapping("/recordping")
    public R list(String probedata, Integer page, Integer limit) throws Exception {
        return R.ok();
    }

}
