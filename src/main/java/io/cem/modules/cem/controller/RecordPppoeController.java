package io.cem.modules.cem.controller;

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

import io.cem.modules.cem.entity.RecordPppoeEntity;
import io.cem.modules.cem.service.RecordPppoeService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;

import static java.lang.Thread.sleep;


/**
 */
@RestController
@RequestMapping("recordpppoe")
public class RecordPppoeController {
	@Autowired
	private RecordPppoeService recordPppoeService;

	@Autowired
	private TaskDispatchService taskDispatchService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordpppoe:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordPppoeEntity> recordPppoeList = recordPppoeService.queryList(query);
		int total = recordPppoeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordPppoeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/diagnose")
	public R diagnose(String resultdata, Integer page, Integer limit, Integer dispatchId) throws Exception {
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
			total = recordPppoeService.queryTotal(map);
		}
		map.put("dispatch_id", dispatchId);
		while (true) {
//            int testStatus = Integer.parseInt(map.get("dispatchId").toString());
//            if (taskDispatchService.queryTestStatus(testStatus) > 0) {
			if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
				break;
			} else {
				sleep(5000);
			}
		}
		List<RecordPppoeEntity> resultList = recordPppoeService.queryPppoeTest(map);
		System.out.println(resultList);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordpppoe:info")
	public R info(@PathVariable("id") Integer id){
		RecordPppoeEntity recordPppoe = recordPppoeService.queryObject(id);
		
		return R.ok().put("recordPppoe", recordPppoe);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordpppoe:save")
	public R save(@RequestBody RecordPppoeEntity recordPppoe){
		recordPppoeService.save(recordPppoe);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordpppoe:update")
	public R update(@RequestBody RecordPppoeEntity recordPppoe){
		recordPppoeService.update(recordPppoe);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordpppoe:delete")
	public R delete(@RequestBody Integer[] ids){
		recordPppoeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
