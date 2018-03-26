package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:52
 */
public interface RecordHourWebPageService {
	
	RecordHourWebPageEntity queryObject(Integer id);
	
	List<RecordHourWebPageEntity> queryList(Map<String, Object> map);

	List<RecordHourWebPageEntity> queryWebPage(Map<String,Object> map);

	List<RecordHourWebPageEntity> queryWebList(Map<String, Object> map);
	List<RecordHourWebPageEntity> queryWebAreaList(Map<String, Object> map);

	List<RecordHourWebPageEntity> queryWebRankList(Map<String, Object> map);

	List<RecordHourWebPageEntity> queryDayList(Map<String, Object> map);

	List<ScoreEntity> calculateService3(List<RecordHourWebPageEntity> webPageList);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordHourWebPageEntity recordHourWebPage);
	
	void update(RecordHourWebPageEntity recordHourWebPage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
