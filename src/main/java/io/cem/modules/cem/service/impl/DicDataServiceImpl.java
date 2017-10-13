package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.DicDataDao;
import io.cem.modules.cem.entity.DicDataEntity;
import io.cem.modules.cem.service.DicDataService;



@Service("dicDataService")
public class DicDataServiceImpl implements DicDataService {
	@Autowired
	private DicDataDao dicDataDao;
	
	@Override
	public DicDataEntity queryObject(Integer id){
		return dicDataDao.queryObject(id);
	}
	
	@Override
	public List<DicDataEntity> queryList(Map<String, Object> map){
		return dicDataDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dicDataDao.queryTotal(map);
	}
	
	@Override
	public void save(DicDataEntity dicData){
		dicDataDao.save(dicData);
	}
	
	@Override
	public void update(DicDataEntity dicData){
		dicDataDao.update(dicData);
	}
	
	@Override
	public void delete(Integer id){
		dicDataDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		dicDataDao.deleteBatch(ids);
	}
	
}
