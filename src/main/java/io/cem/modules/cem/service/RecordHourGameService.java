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
	List<RecordHourGameEntity> queryGame(Map<String,Object> map);

	Future<List<RecordHourGameEntity>> queryGameList(Map<String, Object> map);
	Future<List<RecordHourGameEntity>> queryExitList(Map<String, Object> map);
	Future<List<RecordHourGameEntity>> queryGameAreaList(Map<String, Object> map);
	Future<List<RecordHourGameEntity>> queryGameRankList(Map<String, Object> map);

	Future<List<RecordHourGameEntity>> queryDayAreaList(Map<String, Object> map);
	Future<List<RecordHourGameEntity>> queryDayRankList(Map<String, Object> map);

	Future<List<RecordHourGameEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService6(List<RecordHourGameEntity> gameList);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourGameEntity recordHourGame);
	
	void update(RecordHourGameEntity recordHourGame);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
