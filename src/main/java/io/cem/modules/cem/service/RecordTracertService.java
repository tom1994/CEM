package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.RecordTracertEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordTracertService {
	
	RecordTracertEntity queryObject(Integer id);

	List<RecordTracertEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordTracertEntity>
	 */
	List<RecordTracertEntity> queryTracertTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordTracertEntity>
	 */
	List<RecordTracertEntity> queryTracertList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordTracertEntity>
	 */
	List<RecordHourTracertEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordTracertEntity>
	 */
	int queryIntervalTotal(Map<String, Object> map);

	/**
	 * 数据统计-总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	void save(RecordTracertEntity recordTracert);
	
	void update(RecordTracertEntity recordTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
