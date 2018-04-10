package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;
import io.cem.modules.cem.entity.RecordPppoeEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordPppoeService {
	
	RecordPppoeEntity queryObject(Integer id);
	
	List<RecordPppoeEntity> queryList(Map<String, Object> map);

	List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map);

	List<RecordPppoeEntity> queryPppoeList(Map<String, Object> map);

	List<RecordHourPppoeEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordPppoeEntity recordPppoe);
	
	void update(RecordPppoeEntity recordPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
