package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.RecordPingEntity;

import java.util.List;
import java.util.Map;

/**

 */
public interface RecordPingService {
	
	RecordPingEntity queryObject(Integer id);
	
	List<RecordPingEntity> queryList(Map<String, Object> map);

	List<RecordPingEntity> queryPingList(Map<String, Object> map);

	List<RecordHourPingEntity> queryIntervalList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordPingEntity recordPing);
	
	void update(RecordPingEntity recordPing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
