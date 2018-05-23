package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.entity.RecordWebPageEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebPageService {
	
	RecordWebPageEntity queryObject(Integer id);
	
	List<RecordWebPageEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordWebPageEntity>
	 */
	List<RecordWebPageEntity> queryWebPageTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebPageEntity>
	 */
	List<RecordWebPageEntity> queryWebPageList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebPageEntity>
	 */
	List<RecordHourWebPageEntity> queryIntervalList(Map<String, Object> map);

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
	
	void save(RecordWebPageEntity recordWebPage);
	
	void update(RecordWebPageEntity recordWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
