package io.cem.modules.cem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.modules.cem.entity.DiagnoseEntity;
import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.cem.service.TaskDispatchService;
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

import static java.lang.Thread.sleep;


/**
 *
 */
@RestController
@RequestMapping("recordwebvideo")
public class RecordWebVideoController {
	@Autowired
	private RecordWebVideoService recordWebVideoService;

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
			List<RecordWebVideoEntity> resultList = recordWebVideoService.queryWebVideoList(map);
			System.out.println(resultList);
			total = recordWebVideoService.queryTotal(map);
			PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
			return R.ok().put("page", pageUtil);
		} else {
			List<RecordHourWebVideoEntity> resultList = recordWebVideoService.queryIntervalList(map);
			System.out.println(resultList);
			total = recordWebVideoService.queryIntervalTotal(map);
			PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
			return R.ok().put("page", pageUtil);
		}
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
		int total = recordWebVideoService.queryTotal(map);
		for (int i = 0; i < 20; i++){
			if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
				break;
			} else {
				sleep(20000);
			}
		}
		List<RecordWebVideoEntity> resultList = new ArrayList<>();
		for(int i = 0; i<dispatchId.length;i++){
			map.put("dispatch_id", dispatchId[i]);
			resultList.addAll(recordWebVideoService.queryWebVideoTest(map));
		}
		System.out.println(resultList);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
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
