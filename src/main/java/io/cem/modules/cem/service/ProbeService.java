package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Miao
 */
public interface ProbeService {
	/**
	 * 根据id筛选探针
	 * @param id
	 * @return ProbeEntity
	 */
	ProbeEntity queryObject(Integer id);

	/**
	 * 探针列表
	 * @param map
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryList(Map<String, Object> map);

	/**
	 * 查询所有探针
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryShowList();

	/**
	 * 按条件筛选探针列表
	 * @param map
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryProbeList(Map<String, Object> map);

	/**
	 * 根据id筛选探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryProbe(Integer id);

	/**
	 * 根据层级筛选探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryProbeByLayer(Integer id);

	/**
	 * 根据城市筛选探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryProbeByCity(Integer id);

	/**
	 * 筛选在线探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryOnlineList(Integer id);

	/**
	 * 筛选核心探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryCenterList(Integer id);

	/**
	 * 根据探针组筛选探针
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryProbeListByGroup(Integer id);

	/**
	 * 筛选出口探针
	 * @param map
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryExitList(Map<String, Object> map);

	/**
	 * 筛选探针端口
	 * @param id
	 * @return List<ProbeEntity>
	 */
	List<ProbeEntity> queryPortList(Integer id);

	/**
	 * 查看重名探针个数
	 * @param probeName
	 * @return int
	 */
	int queryExist(String probeName);

	/**
	 * 查看重名探针个数
	 * @param probeName
	 * @param probeId
	 * @return int
	 */
	int queryExist(String probeName, int probeId);

	/**
	 * 查看探针详情
	 * @param id
	 * @return ProbeEntity
	 */
	ProbeEntity queryDetail(Integer id);

	/**
	 * 探针总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param probe
	 */
	void save(ProbeEntity probe);

	/**
	 * 更新
	 * @param probe
	 */
	void update(ProbeEntity probe);

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

	/**
	 * 更新上联探针
	 * @param id
	 */
	void updateUpstream(Integer id);
}
