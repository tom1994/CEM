package io.cem.modules.sys.task;

import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import io.cem.modules.sys.entity.SysUserEntity;
import io.cem.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 测试定时任务(演示Demo，可删除)
 * 
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
	
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		
		try {
			Thread.sleep(1000L);
			SysUserEntity user = sysUserService.queryObject(1L);
			System.out.println(ToStringBuilder.reflectionToString(user));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void test2(){
		logger.info("我是不带参数的test2方法，正在被执行");
	}

//	ping按1小时压
	public void pingHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourPingEntity> list = recordHourPingService.queryPing(map);
		for(int i=0;i<list.size();i++){
			recordHourPingService.save(list.get(i));
		}
	}
	//ping按1天压
	public void pingDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayPingEntity> list = recordDayPingService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayPingService.save(list.get(i));
		}
	}

	//trace route按1小时压
	public void tracertHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourTracertEntity> list = recordHourTracertService.queryTracert(map);
		for(int i=0;i<list.size();i++){
			recordHourTracertService.save(list.get(i));
		}
	}
	//trace route按1天压
	public void tracertDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayTracertEntity> list = recordDayTracertService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayTracertService.save(list.get(i));
		}
	}

	//sla按1小时压
	public void slaHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourSlaEntity> list = recordHourSlaService.querySla(map);
		for(int i=0;i<list.size();i++){
			recordHourSlaService.save(list.get(i));
		}
	}
	//sla按1天压
	public void slaDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDaySlaEntity> list = recordDaySlaService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDaySlaService.save(list.get(i));
		}
	}

	//dns按1小时压
	public void dnsHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourDnsEntity> list = recordHourDnsService.queryDns(map);
		for(int i=0;i<list.size();i++){
			recordHourDnsService.save(list.get(i));
		}
	}
	//dns按1天压
	public void dnsDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayDnsEntity> list = recordDayDnsService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayDnsService.save(list.get(i));
		}
	}

	//dhcp按1小时压
	public void dhcpHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourDhcpEntity> list = recordHourDhcpService.queryDhcp(map);
		for(int i=0;i<list.size();i++){
			recordHourDhcpService.save(list.get(i));
		}
	}
	//dhcp按1天压
	public void dhcpDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayDhcpEntity> list = recordDayDhcpService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayDhcpService.save(list.get(i));
		}
	}

	//pppoe按1小时压
	public void pppoeHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourPppoeEntity> list = recordHourPppoeService.queryPppoe(map);
		for(int i=0;i<list.size();i++){
			recordHourPppoeService.save(list.get(i));
		}
	}
	//pppoe按1天压
	public void pppoeDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayPppoeEntity> list = recordDayPppoeService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayPppoeService.save(list.get(i));
		}
	}

	//radius按1小时压
	public void radiusHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourRadiusEntity> list = recordHourRadiusService.queryRadius(map);
		for(int i=0;i<list.size();i++){
			recordHourRadiusService.save(list.get(i));
		}
	}
	//radius按1天压
	public void radiusDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayRadiusEntity> list = recordDayRadiusService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayRadiusService.save(list.get(i));
		}
	}

	//web page按1小时压
	public void webPageHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourWebPageEntity> list = recordHourWebPageService.queryWebPage(map);
		for(int i=0;i<list.size();i++){
			recordHourWebPageService.save(list.get(i));
		}
	}
	//web page按1天压
	public void webPageDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayWebPageEntity> list = recordDayWebPageService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayWebPageService.save(list.get(i));
		}
	}
	//ftp按1小时压
	public void ftpHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourFtpEntity> list = recordHourFtpService.queryFtp(map);
		for(int i=0;i<list.size();i++){
			recordHourFtpService.save(list.get(i));
		}
	}
	//ftp按1天压
	public void ftpDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayFtpEntity> list = recordDayFtpService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayFtpService.save(list.get(i));
		}
	}

	//web download按1小时压
	public void webDownloadHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourWebDownloadEntity> list = recordHourWebDownloadService.queryWebDownload(map);
		for(int i=0;i<list.size();i++){
			recordHourWebDownloadService.save(list.get(i));
		}
	}
	//web download按1天压
	public void webDownloadDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayWebDownloadEntity> list = recordDayWebDownloadService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayWebDownloadService.save(list.get(i));
		}
	}

	//web video按1小时压
	public void webVideoHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourWebVideoEntity> list = recordHourWebVideoService.queryWebVideo(map);
		for(int i=0;i<list.size();i++){
			recordHourWebVideoService.save(list.get(i));
		}
	}
	//web video按1天压
	public void webVideoDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayWebVideoEntity> list = recordDayWebVideoService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayWebVideoService.save(list.get(i));
		}
	}

	//game按1小时压
	public void gameHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourGameEntity> list = recordHourGameService.queryGame(map);
		for(int i=0;i<list.size();i++){
			recordHourGameService.save(list.get(i));
		}
	}
	//game按1天压
	public void gameDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayGameEntity> list = recordDayGameService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayGameService.save(list.get(i));
		}
	}





}
