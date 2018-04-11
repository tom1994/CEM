package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourWebDownloadService {
	
	RecordHourWebDownloadEntity queryObject(Integer id);

	List<RecordHourWebDownloadEntity> queryWebDownload(Map<String,Object> map);
	
	List<RecordHourWebDownloadEntity> queryList(Map<String, Object> map);

	Future<List<RecordHourWebDownloadEntity>> queryWebDownloadList(Map<String, Object> map);

	Future<List<RecordHourWebDownloadEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourWebDownloadEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourWebDownloadEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateWebDownload(List<RecordHourWebDownloadEntity> webDownloadList);

	List<ScoreEntity> calculateFtpDownload(List<RecordHourFtpEntity> ftpList);

	List<ScoreEntity> calculateFtpUpload(List<RecordHourFtpEntity> ftpList);

	List<ScoreEntity> calculateService4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	List<ScoreEntity> calculateArea4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	List<ScoreEntity> calculateTarget4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	List<ScoreEntity> calculateDate4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void update(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
