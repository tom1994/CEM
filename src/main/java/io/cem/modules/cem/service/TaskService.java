package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TaskService {
	
	TaskEntity queryObject(Integer id);
	
	List<TaskEntity> queryList(Map<String, Object> map);

	List<TaskEntity> queryTaskList(Map<String, Object> map);

	List<TaskEntity> infoByService(Integer servicetype);

	int queryTotal(Map<String, Object> map);
	
	void save(TaskEntity task);
	
	void update(TaskEntity task);
	
	void delete(Integer id);
	int queryExist(String taskName);
	
	void deleteBatch(Integer[] ids);
}
