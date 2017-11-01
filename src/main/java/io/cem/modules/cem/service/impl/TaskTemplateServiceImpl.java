package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskTemplateDao;
import io.cem.modules.cem.entity.TaskTemplateEntity;
import io.cem.modules.cem.service.TaskTemplateService;



@Service("taskTemplateService")
public class TaskTemplateServiceImpl implements TaskTemplateService {
	@Autowired
	private TaskTemplateDao taskTemplateDao;
	
	@Override
	public TaskTemplateEntity queryObject(Integer id){
		return taskTemplateDao.queryObject(id);
	}
	
	@Override
	public List<TaskTemplateEntity> queryList(Map<String, Object> map){
		return taskTemplateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return taskTemplateDao.queryTotal(map);
	}
	
	@Override
	public void save(TaskTemplateEntity taskTemplate){
		taskTemplateDao.save(taskTemplate);
	}
	
	@Override
	public void update(TaskTemplateEntity taskTemplate){
		taskTemplateDao.update(taskTemplate);
	}
	
	@Override
	public void delete(Integer id){
		taskTemplateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		taskTemplateDao.deleteBatch(ids);
	}
	
}
