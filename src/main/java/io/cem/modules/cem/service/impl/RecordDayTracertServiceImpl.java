package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordTracertDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayTracertDao;
import io.cem.modules.cem.entity.RecordDayTracertEntity;
import io.cem.modules.cem.service.RecordDayTracertService;



@Service("recordDayTracertService")
public class RecordDayTracertServiceImpl implements RecordDayTracertService {
	@Autowired
	private RecordDayTracertDao recordDayTracertDao;
	@Autowired
	private RecordTracertDao recordTracertDao;
	
	@Override
	public RecordDayTracertEntity queryObject(Integer id){
		return recordDayTracertDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayTracertEntity> queryList(Map<String, Object> map){
		return recordDayTracertDao.queryList(map);
	}

	@Override
	public List<RecordDayTracertEntity> queryDay(Map<String,Object> map) {return recordTracertDao.queryDay(map);}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayTracertDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayTracertEntity recordDayTracert){
		recordDayTracertDao.save(recordDayTracert);
	}
	
	@Override
	public void update(RecordDayTracertEntity recordDayTracert){
		recordDayTracertDao.update(recordDayTracert);
	}
	
	@Override
	public void delete(Integer id){
		recordDayTracertDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayTracertDao.deleteBatch(ids);
	}
	
}
