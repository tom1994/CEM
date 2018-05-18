package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.cem.entity.RecordHourGameEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordGameService {
	
	RecordGameEntity queryObject(Integer id);
	
	List<RecordGameEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordGameEntity>
	 */
	List<RecordGameEntity> queryGameTest(Map<String, Object> map);

	/**
	 * 结果列表
	 * @param map
	 * @return List<RecordGameEntity>
	 */
	List<RecordGameEntity> queryGameList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordGameEntity>
	 */
	List<RecordHourGameEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计总数
	 * @param map
	 * @return int
	 */
	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordGameEntity recordGame);
	
	void update(RecordGameEntity recordGame);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
