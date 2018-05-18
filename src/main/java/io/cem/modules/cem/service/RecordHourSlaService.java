package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 */
public interface RecordHourSlaService {
	
	RecordHourSlaEntity queryObject(Integer id);
	
	List<RecordHourSlaEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordHourSlaEntity>
	 */
	List<RecordHourSlaEntity> querySla(Map<String,Object> map);

	/**
	 * 查询小时表
	 * @param map
	 * @return Future<List<RecordHourSlaEntity>>
	 */
	Future<List<RecordHourSlaEntity>> querySlaList(Map<String, Object> map);

	/**
	 * 门户查询小时表
	 * @param map
	 * @return Future<List<RecordHourSlaEntity>>
	 */
	Future<List<RecordHourSlaEntity>> queryTargetHourList(Map<String, Object> map);

	/**
	 * 出口查询小时表
	 * @param map
	 * @return Future<List<RecordHourSlaEntity>>
	 */
	Future<List<RecordHourSlaEntity>> queryExitList(Map<String, Object> map);

	/**
	 * 出口查询天表
	 * @param map
	 * @return Future<List<RecordHourSlaEntity>>
	 */
	Future<List<RecordHourSlaEntity>> queryDayExitList(Map<String, Object> map);

	/**
	 * 查询天表
	 * @param map
	 * @return Future<List<RecordHourSlaEntity>>
	 */
	Future<List<RecordHourSlaEntity>> queryDayList(Map<String, Object> map);

	/**
	 * 业务1得分
	 * @param slaList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateSlaTcp(List<RecordHourSlaEntity> slaList);

	/**
	 * 业务2得分
	 * @param slaList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateSlaUdp(List<RecordHourSlaEntity> slaList);

	/**
	 * 业务3得分
	 * @param dnsList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDns(List<RecordHourDnsEntity> dnsList);

	/**
	 * 业务4得分
	 * @param dhcpList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDhcp(List<RecordHourDhcpEntity> dhcpList);

	/**
	 * 业务5得分
	 * @param pppoeList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculatePppoe(List<RecordHourPppoeEntity> pppoeList);

	/**
	 * 业务6得分
	 * @param radiusList
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateRadius(List<RecordHourRadiusEntity> radiusList);

	/**
	 * 网络层质量业务--探针维度
	 * @param slaTcp
	 * @param slaUdp
	 * @param dns
	 * @param dhcp
	 * @param pppoe
	 * @param radius
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateService2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	/**
	 * 网络层质量业务--区域维度
	 * @param slaTcp
	 * @param slaUdp
	 * @param dns
	 * @param dhcp
	 * @param pppoe
	 * @param radius
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateArea2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	/**
	 * 网络层质量业务--门户维度
	 * @param slaTcp
	 * @param slaUdp
	 * @param dns
	 * @param dhcp
	 * @param pppoe
	 * @param radius
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateTarget2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	/**
	 * 网络层质量业务--时间维度
	 * @param slaTcp
	 * @param slaUdp
	 * @param dns
	 * @param dhcp
	 * @param pppoe
	 * @param radius
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateDate2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	/**
	 * 网络层质量业务--层级维度
	 * @param slaTcp
	 * @param slaUdp
	 * @param dns
	 * @param dhcp
	 * @param pppoe
	 * @param radius
	 * @return List<ScoreEntity>
	 */
	List<ScoreEntity> calculateLayer2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourSlaEntity recordHourSla);
	
	void update(RecordHourSlaEntity recordHourSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
