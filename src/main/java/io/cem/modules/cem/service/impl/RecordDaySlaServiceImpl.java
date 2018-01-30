package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDaySlaDao;
import io.cem.modules.cem.entity.RecordDaySlaEntity;
import io.cem.modules.cem.service.RecordDaySlaService;



@Service("recordDaySlaService")
public class RecordDaySlaServiceImpl implements RecordDaySlaService {
	@Autowired
	private RecordDaySlaDao recordDaySlaDao;
	
	@Override
	public RecordDaySlaEntity queryObject(Integer id){
		return recordDaySlaDao.queryObject(id);
	}
	
	@Override
	public List<RecordDaySlaEntity> queryList(Map<String, Object> map){
		return recordDaySlaDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDaySlaDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDaySlaEntity recordDaySla){
		recordDaySlaDao.save(recordDaySla);
	}
	
	@Override
	public void update(RecordDaySlaEntity recordDaySla){
		recordDaySlaDao.update(recordDaySla);
	}
	
	@Override
	public void delete(Integer id){
		recordDaySlaDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDaySlaDao.deleteBatch(ids);
	}
	
}
