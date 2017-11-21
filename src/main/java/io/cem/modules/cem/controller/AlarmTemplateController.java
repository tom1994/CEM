package io.cem.modules.cem.controller;

import java.util.HashMap;
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
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
@RestController
@RequestMapping("/cem/alarmtemplate")
public class AlarmTemplateController {
	@Autowired
	private AlarmTemplateService alarmTemplateService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("alarmtemplate:list")
	public R list(Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = alarmTemplateService.queryTotal(map);
		}
		List<AlarmTemplateEntity> alarmTemplateList = alarmTemplateService.queryList(map);
		PageUtils pageUtil = new PageUtils(alarmTemplateList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("alarmtemplate:info")
	public R info(@PathVariable("id") Integer id){
		List<AlarmTemplateEntity> atList = alarmTemplateService.queryatList(id);
		return R.ok().put("atList", atList);
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
