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

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourTracertEntity>
	 */
	List<RecordHourTracertEntity> queryTracert(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourTracertEntity>>
	 */
	Future<List<RecordHourTracertEntity>> queryTracertList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourTracertEntity>>
	 */
	Future<List<RecordHourTracertEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourTracertEntity>>
	 */
	Future<List<RecordHourTracertEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourTracertEntity>>
	 */
	Future<List<RecordHourTracertEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourTracertEntity>>
	 */
	Future<List<RecordHourTracertEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 综合业务（探针）
	 * @param connection
	 * @param quality
	 * @param broswer
	 * @param download
	 * @param video
	 * @param game
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);

	/**
	 * 综合业务（区域）
	 * @param connection
	 * @param quality
	 * @param broswer
	 * @param download
	 * @param video
	 * @param game
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateArea0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);

	/**
	 * 综合业务（门户）
	 * @param connection
	 * @param quality
	 * @param broswer
	 * @param download
	 * @param video
	 * @param game
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTarget0(List<ScoreEntity> connection,List<ScoreEntity> quality,List<ScoreEntity> broswer,List<ScoreEntity> download,List<ScoreEntity> video,List<ScoreEntity> game);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourTracertEntity recordHourTracert);
	
	void update(RecordHourTracertEntity recordHourTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
