package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.cem.modules.cem.dao.RecordPingDao;
import io.cem.modules.cem.dao.RecordHourPingDao;
import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.cem.service.RecordPingService;



@Service("recordPingService")
public class RecordPingServiceImpl implements RecordPingService {
	@Autowired
	private RecordPingDao recordPingDao;

	@Autowired
	private RecordHourPingDao recordHourPingDao;

	@Override
	public RecordPingEntity queryObject(Integer id){
		return recordPingDao.queryObject(id);
	}
	
	@Override
	public List<RecordPingEntity> queryList(Map<String, Object> map){
		return recordPingDao.queryList(map);
	}

	@Override
	public List<RecordPingEntity> queryPingList(Map<String, Object> map){
		return recordPingDao.queryPingList(map);
	}

	@Override
	public List<RecordHourPingEntity> queryIntervalList(Map<String, Object> map){
		int interval = Integer.parseInt(map.get("interval").toString());
//		int start = 0;
//		Date startTime = new Date();
//		DateFormat df = new SimpleDateFormat("HH:mm:ss");
//		try {
//			startTime = df.parse(st);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			System.out.println("输入有误！！！！");
//		}
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(startTime);
		List<RecordHourPingEntity> recordPingList = new ArrayList<>();
		for (int i=0; i<=24-interval; i=i+interval){
			if (i<10){
				String st = "0"+i;
				map.put("startTime", st);
			} else{
				map.put("startTime",i);
			}
			int j = i+interval;
			if(j<10){
				String et = "0"+j;
				map.put("terminalTime", et);
			} else{
				map.put("terminalTime",j);
			}
//			map.put("startTime", df.format(calendar));
//			calendar.add(Calendar.HOUR_OF_DAY, interval);
//			map.put("terminalTime", df.format(calendar));
//			int tmp = Integer.parseInt(String.valueOf(st.charAt(1));
			List<RecordHourPingEntity> recordHourPing = recordHourPingDao.queryIntervalList(map);
			for(int k=0; k<recordHourPing.size(); k++){
				recordHourPing.get(k).setTimeRange(i+":00-"+j+":00");
			}
			recordPingList.addAll(recordHourPing);
		}
		return recordPingList;
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordPingDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordPingEntity recordPing){
		recordPingDao.save(recordPing);
	}
	
	@Override
	public void update(RecordPingEntity recordPing){
		recordPingDao.update(recordPing);
	}
	
	@Override
	public void delete(Integer id){
		recordPingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordPingDao.deleteBatch(ids);
	}
	
}
