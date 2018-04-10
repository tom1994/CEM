package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TargetDao;
import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.cem.service.TargetService;



@Service("targetService")
public class TargetServiceImpl implements TargetService {
	@Autowired
	private TargetDao targetDao;
	
	@Override
	public TargetEntity queryObject(Integer id){
		return targetDao.queryObject(id);
	}

	@Override
	public List<TargetEntity> infoBatch(Integer serviceId){
		return targetDao.infoBatch(serviceId); }
	
	@Override
	public List<TargetEntity> queryList(Map<String, Object> map){
		return targetDao.queryList(map);
	}

	@Override
	public List<TargetEntity> queryTargetNames(int[] ids){
		return targetDao.queryTargetNames(ids);
	}


	@Override
	public List<TargetEntity> queryTgByTList(Map<String, Object> map) {
		return targetDao.queryTgByTList(map);
	}

	@Override
	public List<TargetEntity> queryTargetList(Integer spId){
		return targetDao.queryTargetList(spId);
	}

	@Override
	public List<TargetEntity> queryTargetListByGroup(Integer id){
		return targetDao.queryTargetListByGroup(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return targetDao.queryTotal(map);
	}

	@Override
	public int queryExist(String targetName){
		return targetDao.queryExist(targetName);
	}
	
	@Override
	public void save(TargetEntity target){
		targetDao.save(target);
	}
	
	@Override
	public void update(TargetEntity target){
		targetDao.update(target);
	}
	
	@Override
	public void delete(Integer id){
		targetDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		targetDao.deleteBatch(ids);
	}
	
}
