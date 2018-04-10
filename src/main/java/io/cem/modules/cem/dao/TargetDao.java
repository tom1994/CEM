package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.Map;
import java.util.List;


public interface TargetDao extends BaseDao<TargetEntity> {
    List<TargetEntity> infoBatch(Integer serviceId);

    List<TargetEntity> queryTgByTList(Map<String, Object> map);

    List<TargetEntity> queryTargetList(Integer id);

    List<TargetEntity> queryTargetListByGroup(Integer id);

    int queryExist(String targetName);

    List<TargetEntity> queryTargetNames(int[] ids);
}
