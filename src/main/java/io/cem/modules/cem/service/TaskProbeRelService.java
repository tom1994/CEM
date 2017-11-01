package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskProbeRelEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public interface TaskProbeRelService {
	
	TaskProbeRelEntity queryObject(Integer id);
	
	List<TaskProbeRelEntity> queryList(Map<String, Object> map);

	List<TaskProbeRelEntity> queryTaskList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TaskProbeRelEntity taskProbeRel);
	
	void update(TaskProbeRelEntity taskProbeRel);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
