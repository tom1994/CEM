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

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourWebPageEntity>
	 */
	List<RecordHourWebPageEntity> queryWebPage(Map<String,Object> map);

	/**
	 * 时间查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryWebList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 区域查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryWebAreaList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryWebTargetList(Map<String, Object> map);

	/**
	 * 探针查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryWebRankList(Map<String, Object> map);

	/**
	 * 区域查询天表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryDayAreaList(Map<String, Object> map);

	/**
	 * 门户查询天表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryDayTargetList(Map<String, Object> map);

	/**
	 * 探针查询天表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryDayRankList(Map<String, Object> map);

	/**
	 * 时间查询天表
	 * @param map
	 * @return Future<List<RecordHourWebPageEntity>>
	 */
	Future<List<RecordHourWebPageEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 网页浏览类业务
	 * @param webPageList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService3(List<RecordHourWebPageEntity> webPageList);

	/**
	 * 网页浏览类业务-层级
	 * @param webPageList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer3(List<ScoreEntity> webPageList);

	/**
	 * 网页浏览类业务-时间
	 * @param webPageList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate3(List<ScoreEntity> webPageList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebPageEntity recordHourWebPage);
	
	void update(RecordHourWebPageEntity recordHourWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
