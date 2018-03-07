package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.entity.RecordWebPageEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebPageService {
	
	RecordWebPageEntity queryObject(Integer id);
	
	List<RecordWebPageEntity> queryList(Map<String, Object> map);

	List<RecordWebPageEntity> queryWebPageTest(Map<String, Object> map);

	List<RecordWebPageEntity> queryWebPageList(Map<String, Object> map);

	List<RecordHourWebPageEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordWebPageEntity recordWebPage);
	
	void update(RecordWebPageEntity recordWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
