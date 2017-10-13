package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskTargetRelEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public interface TaskTargetRelService {
	
	TaskTargetRelEntity queryObject(Integer id);
	
	List<TaskTargetRelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TaskTargetRelEntity taskTargetRel);
	
	void update(TaskTargetRelEntity taskTargetRel);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
