package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourWebVideoDao;
import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
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

	@Autowired
	private RecordHourWebVideoDao recordHourWebVideoDao;
	
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
	public List<RecordWebVideoEntity> queryWebVideoList(Map<String, Object> map) {
		return recordWebVideoDao.queryWebVideoList(map);
	}

	@Override
	public List<RecordHourWebVideoEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourWebVideoDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourWebVideoDao.queryIntervalTotal(map);
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
