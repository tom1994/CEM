package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourFtpEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourFtpService {
	
	RecordHourFtpEntity queryObject(Integer id);
	
	List<RecordHourFtpEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return
	 */
	List<RecordHourFtpEntity> queryFtp(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourFtpEntity>>
	 */
	Future<List<RecordHourFtpEntity>> queryFtpList(Map<String, Object> map);

	/**
	 * 门户排名查询小时表
	 * @param map
	 * @return Future<List<RecordHourFtpEntity>>
	 */
	Future<List<RecordHourFtpEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourFtpEntity>>
	 */
	Future<List<RecordHourFtpEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourFtpEntity>>
	 */
	Future<List<RecordHourFtpEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourFtpEntity>>
	 */
	Future<List<RecordHourFtpEntity>> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourFtpEntity recordHourFtp);
	
	void update(RecordHourFtpEntity recordHourFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
