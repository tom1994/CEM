package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayWebVideoEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDayWebVideoService {
	
	RecordDayWebVideoEntity queryObject(Integer id);
	
	List<RecordDayWebVideoEntity> queryList(Map<String, Object> map);

	List<RecordDayWebVideoEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayWebVideoEntity recordDayWebVideo);
	
	void update(RecordDayWebVideoEntity recordDayWebVideo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
