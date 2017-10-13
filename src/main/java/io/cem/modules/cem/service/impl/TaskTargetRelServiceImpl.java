package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskTargetRelDao;
import io.cem.modules.cem.entity.TaskTargetRelEntity;
import io.cem.modules.cem.service.TaskTargetRelService;



@Service("taskTargetRelService")
public class TaskTargetRelServiceImpl implements TaskTargetRelService {
	@Autowired
	private TaskTargetRelDao taskTargetRelDao;
	
	@Override
	public TaskTargetRelEntity queryObject(Integer id){
		return taskTargetRelDao.queryObject(id);
	}
	
	@Override
	public List<TaskTargetRelEntity> queryList(Map<String, Object> map){
		return taskTargetRelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return taskTargetRelDao.queryTotal(map);
	}
	
	@Override
	public void save(TaskTargetRelEntity taskTargetRel){
		taskTargetRelDao.save(taskTargetRel);
	}
	
	@Override
	public void update(TaskTargetRelEntity taskTargetRel){
		taskTargetRelDao.update(taskTargetRel);
	}
	
	@Override
	public void delete(Integer id){
		taskTargetRelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		taskTargetRelDao.deleteBatch(ids);
	}
	
}
