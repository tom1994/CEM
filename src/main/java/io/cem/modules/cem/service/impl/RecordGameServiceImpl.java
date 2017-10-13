package io.cem.modules.cem.service.impl;

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
