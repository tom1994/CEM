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

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourRadiusEntity>
	 */
	List<RecordHourRadiusEntity> queryRadius(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourRadiusEntity>>
	 */
	Future<List<RecordHourRadiusEntity>> queryRadiusList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourRadiusEntity>>
	 */
	Future<List<RecordHourRadiusEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourRadiusEntity>>
	 */
	Future<List<RecordHourRadiusEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourRadiusEntity>>
	 */
	Future<List<RecordHourRadiusEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourRadiusEntity>>
	 */
	Future<List<RecordHourRadiusEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 大于1天的探针排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于0天的探针排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于1天的探针排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于1天的区域排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateAreaDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于0天的区域排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateAreaHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于1天的区域排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateAreaDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于1天的门户排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateTargetDayScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于1天的门户排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateTargetHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 等于0天的门户排名
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> calculateTargetDayHourScore(Map<String, Object> map) throws ExecutionException, InterruptedException;


	/**
	 * 大于5天的周期诊断
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> diagnoseDay(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 *大于1天小于5天的周期诊断
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> diagnoseHour(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 *小于1天的周期诊断
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> diagnoseDayHour(Map<String, Object> map) throws ExecutionException, InterruptedException;

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourRadiusEntity recordHourRadius);
	
	void update(RecordHourRadiusEntity recordHourRadius);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
