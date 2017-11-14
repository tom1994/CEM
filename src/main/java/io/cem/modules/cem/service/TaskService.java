package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
public interface TaskService {
	
	TaskEntity queryObject(Integer id);
	
	List<TaskEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TaskEntity task);
	
	void update(TaskEntity task);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
