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

	List<RecordHourSlaEntity> querySla(Map<String,Object> map);

	Future<List<RecordHourSlaEntity>> querySlaList(Map<String, Object> map);

	Future<List<RecordHourSlaEntity>> queryTargetHourList(Map<String, Object> map);

	Future<List<RecordHourSlaEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourSlaEntity>> queryDayExitList(Map<String, Object> map);

	Future<List<RecordHourSlaEntity>> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateSlaTcp(List<RecordHourSlaEntity> slaList);

	List<ScoreEntity> calculateSlaUdp(List<RecordHourSlaEntity> slaList);

	List<ScoreEntity> calculateDns(List<RecordHourDnsEntity> dnsList);

	List<ScoreEntity> calculateDhcp(List<RecordHourDhcpEntity> dhcpList);

	List<ScoreEntity> calculatePppoe(List<RecordHourPppoeEntity> pppoeList);

	List<ScoreEntity> calculateRadius(List<RecordHourRadiusEntity> radiusList);

	List<ScoreEntity> calculateService2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	List<ScoreEntity> calculateArea2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	List<ScoreEntity> calculateTarget2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	List<ScoreEntity> calculateDate2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourSlaEntity recordHourSla);
	
	void update(RecordHourSlaEntity recordHourSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
