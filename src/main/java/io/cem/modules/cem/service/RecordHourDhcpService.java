package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 */
public interface RecordHourDhcpService {
	
	RecordHourDhcpEntity queryObject(Integer id);
	
	List<RecordHourDhcpEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourDhcpEntity>
	 */
	List<RecordHourDhcpEntity> queryDhcp(Map<String,Object> map);

	/**
	 * 查询小时表数据
	 * @param map
	 * @return Future<List<RecordHourDhcpEntity>>
	 */
	Future<List<RecordHourDhcpEntity>> queryDhcpList(Map<String, Object> map);

	/**
	 * 门户排名查询小时表
	 * @param map
	 * @return Future<List<RecordHourDhcpEntity>>
	 */
	Future<List<RecordHourDhcpEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询数据
	 * @param map
	 * @return Future<List<RecordHourDhcpEntity>>
	 */
	Future<List<RecordHourDhcpEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourDhcpEntity>>
	 */
	Future<List<RecordHourDhcpEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourDhcpEntity>>
	 */
	Future<List<RecordHourDhcpEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 大于5天网络连通性
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> connectionDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于2天小于5天网络连通性
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> connectionHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于2天网络连通性
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> connectionDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于5天网络层质量
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> qualityDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于2天小于5天网络层质量
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> qualityHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于2天网络层质量
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> qualityDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于5天网页浏览
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> pageDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于5天网页浏览
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> pageHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于5天文件下载
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> downloadDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于2天小于5天文件下载
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> downloadHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于2天文件下载
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> downloadDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于5天在线视频
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> videoDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于5天在线视频
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> videoHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 大于5天网络游戏
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> gameDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 小于5天网络游戏
	 * @param map
	 * @return List<ScoreEntity>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	List<ScoreEntity> gameHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException;

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourDhcpEntity recordHourDhcp);
	
	void update(RecordHourDhcpEntity recordHourDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
