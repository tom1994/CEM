package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ReportPolicyEntity;

import java.util.List;
import java.util.Map;

/**
 * 报表策略
 */
public interface ReportPolicyService {
	
	ReportPolicyEntity queryObject(Integer id);

	/**
	 * 列表
	 * @param map
	 * @return
	 */
	List<ReportPolicyEntity> queryList(Map<String, Object> map);

	/**
	 * 总数
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param reportPolicy
	 */
	void save(ReportPolicyEntity reportPolicy);

	/**
	 * 更新
	 * @param reportPolicy
	 */
	void update(ReportPolicyEntity reportPolicy);

	/**
	 * 名称是否重复
	 * @param reportName
	 * @return int
	 */
	int queryExist(String reportName);

	/**
	 * 删除
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(Integer[] ids);
}
