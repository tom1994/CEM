package io.cem.modules.cem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;

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
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
@RestController
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
	
	/**
	 * ZTY用于质量排名界面计算分
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourping:list")
	public R list(String probedata, Integer page, Integer limit) throws Exception{
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		System.out.println(probedata_jsonobject);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int service = Integer.parseInt(map.get("service").toString());
		System.out.println(service);
		String dateStr = map.get("ava_start").toString();
		String dateStr2 = map.get("ava_terminal").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		int dateDifferent = 0;
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date,date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ScoreEntity> scoreList = new ArrayList<>();
		//查询天表
		if (dateDifferent>5){
			if (service == 0){}
			else if (service==1){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
				scoreList = recordHourPingService.calculateService1(pingList, tracertList);
			}
			else if (service==2){}
			else if (service==3){}
			else if (service==4){}
			else if (service==5){}
			else if (service==6){}
			else {}
		}
		//查询小时表
        else {
			if (service == 0) {
				List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
				List<ScoreEntity> connection = recordHourPingService.calculateService1(pingList, tracertList);

				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
				List<ScoreEntity> quality = recordHourSlaService.calculateService2(slaList, dnsList, dhcpList, pppoeList, radiusList);

				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);

				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
				List<ScoreEntity> download = recordHourWebDownloadService.calculateService4(webDownloadList, ftpList);

				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);

				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);

				scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

			} else if (service == 1) {
				List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
				scoreList = recordHourPingService.calculateService1(pingList, tracertList);
			} else if (service == 2) {
				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
				scoreList = recordHourSlaService.calculateService2(slaList, dnsList, dhcpList, pppoeList, radiusList);
			} else if (service == 3) {
				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
				scoreList = recordHourWebPageService.calculateService3(webPageList);
			} else if (service == 4) {
				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
				scoreList = recordHourWebDownloadService.calculateService4(webDownloadList, ftpList);
			} else if (service == 5) {
				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
				scoreList = recordHourWebVideoService.calculateService5(videoList);
			} else if (service == 6) {
				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
				scoreList = recordHourGameService.calculateService6(gameList);
			} else {
			}
		}

		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
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
	public R info(@PathVariable("id") Integer id){
		RecordHourPingEntity recordHourPing = recordHourPingService.queryObject(id);
		
		return R.ok().put("recordHourPing", recordHourPing);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordhourping:save")
	public R save(@RequestBody RecordHourPingEntity recordHourPing){
		recordHourPingService.save(recordHourPing);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordhourping:update")
	public R update(@RequestBody RecordHourPingEntity recordHourPing){
		recordHourPingService.update(recordHourPing);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordhourping:delete")
	public R delete(@RequestBody Integer[] ids){
		recordHourPingService.deleteBatch(ids);
		
		return R.ok();
	}


}
