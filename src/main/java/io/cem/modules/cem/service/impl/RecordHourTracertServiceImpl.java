package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourTracertDao;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.service.RecordHourTracertService;



@Service("recordHourTracertService")
public class RecordHourTracertServiceImpl implements RecordHourTracertService {
	@Autowired
	private RecordHourTracertDao recordHourTracertDao;
	
	@Override
	public RecordHourTracertEntity queryObject(Integer id){
		return recordHourTracertDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourTracertEntity> queryList(Map<String, Object> map){
		return recordHourTracertDao.queryList(map);
	}

	@Override
	public List<RecordHourTracertEntity> queryTracertList(Map<String, Object> map){
		return recordHourTracertDao.queryTracertList(map);
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourTracertDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourTracertEntity recordHourTracert){
		recordHourTracertDao.save(recordHourTracert);
	}
	
	@Override
	public void update(RecordHourTracertEntity recordHourTracert){
		recordHourTracertDao.update(recordHourTracert);
	}
	
	@Override
	public void delete(Integer id){
		recordHourTracertDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourTracertDao.deleteBatch(ids);
	}
	
}
