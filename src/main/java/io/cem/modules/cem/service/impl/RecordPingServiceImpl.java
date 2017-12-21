package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordPingDao;
import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.cem.service.RecordPingService;



@Service("recordPingService")
public class RecordPingServiceImpl implements RecordPingService {
	@Autowired
	private RecordPingDao recordPingDao;
	
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
