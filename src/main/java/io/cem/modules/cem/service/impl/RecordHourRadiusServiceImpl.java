package io.cem.modules.cem.service.impl;

import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.*;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service("recordHourRadiusService")
public class RecordHourRadiusServiceImpl implements RecordHourRadiusService {
	@Autowired
	private RecordHourRadiusDao recordHourRadiusDao;
	@Autowired
	private RecordRadiusDao recordRadiusDao;
	
	@Override
	public RecordHourRadiusEntity queryObject(Integer id){
		return recordHourRadiusDao.queryObject(id);
	}

	@Override
	public List<RecordHourRadiusEntity> queryRadius(Map<String, Object> map){
		return recordRadiusDao.queryRadius(map);
	}

	@Override
	public List<RecordHourRadiusEntity> queryList(Map<String, Object> map){
		return recordHourRadiusDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourRadiusEntity>> queryRadiusList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourRadiusDao.queryRadiusList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourRadiusEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourRadiusDao.queryTargetHourList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourRadiusEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourRadiusDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourRadiusEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourRadiusDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourRadiusEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourRadiusDao.queryDayList(map));
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourRadiusDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourRadiusEntity recordHourRadius){
		recordHourRadiusDao.save(recordHourRadius);
	}
	
	@Override
	public void update(RecordHourRadiusEntity recordHourRadius){
		recordHourRadiusDao.update(recordHourRadius);
	}
	
	@Override
	public void delete(Integer id){
		recordHourRadiusDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourRadiusDao.deleteBatch(ids);
	}

	@Override
	public List<ScoreEntity> calculateAreaDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		//datedifferent>1天的情况
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");

		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		List<ScoreEntity> scoreList = new ArrayList<>();
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayAreaList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future= recordHourWebVideoService.queryDayAreaList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayAreaList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();

					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebAreaList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future= recordHourWebVideoService.queryDayAreaList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();

					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayAreaList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateTargetDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));


		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryTargetHourList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTargetHourList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.queryTargetHourList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryTargetHourList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryTargetHourList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryTargetHourList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryTargetHourList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryTargetHourList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryTargetHourList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayTargetList(map);

			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayTargetList(map);

			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayTargetList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone()&&ftpList_future1.isDone()&&webDownloadList_future2.isDone()&&ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateTarget0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {

			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryTargetHourList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTargetHourList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.queryTargetHourList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryTargetHourList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryTargetHourList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryTargetHourList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryTargetHourList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayTargetList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryTargetHourList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryTargetHourList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone()&&ftpList_future1.isDone()&&webDownloadList_future2.isDone()&&ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayTargetList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayTargetList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateTargetHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebTargetList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoTargetList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameTargetList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateTarget0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {

			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebTargetList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoTargetList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameTargetList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateTargetDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		//datedifferent=1天的情况
		//组装2个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));


		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryTargetHourList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTargetHourList(map2);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.queryTargetHourList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryTargetHourList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryTargetHourList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryTargetHourList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryTargetHourList(map2);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryTargetHourList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryTargetHourList(map2);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebTargetList(map);

			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoTargetList(map);

			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameTargetList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone()&&ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateTarget0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {

			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryTargetHourList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTargetHourList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryTargetHourList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTargetHourList(map2);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryTargetHourList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryTargetHourList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryTargetHourList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryTargetHourList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryTargetHourList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.queryTargetHourList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryTargetHourList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryTargetHourList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryTargetHourList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryTargetHourList(map2);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateTarget2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebTargetList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryTargetHourList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryTargetHourList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryTargetHourList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryTargetHourList(map2);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateTarget4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoTargetList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameTargetList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateAreaHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebAreaList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoAreaList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameAreaList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateArea0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			//total = recordHourPingService.pingListTotal(map);
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebAreaList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoAreaList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameAreaList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateAreaDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		//datedifferent=1天的情况
		//组装2个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebAreaList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoAreaList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameAreaList(map);


			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateArea0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			//total = recordHourPingService.pingListTotal(map);
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebAreaList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoAreaList(map);

			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameAreaList(map);

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		//datedifferent>1天的情况
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");

		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
			map3.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		List<ScoreEntity> scoreList = new ArrayList<>();
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayRankList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future= recordHourWebVideoService.queryDayRankList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayRankList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryDayList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryDayList(map3);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.queryDayList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDayList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDayList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryDayList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryDayList(map3);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryDayList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future= recordHourWebVideoService.queryDayRankList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayRankList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();

					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebRankList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoRankList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameRankList(map);

			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			//total = recordHourPingService.pingListTotal(map);
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebRankList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoRankList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameRankList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> calculateDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
		//datedifferent=1天的情况
		//组装2个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		List<ScoreEntity> scoreList = new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebRankList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoRankList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameRankList(map);


			List<ScoreEntity> connection;
			List<ScoreEntity> quality;
			List<ScoreEntity> download;
			List<ScoreEntity> broswer;
			List<ScoreEntity> video;
			List<ScoreEntity> game;

			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					connection = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					quality = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					download = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					broswer = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					video = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}

			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					game = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}

			scoreList = recordHourTracertService.calculateService0(connection, quality, broswer, download, video, game);

		} else if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
					break;
				}
				Thread.sleep(1000);
			}
			//total = recordHourPingService.pingListTotal(map);
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebRankList(map1);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoRankList(map1);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList = recordHourWebVideoService.calculateService5(videoList);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameRankList(map1);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList = recordHourGameService.calculateService6(gameList);
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> diagnoseDayHour(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException{
		int service = Integer.parseInt(map.get("service").toString());
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");

		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryPingList(map3);
			Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryTracertList(map3);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					pingList.addAll(pingList_future2.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					tracertList.addAll(tracertList_future2.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList.addAll(recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp));
					break;
				}
				Thread.sleep(1000);
			}

		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
			Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.querySlaList(map3);
			Future<List<RecordHourDnsEntity>> dnsList_future2 = recordHourDnsService.queryDnsList(map3);
			Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDhcpList(map3);
			Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryPppoeList(map3);
			Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryRadiusList(map3);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
						slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
						slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					slaList.addAll(slaList_future2.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					dnsList.addAll(dnsList_future2.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					dhcpList.addAll(dhcpList_future2.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					pppoeList.addAll(pppoeList_future2.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					radiusList.addAll(radiusList_future2.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList.addAll(recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map1);
			Future<List<RecordHourWebPageEntity>> webPageList_future1 = recordHourWebPageService.queryWebList(map2);
			Future<List<RecordHourWebPageEntity>> webPageList_future2 = recordHourWebPageService.queryWebList(map3);
			while (true) {
				if (webPageList_future.isDone()&&webPageList_future1.isDone()&&webPageList_future2.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					webPageList.addAll(webPageList_future1.get());
					webPageList.addAll(webPageList_future2.get());
					scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryWebDownloadList(map3);
			Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryFtpList(map3);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					webDownloadList.addAll(webDownloadList_future2.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					ftpList.addAll(ftpList_future2.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList.addAll(recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload));
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map1);
			Future<List<RecordHourWebVideoEntity>> videoList_future1 = recordHourWebVideoService.queryVideoList(map2);
			Future<List<RecordHourWebVideoEntity>> videoList_future2 = recordHourWebVideoService.queryVideoList(map3);
			while (true) {
				if (videoList_future.isDone()&&videoList_future1.isDone()&&videoList_future2.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					videoList.addAll(videoList_future1.get());
					videoList.addAll(videoList_future2.get());
					scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map1);
			Future<List<RecordHourGameEntity>> gameList_future1 = recordHourGameService.queryGameList(map2);
			Future<List<RecordHourGameEntity>> gameList_future2 = recordHourGameService.queryGameList(map3);
			while (true) {
				if (gameList_future.isDone()&&gameList_future1.isDone()&&gameList_future2.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					gameList.addAll(gameList_future1.get());
					gameList.addAll(gameList_future2.get());
					scoreList.addAll(recordHourGameService.calculateService6(gameList));
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> diagnoseDay(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException{
		int service = Integer.parseInt(map.get("service").toString());
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList.addAll(recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList.addAll(recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList.addAll(recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayList(map);
			while (true) {
				if (gameList_future.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					scoreList.addAll(recordHourGameService.calculateService6(gameList));
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}

	@Override
	public List<ScoreEntity> diagnoseHour(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException{
		//datedifferent=1天的情况
		//组装2个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","00:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		int service = Integer.parseInt(map.get("service").toString());
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		if (service == 1) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
			Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
			Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
			while (true) {
				if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
					List<RecordHourPingEntity> pingList = pingList_future.get();
					pingList.addAll(pingList_future1.get());
					List<RecordHourTracertEntity> tracertList = tracertList_future.get();
					tracertList.addAll(tracertList_future1.get());
					List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
					List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
					List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
					List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
					List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
					scoreList.addAll(recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 2) {
			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
			Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
			Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
			Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
			Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
			Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);

			while (true) {
				if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
					List<RecordHourSlaEntity> slaList = slaList_future.get();
					slaList.addAll(slaList_future1.get());
					List<RecordHourDnsEntity> dnsList = dnsList_future.get();
					dnsList.addAll(dnsList_future1.get());
					List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
					dhcpList.addAll(dhcpList_future1.get());
					List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
					pppoeList.addAll(pppoeList_future1.get());
					List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
					radiusList.addAll(radiusList_future1.get());
					List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
					List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
					List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
					List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
					List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
					List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
					scoreList.addAll(recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 4) {
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
			Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

			while (true) {
				if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
					List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
					webDownloadList.addAll(webDownloadList_future1.get());
					List<RecordHourFtpEntity> ftpList = ftpList_future.get();
					ftpList.addAll(ftpList_future1.get());
					List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
					List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
					List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
					scoreList.addAll(recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map1);
			Future<List<RecordHourWebPageEntity>> webPageList_future1 = recordHourWebPageService.queryWebList(map2);
			while (true) {
				if (webPageList_future.isDone()&&webPageList_future1.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					webPageList.addAll(webPageList_future1.get());
					scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map1);
			Future<List<RecordHourWebVideoEntity>> videoList_future1 = recordHourWebVideoService.queryVideoList(map2);
			while (true) {
				if (videoList_future.isDone()&&videoList_future1.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					videoList.addAll(videoList_future1.get());
					scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map1);
			Future<List<RecordHourGameEntity>> gameList_future1 = recordHourGameService.queryGameList(map2);
			while (true) {
				if (gameList_future.isDone()&&gameList_future1.isDone()) {
					List<RecordHourGameEntity> gameList = gameList_future.get();
					gameList.addAll(gameList_future1.get());
					scoreList.addAll(recordHourGameService.calculateService6(gameList));
					break;
				}
				Thread.sleep(1000);
			}
		} else {
		}
		return scoreList;
	}


}
