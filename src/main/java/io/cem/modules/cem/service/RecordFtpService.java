package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordFtpEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
public interface RecordFtpService {
	
	RecordFtpEntity queryObject(Integer id);
	
	List<RecordFtpEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordFtpEntity recordFtp);
	
	void update(RecordFtpEntity recordFtp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
