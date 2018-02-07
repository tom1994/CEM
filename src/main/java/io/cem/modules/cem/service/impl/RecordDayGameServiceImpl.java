package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordGameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayGameDao;
import io.cem.modules.cem.entity.RecordDayGameEntity;
import io.cem.modules.cem.service.RecordDayGameService;



@Service("recordDayGameService")
public class RecordDayGameServiceImpl implements RecordDayGameService {
	@Autowired
	private RecordDayGameDao recordDayGameDao;
	@Autowired
	private RecordGameDao recordGameDao;
	
	@Override
	public RecordDayGameEntity queryObject(Integer id){
		return recordDayGameDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayGameEntity> queryList(Map<String, Object> map){
		return recordDayGameDao.queryList(map);
	}

	@Override
	public List<RecordDayGameEntity> queryDay(Map<String,Object> map) {return recordGameDao.queryDay(map);}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayGameDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayGameEntity recordDayGame){
		recordDayGameDao.save(recordDayGame);
	}
	
	@Override
	public void update(RecordDayGameEntity recordDayGame){
		recordDayGameDao.update(recordDayGame);
	}
	
	@Override
	public void delete(Integer id){
		recordDayGameDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayGameDao.deleteBatch(ids);
	}
	
}
