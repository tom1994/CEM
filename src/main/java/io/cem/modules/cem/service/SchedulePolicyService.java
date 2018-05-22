package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.SchedulePolicyEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface SchedulePolicyService {
	
	SchedulePolicyEntity queryObject(Integer id);

	/**
	 * 列表
	 * @param map
	 * @return List<SchedulePolicyEntity>
	 */
	List<SchedulePolicyEntity> queryList(Map<String, Object> map);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param schedulePolicy
	 */
	void save(SchedulePolicyEntity schedulePolicy);

	/**
	 * 名称是否重复
	 * @param spName
	 * @return int
	 */
	int queryExist(String spName);

	/**
	 * 更新
	 * @param schedulePolicy
	 */
	void update(SchedulePolicyEntity schedulePolicy);

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
