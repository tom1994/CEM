package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDnsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
public interface RecordDnsService {
	
	RecordDnsEntity queryObject(Integer id);
	
	List<RecordDnsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDnsEntity recordDns);
	
	void update(RecordDnsEntity recordDns);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
