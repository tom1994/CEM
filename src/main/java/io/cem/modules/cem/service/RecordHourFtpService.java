package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.EvaluationEntity;
import io.cem.modules.cem.entity.RecordHourFtpEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourFtpService {
	
	RecordHourFtpEntity queryObject(Integer id);
	
	List<RecordHourFtpEntity> queryList(Map<String, Object> map);
	List<RecordHourFtpEntity> queryFtp(Map<String,Object> map);

	Future<List<RecordHourFtpEntity>> queryFtpList(Map<String, Object> map);

	Future<List<RecordHourFtpEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourFtpEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourFtpEntity>> queryDayList(Map<String, Object> map);

	EvaluationEntity calculateDayQualityScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	EvaluationEntity calculateHourQualityScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourFtpEntity recordHourFtp);
	
	void update(RecordHourFtpEntity recordHourFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
