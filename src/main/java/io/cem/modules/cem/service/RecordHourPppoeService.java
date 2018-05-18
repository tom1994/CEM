package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourPppoeService {
	
	RecordHourPppoeEntity queryObject(Integer id);
	
	List<RecordHourPppoeEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourPppoeEntity>
	 */
	List<RecordHourPppoeEntity> queryPppoe(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourPppoeEntity>>
	 */
	Future<List<RecordHourPppoeEntity>> queryPppoeList(Map<String, Object> map);

	/**
	 * 门户排名查询小时表
	 * @param map
	 * @return Future<List<RecordHourPppoeEntity>>
	 */
	Future<List<RecordHourPppoeEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口计算得分小时表
	 * @param map
	 * @return Future<List<RecordHourPppoeEntity>>
	 */
	Future<List<RecordHourPppoeEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口计算得分天表
	 * @param map
	 * @return Future<List<RecordHourPppoeEntity>>
	 */
	Future<List<RecordHourPppoeEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourPppoeEntity>>
	 */
	Future<List<RecordHourPppoeEntity>> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPppoeEntity recordHourPppoe);
	
	void update(RecordHourPppoeEntity recordHourPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
