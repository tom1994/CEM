package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordFtpEntity;
import io.cem.modules.cem.entity.RecordHourFtpEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordFtpService {
	
	RecordFtpEntity queryObject(Integer id);
	
	List<RecordFtpEntity> queryList(Map<String, Object> map);

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordFtpEntity>
	 */
	List<RecordFtpEntity> queryFtpTest(Map<String, Object> map);

	/**
	 * 结果列表
	 * @param map
	 * @return List<RecordFtpEntity>
	 */
	List<RecordFtpEntity> queryFtpList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordFtpEntity>
	 */
	List<RecordHourFtpEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计总数
	 * @param map
	 * @return int
	 */
	int queryIntervalTotal(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordFtpEntity recordFtp);
	
	void update(RecordFtpEntity recordFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
