package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:52
 */
public interface RecordHourWebDownloadService {
	
	RecordHourWebDownloadEntity queryObject(Integer id);
	
	List<RecordHourWebDownloadEntity> queryList(Map<String, Object> map);

	List<RecordHourWebDownloadEntity> queryWebDownloadList(Map<String, Object> map);

	List<RecordHourWebDownloadEntity> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService4 (List<RecordHourWebDownloadEntity> webDownloadList,List<RecordHourFtpEntity> ftpList);

	List<ScoreEntity> calculateServiceArea4 (List<RecordHourWebDownloadEntity> webDownloadList,List<RecordHourFtpEntity> ftpList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void update(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
