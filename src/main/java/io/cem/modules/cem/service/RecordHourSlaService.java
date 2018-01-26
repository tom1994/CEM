package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:35
 */
public interface RecordHourSlaService {
	
	RecordHourSlaEntity queryObject(Integer id);
	
	List<RecordHourSlaEntity> queryList(Map<String, Object> map);

	List<RecordHourSlaEntity> querySlaList(Map<String, Object> map);

	List<RecordHourSlaEntity> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateSlaTcp(List<RecordHourSlaEntity> slaList);

	List<ScoreEntity> calculateSlaUdp(List<RecordHourSlaEntity> slaList);

	List<ScoreEntity> calculateDns(List<RecordHourDnsEntity> dnsList);

	List<ScoreEntity> calculateDhcp(List<RecordHourDhcpEntity> dhcpList);

	List<ScoreEntity> calculatePppoe(List<RecordHourPppoeEntity> pppoeList);

	List<ScoreEntity> calculateRadius(List<RecordHourRadiusEntity> radiusList);

	List<ScoreEntity> calculateService2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	List<ScoreEntity> calculateArea2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	List<ScoreEntity> calculateDate2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourSlaEntity recordHourSla);
	
	void update(RecordHourSlaEntity recordHourSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
