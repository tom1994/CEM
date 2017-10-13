package io.cem.modules.cem.controller;

import java.util.List;
import java.util.Map;

import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.RecordWebPageEntity;
import io.cem.modules.cem.service.RecordWebPageService;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
@RestController
@RequestMapping("recordwebpage")
public class RecordWebPageController {
	@Autowired
	private RecordWebPageService recordWebPageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordwebpage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordWebPageEntity> recordWebPageList = recordWebPageService.queryList(query);
		int total = recordWebPageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordWebPageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordwebpage:info")
	public R info(@PathVariable("id") Integer id){
		RecordWebPageEntity recordWebPage = recordWebPageService.queryObject(id);
		
		return R.ok().put("recordWebPage", recordWebPage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordwebpage:save")
	public R save(@RequestBody RecordWebPageEntity recordWebPage){
		recordWebPageService.save(recordWebPage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordwebpage:update")
	public R update(@RequestBody RecordWebPageEntity recordWebPage){
		recordWebPageService.update(recordWebPage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordwebpage:delete")
	public R delete(@RequestBody Integer[] ids){
		recordWebPageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
