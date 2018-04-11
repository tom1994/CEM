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

	List<RecordFtpEntity> queryFtpTest(Map<String, Object> map);

	List<RecordFtpEntity> queryFtpList(Map<String, Object> map);

	List<RecordHourFtpEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordFtpEntity recordFtp);
	
	void update(RecordFtpEntity recordFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
