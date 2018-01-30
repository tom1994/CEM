package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayGameEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-01-30 12:10:02
 */
public interface RecordDayGameService {
	
	RecordDayGameEntity queryObject(Integer id);
	
	List<RecordDayGameEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayGameEntity recordDayGame);
	
	void update(RecordDayGameEntity recordDayGame);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
