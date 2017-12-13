package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:34
 */
public interface RecordHourPppoeService {
	
	RecordHourPppoeEntity queryObject(Integer id);
	
	List<RecordHourPppoeEntity> queryList(Map<String, Object> map);

	List<RecordHourPppoeEntity> queryPppoeList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPppoeEntity recordHourPppoe);
	
	void update(RecordHourPppoeEntity recordHourPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
