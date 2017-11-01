package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * 任务模板表
 */
public interface TaskTemplateService {
	
	TaskTemplateEntity queryObject(Integer id);
	
	List<TaskTemplateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TaskTemplateEntity taskTemplate);
	
	void update(TaskTemplateEntity taskTemplate);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
