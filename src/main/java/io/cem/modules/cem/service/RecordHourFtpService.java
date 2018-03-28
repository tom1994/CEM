package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourFtpEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:52
 */
public interface RecordHourFtpService {
	
	RecordHourFtpEntity queryObject(Integer id);
	
	List<RecordHourFtpEntity> queryList(Map<String, Object> map);
	List<RecordHourFtpEntity> queryFtp(Map<String,Object> map);

	Future<List<RecordHourFtpEntity>> queryFtpList(Map<String, Object> map);

	List<RecordHourFtpEntity> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourFtpEntity recordHourFtp);
	
	void update(RecordHourFtpEntity recordHourFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
