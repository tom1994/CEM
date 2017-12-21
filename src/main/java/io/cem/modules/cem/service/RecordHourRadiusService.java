package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:23
 */
public interface RecordHourRadiusService {
	
	RecordHourRadiusEntity queryObject(Integer id);
	
	List<RecordHourRadiusEntity> queryList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryRadiusList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourRadiusEntity recordHourRadius);
	
	void update(RecordHourRadiusEntity recordHourRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
