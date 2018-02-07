package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordPppoeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayPppoeDao;
import io.cem.modules.cem.entity.RecordDayPppoeEntity;
import io.cem.modules.cem.service.RecordDayPppoeService;



@Service("recordDayPppoeService")
public class RecordDayPppoeServiceImpl implements RecordDayPppoeService {
	@Autowired
	private RecordDayPppoeDao recordDayPppoeDao;
	@Autowired
	private RecordPppoeDao recordPppoeDao;
	
	@Override
	public RecordDayPppoeEntity queryObject(Integer id){
		return recordDayPppoeDao.queryObject(id);
	}

	@Override
	public List<RecordDayPppoeEntity> queryDay(Map<String,Object> map) {return recordPppoeDao.queryDay(map);}

	@Override
	public List<RecordDayPppoeEntity> queryList(Map<String, Object> map){
		return recordDayPppoeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayPppoeDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayPppoeEntity recordDayPppoe){
		recordDayPppoeDao.save(recordDayPppoe);
	}
	
	@Override
	public void update(RecordDayPppoeEntity recordDayPppoe){
		recordDayPppoeDao.update(recordDayPppoe);
	}
	
	@Override
	public void delete(Integer id){
		recordDayPppoeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayPppoeDao.deleteBatch(ids);
	}
	
}
