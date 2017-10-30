package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.AreasEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 * 行政区域县区信息表
 */
public interface AreasDao extends BaseDao<AreasEntity> {
    List<AreasEntity> queryAreaList(Integer id);
}
