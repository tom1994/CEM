package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.AreasDao;
import io.cem.modules.cem.entity.AreasEntity;
import io.cem.modules.cem.service.AreasService;



@Service("areasService")
public class AreasServiceImpl implements AreasService {
	@Autowired
	private AreasDao areasDao;
	
	@Override
	public AreasEntity queryObject(Integer id){
		return areasDao.queryObject(id);
	}
	
	@Override
	public List<AreasEntity> queryList(Map<String, Object> map){
		return areasDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return areasDao.queryTotal(map);
	}
	
	@Override
	public void save(AreasEntity areas){
		areasDao.save(areas);
	}
	
	@Override
	public void update(AreasEntity areas){
		areasDao.update(areas);
	}
	
	@Override
	public void delete(Integer id){
		areasDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		areasDao.deleteBatch(ids);
	}
	
}
