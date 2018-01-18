package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordWebVideoDao;
import io.cem.modules.cem.entity.RecordWebVideoEntity;
import io.cem.modules.cem.service.RecordWebVideoService;



@Service("recordWebVideoService")
public class RecordWebVideoServiceImpl implements RecordWebVideoService {
	@Autowired
	private RecordWebVideoDao recordWebVideoDao;
	
	@Override
	public RecordWebVideoEntity queryObject(Integer id){
		return recordWebVideoDao.queryObject(id);
	}
	
	@Override
	public List<RecordWebVideoEntity> queryList(Map<String, Object> map){
		return recordWebVideoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordWebVideoDao.queryTotal(map);
	}

	@Override
	public List<RecordWebVideoEntity> queryWebVideoTest(Map<String, Object> map){
		return recordWebVideoDao.queryWebVideoTest(map);
	}

	@Override
	public void save(RecordWebVideoEntity recordWebVideo){
		recordWebVideoDao.save(recordWebVideo);
	}
	
	@Override
	public void update(RecordWebVideoEntity recordWebVideo){
		recordWebVideoDao.update(recordWebVideo);
	}
	
	@Override
	public void delete(Integer id){
		recordWebVideoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordWebVideoDao.deleteBatch(ids);
	}
	
}
