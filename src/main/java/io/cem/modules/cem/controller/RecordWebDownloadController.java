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

import io.cem.modules.cem.entity.RecordWebDownloadEntity;
import io.cem.modules.cem.service.RecordWebDownloadService;
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
@RequestMapping("recordwebdownload")
public class RecordWebDownloadController {
	@Autowired
	private RecordWebDownloadService recordWebDownloadService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordwebdownload:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordWebDownloadEntity> recordWebDownloadList = recordWebDownloadService.queryList(query);
		int total = recordWebDownloadService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordWebDownloadList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordwebdownload:info")
	public R info(@PathVariable("id") Integer id){
		RecordWebDownloadEntity recordWebDownload = recordWebDownloadService.queryObject(id);
		
		return R.ok().put("recordWebDownload", recordWebDownload);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordwebdownload:save")
	public R save(@RequestBody RecordWebDownloadEntity recordWebDownload){
		recordWebDownloadService.save(recordWebDownload);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordwebdownload:update")
	public R update(@RequestBody RecordWebDownloadEntity recordWebDownload){
		recordWebDownloadService.update(recordWebDownload);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordwebdownload:delete")
	public R delete(@RequestBody Integer[] ids){
		recordWebDownloadService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
