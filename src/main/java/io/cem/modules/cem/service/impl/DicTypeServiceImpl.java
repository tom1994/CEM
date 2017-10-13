package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.DicTypeDao;
import io.cem.modules.cem.entity.DicTypeEntity;
import io.cem.modules.cem.service.DicTypeService;



@Service("dicTypeService")
public class DicTypeServiceImpl implements DicTypeService {
	@Autowired
	private DicTypeDao dicTypeDao;
	
	@Override
	public DicTypeEntity queryObject(Integer id){
		return dicTypeDao.queryObject(id);
	}
	
	@Override
	public List<DicTypeEntity> queryList(Map<String, Object> map){
		return dicTypeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dicTypeDao.queryTotal(map);
	}
	
	@Override
	public void save(DicTypeEntity dicType){
		dicTypeDao.save(dicType);
	}
	
	@Override
	public void update(DicTypeEntity dicType){
		dicTypeDao.update(dicType);
	}
	
	@Override
	public void delete(Integer id){
		dicTypeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		dicTypeDao.deleteBatch(ids);
	}
	
}
