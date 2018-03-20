package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ReportPolicyEntity;

import java.util.List;
import java.util.Map;

/**
 * 报表策略
 */
public interface ReportPolicyService {
	
	ReportPolicyEntity queryObject(Integer id);
	
	List<ReportPolicyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ReportPolicyEntity reportPolicy);
	
	void update(ReportPolicyEntity reportPolicy);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
