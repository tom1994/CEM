package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourPingDao;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.service.RecordHourPingService;



@Service("recordHourPingService")
public class RecordHourPingServiceImpl implements RecordHourPingService {
	@Autowired
	private RecordHourPingDao recordHourPingDao;
	
	@Override
	public RecordHourPingEntity queryObject(Integer id){
		return recordHourPingDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourPingEntity> queryList(Map<String, Object> map){
		return recordHourPingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourPingDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourPingEntity recordHourPing){
		recordHourPingDao.save(recordHourPing);
	}
	
	@Override
	public void update(RecordHourPingEntity recordHourPing){
		recordHourPingDao.update(recordHourPing);
	}
	
	@Override
	public void delete(Integer id){
		recordHourPingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourPingDao.deleteBatch(ids);
	}
	
}
