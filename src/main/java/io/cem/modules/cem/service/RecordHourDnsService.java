package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourDnsEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 *
 */
public interface RecordHourDnsService {
	
	RecordHourDnsEntity queryObject(Integer id);
	
	List<RecordHourDnsEntity> queryList(Map<String, Object> map);

	List<RecordHourDnsEntity> queryDns(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourDnsEntity>>
	 */
	Future<List<RecordHourDnsEntity>> queryDnsList(Map<String, Object> map);

	/**
	 * 门户排名查询小时表
	 * @param map
	 * @return Future<List<RecordHourDnsEntity>>
	 */
	Future<List<RecordHourDnsEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourDnsEntity>>
	 */
	Future<List<RecordHourDnsEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourDnsEntity>>
	 */
	Future<List<RecordHourDnsEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourDnsEntity>>
	 */
	Future<List<RecordHourDnsEntity>> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourDnsEntity recordHourDns);
	
	void update(RecordHourDnsEntity recordHourDns);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
