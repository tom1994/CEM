package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
public interface RecordHourPingService {
	
	RecordHourPingEntity queryObject(Integer id);
	
	List<RecordHourPingEntity> queryList(Map<String, Object> map);

	List<RecordHourPingEntity> queryPingList(Map<String, Object> map);

	List<ScoreEntity> calculateService1(List<RecordHourPingEntity> pingList,List<RecordHourTracertEntity> tracertList);

	List<ScoreEntity> calculateServiceDate1(List<RecordHourPingEntity> pingList,List<RecordHourTracertEntity> tracertList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPingEntity recordHourPing);
	
	void update(RecordHourPingEntity recordHourPing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
