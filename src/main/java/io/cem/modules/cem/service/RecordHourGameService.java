package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourGameEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourGameService {
	
	RecordHourGameEntity queryObject(Integer id);
	
	List<RecordHourGameEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return
	 */
	List<RecordHourGameEntity> queryGame(Map<String,Object> map);

	/**
	 * 质量评分小时表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryGameList(Map<String, Object> map);

	/**
	 * 出口小时表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口天表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 区域排名小时表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryGameAreaList(Map<String, Object> map);

	/**
	 * 探针排名小时表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryGameRankList(Map<String, Object> map);

	/**
	 * 门户排名小时表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryGameTargetList(Map<String, Object> map);

	/**
	 * 区域排名天表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryDayAreaList(Map<String, Object> map);

	/**
	 * 探针排名天表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryDayRankList(Map<String, Object> map);

	/**
	 * 门户排名天表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryDayTargetList(Map<String, Object> map);

	/**
	 * 质量评分天表
	 * @param map
	 * @return Future<List<RecordHourGameEntity>>
	 */
	Future<List<RecordHourGameEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 网络游戏
	 * @param gameList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService6(List<RecordHourGameEntity> gameList);

	/**
	 * 网络游戏-层级
	 * @param gameList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer6(List<ScoreEntity> gameList);

	/**
	 * 网络游戏-时间
	 * @param gameList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate6(List<ScoreEntity> gameList);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourGameEntity recordHourGame);
	
	void update(RecordHourGameEntity recordHourGame);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
