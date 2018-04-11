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

	Future<List<RecordHourDnsEntity>> queryDnsList(Map<String, Object> map);

	Future<List<RecordHourDnsEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourDnsEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourDnsEntity>> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourDnsEntity recordHourDns);
	
	void update(RecordHourDnsEntity recordHourDns);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
