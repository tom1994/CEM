package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TargetGroupDao;
import io.cem.modules.cem.entity.TargetGroupEntity;
import io.cem.modules.cem.service.TargetGroupService;
import org.springframework.transaction.annotation.Transactional;


@Service("targetGroupService")
public class TargetGroupServiceImpl implements TargetGroupService {
	@Autowired
	private TargetGroupDao targetGroupDao;
	
	@Override
	public TargetGroupEntity queryObject(Integer id){
		return targetGroupDao.queryObject(id);
	}
	
	@Override
	public List<TargetGroupEntity> queryList(Map<String, Object> map){
		return targetGroupDao.queryList(map);
	}

	@Override
	public List<TargetGroupEntity> queryByTgNameList(Map<String, Object> map){
		return targetGroupDao.queryByTgNameList(map);
	}


	@Override
	public List<TargetGroupEntity> queryTGList(Integer id){
		return targetGroupDao.queryTGList(id);
	}

	@Override
	public int queryExist(String tgName){
		return targetGroupDao.queryExist(tgName);
	}

	@Override
	public int queryUpdate(String tgName,int id){
		return targetGroupDao.queryUpdate(tgName,id);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return targetGroupDao.queryTotal(map);
	}
	
	@Override
	public void save(TargetGroupEntity targetGroup){
		targetGroupDao.save(targetGroup);
	}

	@Override
	public void update(TargetGroupEntity targetGroup){
		targetGroupDao.update(targetGroup);
	}
	
	@Override
	public void delete(Integer id){
		targetGroupDao.delete(id);
	}


	@Override
	public void deleteBatch(Integer[] ids){
		targetGroupDao.deleteBatch(ids);
	}
	
}
