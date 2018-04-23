package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourRadiusService {
	
	RecordHourRadiusEntity queryObject(Integer id);
	
	List<RecordHourRadiusEntity> queryList(Map<String, Object> map);

	List<RecordHourRadiusEntity> queryRadius(Map<String,Object> map);

	Future<List<RecordHourRadiusEntity>> queryRadiusList(Map<String, Object> map);

	Future<List<RecordHourRadiusEntity>> queryTargetHourList(Map<String, Object> map);

	Future<List<RecordHourRadiusEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourRadiusEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourRadiusEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateAreaDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateTargetDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;
	List<ScoreEntity> calculateTargetHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;
	List<ScoreEntity> calculateTargetDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateAreaHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> calculateAreaDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> diagnoseDay(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException;

	List<ScoreEntity> diagnoseHour(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException;
	List<ScoreEntity> diagnoseDayHour(Map<String, Object> map,List<ScoreEntity> scoreList) throws ExecutionException, InterruptedException;

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourRadiusEntity recordHourRadius);
	
	void update(RecordHourRadiusEntity recordHourRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
