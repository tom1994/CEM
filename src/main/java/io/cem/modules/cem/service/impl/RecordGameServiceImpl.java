package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourGameDao;
import io.cem.modules.cem.entity.RecordHourGameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordGameDao;
import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.cem.service.RecordGameService;



@Service("recordGameService")
public class RecordGameServiceImpl implements RecordGameService {
	@Autowired
	private RecordGameDao recordGameDao;

	@Autowired
	private RecordHourGameDao recordHourGameDao;
	
	@Override
	public RecordGameEntity queryObject(Integer id){
		return recordGameDao.queryObject(id);
	}
	
	@Override
	public List<RecordGameEntity> queryList(Map<String, Object> map){
		return recordGameDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordGameDao.queryTotal(map);
	}

	@Override
	public List<RecordGameEntity> queryGameTest(Map<String, Object> map){
		return recordGameDao.queryGameTest(map);
	}

	@Override
	public List<RecordGameEntity> queryGameList(Map<String, Object> map) {
		return recordGameDao.queryGameList(map);
	}

	@Override
	public List<RecordHourGameEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourGameDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourGameDao.queryIntervalTotal(map);
	}
	
	@Override
	public void save(RecordGameEntity recordGame){
		recordGameDao.save(recordGame);
	}
	
	@Override
	public void update(RecordGameEntity recordGame){
		recordGameDao.update(recordGame);
	}
	
	@Override
	public void delete(Integer id){
		recordGameDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordGameDao.deleteBatch(ids);
	}
	
}
