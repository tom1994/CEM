package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourDhcpEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:34
 */
public interface RecordHourDhcpService {
	
	RecordHourDhcpEntity queryObject(Integer id);
	
	List<RecordHourDhcpEntity> queryList(Map<String, Object> map);

	List<RecordHourDhcpEntity> queryDhcp(Map<String,Object> map);

	List<RecordHourDhcpEntity> queryDhcpList(Map<String, Object> map);

	List<RecordHourDhcpEntity> queryExitList(Map<String, Object> map);

	List<RecordHourDhcpEntity> queryDayList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourDhcpEntity recordHourDhcp);
	
	void update(RecordHourDhcpEntity recordHourDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
