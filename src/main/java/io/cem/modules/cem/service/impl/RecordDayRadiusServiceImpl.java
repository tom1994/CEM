package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayRadiusDao;
import io.cem.modules.cem.entity.RecordDayRadiusEntity;
import io.cem.modules.cem.service.RecordDayRadiusService;



@Service("recordDayRadiusService")
public class RecordDayRadiusServiceImpl implements RecordDayRadiusService {
	@Autowired
	private RecordDayRadiusDao recordDayRadiusDao;
	
	@Override
	public RecordDayRadiusEntity queryObject(Integer id){
		return recordDayRadiusDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayRadiusEntity> queryList(Map<String, Object> map){
		return recordDayRadiusDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayRadiusDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayRadiusEntity recordDayRadius){
		recordDayRadiusDao.save(recordDayRadius);
	}
	
	@Override
	public void update(RecordDayRadiusEntity recordDayRadius){
		recordDayRadiusDao.update(recordDayRadius);
	}
	
	@Override
	public void delete(Integer id){
		recordDayRadiusDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayRadiusDao.deleteBatch(ids);
	}
	
}
