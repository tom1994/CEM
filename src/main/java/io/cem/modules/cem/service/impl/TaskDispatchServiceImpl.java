package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskDispatchDao;
import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.service.TaskDispatchService;



@Service("taskDispatchService")
public class TaskDispatchServiceImpl implements TaskDispatchService {
	@Autowired
	private TaskDispatchDao taskDispatchDao;
	
	@Override
	public TaskDispatchEntity queryObject(Integer id){
		return taskDispatchDao.queryObject(id);
	}
	
	@Override
	public List<TaskDispatchEntity> queryList(Map<String, Object> map){
		return taskDispatchDao.queryList(map);
	}

	@Override
	public List<TaskDispatchEntity> queryDispatchList(Integer id){
		return taskDispatchDao.queryDispatchList(id);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return taskDispatchDao.queryTotal(map);
	}
	
	@Override
	public void save(TaskDispatchEntity taskDispatch){
		taskDispatchDao.save(taskDispatch);
	}
	
	@Override
	public void update(TaskDispatchEntity taskDispatch){
		taskDispatchDao.update(taskDispatch);
	}
	
	@Override
	public void delete(Integer id){
		taskDispatchDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		taskDispatchDao.deleteBatch(ids);
	}
	
}
