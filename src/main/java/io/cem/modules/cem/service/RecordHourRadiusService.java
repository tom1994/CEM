package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourRadiusService {
	
	RecordHourRadiusEntity queryObject(Integer id);
	
	List<RecordHourRadiusEntity> queryList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryRadius(Map<String,Object> map);

	Future<List<RecordHourRadiusEntity>> queryRadiusList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryExitList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourRadiusEntity recordHourRadius);
	
	void update(RecordHourRadiusEntity recordHourRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
