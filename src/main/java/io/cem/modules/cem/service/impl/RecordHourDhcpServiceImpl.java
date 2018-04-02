package io.cem.modules.cem.service.impl;

import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordDhcpDao;
import io.cem.modules.cem.dao.RecordHourDhcpDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service("recordHourDhcpService")
public class RecordHourDhcpServiceImpl implements RecordHourDhcpService {
	@Autowired
	private RecordHourDhcpDao recordHourDhcpDao;
	@Autowired
	private RecordDhcpDao recordDhcpDao;
	
	@Override
	public RecordHourDhcpEntity queryObject(Integer id){
		return recordHourDhcpDao.queryObject(id);
	}

	@Override
	public List<RecordHourDhcpEntity> queryDhcp(Map<String, Object> map){
		return recordDhcpDao.queryDhcp(map);
	}

	@Override
	public List<RecordHourDhcpEntity> queryList(Map<String, Object> map){
		return recordHourDhcpDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDhcpList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourDhcpDao.queryDhcpList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<>
				( recordHourDhcpDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<>(recordHourDhcpDao.queryDayList(map));
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourDhcpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.save(recordHourDhcp);
	}
	
	@Override
	public void update(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.update(recordHourDhcp);
	}
	
	@Override
	public void delete(Integer id){
		recordHourDhcpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourDhcpDao.deleteBatch(ids);
	}
	
	@Override
	public List<ScoreEntity> connectionDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
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
				scoreList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);

		return scoreList;
	}

	@Override
	public List<ScoreEntity> connectionHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
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
				scoreList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
	    return scoreList;
	}

	@Override
	public List<ScoreEntity> qualityDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
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
				scoreList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> qualityHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
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
				scoreList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> pageDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		//网页浏览类业务
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayList(map);
		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				scoreList = recordHourWebPageService.calculateService3(webPageList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> pageHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map);
		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				scoreList = recordHourWebPageService.calculateService3(webPageList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> downloadDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);
		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				scoreList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> downloadHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map);
		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				scoreList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> videoDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayList(map);
		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				scoreList = recordHourWebVideoService.calculateService5(videoList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> videoHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map);
		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				scoreList = recordHourWebVideoService.calculateService5(videoList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	
	}

	@Override
	public List<ScoreEntity> gameDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayList(map);
		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				scoreList = recordHourGameService.calculateService6(gameList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> gameHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map);
		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				scoreList = recordHourGameService.calculateService6(gameList);
				break;
			}
			Thread.sleep(1000);
		}
		scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> combination(Map<String,Object> map,List<ScoreEntity> scoreList){
		List<ScoreEntity> list=new ArrayList<>();
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		if (map.get("city_Id") == null && map.get("county_id") == null && map.get("probe_id") == null) {
			list = recordHourPingService.dateChart1(scoreList);
		} else if (map.get("county_id") == null && map.get("probe_id") == null) {
			list = recordHourPingService.cityChart1(scoreList);
		} else if (map.get("probe_id") == null) {
			list = recordHourPingService.probeChart1(scoreList);
		} else {
		}
		return list;
	}


	
}
