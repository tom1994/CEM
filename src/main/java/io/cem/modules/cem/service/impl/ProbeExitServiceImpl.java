package io.cem.modules.cem.service.impl;

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

import io.cem.modules.cem.dao.ProbeExitDao;


@Service("probeExitService")
public class ProbeExitServiceImpl implements ProbeExitService {
	@Autowired
	private ProbeExitDao probeExitDao;
	@Autowired
	private RecordHourTracertService recordHourTracertService;
	@Autowired
	private RecordHourPingService recordHourPingService;
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
	public ScoreEntity calculateScore(Map<String, Object> map){
		ScoreEntity score=new ScoreEntity();
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
		System.out.println("map!!!!!"+map);

		if (dateDifferent>5){
			if(service==0){

			}else if(service==1){

			}else if(service==2){

			}else if(service==3){

			}else if(service==4){

			}else if(service==5){

			}else if(service==6){

			}else{}
		}else{
			if(service==0){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryExitList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryExitList(map);
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				List<ScoreEntity> connection = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);

				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryExitList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryExitList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryExitList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryExitList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryExitList(map);
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				List<ScoreEntity> quality = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);

				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryExitList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryExitList(map);
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				List<ScoreEntity> download = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);

				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryExitList(map);
				List<ScoreEntity> broswer = recordHourWebPageService.calculateService3(webPageList);

				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryExitList(map);
				List<ScoreEntity> video = recordHourWebVideoService.calculateService5(videoList);

				List<RecordHourGameEntity> gameList = recordHourGameService.queryExitList(map);
				List<ScoreEntity> game = recordHourGameService.calculateService6(gameList);

				scoreList = recordHourTracertService.calculateService0(connection,quality,broswer,download,video,game);
			}else if(service==1){
				List<RecordHourPingEntity> pingList = recordHourPingService.queryExitList(map);
				List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryExitList(map);
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
				scoreList = recordHourPingService.calculateService1(pingIcmp,pingTcp,pingUdp,tracertIcmp,tracertUdp);
			}else if(service==2){
				List<RecordHourSlaEntity> slaList = recordHourSlaService.queryExitList(map);
				List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryExitList(map);
				List<RecordHourDhcpEntity> dhcpList = recordHourDhcpService.queryExitList(map);
				List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryExitList(map);
				List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryExitList(map);
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList);
				scoreList = recordHourSlaService.calculateService2(slaTcp,slaUdp,dns,dhcp,pppoe,radius);
			}else if(service==3){
				List<RecordHourWebPageEntity> webPageList = recordHourWebPageService.queryExitList(map);
				scoreList = recordHourWebPageService.calculateService3(webPageList);
			}else if(service==4){
				List<RecordHourWebDownloadEntity> webDownloadList = recordHourWebDownloadService.queryExitList(map);
				List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryExitList(map);
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList);
				scoreList = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
			}else if(service==5){
				List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryExitList(map);
				scoreList = recordHourWebVideoService.calculateService5(videoList);
			}else if(service==6){
				List<RecordHourGameEntity> gameList = recordHourGameService.queryExitList(map);
				scoreList = recordHourGameService.calculateService6(gameList);
			}else{}

		}
        if(scoreList.size()!=0){
		    score=scoreList.get(0);
		}


		return score;
	};
	
}
