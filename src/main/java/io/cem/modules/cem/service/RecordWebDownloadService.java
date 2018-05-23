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

	/**
	 * 实时诊断
	 * @param map
	 * @return List<RecordWebDownloadEntity>
	 */
	List<RecordWebDownloadEntity> queryWebDownloadTest(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebDownloadEntity>
	 */
	List<RecordWebDownloadEntity> queryWebDownloadList(Map<String, Object> map);

	/**
	 * 数据统计
	 * @param map
	 * @return List<RecordWebDownloadEntity>
	 */
	List<RecordHourWebDownloadEntity> queryIntervalList(Map<String, Object> map);

	/**
	 * 数据统计-总数
	 * @param map
	 * @return int
	 */
	int queryIntervalTotal(Map<String, Object> map);

	/**
	 * 数据统计-总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);
	
	void save(RecordWebDownloadEntity recordWebDownload);
	
	void update(RecordWebDownloadEntity recordWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
