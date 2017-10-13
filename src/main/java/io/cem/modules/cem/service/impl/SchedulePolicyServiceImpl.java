package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.SchedulePolicyDao;
import io.cem.modules.cem.entity.SchedulePolicyEntity;
import io.cem.modules.cem.service.SchedulePolicyService;



@Service("schedulePolicyService")
public class SchedulePolicyServiceImpl implements SchedulePolicyService {
	@Autowired
	private SchedulePolicyDao schedulePolicyDao;
	
	@Override
	public SchedulePolicyEntity queryObject(Integer id){
		return schedulePolicyDao.queryObject(id);
	}
	
	@Override
	public List<SchedulePolicyEntity> queryList(Map<String, Object> map){
		return schedulePolicyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return schedulePolicyDao.queryTotal(map);
	}
	
	@Override
	public void save(SchedulePolicyEntity schedulePolicy){
		schedulePolicyDao.save(schedulePolicy);
	}
	
	@Override
	public void update(SchedulePolicyEntity schedulePolicy){
		schedulePolicyDao.update(schedulePolicy);
	}
	
	@Override
	public void delete(Integer id){
		schedulePolicyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		schedulePolicyDao.deleteBatch(ids);
	}
	
}
