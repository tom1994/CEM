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

	List<RecordRadiusEntity> queryRadiusTest(Map<String, Object> map);

	List<RecordRadiusEntity> queryRadiusList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordRadiusEntity recordRadius);
	
	void update(RecordRadiusEntity recordRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
