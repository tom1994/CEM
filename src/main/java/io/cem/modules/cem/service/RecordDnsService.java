package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.cem.entity.RecordHourDnsEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordDnsService {
	
	RecordDnsEntity queryObject(Integer id);
	
	List<RecordDnsEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordDnsEntity>
	 */
	List<RecordDnsEntity> queryDnsTest(Map<String, Object> map);

	/**
	 * 结果列表
	 * @param map
	 * @return List<RecordDnsEntity>
	 */
	List<RecordDnsEntity> queryDnsList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordDnsEntity>
	 */
	List<RecordHourDnsEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计总数
	 * @param map
	 * @return int
	 */
	int queryIntervalTotal(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordDnsEntity recordDns);
	
	void update(RecordDnsEntity recordDns);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
