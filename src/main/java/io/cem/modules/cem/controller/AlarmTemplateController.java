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

import io.cem.modules.cem.entity.AlarmTemplateEntity;
import io.cem.modules.cem.service.AlarmTemplateService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
@RestController
@RequestMapping("alarmtemplate")
public class AlarmTemplateController {
	@Autowired
	private AlarmTemplateService alarmTemplateService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("alarmtemplate:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AlarmTemplateEntity> alarmTemplateList = alarmTemplateService.queryList(query);
		int total = alarmTemplateService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(alarmTemplateList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("alarmtemplate:info")
	public R info(@PathVariable("id") Integer id){
		AlarmTemplateEntity alarmTemplate = alarmTemplateService.queryObject(id);
		
		return R.ok().put("alarmTemplate", alarmTemplate);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("alarmtemplate:save")
	public R save(@RequestBody AlarmTemplateEntity alarmTemplate){
		alarmTemplateService.save(alarmTemplate);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("alarmtemplate:update")
	public R update(@RequestBody AlarmTemplateEntity alarmTemplate){
		alarmTemplateService.update(alarmTemplate);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("alarmtemplate:delete")
	public R delete(@RequestBody Integer[] ids){
		alarmTemplateService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
