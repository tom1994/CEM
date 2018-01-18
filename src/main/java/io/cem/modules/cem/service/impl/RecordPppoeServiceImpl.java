package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordPppoeDao;
import io.cem.modules.cem.entity.RecordPppoeEntity;
import io.cem.modules.cem.service.RecordPppoeService;



@Service("recordPppoeService")
public class RecordPppoeServiceImpl implements RecordPppoeService {
	@Autowired
	private RecordPppoeDao recordPppoeDao;
	
	@Override
	public RecordPppoeEntity queryObject(Integer id){
		return recordPppoeDao.queryObject(id);
	}
	
	@Override
	public List<RecordPppoeEntity> queryList(Map<String, Object> map){
		return recordPppoeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordPppoeDao.queryTotal(map);
	}

	@Override
	public List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map){
		return recordPppoeDao.queryPppoeTest(map);
	}


	@Override
	public void save(RecordPppoeEntity recordPppoe){
		recordPppoeDao.save(recordPppoe);
	}
	
	@Override
	public void update(RecordPppoeEntity recordPppoe){
		recordPppoeDao.update(recordPppoe);
	}
	
	@Override
	public void delete(Integer id){
		recordPppoeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordPppoeDao.deleteBatch(ids);
	}
	
}
