package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourWebDownloadDao;
import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordWebDownloadDao;
import io.cem.modules.cem.entity.RecordWebDownloadEntity;
import io.cem.modules.cem.service.RecordWebDownloadService;



@Service("recordWebDownloadService")
public class RecordWebDownloadServiceImpl implements RecordWebDownloadService {
	@Autowired
	private RecordWebDownloadDao recordWebDownloadDao;

	@Autowired
	private RecordHourWebDownloadDao recordHourWebDownloadDao;

	@Override
	public RecordWebDownloadEntity queryObject(Integer id){
		return recordWebDownloadDao.queryObject(id);
	}
	
	@Override
	public List<RecordWebDownloadEntity> queryList(Map<String, Object> map){
		return recordWebDownloadDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordWebDownloadDao.queryTotal(map);
	}

	@Override
	public List<RecordWebDownloadEntity> queryWebDownloadTest(Map<String, Object> map){
		return recordWebDownloadDao.queryWebDownloadTest(map);
	}

	@Override
	public List<RecordWebDownloadEntity> queryWebDownloadList(Map<String, Object> map) {
		return recordWebDownloadDao.queryWebDownloadList(map);
	}

	@Override
	public List<RecordHourWebDownloadEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourWebDownloadDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourWebDownloadDao.queryIntervalTotal(map);
	}


	@Override
	public void save(RecordWebDownloadEntity recordWebDownload){
		recordWebDownloadDao.save(recordWebDownload);
	}
	
	@Override
	public void update(RecordWebDownloadEntity recordWebDownload){
		recordWebDownloadDao.update(recordWebDownload);
	}
	
	@Override
	public void delete(Integer id){
		recordWebDownloadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordWebDownloadDao.deleteBatch(ids);
	}
	
}
