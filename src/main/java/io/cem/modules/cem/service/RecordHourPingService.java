package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPingEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
public interface RecordHourPingService {
	
	RecordHourPingEntity queryObject(Integer id);
	
	List<RecordHourPingEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPingEntity recordHourPing);
	
	void update(RecordHourPingEntity recordHourPing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
