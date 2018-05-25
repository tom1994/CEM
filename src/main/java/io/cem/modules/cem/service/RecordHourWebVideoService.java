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

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourWebVideoEntity>
	 */
	List<RecordHourWebVideoEntity> queryWebVideo(Map<String,Object> map);

	/**
	 * 时间查询小时表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryVideoList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 区域查询小时表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryVideoAreaList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryVideoTargetList(Map<String, Object> map);

	/**
	 * 探针查询小时表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryVideoRankList(Map<String, Object> map);

	/**
	 * 区域查询天表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryDayAreaList(Map<String, Object> map);

	/**
	 * 探针查询天表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryDayRankList(Map<String, Object> map);

	/**
	 * 门户查询天表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryDayTargetList(Map<String, Object> map);

	/**
	 * 时间查询天表
	 * @param map
	 * @return
	 */
	Future<List<RecordHourWebVideoEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 在线视频业务
	 * @param videoList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService5 (List<RecordHourWebVideoEntity> videoList,Map<String, Object> map);

	/**
	 * 在线视频业务-层级
	 * @param videoList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer5(List<ScoreEntity> videoList);

	/**
	 * 在线视频业务-时间
	 * @param videoList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate5(List<ScoreEntity> videoList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebVideoEntity recordHourWebVideo);
	
	void update(RecordHourWebVideoEntity recordHourWebVideo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
