package io.cem.modules.sys.task;

import io.cem.modules.cem.entity.RecordDayPingEntity;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.service.RecordDayPingService;
import io.cem.modules.cem.service.RecordHourPingService;
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

	public void pingHour(){
		Map<String,Object> map = recordHourPingService.queryTime();
		System.out.println(map);
		List<RecordHourPingEntity> list = recordHourPingService.queryPing(map);
		for(int i=0;i<list.size();i++){
			recordHourPingService.save(list.get(i));
		}
	}

	public void pingDay(){
		Map<String,Object> map = recordHourPingService.queryDay();
		System.out.println(map);
		List<RecordDayPingEntity> list = recordDayPingService.queryDay(map);
		for(int i=0;i<list.size();i++){
			recordDayPingService.save(list.get(i));
		}
	}

}
