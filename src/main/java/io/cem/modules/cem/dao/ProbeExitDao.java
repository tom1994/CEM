package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 端口-出口对照表
 */
public interface ProbeExitDao extends BaseDao<ProbeExitEntity> {
    void operateStatus0(Integer id);
    void operateStatus1(Integer id);
    int queryNameExist(String exit);
    int queryUpdate(String exit,int id);
    int queryProbeExist(Integer probeId);
    int queryPortExist(String port);
    List<ProbeExitEntity> queryscoreList(Map<String, Object> map);
}
