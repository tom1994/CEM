package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.CountyDao;
import io.cem.modules.cem.entity.CountyEntity;
import io.cem.modules.cem.service.CountyService;



@Service("countyService")
public class CountyServiceImpl implements CountyService {
	@Autowired
	private CountyDao countyDao;

	@Override
	public CountyEntity queryObject(Integer id){
		return countyDao.queryObject(id);
	}

	@Override
	public List<CountyEntity> queryCountyList(Integer id){
		return countyDao.queryCountyList(id);
	}
	
	@Override
	public List<CountyEntity> queryList(Map<String, Object> map){
		return countyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return countyDao.queryTotal(map);
	}
	
	@Override
	public void save(CountyEntity county){
		countyDao.save(county);
	}
	
	@Override
	public void update(CountyEntity county){
		countyDao.update(county);
	}
	
	@Override
	public void delete(Integer id){
		countyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		countyDao.deleteBatch(ids);
	}
	
}
