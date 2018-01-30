package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordPingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayPingDao;
import io.cem.modules.cem.entity.RecordDayPingEntity;
import io.cem.modules.cem.service.RecordDayPingService;



@Service("recordDayPingService")
public class RecordDayPingServiceImpl implements RecordDayPingService {
	@Autowired
	private RecordDayPingDao recordDayPingDao;
	@Autowired
	private RecordPingDao recordPingDao;

	@Override
	public RecordDayPingEntity queryObject(Integer id){
		return recordDayPingDao.queryObject(id);
	}

	@Override
	public List<RecordDayPingEntity> queryList(Map<String, Object> map){
		return recordDayPingDao.queryList(map);
	}

	@Override
	public List<RecordDayPingEntity> queryDay(Map<String, Object> map){
		return recordPingDao.queryDay(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayPingDao.queryTotal(map);
	}

	@Override
	public void save(RecordDayPingEntity recordDayPing){
		recordDayPingDao.save(recordDayPing);
	}
	
	@Override
	public void update(RecordDayPingEntity recordDayPing){
		recordDayPingDao.update(recordDayPing);
	}

	@Override
	public void delete(Integer id){
		recordDayPingDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		recordDayPingDao.deleteBatch(ids);
	}
	
}
