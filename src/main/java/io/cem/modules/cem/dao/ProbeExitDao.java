package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 端口-出口对照表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-03-26 19:47:28
 */
public interface ProbeExitDao extends BaseDao<ProbeExitEntity> {
    void operateStatus0(Integer id);
    void operateStatus1(Integer id);
    List<ProbeExitEntity> queryscoreList(Map<String, Object> map);
}
