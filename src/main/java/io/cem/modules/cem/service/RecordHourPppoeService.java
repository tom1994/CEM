package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourPppoeService {
	
	RecordHourPppoeEntity queryObject(Integer id);
	
	List<RecordHourPppoeEntity> queryList(Map<String, Object> map);

	List<RecordHourPppoeEntity> queryPppoe(Map<String,Object> map);

	Future<List<RecordHourPppoeEntity>> queryPppoeList(Map<String, Object> map);

	Future<List<RecordHourPppoeEntity>> queryTargetHourList(Map<String, Object> map);

	Future<List<RecordHourPppoeEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourPppoeEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourPppoeEntity>> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPppoeEntity recordHourPppoe);
	
	void update(RecordHourPppoeEntity recordHourPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
