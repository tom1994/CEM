package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ErrorCodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
public interface ErrorCodeService {
	
	ErrorCodeEntity queryObject(Integer code);
	
	List<ErrorCodeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErrorCodeEntity errorCode);
	
	void update(ErrorCodeEntity errorCode);
	
	void delete(Integer code);
	
	void deleteBatch(Integer[] codes);
}
