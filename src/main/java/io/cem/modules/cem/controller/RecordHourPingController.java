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
			if (service == 0){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
				List<ScoreEntity> connection = recordHourPingService.calculateService1(pingList, tracertList);

				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
				List<ScoreEntity> quality = recordHourSlaService.calculateService2(slaList, dnsList, dhcpList, pppoeList, radiusList);

				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);

				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
				List<ScoreEntity> download = recordHourWebDownloadService.calculateService4(webDownloadList, ftpList);

				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);

				List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);

				scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);
			}
			else if (service==1){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
				scoreList = recordHourPingService.calculateService1(pingList, tracertList);
			}
			else if (service==2){
				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
				scoreList = recordHourSlaService.calculateService2(slaList, dnsList, dhcpList, pppoeList, radiusList);
			}
			else if (service==3){
				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
				scoreList = recordHourWebPageService.calculateService3(webPageList);
			}
			else if (service==4){
				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
				scoreList = recordHourWebDownloadService.calculateService4(webDownloadList, ftpList);
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
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date,date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		EvaluationEntity score = new EvaluationEntity();
		//查询天表
		if (dateDifferent>5){
			//网络连通性业务
			List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
			List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
			List<ScoreEntity> connectionList = recordHourPingService.calculateServiceDate1(pingList, tracertList);
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
			List<ScoreEntity> qualityList = recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList);
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


			//文件下载类业务
			List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
			List<ScoreEntity> pageList = recordHourWebPageService.calculateService3(webPageList);
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

			//网页浏览类业务
			List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
			List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
			List<ScoreEntity> downloadList = recordHourWebDownloadService.calculateServiceDate4(webDownloadList, ftpList);
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
			List<ScoreEntity> connectionList = recordHourPingService.calculateServiceDate1(pingList, tracertList);
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
			List<ScoreEntity> qualityList = recordHourSlaService.calculateServiceDate2(slaList, dnsList, dhcpList, pppoeList, radiusList);
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
			List<ScoreEntity> downloadList = recordHourWebDownloadService.calculateServiceDate4(webDownloadList, ftpList);
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
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date,date2);
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
