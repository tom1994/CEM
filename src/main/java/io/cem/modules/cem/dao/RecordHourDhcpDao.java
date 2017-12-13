package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:34
 */
public interface RecordHourDhcpDao extends BaseDao<RecordHourDhcpEntity> {
    List<RecordHourDhcpEntity> queryDhcpList(Map<String, Object> map);
	
}
