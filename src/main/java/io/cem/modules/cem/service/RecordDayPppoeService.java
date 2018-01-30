package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayPppoeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-01-30 12:10:03
 */
public interface RecordDayPppoeService {
	
	RecordDayPppoeEntity queryObject(Integer id);
	
	List<RecordDayPppoeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayPppoeEntity recordDayPppoe);
	
	void update(RecordDayPppoeEntity recordDayPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
