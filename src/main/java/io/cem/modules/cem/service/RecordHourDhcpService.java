package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 */
public interface RecordHourDhcpService {
	
	RecordHourDhcpEntity queryObject(Integer id);
	
	List<RecordHourDhcpEntity> queryList(Map<String, Object> map);

	List<RecordHourDhcpEntity> queryDhcp(Map<String,Object> map);

	Future<List<RecordHourDhcpEntity>> queryDhcpList(Map<String, Object> map);

	Future<List<RecordHourDhcpEntity>> queryTargetHourList(Map<String, Object> map);

	Future<List<RecordHourDhcpEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourDhcpEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourDhcpEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> connectionDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> connectionHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> connectionDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> qualityDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> qualityHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> qualityDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> pageDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> pageHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;


	List<ScoreEntity> downloadDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> downloadHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> downloadDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> videoDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> videoHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;



	List<ScoreEntity> gameDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	List<ScoreEntity> gameHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;


	List<ScoreEntity> combination(Map<String,Object> map,List<ScoreEntity> scoreList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourDhcpEntity recordHourDhcp);
	
	void update(RecordHourDhcpEntity recordHourDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
