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

	List<ScoreEntity> calculateService2(List<RecordHourSlaEntity> slaList, List<RecordHourDnsEntity> dnsList, List<RecordHourDhcpEntity> dhcpList, List<RecordHourPppoeEntity> pppoeList, List<RecordHourRadiusEntity> radiusList);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourSlaEntity recordHourSla);
	
	void update(RecordHourSlaEntity recordHourSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
