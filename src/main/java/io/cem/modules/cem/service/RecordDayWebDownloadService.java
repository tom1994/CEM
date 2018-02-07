package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayWebDownloadEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-01-30 12:10:03
 */
public interface RecordDayWebDownloadService {
	
	RecordDayWebDownloadEntity queryObject(Integer id);
	
	List<RecordDayWebDownloadEntity> queryList(Map<String, Object> map);

	List<RecordDayWebDownloadEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayWebDownloadEntity recordDayWebDownload);
	
	void update(RecordDayWebDownloadEntity recordDayWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
