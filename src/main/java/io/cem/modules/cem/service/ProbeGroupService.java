package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeGroupEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface ProbeGroupService {
	/**
	 * 根据id筛选信息
	 * @param id
	 * @return ProbeGroupEntity
	 */
	ProbeGroupEntity queryObject(Integer id);

	/**
	 * 探针组列表
	 * @param map
	 * @return List<ProbeGroupEntity>
	 */
	List<ProbeGroupEntity> queryList(Map<String, Object> map);

	/**
	 * 探针组个数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param probeGroup
	 */
	void save(ProbeGroupEntity probeGroup);

	/**
	 * 查询重名探针组个数
	 * @param pgName
	 * @return
	 */
	int queryExist(String pgName);

	/**
	 * 查询重名探针组个数
	 * @param pgName
	 * @param id
	 * @return
	 */
	int queryUpdate(String pgName,int id);

	/**
	 * 修改
	 * @param probeGroup
	 */
	void update(ProbeGroupEntity probeGroup);

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
