package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskDao;
import io.cem.modules.cem.entity.TaskEntity;
import io.cem.modules.cem.service.TaskService;



@Service("taskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskDao taskDao;
	
	@Override
	public TaskEntity queryObject(Integer id){
		return taskDao.queryObject(id);
	}
	
	@Override
	public List<TaskEntity> queryList(Map<String, Object> map){
		return taskDao.queryList(map);
	}

	@Override
	public List<TaskEntity> queryTaskList(Map<String, Object> map){
		return taskDao.queryTaskList(map);
	}

	@Override
	public List<TaskEntity> infoByService(Integer servicetype){ return taskDao.infoByService(servicetype); }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return taskDao.queryTotal(map);
	}
	
	@Override
	public void save(TaskEntity task){
		taskDao.save(task);
	}
	
	@Override
	public void update(TaskEntity task){
		taskDao.update(task);
	}
	
	@Override
	public void delete(Integer id){
		taskDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		taskDao.deleteBatch(ids);
	}
	
}
