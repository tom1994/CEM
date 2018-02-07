package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayTracertEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-01-30 12:10:16
 */
public interface RecordDayTracertService {
	
	RecordDayTracertEntity queryObject(Integer id);
	
	List<RecordDayTracertEntity> queryList(Map<String, Object> map);

	List<RecordDayTracertEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayTracertEntity recordDayTracert);
	
	void update(RecordDayTracertEntity recordDayTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
