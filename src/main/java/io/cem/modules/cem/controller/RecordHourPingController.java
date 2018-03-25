package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
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
	@Autowired
	private ProbeService probeService;
	
	/**
	 * ZTY用于质量排名界面计算分
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourping:list")
	public R list(String probedata, Integer page, Integer limit){
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
		int dateDifferent = 0;

		try {
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date, date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("map!!!!!"+map);

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

		List<ScoreEntity> scoreList = new ArrayList<>();
		//查询天表
		if (dateDifferent>5){
			if (service == 0){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				List<ScoreEntity> connection = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);

				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				List<ScoreEntity> quality = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);

				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);

				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				List<ScoreEntity> download = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);

				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);

				List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);
				scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

			}
			else if (service==1){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);

				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);

				scoreList = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			}
			else if (service==2){
				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);

				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);

				scoreList = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			}
			else if (service==3){
				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
				scoreList = recordHourWebPageService.calculateService3(webPageList);
			}
			else if (service==4){
				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				scoreList = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
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
			if (service == 0) {
				List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				List<ScoreEntity> connection = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);

				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				List<ScoreEntity> quality = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);

				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				List<ScoreEntity> download = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);

				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebRankList(map);
				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);

				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoRankList(map);
				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);

				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameRankList(map);
				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);

				scoreList = recordHourTracertService.calculateService0(connection,quality,broswer,download,video,game);

			} else if (service == 1) {
				List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);

				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);

				scoreList = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
				//total = recordHourPingService.pingListTotal(map);
			} else if (service == 2) {
				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);

				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				scoreList = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			} else if (service == 4) {
				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);

				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				scoreList = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
			} else if (service == 3) {
				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebRankList(map);
				scoreList = recordHourWebPageService.calculateService3(webPageList);
			} else if (service == 5) {
				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoRankList(map);
				scoreList = recordHourWebVideoService.calculateService5(videoList);
			} else if (service == 6) {
				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameRankList(map);
				scoreList = recordHourGameService.calculateService6(gameList);
			} else {
			}
		}

		if(map.get("target_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setTargetName("");
			}
		}else{}

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
		/*if (service == 1) {
			total = recordHourPingService.pingListTotal(map);
		}*/
		PageUtils pageUtil = new PageUtils(scoreList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	/**
	 * ZTY用于质量评分界面计算分
	 */
	@RequestMapping("/qualityList")
	@RequiresPermissions("recordhourping:qualityList")
	public R qualityList(String probedata){
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


		EvaluationEntity score = new EvaluationEntity();
		//查询天表
		if (dateDifferent>5){
			//网络连通性业务
			List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
			List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
			List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
			List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
			List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
			List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
			List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
			List<ScoreEntity> connectionList = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				connectionList = recordHourPingService.dateChart1(connectionList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				connectionList = recordHourPingService.cityChart1(connectionList);
			}else if(map.get("probe_id")==null){
				connectionList = recordHourPingService.probeChart1(connectionList);
			}else{}
			if(connectionList.size()!=0) {
				double maxConnection = connectionList.get(0).getScore();
				double averageConnection = 0;
				double sumConnection = 0;
				double minConnection = connectionList.get(0).getScore();
				for (int i = 1; i < connectionList.size(); i++) {
					if (connectionList.get(i).getScore() > maxConnection) {
						maxConnection = connectionList.get(i).getScore();
					} else {
					}
				}
				score.setConnectionMax(maxConnection);
				for (int i = 1; i < connectionList.size(); i++) {
					if (connectionList.get(i).getScore() < minConnection) {
						minConnection = connectionList.get(i).getScore();
					} else {
					}
				}
				score.setConnectionMin(minConnection);

				for (int i = 0; i < connectionList.size(); i++) {
					sumConnection += connectionList.get(i).getScore();
				}
				averageConnection = sumConnection / connectionList.size();
				score.setConnectionAverage(averageConnection);
			}else{
				score.setConnectionMax(0.0);
				score.setConnectionAverage(0.0);
				score.setConnectionMin(0.0);
			}

			//网络层质量业务
			List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
			List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
			List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
			List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
			List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
			List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
			List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
			List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
			List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
			List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
			List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
			List<ScoreEntity> qualityList = recordHourSlaService.calculateDate2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				qualityList = recordHourPingService.dateChart1(qualityList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				qualityList = recordHourPingService.cityChart1(qualityList);
			}else if(map.get("probe_id")==null){
				qualityList = recordHourPingService.probeChart1(qualityList);
			}else{}
			if(qualityList.size()!=0) {
				double maxQuality = qualityList.get(0).getScore();
				double averageQuality = 0;
				double sumQuality = 0;
				double minQuality = qualityList.get(0).getScore();
				for (int i = 1; i < qualityList.size(); i++) {
					if (qualityList.get(i).getScore() > maxQuality) {
						maxQuality = qualityList.get(i).getScore();
					} else {
					}
				}
				score.setQualityMax(maxQuality);
				for (int i = 1; i < qualityList.size(); i++) {
					if (qualityList.get(i).getScore() < minQuality) {
						minQuality = qualityList.get(i).getScore();
					} else {
					}
				}
				score.setQualityMin(minQuality);

				for (int i = 0; i < qualityList.size(); i++) {
					sumQuality += qualityList.get(i).getScore();
				}
				averageQuality = sumQuality / qualityList.size();
				score.setQualityAverage(averageQuality);
			}else{
				score.setQualityMax(0.0);
				score.setQualityAverage(0.0);
				score.setQualityMin(0.0);
			}


			//网页浏览类业务
			List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
			List<ScoreEntity> pageList = recordHourWebPageService.calculateService3(webPageList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				pageList = recordHourPingService.dateChart1(pageList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				pageList = recordHourPingService.cityChart1(pageList);
			}else if(map.get("probe_id")==null){
				pageList = recordHourPingService.probeChart1(pageList);
			}else{}
			if (pageList.size()!=0) {
				double maxPage = pageList.get(0).getScore();
				double averagePage = 0;
				double sumPage = 0;
				double minPage = pageList.get(0).getScore();
				for (int i = 1; i < pageList.size(); i++) {
					if (pageList.get(i).getScore() > maxPage) {
						maxPage = pageList.get(i).getScore();
					} else {
					}
				}
				score.setPageMax(maxPage);
				for (int i = 1; i < pageList.size(); i++) {
					if (pageList.get(i).getScore() < minPage) {
						minPage = pageList.get(i).getScore();
					} else {
					}
				}
				score.setPageMin(minPage);

				for (int i = 0; i < pageList.size(); i++) {
					sumPage += pageList.get(i).getScore();
				}
				averagePage = sumPage / pageList.size();
				score.setPageAverage(averagePage);
			} else {
				score.setPageMax(0.0);
				score.setPageAverage(0.0);
				score.setPageMin(0.0);
			}

			//文件下载业务
			List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
			List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
			List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
			List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
			List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
			List<ScoreEntity> downloadList = recordHourWebDownloadService.calculateDate4(webDownload,ftpDownload,ftpUpload);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				downloadList = recordHourPingService.dateChart1(downloadList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				downloadList = recordHourPingService.cityChart1(downloadList);
			}else if(map.get("probe_id")==null){
				downloadList = recordHourPingService.probeChart1(downloadList);
			}else{}
			if (downloadList.size()!=0) {
				double maxDownload = downloadList.get(0).getScore();
				double averageDownload = 0;
				double sumDownload = 0;
				double minDownload = downloadList.get(0).getScore();
				for (int i = 1; i < downloadList.size(); i++) {
					if (downloadList.get(i).getScore() > maxDownload) {
						maxDownload = downloadList.get(i).getScore();
					} else {
					}
				}
				score.setDownloadMax(maxDownload);
				for (int i = 1; i < downloadList.size(); i++) {
					if (downloadList.get(i).getScore() < minDownload) {
						minDownload = downloadList.get(i).getScore();
					} else {
					}
				}
				score.setDownloadMin(minDownload);

				for (int i = 0; i < downloadList.size(); i++) {
					sumDownload += downloadList.get(i).getScore();
				}
				averageDownload = sumDownload / downloadList.size();
				score.setDownloadAverage(averageDownload);
			} else {
				score.setDownloadMax(0.0);
				score.setDownloadAverage(0.0);
				score.setDownloadMin(0.0);
			}

			//在线视频业务
			List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
			List<ScoreEntity> videoServiceList = recordHourWebVideoService.calculateService5(videoList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				videoServiceList = recordHourPingService.dateChart1(videoServiceList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				videoServiceList = recordHourPingService.cityChart1(videoServiceList);
			}else if(map.get("probe_id")==null){
				videoServiceList = recordHourPingService.probeChart1(videoServiceList);
			}else{}
			if (videoServiceList.size()!=0) {
				double maxVideo = videoServiceList.get(0).getScore();
				double averageVideo = 0;
				double sumVideo = 0;
				double minVideo = videoServiceList.get(0).getScore();
				for (int i = 1; i < videoServiceList.size(); i++) {
					if (videoServiceList.get(i).getScore() > maxVideo) {
						maxVideo = videoServiceList.get(i).getScore();
					} else {
					}
				}
				score.setVideoMax(maxVideo);
				for (int i = 1; i < videoServiceList.size(); i++) {
					if (videoServiceList.get(i).getScore() < minVideo) {
						minVideo = videoServiceList.get(i).getScore();
					} else {
					}
				}
				score.setVideoMin(minVideo);

				for (int i = 0; i < videoServiceList.size(); i++) {
					sumVideo += videoServiceList.get(i).getScore();
				}
				averageVideo = sumVideo / videoServiceList.size();
				score.setVideoAverage(averageVideo);
			} else {
				score.setVideoMax(0.0);
				score.setVideoAverage(0.0);
				score.setVideoMin(0.0);
			}

			//网络游戏业务
			List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
			List<ScoreEntity> gameServiceList = recordHourGameService.calculateService6(gameList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				gameServiceList = recordHourPingService.dateChart1(gameServiceList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				gameServiceList = recordHourPingService.cityChart1(gameServiceList);
			}else if(map.get("probe_id")==null){
				gameServiceList = recordHourPingService.probeChart1(gameServiceList);
			}else{}
			if (gameServiceList.size()!=0) {
				double maxGame = gameServiceList.get(0).getScore();
				double averageGame = 0;
				double sumGame = 0;
				double minGame = gameServiceList.get(0).getScore();
				for (int i = 1; i < gameServiceList.size(); i++) {
					if (gameServiceList.get(i).getScore() > maxGame) {
						maxGame = gameServiceList.get(i).getScore();
					} else {
					}
				}
				score.setGameMax(maxGame);
				for (int i = 1; i < gameServiceList.size(); i++) {
					if (gameServiceList.get(i).getScore() < minGame) {
						minGame = gameServiceList.get(i).getScore();
					} else {
					}
				}
				score.setGameMin(minGame);

				for (int i = 0; i < gameServiceList.size(); i++) {
					sumGame += gameServiceList.get(i).getScore();
				}
				averageGame = sumGame / gameServiceList.size();
				score.setGameAverage(averageGame);
			} else {
				score.setGameMax(0.0);
				score.setGameAverage(0.0);
				score.setGameMin(0.0);
			}

		}
		//查询小时表
		else {
			//网络连通性业务
			List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
			List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
			List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
			List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
			List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
			List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
			List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
			List<ScoreEntity> connectionList = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				connectionList = recordHourPingService.dateChart1(connectionList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				connectionList = recordHourPingService.cityChart1(connectionList);
			}else if(map.get("probe_id")==null){
				connectionList = recordHourPingService.probeChart1(connectionList);
			}else{}
			if (connectionList.size()!=0) {
				double maxConnection = connectionList.get(0).getScore();
				double averageConnection = 0;
				double sumConnection = 0;
				double minConnection = connectionList.get(0).getScore();
				for (int i = 1; i < connectionList.size(); i++) {
					if (connectionList.get(i).getScore() > maxConnection) {
						maxConnection = connectionList.get(i).getScore();
					} else {
					}
				}
				score.setConnectionMax(maxConnection);
				for (int i = 1; i < connectionList.size(); i++) {
					if (connectionList.get(i).getScore() < minConnection) {
						minConnection = connectionList.get(i).getScore();
					} else {
					}
				}
				score.setConnectionMin(minConnection);

				for (int i = 0; i < connectionList.size(); i++) {
					sumConnection += connectionList.get(i).getScore();
				}
				averageConnection = sumConnection / connectionList.size();
				score.setConnectionAverage(averageConnection);
			} else {
				score.setConnectionMax(0.0);
				score.setConnectionAverage(0.0);
				score.setConnectionMin(0.0);
			}

			//网络层质量业务
			List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
			List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
			List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
			List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
			List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
			List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
			List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
			List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
			List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
			List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
			List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
			List<ScoreEntity> qualityList = recordHourSlaService.calculateDate2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				qualityList = recordHourPingService.dateChart1(qualityList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				qualityList = recordHourPingService.cityChart1(qualityList);
			}else if(map.get("probe_id")==null){
				qualityList = recordHourPingService.probeChart1(qualityList);
			}else{}
			if (qualityList.size()!=0) {
				double maxQuality = qualityList.get(0).getScore();
				double averageQuality = 0;
				double sumQuality = 0;
				double minQuality = qualityList.get(0).getScore();
				for (int i = 1; i < qualityList.size(); i++) {
					if (qualityList.get(i).getScore() > maxQuality) {
						maxQuality = qualityList.get(i).getScore();
					} else {
					}
				}
				score.setQualityMax(maxQuality);
				for (int i = 1; i < qualityList.size(); i++) {
					if (qualityList.get(i).getScore() < minQuality) {
						minQuality = qualityList.get(i).getScore();
					} else {
					}
				}
				score.setQualityMin(minQuality);

				for (int i = 0; i < qualityList.size(); i++) {
					sumQuality += qualityList.get(i).getScore();
				}
				averageQuality = sumQuality / qualityList.size();
				score.setQualityAverage(averageQuality);
			} else {
				score.setQualityMax(0.0);
				score.setQualityAverage(0.0);
				score.setQualityMin(0.0);
			}

			//网页浏览类业务
			List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
			List<ScoreEntity> pageList = recordHourWebPageService.calculateService3(webPageList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				pageList = recordHourPingService.dateChart1(pageList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				pageList = recordHourPingService.cityChart1(pageList);
			}else if(map.get("probe_id")==null){
				pageList = recordHourPingService.probeChart1(pageList);
			}else{}
			if (pageList.size()!=0) {
				double maxPage = pageList.get(0).getScore();
				double averagePage = 0;
				double sumPage = 0;
				double minPage = pageList.get(0).getScore();
				for (int i = 1; i < pageList.size(); i++) {
					if (pageList.get(i).getScore() > maxPage) {
						maxPage = pageList.get(i).getScore();
					} else {
					}
				}
				score.setPageMax(maxPage);
				for (int i = 1; i < pageList.size(); i++) {
					if (pageList.get(i).getScore() < minPage) {
						minPage = pageList.get(i).getScore();
					} else {
					}
				}
				score.setPageMin(minPage);

				for (int i = 0; i < pageList.size(); i++) {
					sumPage += pageList.get(i).getScore();
				}
				averagePage = sumPage / pageList.size();
				score.setPageAverage(averagePage);
			} else {
				score.setPageMax(0.0);
				score.setPageAverage(0.0);
				score.setPageMin(0.0);
			}


			//文件下载类业务
			List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
			List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
			List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
			List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
			List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
			List<ScoreEntity> downloadList = recordHourWebDownloadService.calculateDate4(webDownload,ftpDownload,ftpUpload);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				downloadList = recordHourPingService.dateChart1(downloadList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				downloadList = recordHourPingService.cityChart1(downloadList);
			}else if(map.get("probe_id")==null){
				downloadList = recordHourPingService.probeChart1(downloadList);
			}else{}
			if (downloadList.size()!=0) {
				double maxDownload = downloadList.get(0).getScore();
				double averageDownload = 0;
				double sumDownload = 0;
				double minDownload = downloadList.get(0).getScore();
				for (int i = 1; i < downloadList.size(); i++) {
					if (downloadList.get(i).getScore() > maxDownload) {
						maxDownload = downloadList.get(i).getScore();
					} else {
					}
				}
				score.setDownloadMax(maxDownload);
				for (int i = 1; i < downloadList.size(); i++) {
					if (downloadList.get(i).getScore() < minDownload) {
						minDownload = downloadList.get(i).getScore();
					} else {
					}
				}
				score.setDownloadMin(minDownload);

				for (int i = 0; i < downloadList.size(); i++) {
					sumDownload += downloadList.get(i).getScore();
				}
				averageDownload = sumDownload / downloadList.size();
				score.setDownloadAverage(averageDownload);
			} else {
				score.setDownloadMax(0.0);
				score.setDownloadAverage(0.0);
				score.setDownloadMin(0.0);
			}

			//在线视频业务
			List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
			List<ScoreEntity> videoServiceList = recordHourWebVideoService.calculateService5(videoList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				videoServiceList = recordHourPingService.dateChart1(videoServiceList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				videoServiceList = recordHourPingService.cityChart1(videoServiceList);
			}else if(map.get("probe_id")==null){
				videoServiceList = recordHourPingService.probeChart1(videoServiceList);
			}else{}
			if (videoServiceList.size()!=0) {
				double maxVideo = videoServiceList.get(0).getScore();
				double averageVideo = 0;
				double sumVideo = 0;
				double minVideo = videoServiceList.get(0).getScore();
				for (int i = 1; i < videoServiceList.size(); i++) {
					if (videoServiceList.get(i).getScore() > maxVideo) {
						maxVideo = videoServiceList.get(i).getScore();
					} else {
					}
				}
				score.setVideoMax(maxVideo);
				for (int i = 1; i < videoServiceList.size(); i++) {
					if (videoServiceList.get(i).getScore() < minVideo) {
						minVideo = videoServiceList.get(i).getScore();
					} else {
					}
				}
				score.setVideoMin(minVideo);

				for (int i = 0; i < videoServiceList.size(); i++) {
					sumVideo += videoServiceList.get(i).getScore();
				}
				averageVideo = sumVideo / videoServiceList.size();
				score.setVideoAverage(averageVideo);
			} else {
				score.setVideoMax(0.0);
				score.setVideoAverage(0.0);
				score.setVideoMin(0.0);
			}

			//网络游戏业务
			List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
			List<ScoreEntity> gameServiceList = recordHourGameService.calculateService6(gameList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				gameServiceList = recordHourPingService.dateChart1(gameServiceList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				gameServiceList = recordHourPingService.cityChart1(gameServiceList);
			}else if(map.get("probe_id")==null){
				gameServiceList = recordHourPingService.probeChart1(gameServiceList);
			}else{}
			if (gameServiceList.size()!=0) {
				double maxGame = gameServiceList.get(0).getScore();
				double averageGame = 0;
				double sumGame = 0;
				double minGame = gameServiceList.get(0).getScore();
				for (int i = 1; i < gameServiceList.size(); i++) {
					if (gameServiceList.get(i).getScore() > maxGame) {
						maxGame = gameServiceList.get(i).getScore();
					} else {
					}
				}
				score.setGameMax(maxGame);
				for (int i = 1; i < gameServiceList.size(); i++) {
					if (gameServiceList.get(i).getScore() < minGame) {
						minGame = gameServiceList.get(i).getScore();
					} else {
					}
				}
				score.setGameMin(minGame);

				for (int i = 0; i < gameServiceList.size(); i++) {
					sumGame += gameServiceList.get(i).getScore();
				}
				averageGame = sumGame / gameServiceList.size();
				score.setGameAverage(averageGame);
			} else {
				score.setGameMax(0.0);
				score.setGameAverage(0.0);
				score.setGameMin(0.0);
			}
		}

		return R.ok().put("score", score);

	}

	/**
	 * ZTY用于显示ping界面的详情
	 */
	@RequestMapping("/pingdetails")
	@RequiresPermissions("recordhourping:pingdetails")
	public R pingDetailsList(String probedata, Integer page, Integer limit){
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


		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = recordHourPingService.queryTotal(map);
		}
		List<RecordHourPingEntity> pingList;
		//查询天表
		if (dateDifferent > 5) {
			pingList = recordHourPingService.queryDayList(map);
		}
		//查询小时表
		else {
			pingList = recordHourPingService.queryList(map);
		}
		System.out.println(pingList);
		PageUtils pageUtil = new PageUtils(pingList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * ZTY用于绘制网络连通性图
	 */
	@RequestMapping("/connection")
	@RequiresPermissions("recordhourping:connection")
	public R connectionImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
			List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
			List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
			List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
			List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
			List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
			List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
			scoreList = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourPingEntity> pingList = recordHourPingService.queryPingList(map);
			List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracertList(map);
			List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
			List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
			List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
			List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
			List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
			scoreList = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		System.out.println(scoreList);
		return R.ok().put("scoreList", scoreList);
	}

	/**
	 * ZTY用于绘制网络层质量图
	 */
	@RequestMapping("/quality")
	@RequiresPermissions("recordhourping:quality")
	public R qualityImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
			List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
			List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
			List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
			List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
			List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
			List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
			List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
			List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
			List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
			List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
			scoreList = recordHourSlaService.calculateDate2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
			List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
			List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
			List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
			List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
			List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
			List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
			List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
			List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
			List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
			List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
			scoreList = recordHourSlaService.calculateDate2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList= recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		System.out.println(scoreList);
		return R.ok().put("scoreList", scoreList);
	}

	/**
	 * ZTY用于绘制网页浏览图
	 */
	@RequestMapping("/page")
	@RequiresPermissions("recordhourping:page")
	public R pageImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
			scoreList = recordHourWebPageService.calculateService3(webPageList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
			scoreList = recordHourWebPageService.calculateService3(webPageList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		return R.ok().put("scoreList", scoreList);
	}

	/**
	 * ZTY用于绘制文件下载类图
	 */
	@RequestMapping("/download")
	@RequiresPermissions("recordhourping:download")
	public R downloadImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
			List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
			List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
			List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
			List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
			scoreList = recordHourWebDownloadService.calculateDate4(webDownload,ftpDownload,ftpUpload);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
			List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
			List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
			List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
			List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
			scoreList = recordHourWebDownloadService.calculateDate4(webDownload,ftpDownload,ftpUpload);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		return R.ok().put("scoreList", scoreList);
	}

	/**
	 * ZTY用于绘制在线视频类图
	 */
	@RequestMapping("/video")
	@RequiresPermissions("recordhourping:video")
	public R videoImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
			scoreList = recordHourWebVideoService.calculateService5(videoList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
			scoreList = recordHourWebVideoService.calculateService5(videoList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		System.out.println(scoreList);
		return R.ok().put("scoreList", scoreList);
	}

	/**
	 * ZTY用于绘制游戏类图
	 */
	@RequestMapping("/game")
	@RequiresPermissions("recordhourping:game")
	public R gameImage(String chartdata){
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
		if (dateDifferent > 5) {
			List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
			scoreList = recordHourGameService.calculateService6(gameList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		//查询小时表
		else {
			List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
			scoreList = recordHourGameService.calculateService6(gameList);
			if(map.get("city_Id")==null&&map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.dateChart1(scoreList);
			}else if(map.get("county_id")==null&&map.get("probe_id")==null){
				scoreList = recordHourPingService.cityChart1(scoreList);
			}else if(map.get("probe_id")==null){
				scoreList = recordHourPingService.probeChart1(scoreList);
			}else{}
		}
		System.out.println(scoreList);
		return R.ok().put("scoreList", scoreList);
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
