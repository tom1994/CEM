package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.AlarmTemplateDao;
import io.cem.modules.cem.entity.AlarmTemplateEntity;
import io.cem.modules.cem.service.AlarmTemplateService;



@Service("alarmTemplateService")
public class AlarmTemplateServiceImpl implements AlarmTemplateService {
	@Autowired
	private AlarmTemplateDao alarmTemplateDao;
	
	@Override
	public AlarmTemplateEntity queryObject(Integer id){
		return alarmTemplateDao.queryObject(id);
	}
	
	@Override
	public List<AlarmTemplateEntity> queryList(Map<String, Object> map){
		return alarmTemplateDao.queryList(map);
	}

	@Override
	public List<AlarmTemplateEntity> queryatList(Integer id){
		return alarmTemplateDao.queryatList(id);
	}

	@Override
	public List<AlarmTemplateEntity> queryAtByService(Integer id){
		return alarmTemplateDao.queryAtByService(id);
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return alarmTemplateDao.queryTotal(map);
	}
	
	@Override
	public void save(AlarmTemplateEntity alarmTemplate){
		alarmTemplateDao.save(alarmTemplate);
	}
	
	@Override
	public void update(AlarmTemplateEntity alarmTemplate){
		alarmTemplateDao.update(alarmTemplate);
	}
	
	@Override
	public void delete(Integer id){
		alarmTemplateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		alarmTemplateDao.deleteBatch(ids);
	}
	
}
