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

import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.cem.service.RecordDnsService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;

import static java.lang.Thread.sleep;


/**
 */
@RestController
@RequestMapping("recorddns")
public class RecordDnsController {
	@Autowired
	private RecordDnsService recordDnsService;

	@Autowired
	private TaskDispatchService taskDispatchService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recorddns:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordDnsEntity> recordDnsList = recordDnsService.queryList(query);
		int total = recordDnsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordDnsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recorddns:info")
	public R info(@PathVariable("id") Integer id){
		RecordDnsEntity recordDns = recordDnsService.queryObject(id);
		
		return R.ok().put("recordDns", recordDns);
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
			total = recordDnsService.queryTotal(map);
		}
		while (true) {

			if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
				break;
			} else {
				sleep(5000);
			}
		}
		List<RecordDnsEntity> resultList = new ArrayList<>();
		for(int i = 0; i<dispatchId.length;i++){
			map.put("dispatch_id", dispatchId[i]);
			resultList.addAll(recordDnsService.queryDnsTest(map));
		}
		System.out.println(resultList);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recorddns:save")
	public R save(@RequestBody RecordDnsEntity recordDns){
		recordDnsService.save(recordDns);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recorddns:update")
	public R update(@RequestBody RecordDnsEntity recordDns){
		recordDnsService.update(recordDns);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recorddns:delete")
	public R delete(@RequestBody Integer[] ids){
		recordDnsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
