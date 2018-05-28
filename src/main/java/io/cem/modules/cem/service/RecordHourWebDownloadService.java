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

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourWebDownloadEntity>
	 */
	List<RecordHourWebDownloadEntity> queryWebDownload(Map<String,Object> map);
	
	List<RecordHourWebDownloadEntity> queryList(Map<String, Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebDownloadEntity>>
	 */
	Future<List<RecordHourWebDownloadEntity>> queryWebDownloadList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebDownloadEntity>>
	 */
	Future<List<RecordHourWebDownloadEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourWebDownloadEntity>>
	 */
	Future<List<RecordHourWebDownloadEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourWebDownloadEntity>>
	 */
	Future<List<RecordHourWebDownloadEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourWebDownloadEntity>>
	 */
	Future<List<RecordHourWebDownloadEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 计算业务1
	 * @param webDownloadList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateWebDownload(List<RecordHourWebDownloadEntity> webDownloadList,Map<String, Object> map);

	/**
	 * 计算业务2
	 * @param ftpList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateFtpDownload(List<RecordHourFtpEntity> ftpList,Map<String, Object> map);

	/**
	 * 计算业务3
	 * @param ftpList
	 * @return
	 */
	List<ScoreEntity> calculateFtpUpload(List<RecordHourFtpEntity> ftpList,Map<String, Object> map);

	/**
	 * 文件下载类业务（探针）
	 * @param webDownload
	 * @param ftpDownload
	 * @param ftpUpload
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	/**
	 * 文件下载类业务（区域）
	 * @param webDownload
	 * @param ftpDownload
	 * @param ftpUpload
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateArea4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	/**
	 * 文件下载类业务（门户）
	 * @param webDownload
	 * @param ftpDownload
	 * @param ftpUpload
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTarget4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	/**
	 * 文件下载类业务（时间）
	 * @param webDownload
	 * @param ftpDownload
	 * @param ftpUpload
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	/**
	 * 文件下载类业务（层级）
	 * @param webDownload
	 * @param ftpDownload
	 * @param ftpUpload
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void update(RecordHourWebDownloadEntity recordHourWebDownload);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
