package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourRadiusDao;
import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.cem.service.RecordHourRadiusService;



@Service("recordHourRadiusService")
public class RecordHourRadiusServiceImpl implements RecordHourRadiusService {
	@Autowired
	private RecordHourRadiusDao recordHourRadiusDao;
	
	@Override
	public RecordHourRadiusEntity queryObject(Integer id){
		return recordHourRadiusDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourRadiusEntity> queryList(Map<String, Object> map){
		return recordHourRadiusDao.queryList(map);
	}

	@Override
	public List<RecordHourRadiusEntity> queryRadiusList(Map<String, Object> map){
		return recordHourRadiusDao.queryRadiusList(map);
	}

	@Override
	public List<RecordHourRadiusEntity> queryDayList(Map<String, Object> map){
		return recordHourRadiusDao.queryDayList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourRadiusDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourRadiusEntity recordHourRadius){
		recordHourRadiusDao.save(recordHourRadius);
	}
	
	@Override
	public void update(RecordHourRadiusEntity recordHourRadius){
		recordHourRadiusDao.update(recordHourRadius);
	}
	
	@Override
	public void delete(Integer id){
		recordHourRadiusDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourRadiusDao.deleteBatch(ids);
	}
	
}
