//package io.cem.modules.cem.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import io.cem.common.exception.RRException;
//import io.cem.common.utils.JSONUtils;
//import io.cem.common.utils.PageUtils;
//import io.cem.common.utils.R;
//import io.cem.modules.cem.entity.*;
//import io.cem.modules.cem.service.*;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
//
///**
// */
//@RestController
//@RequestMapping("recordhourtracert")
//public class RecordHourTracertController {
//	@Autowired
//	private RecordHourPingService recordHourPingService;
//	@Autowired
//	private RecordHourTracertService recordHourTracertService;
//	@Autowired
//	private RecordHourSlaService recordHourSlaService;
//	@Autowired
//	private RecordHourDnsService recordHourDnsService;
//	@Autowired
//	private RecordHourDhcpService recordHourDhcpService;
//	@Autowired
//	private RecordHourPppoeService recordHourPppoeService;
//	@Autowired
//	private RecordHourRadiusService recordHourRadiusService;
//	@Autowired
//	private RecordHourWebPageService recordHourWebPageService;
//	@Autowired
//	private RecordHourWebDownloadService recordHourWebDownloadService;
//	@Autowired
//	private RecordHourFtpService recordHourFtpService;
//	@Autowired
//	private RecordHourWebVideoService recordHourWebVideoService;
//	@Autowired
//	private RecordHourGameService recordHourGameService;
//
//	/**
//	 * ZTY用于区域排名
//	 */
//	@RequestMapping("/list")
//	@RequiresPermissions("recordhourtracert:list")
//	public R list(String probedata, Integer page, Integer limit) throws ExecutionException, InterruptedException {
//		//查询列表数据
//		Map<String, Object> map = new HashMap<>();
//		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
//		System.out.println(probedata_jsonobject);
//		try {
//			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
//		} catch (RuntimeException e) {
//			throw new RRException("内部参数错误，请重试！");
//		}
//		int service = Integer.parseInt(map.get("service").toString());
//		System.out.println(service);
//		String dateStr = map.get("ava_start").toString();
//		String dateStr2 = map.get("ava_terminal").toString();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
//		int dateDifferent = 0;
//		try
//		{
//			Date date2 = format.parse(dateStr2);
//			Date date = format.parse(dateStr);
//
//			dateDifferent = recordHourPingService.differentDays(date,date2);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<ScoreEntity> scoreList = new ArrayList<>();
//		//查询天表
//		if (dateDifferent>5){
//			if (service == 0){
//				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
//				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
//				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
//				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
//				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
//				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
//				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
//				List<ScoreEntity> connection = recordHourPingService.calculateArea1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
//
//				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
//				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
//				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
//				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
//				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
//				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
//				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
//				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
//				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
//				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
//				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
//				List<ScoreEntity> quality = recordHourSlaService.calculateArea2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
//
//				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
//				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);
//
//				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
//				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
//				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
//				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
//				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
//				List<ScoreEntity> download = recordHourWebDownloadService.calculateArea4(webDownload,ftpDownload,ftpUpload);
//
//				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
//				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);
//
//				List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
//				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);
//
//				scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);
//			}
//			else if (service==1){
//				List<RecordHourPingEntity> pingList = recordHourPingService.queryDayList(map);
//				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryDayList(map);
//				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
//				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
//				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
//				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
//				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
//				scoreList = recordHourPingService.calculateArea1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
//			}
//			else if (service==2){
//				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryDayList(map);
//				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDayList(map);
//				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDayList(map);
//				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryDayList(map);
//				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryDayList(map);
//				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
//				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
//				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
//				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
//				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
//				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
//				scoreList = recordHourSlaService.calculateArea2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
//			}
//			else if (service==3){
//				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryDayList(map);
//				scoreList = recordHourWebPageService.calculateService3(webPageList);
//			}
//			else if (service==4){
//				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryDayList(map);
//				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryDayList(map);
//				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
//				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
//				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
//				scoreList = recordHourWebDownloadService.calculateArea4(webDownload,ftpDownload,ftpUpload);
//			}
//			else if (service==5){
//				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryDayList(map);
//				scoreList = recordHourWebVideoService.calculateService5(videoList);
//			}
//			else if (service==6){
//				List<RecordHourGameEntity> gameList = recordHourGameService.queryDayList(map);
//				scoreList = recordHourGameService.calculateService6(gameList);
//			}
//			else {}
//		}
//		//查询小时表
//		else {
//			if (service == 0) {
//				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
//				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
//				List<ScoreEntity> connection;
//				while (true) {
//					if (pingList_future.isDone() && tracertList_future.isDone()) {
//						List<RecordHourPingEntity> pingList = pingList_future.get();
//						List<RecordHourTracertEntity> tracertList = tracertList_future.get();
//						List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
//						List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
//						List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
//						List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
//						List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
//						connection = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
//						break;
//					}
//					Thread.sleep(1000);
//				}
//				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
//				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
//				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
//				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
//				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
//				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
//				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
//				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
//				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
//				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
//				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
//				List<ScoreEntity> quality = recordHourSlaService.calculateArea2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
//
//				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebList(map);
//				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);
//
//				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
//				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
//				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
//				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
//				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
//				List<ScoreEntity> download = recordHourWebDownloadService.calculateArea4(webDownload,ftpDownload,ftpUpload);
//
//				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoList(map);
//				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);
//
//				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameList(map);
//				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);
//
//				scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);
//
//			} else if (service == 1) {
//				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
//				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
//				List<ScoreEntity> connection;
//				while (true) {
//					if (pingList_future.isDone() && tracertList_future.isDone()) {
//						List<RecordHourPingEntity> pingList = pingList_future.get();
//						List<RecordHourTracertEntity> tracertList = tracertList_future.get();
//						List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
//						List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
//						List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
//						List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
//						List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
//						connection = recordHourPingService.calculateDate1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
//						break;
//					}
//					Thread.sleep(1000);
//				}
//			} else if (service == 2) {
//				List<RecordHourSlaEntity> slaList = recordHourSlaService.querySlaList(map);
//				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDnsList(map);
//				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryDhcpList(map);
//				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoeList(map);
//				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadiusList(map);
//				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
//				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
//				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
//				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
//				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
//				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
//				scoreList = recordHourSlaService.calculateArea2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
//			} else if (service == 3) {
//				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryWebAreaList(map);
//				scoreList = recordHourWebPageService.calculateService3(webPageList);
//			} else if (service == 4) {
//				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryWebDownloadList(map);
//				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtpList(map);
//				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
//				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
//				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
//				scoreList = recordHourWebDownloadService.calculateArea4(webDownload,ftpDownload,ftpUpload);
//			} else if (service == 5) {
//				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryVideoAreaList(map);
//				scoreList = recordHourWebVideoService.calculateService5(videoList);
//			} else if (service == 6) {
//				List<RecordHourGameEntity> gameList = recordHourGameService.queryGameAreaList(map);
//				scoreList = recordHourGameService.calculateService6(gameList);
//			} else {
//			}
//		}
//
//		if(map.get("target_id")==null){
//			for(int i=0;i<scoreList.size();i++){
//				scoreList.get(i).setTargetName("");
//			}
//		}else{}
//
//		int total = 0;
//		if(page==null) {              /*没有传入page,则取全部值*/
//			map.put("offset", null);
//			map.put("limit", null);
//			page = 0;
//			limit = 0;
//		}else {
//			map.put("offset", (page - 1) * limit);
//			map.put("limit", limit);
//			total = scoreList.size();
//		}
//		//List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
//		PageUtils pageUtil = new PageUtils(scoreList, total, limit, page);
//		return R.ok().put("page", pageUtil);
//	}
//
//
//	/**
//	 * 信息
//	 */
//	@RequestMapping("/info/{id}")
//	@RequiresPermissions("recordhourtracert:info")
//	public R info(@PathVariable("id") Integer id){
//		RecordHourTracertEntity recordHourTracert = recordHourTracertService.queryObject(id);
//
//		return R.ok().put("recordHourTracert", recordHourTracert);
//	}
//
//	/**
//	 * 保存
//	 */
//	@RequestMapping("/save")
//	@RequiresPermissions("recordhourtracert:save")
//	public R save(@RequestBody RecordHourTracertEntity recordHourTracert){
//		recordHourTracertService.save(recordHourTracert);
//
//		return R.ok();
//	}
//
//	/**
//	 * 修改
//	 */
//	@RequestMapping("/update")
//	@RequiresPermissions("recordhourtracert:update")
//	public R update(@RequestBody RecordHourTracertEntity recordHourTracert){
//		recordHourTracertService.update(recordHourTracert);
//
//		return R.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@RequestMapping("/delete")
//	@RequiresPermissions("recordhourtracert:delete")
//	public R delete(@RequestBody Integer[] ids){
//		recordHourTracertService.deleteBatch(ids);
//
//		return R.ok();
//	}
//
//}
