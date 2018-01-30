package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayWebVideoDao;
import io.cem.modules.cem.entity.RecordDayWebVideoEntity;
import io.cem.modules.cem.service.RecordDayWebVideoService;



@Service("recordDayWebVideoService")
public class RecordDayWebVideoServiceImpl implements RecordDayWebVideoService {
	@Autowired
	private RecordDayWebVideoDao recordDayWebVideoDao;
	
	@Override
	public RecordDayWebVideoEntity queryObject(Integer id){
		return recordDayWebVideoDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayWebVideoEntity> queryList(Map<String, Object> map){
		return recordDayWebVideoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayWebVideoDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayWebVideoEntity recordDayWebVideo){
		recordDayWebVideoDao.save(recordDayWebVideo);
	}
	
	@Override
	public void update(RecordDayWebVideoEntity recordDayWebVideo){
		recordDayWebVideoDao.update(recordDayWebVideo);
	}
	
	@Override
	public void delete(Integer id){
		recordDayWebVideoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayWebVideoDao.deleteBatch(ids);
	}
	
}
