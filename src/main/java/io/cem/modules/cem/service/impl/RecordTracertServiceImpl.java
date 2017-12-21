package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordTracertDao;
import io.cem.modules.cem.entity.RecordTracertEntity;
import io.cem.modules.cem.service.RecordTracertService;



@Service("recordTracertService")
public class RecordTracertServiceImpl implements RecordTracertService {
	@Autowired
	private RecordTracertDao recordTracertDao;
	
	@Override
	public RecordTracertEntity queryObject(Integer id){
		return recordTracertDao.queryObject(id);
	}
	
	@Override
	public List<RecordTracertEntity> queryList(Map<String, Object> map){
		return recordTracertDao.queryList(map);
	}

	@Override
	public List<RecordTracertEntity> queryTracertList(Map<String, Object> map){
		return recordTracertDao.queryTracertList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordTracertDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordTracertEntity recordTracert){
		recordTracertDao.save(recordTracert);
	}
	
	@Override
	public void update(RecordTracertEntity recordTracert){
		recordTracertDao.update(recordTracert);
	}
	
	@Override
	public void delete(Integer id){
		recordTracertDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordTracertDao.deleteBatch(ids);
	}
	
}
