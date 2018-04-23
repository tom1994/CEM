package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourTracertService {
	
	RecordHourTracertEntity queryObject(Integer id);
	
	List<RecordHourTracertEntity> queryList(Map<String, Object> map);

	List<RecordHourTracertEntity> queryTracert(Map<String,Object> map);

	Future<List<RecordHourTracertEntity>> queryTracertList(Map<String, Object> map);

	Future<List<RecordHourTracertEntity>> queryTargetHourList(Map<String, Object> map);

	Future<List<RecordHourTracertEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourTracertEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourTracertEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);


	List<ScoreEntity> calculateArea0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);

	List<ScoreEntity> calculateTarget0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourTracertEntity recordHourTracert);
	
	void update(RecordHourTracertEntity recordHourTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
