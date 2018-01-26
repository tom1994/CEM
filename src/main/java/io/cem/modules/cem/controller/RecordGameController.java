package io.cem.modules.cem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.modules.cem.service.TaskDispatchService;
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

import static java.lang.Thread.sleep;


/**
 */
@RestController
@RequestMapping("recordgame")
public class RecordGameController {
	@Autowired
	private RecordGameService recordGameService;

	@Autowired
	private TaskDispatchService taskDispatchService;
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

	@RequestMapping("/diagnose")
	public R diagnose(String resultdata, Integer page, Integer limit, Integer[] dispatchId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject resultdata_jsonobject = JSONObject.parseObject(resultdata);
		try {
			map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int total = 0;
		if (page == null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		} else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = recordGameService.queryTotal(map);
		}
		while (true) {

			if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
				break;
			} else {
				sleep(5000);
			}
		}
		List<RecordGameEntity> resultList = new ArrayList<>();
		for(int i = 0; i<dispatchId.length;i++){
			map.put("dispatch_id", dispatchId[i]);
			resultList.addAll(recordGameService.queryGameTest(map));
		}
		System.out.println(resultList);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
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
