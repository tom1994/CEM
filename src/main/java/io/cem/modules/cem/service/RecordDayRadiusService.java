package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayRadiusEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordDayRadiusService {
	
	RecordDayRadiusEntity queryObject(Integer id);
	
	List<RecordDayRadiusEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordDayRadiusEntity>
	 */
	List<RecordDayRadiusEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayRadiusEntity recordDayRadius);
	
	void update(RecordDayRadiusEntity recordDayRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
