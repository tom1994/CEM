package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordWebDownloadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayWebDownloadDao;
import io.cem.modules.cem.entity.RecordDayWebDownloadEntity;
import io.cem.modules.cem.service.RecordDayWebDownloadService;



@Service("recordDayWebDownloadService")
public class RecordDayWebDownloadServiceImpl implements RecordDayWebDownloadService {
	@Autowired
	private RecordDayWebDownloadDao recordDayWebDownloadDao;
	@Autowired
	private RecordWebDownloadDao recordWebDownloadDao;
	
	@Override
	public RecordDayWebDownloadEntity queryObject(Integer id){
		return recordDayWebDownloadDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayWebDownloadEntity> queryList(Map<String, Object> map){
		return recordDayWebDownloadDao.queryList(map);
	}

	@Override
	public List<RecordDayWebDownloadEntity> queryDay(Map<String,Object> map) {return recordWebDownloadDao.queryDay(map);}


	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayWebDownloadDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayWebDownloadEntity recordDayWebDownload){
		recordDayWebDownloadDao.save(recordDayWebDownload);
	}
	
	@Override
	public void update(RecordDayWebDownloadEntity recordDayWebDownload){
		recordDayWebDownloadDao.update(recordDayWebDownload);
	}
	
	@Override
	public void delete(Integer id){
		recordDayWebDownloadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayWebDownloadDao.deleteBatch(ids);
	}
	
}
