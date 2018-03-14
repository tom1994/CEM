package io.cem.modules.cem.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.cem.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.TaskDispatchDao;
import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.service.TaskDispatchService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
//import org.junit.Test;
//
//import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
//import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@Service("taskDispatchService")
public class TaskDispatchServiceImpl implements TaskDispatchService {
    @Autowired
    private TaskDispatchDao taskDispatchDao;

    @Autowired
    private TargetService targetService;

    @Override
    public TaskDispatchEntity queryObject(Integer id) {
        return taskDispatchDao.queryObject(id);
    }

    @Override
    public List<TaskDispatchEntity> queryList(Map<String, Object> map) {
        return taskDispatchDao.queryList(map);
    }

    @Override
    public List<TaskDispatchEntity> queryDispatchList(Map<String, Object> map) {
        return taskDispatchDao.queryDispatchList(map);
    }

    @Override
    public List<TaskDispatchEntity> taskQueryDispatchList(Map<String, Object> map) {
        return taskDispatchDao.taskQueryDispatchList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return taskDispatchDao.queryTotal(map);
    }

    @Override
    public int queryDispatchTotal(Integer id) {
        return taskDispatchDao.queryDispatchTotal(id);
    }

    @Override
    public int taskQueryDispatchTotal(Map<String, Object> map) {
        return taskDispatchDao.taskQueryDispatchTotal(map);
    }

    @Override
    public int queryTestStatus(Integer[] ids) {
        int status = 0;
        for (int j = 0; j < ids.length; j++) {
            status = taskDispatchDao.queryTestStatus(ids[j]);
            System.out.println(status);
            if (status > 0) {
            } else {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public void save(TaskDispatchEntity taskDispatch) {
        taskDispatchDao.save(taskDispatch);
    }

    @Override
    public int saveAndReturn(TaskDispatchEntity taskDispatch) {
        return taskDispatchDao.saveAndReturn(taskDispatch);
    }

    @Override
    public void saveAll(List<TaskDispatchEntity> taskDispatchList) {
        taskDispatchDao.saveAll(taskDispatchList);
    }

    @Override
    public void update(TaskDispatchEntity taskDispatch) {
        taskDispatchDao.update(taskDispatch);
    }

    @Override
    public void delete(Integer id) {
        taskDispatchDao.delete(id);
    }

    @Override
    public void cancelTask(Integer id) {
        taskDispatchDao.cancelTask(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        taskDispatchDao.deleteBatch(ids);
    }

    @Override
    public String queryTargetBatch(String[] targetIdList) {
        String[] targetNameList = taskDispatchDao.queryTargetBatch(targetIdList);
        String targetName = "";
        for (int i = 0; i < targetNameList.length - 1; i++) {
            targetName = targetName + targetNameList[i] + ",";
        }
        targetName = targetName + targetNameList[targetNameList.length - 1];
        return targetName;
    }

    @Override
    public List<TaskDispatchEntity> transformTarget(List<TaskDispatchEntity> dispatchList) {
        for (int i = 0; i < dispatchList.size(); i++) {
            String target = dispatchList.get(i).getTarget();
            JSONArray targetJson = JSON.parseArray(target);
            int[] targetIds = new int[targetJson.size()];
            for (int j = 0; j < targetJson.size(); j++) {
                targetIds[j] = Integer.parseInt(targetJson.getJSONObject(j).get("target_id").toString());
            }
            StringBuilder targetNames = new StringBuilder("");
            List<TargetEntity> targetEntityList = targetService.queryTargetNames(targetIds);
            if (targetEntityList != null && targetEntityList.size() != 0){
                for (int j = 0; j <targetEntityList.size()-1 ; j++) {
                    targetNames.append(targetEntityList.get(j).getTargetName());
                    targetNames.append(",");
                }
                targetNames.append(targetEntityList.get(targetEntityList.size()-1).getTargetName());
            }
            dispatchList.get(i).setTarget(targetNames.toString());
        }
        return dispatchList;
    }

//	public class TestClass {
//
//		@Test
//		public void testHttpCall() throws IOException {
//			// given
//			HttpPost request = new HttpPost("https://114.236.91.16:23456/web/v1/tasks/2290");
//			request.add("Authorization", "Bearer 6b7544ae-63d3-4db6-9cc8-1dc95a991d50");
//
//
//			// when
//			HttpResponse response = HttpClientBuilder.create().build().execute(request);
//
//			// then
//			HttpEntity entity = response.getEntity();
//			String jsonString = EntityUtils.toString(entity);
//
//			// and if the response is
//			// {
//			//     "status": "OK"
//			// }
//			// Then we can assert it with
//			assertThat(jsonString, hasJsonPath("$.status", is("OK")));
//		}
//	}
}
