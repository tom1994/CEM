package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordWebPageDao;
import io.cem.modules.cem.entity.RecordWebPageEntity;
import io.cem.modules.cem.service.RecordWebPageService;



@Service("recordWebPageService")
public class RecordWebPageServiceImpl implements RecordWebPageService {
	@Autowired
	private RecordWebPageDao recordWebPageDao;
	
	@Override
	public RecordWebPageEntity queryObject(Integer id){
		return recordWebPageDao.queryObject(id);
	}
	
	@Override
	public List<RecordWebPageEntity> queryList(Map<String, Object> map){
		return recordWebPageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordWebPageDao.queryTotal(map);
	}

	@Override
	public List<RecordWebPageEntity> queryWebPageTest(Map<String, Object> map){
		return recordWebPageDao.queryWebPageTest(map);
	}

	@Override
	public void save(RecordWebPageEntity recordWebPage){
		recordWebPageDao.save(recordWebPage);
	}
	
	@Override
	public void update(RecordWebPageEntity recordWebPage){
		recordWebPageDao.update(recordWebPage);
	}
	
	@Override
	public void delete(Integer id){
		recordWebPageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordWebPageDao.deleteBatch(ids);
	}
	
}
