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

import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.cem.service.RecordGameService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
@RestController
@RequestMapping("recordgame")
public class RecordGameController {
	@Autowired
	private RecordGameService recordGameService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordgame:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordGameEntity> recordGameList = recordGameService.queryList(query);
		int total = recordGameService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordGameList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordgame:info")
	public R info(@PathVariable("id") Integer id){
		RecordGameEntity recordGame = recordGameService.queryObject(id);
		
		return R.ok().put("recordGame", recordGame);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordgame:save")
	public R save(@RequestBody RecordGameEntity recordGame){
		recordGameService.save(recordGame);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordgame:update")
	public R update(@RequestBody RecordGameEntity recordGame){
		recordGameService.update(recordGame);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordgame:delete")
	public R delete(@RequestBody Integer[] ids){
		recordGameService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
