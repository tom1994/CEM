package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordPingService;
import io.cem.modules.cem.service.RecordTracertService;
import io.cem.modules.cem.service.ReportPolicyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 报表策略
 */
@RestController
@RequestMapping("reportpolicy")
public class ReportPolicyController {
	@Autowired
	private ReportPolicyService reportPolicyService;
	@Autowired
	private RecordPingService recordPingService;
	@Autowired
	private RecordTracertService recordTracertService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("reportpolicy:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ReportPolicyEntity> reportPolicyList = reportPolicyService.queryList(query);
		int total = reportPolicyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(reportPolicyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 定时报表
	 */
	@RequestMapping("/reportlist")
	@RequiresPermissions("reportpolicy:reportlist")
	public R reportlist(String reportdata, Integer page, Integer limit) throws Exception{
		Map<String, Object> map = new HashMap<>();
		JSONObject reportdata_jsonobject = JSONObject.parseObject(reportdata);
		try {
			map.putAll(JSONUtils.jsonToMap(reportdata_jsonobject));
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
			total = reportPolicyService.queryTotal(map);
		}
		List<ReportPolicyEntity> probeList = reportPolicyService.queryList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/download/{reportdata}")
	@RequiresPermissions("reportpolicy:download")
	public void downloadProbe(HttpServletResponse response, @PathVariable("reportdata") Integer[] reportdata) throws RRException {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(reportdata[0]);
		System.out.println(reportdata[1]);
		System.out.println(reportdata[2]);
		ReportPolicyEntity detail = reportPolicyService.queryObject(reportdata[0]);
		int service = detail.getServiceType();
		int queryType = detail.getQueryType();
		String startDate = "";
		String terminalDate = "";
		try {
			startDate=reportPolicyService.strToDateFormat(reportdata[1].toString());
			terminalDate=reportPolicyService.strToDateFormat(reportdata[2].toString());
		}catch (Exception e) {
			e.printStackTrace();
		}

		map.put("probe_id",detail.getProbeId());
		map.put("service_type",detail.getServiceType());
		map.put("start_time",detail.getStartTime());
		map.put("end_time",detail.getEndTime());
		map.put("startDate",startDate);
		map.put("terminalDate",terminalDate);

		if (queryType== 0) {
			if(service == 1||service==2||service==3){
				List<RecordPingEntity> list = recordPingService.queryPingList(map);
				System.out.println(list);
				CollectionToFile.collectionToFile(response, list, RecordPingEntity.class);
			}else if(service==4||service==5){
				List<RecordTracertEntity> list = recordTracertService.queryTracertList(map);
				CollectionToFile.collectionToFile(response, list, RecordTracertEntity.class);
			}else if(service==10||service==11){

			}
			else{}


		} else {
			map.put("interval",detail.getInterval());
			if(service == 1||service==2||service==3){
				List<RecordHourPingEntity> list = recordPingService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourPingEntity.class);
			}else if(service==4||service==5){
				List<RecordHourTracertEntity> list = recordTracertService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourTracertEntity.class);
			}
			else{}

		}
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("reportpolicy:info")
	public R info(@PathVariable("id") Integer id){
		ReportPolicyEntity reportPolicy = reportPolicyService.queryObject(id);
		
		return R.ok().put("reportPolicy", reportPolicy);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("reportpolicy:save")
	public R save(@RequestBody ReportPolicyEntity reportPolicy){
		reportPolicyService.save(reportPolicy);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("reportpolicy:update")
	public R update(@RequestBody ReportPolicyEntity reportPolicy){
		reportPolicyService.update(reportPolicy);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("reportpolicy:delete")
	public R delete(@RequestBody Integer[] ids){
		reportPolicyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
