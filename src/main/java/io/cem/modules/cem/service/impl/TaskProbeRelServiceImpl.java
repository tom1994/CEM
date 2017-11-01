package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskProbeRelDao;
import io.cem.modules.cem.entity.TaskProbeRelEntity;
import io.cem.modules.cem.service.TaskProbeRelService;



@Service("taskProbeRelService")
public class TaskProbeRelServiceImpl implements TaskProbeRelService {
	@Autowired
	private TaskProbeRelDao taskProbeRelDao;
	
	@Override
	public TaskProbeRelEntity queryObject(Integer id){
		return taskProbeRelDao.queryObject(id);
	}
	
	@Override
	public List<TaskProbeRelEntity> queryList(Map<String, Object> map){
		return taskProbeRelDao.queryList(map);
	}

	@Override
	public List<TaskProbeRelEntity> queryTaskList(Map<String, Object> map){
		return taskProbeRelDao.queryTaskList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return taskProbeRelDao.queryTotal(map);
	}
	
	@Override
	public void save(TaskProbeRelEntity taskProbeRel){
		taskProbeRelDao.save(taskProbeRel);
	}
	
	@Override
	public void update(TaskProbeRelEntity taskProbeRel){
		taskProbeRelDao.update(taskProbeRel);
	}
	
	@Override
	public void delete(Integer id){
		taskProbeRelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		taskProbeRelDao.deleteBatch(ids);
	}
	
}
