package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TaskService {
	
	TaskEntity queryObject(Integer id);
	
	List<TaskEntity> queryList(Map<String, Object> map);

	/**
	 * 列表
	 * @param map
	 * @return List<TaskEntity>
	 */
	List<TaskEntity> queryTaskList(Map<String, Object> map);

	/**
	 * 根据业务类型显示任务
	 * @param servicetype
	 * @return List<TaskEntity>
	 */
	List<TaskEntity> infoByService(Integer servicetype);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param task
	 */
	void save(TaskEntity task);

	/**
	 * 更新
	 * @param task
	 */
	void update(TaskEntity task);

	/**
	 * 删除
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 名称是否重复
	 * @param taskName
	 * @return int
	 */
	int queryExist(String taskName);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(Integer[] ids);
}
