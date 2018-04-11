package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayWebPageEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDayWebPageService {
	
	RecordDayWebPageEntity queryObject(Integer id);
	
	List<RecordDayWebPageEntity> queryList(Map<String, Object> map);

	List<RecordDayWebPageEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayWebPageEntity recordDayWebPage);
	
	void update(RecordDayWebPageEntity recordDayWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
