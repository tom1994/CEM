package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskDispatchEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
public interface TaskDispatchService {
	
	TaskDispatchEntity queryObject(Integer id);
	
	List<TaskDispatchEntity> queryList(Map<String, Object> map);

	List<TaskDispatchEntity> queryDispatchList(Integer id);
	
	int queryTotal(Map<String, Object> map);

	int queryDispatchTotal(Integer id);
	
	void save(TaskDispatchEntity taskDispatch);
	
	void update(TaskDispatchEntity taskDispatch);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
