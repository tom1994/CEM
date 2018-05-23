package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourSlaEntity;
import io.cem.modules.cem.entity.RecordSlaEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordSlaService {
	
	RecordSlaEntity queryObject(Integer id);
	
	List<RecordSlaEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordSlaEntity>
	 */
	List<RecordSlaEntity> querySlaTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordSlaEntity>
	 */
	List<RecordSlaEntity> querySlaList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordSlaEntity>
	 */
	List<RecordHourSlaEntity> queryIntervalList(Map<String, Object> map);

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
	
	void save(RecordSlaEntity recordSla);
	
	void update(RecordSlaEntity recordSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
