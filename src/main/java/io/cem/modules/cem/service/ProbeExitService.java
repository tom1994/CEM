package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 端口-出口对照表
 */
public interface ProbeExitService {
	/**
	 * 根据id筛选出口
	 * @param id
	 * @return ProbeExitEntity
	 */
	ProbeExitEntity queryObject(Integer id);

	/**
	 * 根据条件筛选信息
	 * @param map
	 * @return List<ProbeExitEntity>
	 */
	List<ProbeExitEntity> queryList(Map<String, Object> map);

	/**
	 * 筛选正在监控的出口
	 * @param map
	 * @return List<ProbeExitEntity>
	 */
	List<ProbeExitEntity> queryscoreList(Map<String, Object> map);

	/**
	 * 计算出口分数
	 * @param map
	 * @return ScoreEntity
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	ScoreEntity calculateScore(Map<String, Object> map) throws ExecutionException, InterruptedException;

	/**
	 * 获取总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param probeExit
	 */
	void save(ProbeExitEntity probeExit);

	/**
	 * 修改
	 * @param probeExit
	 */
	void update(ProbeExitEntity probeExit);

	/**
	 * 查看重名的个数
	 * @param exit
	 * @return int
	 */
	int queryNameExist(String exit);

	/**
	 * 查看重名的个数
	 * @param exit
	 * @param id
	 * @return int
	 */
	int queryUpdate(String exit,int id);

	/**
	 * 查看探针重复的个数
	 * @param probeId
	 * @return int
	 */
	int queryProbeExist(Integer probeId);

	/**
	 * 查看端口重复的个数
	 * @param port
	 * @return int
	 */
	int queryPortExist(String port);

	/**
	 * 修改出口监控状态
	 * @param id
	 */
	void operateStatus0(Integer id);

	/**
	 * 修改出口监控状态
	 * @param id
	 */
	void operateStatus1(Integer id);

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
