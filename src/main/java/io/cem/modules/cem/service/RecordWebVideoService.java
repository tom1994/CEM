package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.cem.entity.RecordWebVideoEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebVideoService {
	
	RecordWebVideoEntity queryObject(Integer id);
	
	List<RecordWebVideoEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordWebVideoEntity>
	 */
	List<RecordWebVideoEntity> queryWebVideoTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebVideoEntity>
	 */
	List<RecordWebVideoEntity> queryWebVideoList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebVideoEntity>
	 */
	List<RecordHourWebVideoEntity> queryIntervalList(Map<String, Object> map);

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
	
	void save(RecordWebVideoEntity recordWebVideo);
	
	void update(RecordWebVideoEntity recordWebVideo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
