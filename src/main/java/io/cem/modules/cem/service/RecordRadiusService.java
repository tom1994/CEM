package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.cem.entity.RecordRadiusEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordRadiusService {
	
	RecordRadiusEntity queryObject(Integer id);
	
	List<RecordRadiusEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordRadiusEntity>
	 */
	List<RecordRadiusEntity> queryRadiusTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordRadiusEntity>
	 */
	List<RecordRadiusEntity> queryRadiusList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordRadiusEntity>
	 */
	List<RecordHourRadiusEntity> queryIntervalList(Map<String, Object> map);

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
	
	void save(RecordRadiusEntity recordRadius);
	
	void update(RecordRadiusEntity recordRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
