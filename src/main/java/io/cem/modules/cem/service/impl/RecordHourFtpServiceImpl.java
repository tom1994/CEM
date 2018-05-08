package io.cem.modules.cem.service.impl;

import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordFtpDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourFtpDao;


@Service("recordHourFtpService")
public class RecordHourFtpServiceImpl implements RecordHourFtpService {
	@Autowired
	private RecordHourFtpDao recordHourFtpDao;
	@Autowired
	private RecordFtpDao recordFtpDao;
	
	@Override
	public RecordHourFtpEntity queryObject(Integer id){
		return recordHourFtpDao.queryObject(id);
	}

	@Override
	public List<RecordHourFtpEntity> queryFtp(Map<String, Object> map){
		return recordFtpDao.queryFtp(map);
	}

	@Override
	public List<RecordHourFtpEntity> queryList(Map<String, Object> map){
		return recordHourFtpDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryFtpList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourFtpDao.queryFtpList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourFtpDao.queryTargetHourList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourFtpDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourFtpDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourFtpDao.queryDayList(map));
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourFtpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourFtpEntity recordHourFtp){
		recordHourFtpDao.save(recordHourFtp);
	}
	
	@Override
	public void update(RecordHourFtpEntity recordHourFtp){
		recordHourFtpDao.update(recordHourFtp);
	}
	
	@Override
	public void delete(Integer id){
		recordHourFtpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourFtpDao.deleteBatch(ids);
	}

	@Override
	public EvaluationEntity calculateHourQualityScore(Map<String, Object> map) throws ExecutionException, InterruptedException{
		EvaluationEntity score = new EvaluationEntity();
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
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		//网络连通性业务
		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
		Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
		Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
		Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryPingList(map3);
		Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryTracertList(map3);
		//网络层质量业务
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
		Future<List<RecordHourDnsEntity>> dnsList_future2= recordHourDnsService.queryDnsList(map3);
		Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDhcpList(map3);
		Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryPppoeList(map3);
		Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryRadiusList(map3);
		//网页浏览类业务
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map);
		//文件下载业务
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
		Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryWebDownloadList(map3);
		Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryDayList(map3);
		//在线视频业务
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map);
		//网络游戏业务
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map);
		List<ScoreEntity> connectionList;
		List<ScoreEntity> qualityList;
		List<ScoreEntity> pageList;
		List<ScoreEntity> downloadList;
		List<ScoreEntity> videoServiceList;
		List<ScoreEntity> gameServiceList;

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
				connectionList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		connectionList=recordHourDhcpService.combination(map,connectionList);
		if (connectionList.size() != 0) {
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
				qualityList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		qualityList=recordHourDhcpService.combination(map,qualityList);
		if (qualityList.size() != 0) {
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

		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				pageList = recordHourWebPageService.calculateService3(webPageList);
				break;
			}
			Thread.sleep(1000);
		}
		pageList=recordHourDhcpService.combination(map,pageList);
		if (pageList.size() != 0) {
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
				downloadList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		downloadList=recordHourDhcpService.combination(map,downloadList);
		if (downloadList.size() != 0) {
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

		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				videoServiceList = recordHourWebVideoService.calculateService5(videoList);
				break;
			}
			Thread.sleep(1000);
		}
		videoServiceList=recordHourDhcpService.combination(map,videoServiceList);
		if (videoServiceList.size() != 0) {
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

		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				gameServiceList = recordHourGameService.calculateService6(gameList);
				break;
			}
			Thread.sleep(1000);
		}
		gameServiceList=recordHourDhcpService.combination(map,gameServiceList);
		if (gameServiceList.size() != 0) {
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
		return score;
	}

	@Override
	public EvaluationEntity calculateDayQualityScore(Map<String, Object> map) throws ExecutionException, InterruptedException{
		EvaluationEntity score = new EvaluationEntity();
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
        //网络连通性业务
		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);
		//网络层质量业务
		Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
		Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
		Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
		Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
		Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);
		//网页浏览类业务
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayList(map);
		//文件下载类业务
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);
		//在线视频业务
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayList(map);
		//网络游戏业务
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayList(map);
		List<ScoreEntity> gameServiceList;
		List<ScoreEntity> videoServiceList;
		List<ScoreEntity> downloadList;
		List<ScoreEntity> pageList;
		List<ScoreEntity> qualityList;
		List<ScoreEntity> connectionList;
		while (true) {
			if (pingList_future.isDone() && tracertList_future.isDone()) {
				List<RecordHourPingEntity> pingList = pingList_future.get();
				List<RecordHourTracertEntity> tracertList = tracertList_future.get();
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				connectionList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		connectionList=recordHourDhcpService.combination(map,connectionList);
		if (connectionList.size() != 0) {
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
				qualityList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		qualityList=recordHourDhcpService.combination(map,qualityList);
		if (qualityList.size() != 0) {
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

		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				pageList = recordHourWebPageService.calculateService3(webPageList);
				break;
			}
			Thread.sleep(1000);
		}
		pageList=recordHourDhcpService.combination(map,pageList);
		if (pageList.size() != 0) {
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

		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				downloadList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		downloadList=recordHourDhcpService.combination(map,downloadList);
		if (downloadList.size() != 0) {
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

		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				videoServiceList = recordHourWebVideoService.calculateService5(videoList);
				break;
			}
			Thread.sleep(1000);
		}
		videoServiceList=recordHourDhcpService.combination(map,videoServiceList);
		if (videoServiceList.size() != 0) {
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

		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				gameServiceList = recordHourGameService.calculateService6(gameList);
				break;
			}
			Thread.sleep(1000);
		}
		gameServiceList=recordHourDhcpService.combination(map,gameServiceList);
		if (gameServiceList.size() != 0) {
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
		return score;
	}

	@Override
	public EvaluationEntity calculateDayHourQualityScore(Map<String, Object> map) throws ExecutionException, InterruptedException{
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
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));
		EvaluationEntity score = new EvaluationEntity();
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
		//网络连通性业务
		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
		//Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
		//Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
		//网络层质量业务
		Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map);
		Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map);
		Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map);
		Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map);
		Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map);
//		Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
//		Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
//		Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
//		Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
//		Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
//		//网页浏览类业务
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map);
	//	Future<List<RecordHourWebPageEntity>> webPageList_future1 = recordHourWebPageService.queryWebList(map2);
		//文件下载类业务
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);
//		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
//		Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
//		//在线视频业务
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map);
		//Future<List<RecordHourWebVideoEntity>> videoList_future1 = recordHourWebVideoService.queryVideoList(map2);
		//网络游戏业务
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map);
		//Future<List<RecordHourGameEntity>> gameList_future1 = recordHourGameService.queryGameList(map2);
		List<ScoreEntity> gameServiceList;
		List<ScoreEntity> videoServiceList;
		List<ScoreEntity> downloadList;
		List<ScoreEntity> pageList;
		List<ScoreEntity> qualityList;
		List<ScoreEntity> connectionList;
		while (true) {
			if (pingList_future.isDone() && tracertList_future.isDone()) {
				List<RecordHourPingEntity> pingList = pingList_future.get();
				//pingList.addAll(pingList_future1.get());
				List<RecordHourTracertEntity> tracertList = tracertList_future.get();
				//tracertList.addAll(tracertList_future1.get());
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				connectionList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		connectionList=recordHourDhcpService.combination(map,connectionList);
		if (connectionList.size() != 0) {
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

		while (true) {
			if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
				List<RecordHourSlaEntity> slaList = slaList_future.get();
				//slaList.addAll(slaList_future1.get());
				List<RecordHourDnsEntity> dnsList = dnsList_future.get();
				//dnsList.addAll(dnsList_future1.get());
				List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
				//dhcpList.addAll(dhcpList_future1.get());
				List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
				//pppoeList.addAll(pppoeList_future1.get());
				List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
				//radiusList.addAll(radiusList_future1.get());
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				qualityList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		qualityList=recordHourDhcpService.combination(map,qualityList);
		if (qualityList.size() != 0) {
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

		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				pageList = recordHourWebPageService.calculateService3(webPageList);
				break;
			}
			Thread.sleep(1000);
		}
		pageList=recordHourDhcpService.combination(map,pageList);
		if (pageList.size() != 0) {
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

		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				//webDownloadList.addAll(webDownloadList_future1.get());
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				///ftpList.addAll(ftpList_future1.get());
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				downloadList= recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		downloadList=recordHourDhcpService.combination(map,downloadList);
		if (downloadList.size() != 0) {
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

		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				//videoList.addAll(videoList_future1.get());
				videoServiceList = recordHourWebVideoService.calculateService5(videoList);
				break;
			}
			Thread.sleep(1000);
		}
		videoServiceList=recordHourDhcpService.combination(map,videoServiceList);
		if (videoServiceList.size() != 0) {
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

		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				//gameList.addAll(gameList_future1.get());
				gameServiceList = recordHourGameService.calculateService6(gameList);
				break;
			}
			Thread.sleep(1000);
		}
		gameServiceList=recordHourDhcpService.combination(map,gameServiceList);
		if (gameServiceList.size() != 0) {
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
		return score;
	}
	
}
