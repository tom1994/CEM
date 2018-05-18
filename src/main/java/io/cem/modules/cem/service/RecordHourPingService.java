package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourPingService {
	
	RecordHourPingEntity queryObject(Integer id);

	/**
	 * 定时任务
	 * @return map
	 */
	Map queryTime();

	/**
	 * 定时任务
	 * @return map
	 */
	Map queryDay();

	/**
	 * 前一天
	 * @param date
	 * @return string
	 */
	String queryBeforeDay(String date);

	/**
	 * 后一天
	 * @param date
	 * @return string
	 */
	String queryAfterDay(String date);
	
	List<RecordHourPingEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourPingEntity>
	 */
	List<RecordHourPingEntity> queryPing(Map<String, Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourPingEntity>>
	 */
	Future<List<RecordHourPingEntity>> queryPingList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourPingEntity>>
	 */
	Future<List<RecordHourPingEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourPingEntity>>
	 */
	Future<List<RecordHourPingEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourPingEntity>>
	 */
	Future<List<RecordHourPingEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourPingEntity>>
	 */
	Future<List<RecordHourPingEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 子业务1得分
	 * @param pingList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculatePingIcmp(List<RecordHourPingEntity> pingList);

	/**
	 * 子业务2得分
	 * @param pingList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculatePingTcp(List<RecordHourPingEntity> pingList);

	/**
	 * 子业务3得分
	 * @param pingList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculatePingUdp(List<RecordHourPingEntity> pingList);

	/**
	 * 子业务4得分
	 * @param tracertList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTracertIcmp(List<RecordHourTracertEntity> tracertList);

	/**
	 * 子业务5得分
	 * @param tracertList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTracertUdp(List<RecordHourTracertEntity> tracertList);

	/**
	 * 网络连通性业务
	 * @param pingIcmp
	 * @param pingTcp
	 * @param pingUdp
	 * @param tracertIcmp
	 * @param tracertUdp
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	/**
	 * 网络连通性业务-时间维度
	 * @param pingIcmp
	 * @param pingTcp
	 * @param pingUdp
	 * @param tracertIcmp
	 * @param tracertUdp
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	/**
	 * 网络连通性业务-层级维度
	 * @param pingIcmp
	 * @param pingTcp
	 * @param pingUdp
	 * @param tracertIcmp
	 * @param tracertUdp
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	/**
	 * 网络连通性业务-区域维度
	 * @param pingIcmp
	 * @param pingTcp
	 * @param pingUdp
	 * @param tracertIcmp
	 * @param tracertUdp
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateArea1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	/**
	 * 网络连通性业务-门户维度
	 * @param pingIcmp
	 * @param pingTcp
	 * @param pingUdp
	 * @param tracertIcmp
	 * @param tracertUdp
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTarget1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	/**
	 * 两天相隔的天数
	 * @param date1
	 * @param date2
	 * @return int
	 */
	int differentDays(Date date1, Date date2);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourPingEntity recordHourPing);
	
	void update(RecordHourPingEntity recordHourPing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
