package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDhcpEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public interface RecordDhcpService {
	
	RecordDhcpEntity queryObject(Integer id);
	
	List<RecordDhcpEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDhcpEntity recordDhcp);
	
	void update(RecordDhcpEntity recordDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
