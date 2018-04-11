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
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayAreaList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayAreaList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayAreaList(map);

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
					scoreList = recordHourPingService.calculateArea1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
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
					scoreList = recordHourSlaService.calculateArea2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayAreaList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
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
					scoreList = recordHourWebDownloadService.calculateArea4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayAreaList(map);
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
		int service = Integer.parseInt(map.get("service").toString());
		if (service == 0) {
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);

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
					scoreList = recordHourPingService.calculateTarget1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
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
			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);

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
	public List<ScoreEntity> calculateDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException {
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
			Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
			Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);

			Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
			Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
			Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
			Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
			Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);

			Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
			Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);

			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayRankList(map);
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayRankList(map);
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayRankList(map);

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
					scoreList = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
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
					scoreList = recordHourSlaService.calculateService2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayRankList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList = recordHourWebPageService.calculateService3(webPageList);
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
					scoreList = recordHourWebDownloadService.calculateService4(webDownload, ftpDownload, ftpUpload);
					break;
				}
				Thread.sleep(1000);
			}
		}  else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayRankList(map);
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
		}  else if (service == 5) {
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
					scoreList.addAll(recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp));
					break;
				}
				Thread.sleep(1000);
			}
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
					scoreList.addAll(recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius));
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
					scoreList.addAll(recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 3) {
			Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebRankList(map);
			while (true) {
				if (webPageList_future.isDone()) {
					List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
					scoreList.addAll(recordHourWebPageService.calculateService3(webPageList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 5) {
			Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoRankList(map);
			while (true) {
				if (videoList_future.isDone()) {
					List<RecordHourWebVideoEntity> videoList = videoList_future.get();
					scoreList.addAll(recordHourWebVideoService.calculateService5(videoList));
					break;
				}
				Thread.sleep(1000);
			}
		} else if (service == 6) {
			Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameRankList(map);
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
}
