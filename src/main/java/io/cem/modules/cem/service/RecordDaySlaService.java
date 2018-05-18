package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDaySlaEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDaySlaService {
	
	RecordDaySlaEntity queryObject(Integer id);
	
	List<RecordDaySlaEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordDaySlaEntity>
	 */
	List<RecordDaySlaEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDaySlaEntity recordDaySla);
	
	void update(RecordDaySlaEntity recordDaySla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
