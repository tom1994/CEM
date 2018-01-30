package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayWebPageDao;
import io.cem.modules.cem.entity.RecordDayWebPageEntity;
import io.cem.modules.cem.service.RecordDayWebPageService;



@Service("recordDayWebPageService")
public class RecordDayWebPageServiceImpl implements RecordDayWebPageService {
	@Autowired
	private RecordDayWebPageDao recordDayWebPageDao;
	
	@Override
	public RecordDayWebPageEntity queryObject(Integer id){
		return recordDayWebPageDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayWebPageEntity> queryList(Map<String, Object> map){
		return recordDayWebPageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayWebPageDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayWebPageEntity recordDayWebPage){
		recordDayWebPageDao.save(recordDayWebPage);
	}
	
	@Override
	public void update(RecordDayWebPageEntity recordDayWebPage){
		recordDayWebPageDao.update(recordDayWebPage);
	}
	
	@Override
	public void delete(Integer id){
		recordDayWebPageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayWebPageDao.deleteBatch(ids);
	}
	
}
