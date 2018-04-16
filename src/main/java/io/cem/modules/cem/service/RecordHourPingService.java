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

	Map queryTime();

	Map queryDay();

	String queryBeforeDay(String date);

	String queryAfterDay(String date);
	
	List<RecordHourPingEntity> queryList(Map<String, Object> map);

	List<RecordHourPingEntity> queryPing(Map<String, Object> map);

	Future<List<RecordHourPingEntity>> queryPingList(Map<String, Object> map);

	Future<List<RecordHourPingEntity>> queryDayList(Map<String, Object> map);

	Future<List<RecordHourPingEntity>> queryExitList(Map<String, Object> map);

	Future<List<RecordHourPingEntity>> queryDayExitList(Map<String, Object> map);

	List<ScoreEntity> calculatePingIcmp(List<RecordHourPingEntity> pingList);

	List<ScoreEntity> calculatePingTcp(List<RecordHourPingEntity> pingList);

	List<ScoreEntity> calculatePingUdp(List<RecordHourPingEntity> pingList);

	List<ScoreEntity> calculateTracertIcmp(List<RecordHourTracertEntity> tracertList);

	List<ScoreEntity> calculateTracertUdp(List<RecordHourTracertEntity> tracertList);

	List<ScoreEntity> calculateService1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	List<ScoreEntity> calculateDate1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	List<ScoreEntity> calculateArea1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	List<ScoreEntity> calculateTarget1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp);

	List<ScoreEntity> dateChart1(List<ScoreEntity> scoreList);

	List<ScoreEntity> cityChart1(List<ScoreEntity> scoreList);

	List<ScoreEntity> probeChart1(List<ScoreEntity> scoreList);

	int differentDays(Date date1, Date date2);

	int queryTotal(Map<String, Object> map);

	int pingListTotal(Map<String, Object> map);
	
	void save(RecordHourPingEntity recordHourPing);
	
	void update(RecordHourPingEntity recordHourPing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
