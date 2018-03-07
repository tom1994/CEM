package io.cem.modules.cem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.modules.cem.entity.DiagnoseEntity;
import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.cem.service.TaskDispatchService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.RecordRadiusEntity;
import io.cem.modules.cem.service.RecordRadiusService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;

import static java.lang.Thread.sleep;


/**
 */
@RestController
@RequestMapping("recordradius")
public class RecordRadiusController {
	@Autowired
	private RecordRadiusService recordRadiusService;

	@Autowired
	private TaskDispatchService taskDispatchService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(String resultdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject resultdata_jsonobject = JSONObject.parseObject(resultdata);
		try {
			map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
		}
		if (Integer.parseInt(map.get("queryType").toString()) == 1) {
			List<RecordRadiusEntity> resultList = recordRadiusService.queryRadiusList(map);
			System.out.println(resultList);
			total = recordRadiusService.queryTotal(map);
			PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
			return R.ok().put("page", pageUtil);
		} else {
			List<RecordHourRadiusEntity> resultList = recordRadiusService.queryIntervalList(map);
			System.out.println(resultList);
			total = recordRadiusService.queryIntervalTotal(map);
			PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
			return R.ok().put("page", pageUtil);
		}
	}


	@RequestMapping("/diagnose")
	public R diagnose(@RequestBody DiagnoseEntity diagnoseEntity) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Integer[] dispatchId = diagnoseEntity.getDispatchId();
		int page = diagnoseEntity.getPage();
		int limit = diagnoseEntity.getLimit();
//        try {
////            map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
//        } catch (RuntimeException e) {
//            throw new RRException("内部参数错误，请重试！");
//        }
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		int total = recordRadiusService.queryTotal(map);
		for (int i = 0; i < 20; i++){
			if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
				break;
			} else {
				sleep(10000);
			}
		}
		List<RecordRadiusEntity> resultList = new ArrayList<>();
		for(int i = 0; i<dispatchId.length;i++){
			map.put("dispatch_id", dispatchId[i]);
			resultList.addAll(recordRadiusService.queryRadiusTest(map));
		}
		System.out.println(resultList);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordradius:info")
	public R info(@PathVariable("id") Integer id){
		RecordRadiusEntity recordRadius = recordRadiusService.queryObject(id);
		
		return R.ok().put("recordRadius", recordRadius);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordradius:save")
	public R save(@RequestBody RecordRadiusEntity recordRadius){
		recordRadiusService.save(recordRadius);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordradius:update")
	public R update(@RequestBody RecordRadiusEntity recordRadius){
		recordRadiusService.update(recordRadius);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordradius:delete")
	public R delete(@RequestBody Integer[] ids){
		recordRadiusService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
