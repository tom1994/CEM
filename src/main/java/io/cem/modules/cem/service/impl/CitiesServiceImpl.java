package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.CitiesDao;
import io.cem.modules.cem.entity.CitiesEntity;
import io.cem.modules.cem.service.CitiesService;



@Service("citiesService")
public class CitiesServiceImpl implements CitiesService {
	@Autowired
	private CitiesDao citiesDao;
	
	@Override
	public CitiesEntity queryObject(Integer id){
		return citiesDao.queryObject(id);
	}
	
	@Override
	public List<CitiesEntity> queryList(Map<String, Object> map){
		return citiesDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return citiesDao.queryTotal(map);
	}
	
	@Override
	public void save(CitiesEntity cities){
		citiesDao.save(cities);
	}
	
	@Override
	public void update(CitiesEntity cities){
		citiesDao.update(cities);
	}
	
	@Override
	public void delete(Integer id){
		citiesDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		citiesDao.deleteBatch(ids);
	}
	
}
