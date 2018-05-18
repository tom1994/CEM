package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayTracertEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDayTracertService {
	
	RecordDayTracertEntity queryObject(Integer id);
	
	List<RecordDayTracertEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordDayTracertEntity>
	 */
	List<RecordDayTracertEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayTracertEntity recordDayTracert);
	
	void update(RecordDayTracertEntity recordDayTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
