package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import io.cem.modules.cem.entity.RecordWebDownloadEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebDownloadService {
	
	RecordWebDownloadEntity queryObject(Integer id);
	
	List<RecordWebDownloadEntity> queryList(Map<String, Object> map);

	List<RecordWebDownloadEntity> queryWebDownloadTest(Map<String, Object> map);

	List<RecordWebDownloadEntity> queryWebDownloadList(Map<String, Object> map);

	List<RecordHourWebDownloadEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordWebDownloadEntity recordWebDownload);
	
	void update(RecordWebDownloadEntity recordWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
