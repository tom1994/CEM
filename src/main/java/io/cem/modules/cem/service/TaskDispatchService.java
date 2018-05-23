package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TaskDispatchEntity;

import java.util.List;
import java.util.Map;

/**

 */
public interface TaskDispatchService {
	
	TaskDispatchEntity queryObject(Integer id);
	
	List<TaskDispatchEntity> queryList(Map<String, Object> map);

	/**
	 * 查询列表
	 * @param map
	 * @return List<TaskDispatchEntity>
	 */
	List<TaskDispatchEntity> queryDispatchList(Map<String, Object> map);

	/**
	 * 显示探针任务
	 * @param map
	 * @return List<TaskDispatchEntity>
	 */
	List<TaskDispatchEntity> taskQueryDispatchList(Map<String, Object> map);

	/**
	 * 显示任务
	 * @param taskDispatchEntities
	 * @return
	 */
	List<TaskDispatchEntity> transformTarget(List<TaskDispatchEntity> taskDispatchEntities);

	/**
	 * 实时诊断
	 * @param taskDispatch
	 * @return int
	 */
	int saveAndReturn(TaskDispatchEntity taskDispatch);

	/**
	 * 实时诊断状态
	 * @param id
	 * @return int
	 */
	int queryTestStatus(Integer[] id);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 任务界面显示下发任务
	 * @param id
	 * @return int
	 */
	int queryDispatchTotal(Integer id);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int taskQueryDispatchTotal(Map<String, Object> map);

	/**
	 * 取消保存
	 * @param taskId
	 */
	void cancelSave(Integer taskId);

	/**
	 * 保存
	 * @param taskDispatch
	 */
	void save(TaskDispatchEntity taskDispatch);

	/**
	 * 批量保存
	 * @param taskDispatchList
	 */
	void saveAll(List<TaskDispatchEntity> taskDispatchList);

	/**
	 * 更新
	 * @param taskDispatch
	 */
	void update(TaskDispatchEntity taskDispatch);

	/**
	 * 删除
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(Integer[] ids);

}
