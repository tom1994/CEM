package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskDispatchEntity;

import java.util.List;
import java.util.Map;

/**

 */
public interface TaskDispatchService {
	
	TaskDispatchEntity queryObject(Integer id);
	
	List<TaskDispatchEntity> queryList(Map<String, Object> map);

	List<TaskDispatchEntity> queryDispatchList(Map<String, Object> map);

	List<TaskDispatchEntity> taskQueryDispatchList(Map<String, Object> map);

	int saveAndReturn(TaskDispatchEntity taskDispatch);

	int queryTestStatus(Integer[] id);
	
	int queryTotal(Map<String, Object> map);

	int queryDispatchTotal(Integer id);

	int taskQueryDispatchTotal(Map<String, Object> map);
	
	void save(TaskDispatchEntity taskDispatch);

	void saveAll(List<TaskDispatchEntity> taskDispatchList);
	
	void update(TaskDispatchEntity taskDispatch);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void cancelTask(Integer id);

	String queryTargetBatch(String[] targetIdList);
}
