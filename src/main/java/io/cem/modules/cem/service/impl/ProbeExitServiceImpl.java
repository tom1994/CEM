package io.cem.modules.cem.service.impl;

import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.ProbeExitDao;


@Service("probeExitService")
public class ProbeExitServiceImpl implements ProbeExitService {
	@Autowired
	private ProbeExitDao probeExitDao;

	@Override
	public ProbeExitEntity queryObject(Integer id){
		return probeExitDao.queryObject(id);
	}

	@Override
	public List<ProbeExitEntity> queryList(Map<String, Object> map){
		return probeExitDao.queryList(map);
	}

	@Override
	public List<ProbeExitEntity> queryscoreList(Map<String, Object> map){return probeExitDao.queryscoreList(map);}

	@Override
	public int queryTotal(Map<String, Object> map){
		return probeExitDao.queryTotal(map);
	}

	@Override
	public void save(ProbeExitEntity probeExit){
		probeExitDao.save(probeExit);
	}

	@Override
	public void update(ProbeExitEntity probeExit){
		probeExitDao.update(probeExit);
	}

	@Override
	public void operateStatus0(Integer id){probeExitDao.operateStatus0(id);}

	@Override
	public int queryNameExist(String exit){
		return probeExitDao.queryNameExist(exit);
	}

	@Override
	public int queryUpdate(String exit,int id){
		return probeExitDao.queryUpdate(exit,id);
	}

	@Override
	public int queryProbeExist(Integer probeId){
		return probeExitDao.queryProbeExist(probeId);
	}

	@Override
	public int queryPortExist(String port){
		return probeExitDao.queryPortExist(port);
	}

	@Override
	public void operateStatus1(Integer id){probeExitDao.operateStatus1(id);}

	@Override
	public void delete(Integer id){
		probeExitDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		probeExitDao.deleteBatch(ids);
	}

	@Override
	public ScoreEntity calculateScore(Map<String, Object> map) throws ExecutionException, InterruptedException{
		ScoreEntity score=new ScoreEntity();
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

		List<ScoreEntity> scoreList = new ArrayList<>();
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


		if (dateDifferent>5){
			if(service==0){
				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayExitList(map);
				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayExitList(map);

				Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayExitList(map);
				Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayExitList(map);
				Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayExitList(map);
				Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayExitList(map);
				Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayExitList(map);

				Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayExitList(map);
				Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayExitList(map);

				Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayExitList(map);
				Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayExitList(map);
				Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayExitList(map);

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
			}else if(service==1){
				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayExitList(map);
				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayExitList(map);
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
			}else if(service==2){
				Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayExitList(map);
				Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayExitList(map);
				Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayExitList(map);
				Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayExitList(map);
				Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayExitList(map);

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
			}else if(service==3){
				Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayExitList(map);
				while (true) {
					if (webPageList_future.isDone()) {
						List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
						scoreList = recordHourWebPageService.calculateService3(webPageList);
						break;
					}
					Thread.sleep(1000);
				}
			}else if(service==4){
				Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayExitList(map);
				Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayExitList(map);

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
			}else if(service==5){
				Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayExitList(map);
				while (true) {
					if (videoList_future.isDone()) {
						List<RecordHourWebVideoEntity> videoList = videoList_future.get();
						scoreList = recordHourWebVideoService.calculateService5(videoList);
						break;
					}
					Thread.sleep(1000);
				}
			}else if(service==6){
				Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayExitList(map);
				while (true) {
					if (gameList_future.isDone()) {
						List<RecordHourGameEntity> gameList = gameList_future.get();
						scoreList = recordHourGameService.calculateService6(gameList);
						break;
					}
					Thread.sleep(1000);
				}
			}else{}

		}else{
			if(service==0){
				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryExitList(map);
				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryExitList(map);

				Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryExitList(map);
				Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryExitList(map);
				Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryExitList(map);
				Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryExitList(map);
				Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryExitList(map);

				Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryExitList(map);
				Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryExitList(map);

				Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryExitList(map);
				Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryExitList(map);
				Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryExitList(map);

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
			}else if(service==1){
				Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryExitList(map);
				Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryExitList(map);
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
			}else if(service==2){
				Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryExitList(map);
				Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryExitList(map);
				Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryExitList(map);
				Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryExitList(map);
				Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryExitList(map);

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
			}else if(service==3){
				Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryExitList(map);
				while (true) {
					if (webPageList_future.isDone()) {
						List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
						scoreList = recordHourWebPageService.calculateService3(webPageList);
						break;
					}
					Thread.sleep(1000);
				}
			}else if(service==4){
				Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryExitList(map);
				Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryExitList(map);

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
			}else if(service==5){
				Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryExitList(map);
				while (true) {
					if (videoList_future.isDone()) {
						List<RecordHourWebVideoEntity> videoList = videoList_future.get();
						scoreList = recordHourWebVideoService.calculateService5(videoList);
						break;
					}
					Thread.sleep(1000);
				}
			}else if(service==6){
				Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryExitList(map);
				while (true) {
					if (gameList_future.isDone()) {
						List<RecordHourGameEntity> gameList = gameList_future.get();
						scoreList = recordHourGameService.calculateService6(gameList);
						break;
					}
					Thread.sleep(1000);
				}
			}else{}

		}
        if(scoreList.size()!=0){
		    score=scoreList.get(0);
		}


		return score;
	};

}

