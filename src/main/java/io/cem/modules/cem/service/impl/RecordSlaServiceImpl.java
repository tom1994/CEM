package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordSlaDao;
import io.cem.modules.cem.entity.RecordSlaEntity;
import io.cem.modules.cem.service.RecordSlaService;



@Service("recordSlaService")
public class RecordSlaServiceImpl implements RecordSlaService {
	@Autowired
	private RecordSlaDao recordSlaDao;
	
	@Override
	public RecordSlaEntity queryObject(Integer id){
		return recordSlaDao.queryObject(id);
	}
	
	@Override
	public List<RecordSlaEntity> queryList(Map<String, Object> map){
		return recordSlaDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordSlaDao.queryTotal(map);
	}

	@Override
	public List<RecordSlaEntity> querySlaTest(Map<String, Object> map){
		return recordSlaDao.querySlaTest(map);
	}

	@Override
	public void save(RecordSlaEntity recordSla){
		recordSlaDao.save(recordSla);
	}
	
	@Override
	public void update(RecordSlaEntity recordSla){
		recordSlaDao.update(recordSla);
	}
	
	@Override
	public void delete(Integer id){
		recordSlaDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordSlaDao.deleteBatch(ids);
	}
	
}
