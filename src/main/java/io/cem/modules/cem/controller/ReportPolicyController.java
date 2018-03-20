package io.cem.modules.cem.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.ReportPolicyEntity;
import io.cem.modules.cem.service.ReportPolicyService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 报表策略
 */
@RestController
@RequestMapping("reportpolicy")
public class ReportPolicyController {
	@Autowired
	private ReportPolicyService reportPolicyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("reportpolicy:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ReportPolicyEntity> reportPolicyList = reportPolicyService.queryList(query);
		int total = reportPolicyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(reportPolicyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("reportpolicy:info")
	public R info(@PathVariable("id") Integer id){
		ReportPolicyEntity reportPolicy = reportPolicyService.queryObject(id);
		
		return R.ok().put("reportPolicy", reportPolicy);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("reportpolicy:save")
	public R save(@RequestBody ReportPolicyEntity reportPolicy){
		reportPolicyService.save(reportPolicy);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("reportpolicy:update")
	public R update(@RequestBody ReportPolicyEntity reportPolicy){
		reportPolicyService.update(reportPolicy);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("reportpolicy:delete")
	public R delete(@RequestBody Integer[] ids){
		reportPolicyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
