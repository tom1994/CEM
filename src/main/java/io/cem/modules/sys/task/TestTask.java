package io.cem.modules.sys.task;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import io.cem.modules.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 定时任务
 * testTask为spring bean的名称
 *
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private RecordHourPingService recordHourPingService;
	@Autowired
	private RecordDayPingService recordDayPingService;
	@Autowired
	private RecordHourTracertService recordHourTracertService;
	@Autowired
	private RecordDayTracertService recordDayTracertService;
	@Autowired
	private RecordHourSlaService recordHourSlaService;
	@Autowired
	private RecordDaySlaService recordDaySlaService;
	@Autowired
	private RecordHourDnsService recordHourDnsService;
	@Autowired
	private RecordDayDnsService recordDayDnsService;
	@Autowired
	private RecordHourDhcpService recordHourDhcpService;
	@Autowired
	private RecordDayDhcpService recordDayDhcpService;
	@Autowired
	private RecordHourPppoeService recordHourPppoeService;
	@Autowired
	private RecordDayPppoeService recordDayPppoeService;
	@Autowired
	private RecordHourRadiusService recordHourRadiusService;
	@Autowired
	private RecordDayRadiusService recordDayRadiusService;
	@Autowired
	private RecordHourWebPageService recordHourWebPageService;
	@Autowired
	private RecordDayWebPageService recordDayWebPageService;
	@Autowired
	private RecordHourFtpService recordHourFtpService;
	@Autowired
	private RecordDayFtpService recordDayFtpService;
	@Autowired
	private RecordHourWebDownloadService recordHourWebDownloadService;
	@Autowired
	private RecordDayWebDownloadService recordDayWebDownloadService;
	@Autowired
	private RecordHourWebVideoService recordHourWebVideoService;
	@Autowired
	private RecordDayWebVideoService recordDayWebVideoService;
	@Autowired
	private RecordHourGameService recordHourGameService;
	@Autowired
	private RecordDayGameService recordDayGameService;
	@Autowired
	private IndexHistogramViewService indexHistogramViewService;
	@Autowired
	private IndexLineViewService indexLineViewService;
	@Autowired
	private IndexMapViewService indexMapViewService;
	@Autowired
	private IndexRadaViewService indexRadaViewService;
	@Autowired
	private IndexRankingViewService indexRankingViewService;
	@Autowired
	private RecordFailService recordFailService;

	public void calculateHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourPingEntity> pingList = recordHourPingService.queryPing(map);
		for(int i=0;i<pingList.size();i++){
			recordHourPingService.save(pingList.get(i));
		}
		List<RecordFailEntity> pingFailList = recordFailService.queryPingFail(map);
		for(int i=0;i<pingFailList.size();i++){
			recordFailService.save(pingFailList.get(i));
		}
		List<RecordHourTracertEntity> tracertList = recordHourTracertService.queryTracert(map);
		for(int i=0;i<tracertList.size();i++){
			recordHourTracertService.save(tracertList.get(i));
		}
		List<RecordFailEntity> tracertFailList = recordFailService.queryTracertFail(map);
		for(int i=0;i<tracertFailList.size();i++){
			recordFailService.save(tracertFailList.get(i));
		}
		List<RecordHourSlaEntity> slaList = recordHourSlaService.querySla(map);
		for(int i=0;i<slaList.size();i++){
			recordHourSlaService.save(slaList.get(i));
		}
		List<RecordFailEntity> slaFailList = recordFailService.querySlaFail(map);
		for(int i=0;i<slaFailList.size();i++){
			recordFailService.save(slaFailList.get(i));
		}
		List<RecordHourDnsEntity> dnsList = recordHourDnsService.queryDns(map);
		for(int i=0;i<dnsList.size();i++){
			recordHourDnsService.save(dnsList.get(i));
		}
		List<RecordFailEntity> dnsFailList = recordFailService.queryDnsFail(map);
		for(int i=0;i<dnsFailList.size();i++){
			recordFailService.save(dnsFailList.get(i));
		}
		List<RecordHourDhcpEntity> dhcplist = recordHourDhcpService.queryDhcp(map);
		for(int i=0;i<dhcplist.size();i++){
			recordHourDhcpService.save(dhcplist.get(i));
		}
		List<RecordFailEntity> dhcpFailList = recordFailService.queryDhcpFail(map);
		for(int i=0;i<dhcpFailList.size();i++){
			recordFailService.save(dhcpFailList.get(i));
		}
		List<RecordHourPppoeEntity> pppoeList = recordHourPppoeService.queryPppoe(map);
		for(int i=0;i<pppoeList.size();i++){
			recordHourPppoeService.save(pppoeList.get(i));
		}
		List<RecordFailEntity> pppoeFailList = recordFailService.queryPppoeFail(map);
		for(int i=0;i<pppoeFailList.size();i++){
			recordFailService.save(pppoeFailList.get(i));
		}
		List<RecordHourRadiusEntity> radiusList = recordHourRadiusService.queryRadius(map);
		for(int i=0;i<radiusList.size();i++){
			recordHourRadiusService.save(radiusList.get(i));
		}
		List<RecordFailEntity> radiusFailList = recordFailService.queryRadiusFail(map);
		for(int i=0;i<radiusFailList.size();i++){
			recordFailService.save(radiusFailList.get(i));
		}
		List<RecordHourWebPageEntity> webpageList = recordHourWebPageService.queryWebPage(map);
		for(int i=0;i<webpageList.size();i++){
			recordHourWebPageService.save(webpageList.get(i));
		}

		List<RecordFailEntity> webpageFailList = recordFailService.queryWebPageFail(map);
		for(int i=0;i<webpageFailList.size();i++){
			recordFailService.save(webpageFailList.get(i));
		}

		List<RecordHourFtpEntity> ftpList = recordHourFtpService.queryFtp(map);
		for(int i=0;i<ftpList.size();i++){
			recordHourFtpService.save(ftpList.get(i));
		}
		List<RecordFailEntity> ftpFailList = recordFailService.queryFtpFail(map);
		for(int i=0;i<ftpFailList.size();i++){
			recordFailService.save(ftpFailList.get(i));
		}
		List<RecordHourWebDownloadEntity> webdownloadList = recordHourWebDownloadService.queryWebDownload(map);
		for(int i=0;i<webdownloadList.size();i++){
			recordHourWebDownloadService.save(webdownloadList.get(i));
		}
		List<RecordFailEntity> webdownloadFailList = recordFailService.queryWebDownloadFail(map);
		for(int i=0;i<webdownloadFailList.size();i++){
			recordFailService.save(webdownloadFailList.get(i));
		}
		List<RecordHourWebVideoEntity> videoList = recordHourWebVideoService.queryWebVideo(map);
		for(int i=0;i<videoList.size();i++){
			recordHourWebVideoService.save(videoList.get(i));
		}
		List<RecordFailEntity> videoFailList = recordFailService.queryVideoFail(map);
		for(int i=0;i<videoFailList.size();i++){
			recordFailService.save(videoFailList.get(i));
		}
		List<RecordHourGameEntity> gameList = recordHourGameService.queryGame(map);
		for(int i=0;i<gameList.size();i++){
			recordHourGameService.save(gameList.get(i));
		}
		List<RecordFailEntity> gameFailList = recordFailService.queryGameFail(map);
		for(int i=0;i<gameFailList.size();i++){
			recordFailService.save(gameFailList.get(i));
		}



	}

	public void calculateDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayPingEntity> pingList = recordDayPingService.queryDay(map);
		for(int i=0;i<pingList.size();i++){
			recordDayPingService.save(pingList.get(i));
		}
		List<RecordDayTracertEntity> tarcertList = recordDayTracertService.queryDay(map);
		for(int i=0;i<tarcertList.size();i++){
			recordDayTracertService.save(tarcertList.get(i));
		}
		List<RecordDaySlaEntity> slaList = recordDaySlaService.queryDay(map);
		for(int i=0;i<slaList.size();i++){
			recordDaySlaService.save(slaList.get(i));
		}
		List<RecordDayDnsEntity> dnsList = recordDayDnsService.queryDay(map);
		for(int i=0;i<dnsList.size();i++){
			recordDayDnsService.save(dnsList.get(i));
		}
		List<RecordDayDhcpEntity> dhcpList = recordDayDhcpService.queryDay(map);
		for(int i=0;i<dhcpList.size();i++){
			recordDayDhcpService.save(dhcpList.get(i));
		}
		List<RecordDayPppoeEntity> pppoeList = recordDayPppoeService.queryDay(map);
		for(int i=0;i<pppoeList.size();i++){
			recordDayPppoeService.save(pppoeList.get(i));
		}
		List<RecordDayRadiusEntity> radiusList = recordDayRadiusService.queryDay(map);
		for(int i=0;i<radiusList.size();i++){
			recordDayRadiusService.save(radiusList.get(i));
		}
		List<RecordDayWebPageEntity> webpageList = recordDayWebPageService.queryDay(map);
		for(int i=0;i<webpageList.size();i++){
			recordDayWebPageService.save(webpageList.get(i));
		}
		List<RecordDayFtpEntity> ftpList = recordDayFtpService.queryDay(map);
		for(int i=0;i<ftpList.size();i++){
			recordDayFtpService.save(ftpList.get(i));
		}
		List<RecordDayWebDownloadEntity> webdownloadList = recordDayWebDownloadService.queryDay(map);
		for(int i=0;i<webdownloadList.size();i++){
			recordDayWebDownloadService.save(webdownloadList.get(i));
		}
		List<RecordDayWebVideoEntity> videoList = recordDayWebVideoService.queryDay(map);
		for(int i=0;i<videoList.size();i++){
			recordDayWebVideoService.save(videoList.get(i));
		}
		List<RecordDayGameEntity> gameList = recordDayGameService.queryDay(map);
		for(int i=0;i<gameList.size();i++){
			recordDayGameService.save(gameList.get(i));
		}
	}

	//首页
	public void index(){
		try {
			List<Map<String,Object>> paramList = new LinkedList<Map<String,Object>>();
			InputStream in =new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath())) ;
			Properties prop = new Properties();
			prop.load(in);
			List<Date> mouths = DateUtils.getLastMouths(Integer.parseInt(prop.getProperty("queryMouthRange")));

			String startDate = DateUtils.format(DateUtils.setStartEndDay(mouths.get(0),0));
			String endDate = DateUtils.format(DateUtils.setStartEndDay(mouths.get(mouths.size()-1),1));;

			indexHistogramViewService.saveConnectivityScore(mouths);
			indexHistogramViewService.saveDownLoadScore(mouths);
			indexHistogramViewService.saveGameScore(mouths);
			indexHistogramViewService.saveNetworkLayerScore(mouths);
			indexHistogramViewService.saveWebPageScore(mouths);
			indexHistogramViewService.saveWebVideoScore(mouths);


			indexLineViewService.saveConnectivityScore(mouths,"2000");
			indexLineViewService.saveDownLoadScore(mouths,"2000");
			indexLineViewService.saveGameScore(mouths,"2000");
			indexLineViewService.saveNetworkLayerScore(mouths,"2000");
			indexLineViewService.saveWebPageScore(mouths,"2000");
			indexLineViewService.saveWebVideoScore(mouths,"2000");



			indexMapViewService.saveScore(startDate,endDate,1);

			indexRadaViewService.saveWebVideoScore(startDate,endDate,1);
			indexRadaViewService.saveWebPageScore(startDate,endDate,1);
			indexRadaViewService.saveGameScore(startDate,endDate,1);
			indexRadaViewService.saveDownLoadScore(startDate,endDate,1);
			indexRadaViewService.saveConnectivityScore(startDate,endDate,1);
			indexRadaViewService.saveNetworkLayerScore(startDate,endDate,1);

			indexRankingViewService.saveDownLoadScore(startDate,endDate,1);
			indexRankingViewService.saveGameScore(startDate,endDate,1);
			indexRankingViewService.saveWebPageScore(startDate,endDate,1);
			indexRankingViewService.saveWebVideoScore(startDate,endDate,1);
			indexRankingViewService.saveConnectivityScore(startDate,endDate,1);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}





}
