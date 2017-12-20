package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.AlarmRecordDao;
import io.cem.modules.cem.entity.AlarmRecordEntity;
import io.cem.modules.cem.service.AlarmRecordService;



@Service("alarmRecordService")
public class AlarmRecordServiceImpl implements AlarmRecordService {
	@Autowired
	private AlarmRecordDao alarmRecordDao;
	
	@Override
	public AlarmRecordEntity queryObject(Integer id){
		return alarmRecordDao.queryObject(id);
	}
	
	@Override
	public List<AlarmRecordEntity> queryList(Map<String, Object> map){
		return alarmRecordDao.queryList(map);
	}

	@Override
	public List<AlarmRecordEntity> queryAlarmRecordList(Map<String, Object> map){
		return alarmRecordDao.queryAlarmRecordList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return alarmRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(AlarmRecordEntity alarmRecord){
		alarmRecordDao.save(alarmRecord);
	}
	
	@Override
	public void update(AlarmRecordEntity alarmRecord){
		alarmRecordDao.update(alarmRecord);
	}
	
	@Override
	public void delete(Integer id){
		alarmRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		alarmRecordDao.deleteBatch(ids);
	}
	
}
