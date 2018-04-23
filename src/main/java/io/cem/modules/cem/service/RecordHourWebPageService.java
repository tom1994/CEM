package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourWebPageService {
	
	RecordHourWebPageEntity queryObject(Integer id);
	
	List<RecordHourWebPageEntity> queryList(Map<String, Object> map);

	List<RecordHourWebPageEntity> queryWebPage(Map<String,Object> map);

	Future<List<RecordHourWebPageEntity>> queryWebList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryDayExitList(Map<String, Object> map);
	Future<List<RecordHourWebPageEntity>> queryWebAreaList(Map<String, Object> map);
	Future<List<RecordHourWebPageEntity>> queryWebTargetList(Map<String, Object> map);
	Future<List<RecordHourWebPageEntity>> queryWebRankList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryDayAreaList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryDayTargetList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryDayRankList(Map<String, Object> map);

	Future<List<RecordHourWebPageEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService3(List<RecordHourWebPageEntity> webPageList);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebPageEntity recordHourWebPage);
	
	void update(RecordHourWebPageEntity recordHourWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
