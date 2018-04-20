package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourWebVideoService {
	
	RecordHourWebVideoEntity queryObject(Integer id);
	
	List<RecordHourWebVideoEntity> queryList(Map<String, Object> map);

	List<RecordHourWebVideoEntity> queryWebVideo(Map<String,Object> map);

	Future<List<RecordHourWebVideoEntity>> queryVideoList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryDayExitList(Map<String, Object> map);
	Future<List<RecordHourWebVideoEntity>> queryVideoAreaList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryVideoTargetList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryVideoRankList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryDayAreaList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryDayRankList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryDayTargetList(Map<String, Object> map);

	Future<List<RecordHourWebVideoEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService5 (List<RecordHourWebVideoEntity> videoList);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebVideoEntity recordHourWebVideo);
	
	void update(RecordHourWebVideoEntity recordHourWebVideo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
