package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourRadiusDao;
import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordRadiusDao;
import io.cem.modules.cem.entity.RecordRadiusEntity;
import io.cem.modules.cem.service.RecordRadiusService;



@Service("recordRadiusService")
public class RecordRadiusServiceImpl implements RecordRadiusService {
	@Autowired
	private RecordRadiusDao recordRadiusDao;

	@Autowired
	private RecordHourRadiusDao recordHourRadiusDao;

	@Override
	public RecordRadiusEntity queryObject(Integer id){
		return recordRadiusDao.queryObject(id);
	}
	
	@Override
	public List<RecordRadiusEntity> queryList(Map<String, Object> map){
		return recordRadiusDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordRadiusDao.queryTotal(map);
	}

	@Override
	public List<RecordRadiusEntity> queryRadiusTest(Map<String, Object> map){
		return recordRadiusDao.queryRadiusTest(map);
	}

	@Override
	public List<RecordRadiusEntity> queryRadiusList(Map<String, Object> map) {
		return recordRadiusDao.queryRadiusList(map);
	}

	@Override
	public List<RecordHourRadiusEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourRadiusDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourRadiusDao.queryIntervalTotal(map);
	}

	@Override
	public void save(RecordRadiusEntity recordRadius){
		recordRadiusDao.save(recordRadius);
	}
	
	@Override
	public void update(RecordRadiusEntity recordRadius){
		recordRadiusDao.update(recordRadius);
	}
	
	@Override
	public void delete(Integer id){
		recordRadiusDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordRadiusDao.deleteBatch(ids);
	}
	
}
