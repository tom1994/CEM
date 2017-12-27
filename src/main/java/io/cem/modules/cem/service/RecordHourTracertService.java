package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.EvaluationEntity;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
public interface RecordHourTracertService {
	
	RecordHourTracertEntity queryObject(Integer id);
	
	List<RecordHourTracertEntity> queryList(Map<String, Object> map);

	List<RecordHourTracertEntity> queryTracertList(Map<String, Object> map);

	List<RecordHourTracertEntity> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourTracertEntity recordHourTracert);
	
	void update(RecordHourTracertEntity recordHourTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
