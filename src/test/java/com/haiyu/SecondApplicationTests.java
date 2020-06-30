package com.haiyu;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.haiyu.manager.ManagerApplication;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.*;
import com.haiyu.manager.dto.AdminChartDTO;
import com.haiyu.manager.iot.JsonUtils;
import com.haiyu.manager.pojo.*;
import com.haiyu.manager.service.AdminSubscriptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ManagerApplication.class})
@TestPropertySource("classpath:application-iot.properties")
public class SecondApplicationTests {

	@Autowired
	private BaseAdminUserMapper baseAdminUserMapper;

	@Autowired
	private BaseAdminDeviceMapper baseAdminDeviceMapper;

	@Autowired
	private BaseAdminProductMapper baseAdminProductMapper;

	@Autowired
	private ChartTestMapper chartTestMapper;

	@Autowired
	private BaseAdminChartMapper baseAdminChartMapper;

	@Autowired
	private AdminSubscriptionService adminSubscriptionService;

	@Autowired
	private BaseAdminSubscriptionMapper baseAdminSubscriptionMapper;

	@Autowired
	private BaseAdminOrderMapper baseAdminOrderMapper;

	@Test
	public void test01() {
		BaseAdminDevice result = baseAdminDeviceMapper.getDeviceById(10);
		System.out.println(result.getDeviceId());
	}

	@Test
	public void test02() {
		BaseAdminProduct result = baseAdminProductMapper.getProductByProductId("16546");
		System.out.println(result.getProductName());
	}

	@Test
	public void test03() {
		/*BaseAdminProduct result = baseAdminProductMapper.insert();*/
		/*System.out.println(result.getProductName());*/
	}
	//删除设备测试
	@Test
	public void test04() {
		int result = baseAdminDeviceMapper.destroyDevice(4);
		System.out.println(result);
	}
	//根据设备ID获取产品ID
	@Test
	public void test05() {
		BaseAdminDevice device = baseAdminDeviceMapper.getDeviceByDeviceId("15613513");
		System.out.println(device);
	}
	@Test
	public void test051() {
		BaseAdminProduct device = baseAdminProductMapper.getProductByProductId("3232");
		System.out.println(device);
	}
	//echarts图表测试
	@Test
	public void test06() {
		List<BaseAdminChart> resultList = baseAdminChartMapper.selectAll();
		List list = new ArrayList();
		for (int i=0;i<resultList.size();i++) {
			ChartRequest chart = new ChartRequest(resultList.get(i).getTemperature(),resultList.get(i).getHumilevel(),resultList.get(i).getRegTime());
			list.add(chart);
		}
		String data = JsonUtils.objectToJson(list);
		System.out.println("data数据为："+data);
	}
	@Test
	public void test07() {
		List<ChartTest> resultList = chartTestMapper.selectAll();
		Map<String, Object> dataMap = new HashMap();
		for (int i=0;i<resultList.size();i++) {
			dataMap.put("name",resultList.get(i).getName());
			dataMap.put("value",resultList.get(i).getValue());
		}
		System.out.println("返回的dataMap为："+dataMap);
	}
	@Test
	public void test08() {
		List<BaseAdminChart> resultList = baseAdminChartMapper.getChartListByDeviceName("device2");
		for (BaseAdminChart chart:resultList
			 ) {
			System.out.println("获取的数据："+chart);
		}
	}

	@Test
	public void test09() {
		String deviceName = "device3";
		List<BaseAdminChart> resultList = baseAdminChartMapper.getChartListByDeviceName(deviceName);
		for (BaseAdminChart chart:resultList
		) {
			System.out.println("获取的数据："+chart);
		}
		List list = new ArrayList();
		for (int i=0;i<resultList.size();i++) {
			ChartRequest chart = new ChartRequest(resultList.get(i).getTemperature(),resultList.get(i).getHumilevel(),resultList.get(i).getRegTime());
			list.add(chart);
		}
		String resultData = JsonUtils.objectToJson(list);
		System.out.println("resultData数据为："+resultData);
	}

	@Test
	public void test10() {
		String deviceName = "device3";
		System.out.println(deviceName);
		List<BaseAdminChart> resultList = baseAdminChartMapper.getChartListByDeviceName(deviceName);
		System.out.println("resultList："+resultList);
	}
	@Test
	public void test11() {
		adminSubscriptionService.deleteSubscription(6);
	}
	@Test
	public void test12() {
		BaseAdminSubscription subscription = baseAdminSubscriptionMapper.getSubscriptionBySubId("89587");
		System.out.println(subscription);
	}
	@Test
	public void test13() {
		BaseAdminOrder order = baseAdminOrderMapper.getOrderByCommandId("1");
		BaseAdminOrder order1 = new BaseAdminOrder();
		order1.setProductId("asdf");
		System.out.println(order);
		baseAdminOrderMapper.insert(order1);
	}
	@Test
	public void test14() {
		BaseAdminOrder order = baseAdminOrderMapper.getOrderByCommandId("1");
		String commandId = order.getCommandId();
		int s = baseAdminOrderMapper.updateStatusByCommandId(commandId,"指令已取消");
		System.out.println(s);
	}
	@Test
	public void test15() {
		BaseAdminUser user = baseAdminUserMapper.getUserByUserName("admin",null);
		System.out.println(user.getAppKey());
		System.out.println(user.getAppSecret());
	}
	@Test
	public void test16() {
		BaseAdminChart chart = baseAdminChartMapper.getChartByTime("2020-06-13 10:16:45");
		System.out.println(chart);
	}
}
