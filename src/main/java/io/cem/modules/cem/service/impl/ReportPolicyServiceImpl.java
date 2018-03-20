package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ReportPolicyDao;
import io.cem.modules.cem.entity.ReportPolicyEntity;
import io.cem.modules.cem.service.ReportPolicyService;



@Service("reportPolicyService")
public class ReportPolicyServiceImpl implements ReportPolicyService {
	@Autowired
	private ReportPolicyDao reportPolicyDao;
	
	@Override
	public ReportPolicyEntity queryObject(Integer id){
		return reportPolicyDao.queryObject(id);
	}
	
	@Override
	public List<ReportPolicyEntity> queryList(Map<String, Object> map){
		return reportPolicyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return reportPolicyDao.queryTotal(map);
	}
	
	@Override
	public void save(ReportPolicyEntity reportPolicy){
		reportPolicyDao.save(reportPolicy);
	}
	
	@Override
	public void update(ReportPolicyEntity reportPolicy){
		reportPolicyDao.update(reportPolicy);
	}
	
	@Override
	public void delete(Integer id){
		reportPolicyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		reportPolicyDao.deleteBatch(ids);
	}
	
}
