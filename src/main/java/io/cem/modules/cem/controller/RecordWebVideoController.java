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

import io.cem.modules.cem.entity.RecordWebVideoEntity;
import io.cem.modules.cem.service.RecordWebVideoService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
@RestController
@RequestMapping("recordwebvideo")
public class RecordWebVideoController {
	@Autowired
	private RecordWebVideoService recordWebVideoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordwebvideo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordWebVideoEntity> recordWebVideoList = recordWebVideoService.queryList(query);
		int total = recordWebVideoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordWebVideoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordwebvideo:info")
	public R info(@PathVariable("id") Integer id){
		RecordWebVideoEntity recordWebVideo = recordWebVideoService.queryObject(id);
		
		return R.ok().put("recordWebVideo", recordWebVideo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordwebvideo:save")
	public R save(@RequestBody RecordWebVideoEntity recordWebVideo){
		recordWebVideoService.save(recordWebVideo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordwebvideo:update")
	public R update(@RequestBody RecordWebVideoEntity recordWebVideo){
		recordWebVideoService.update(recordWebVideo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordwebvideo:delete")
	public R delete(@RequestBody Integer[] ids){
		recordWebVideoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
