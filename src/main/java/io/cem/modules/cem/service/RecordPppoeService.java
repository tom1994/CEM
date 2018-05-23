package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;
import io.cem.modules.cem.entity.RecordPppoeEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordPppoeService {
	
	RecordPppoeEntity queryObject(Integer id);
	
	List<RecordPppoeEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordPppoeEntity>
	 */
	List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordPppoeEntity>
	 */
	List<RecordPppoeEntity> queryPppoeList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordPppoeEntity>
	 */
	List<RecordHourPppoeEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计-总数
	 * @param map
	 * @return int
	 */
	int queryIntervalTotal(Map<String, Object> map);

	/**
	 * 数据统计-总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);
	
	void save(RecordPppoeEntity recordPppoe);
	
	void update(RecordPppoeEntity recordPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
