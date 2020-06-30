package com.haiyu;

import com.ctg.ag.sdk.biz.*;
import com.ctg.ag.sdk.biz.aep_device_command.CancelCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_command.CreateCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_command.QueryCommandListRequest;
import com.ctg.ag.sdk.biz.aep_device_command.QueryCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_management.CreateDeviceRequest;
import com.ctg.ag.sdk.biz.aep_device_management.DeleteDeviceRequest;
import com.ctg.ag.sdk.biz.aep_device_management.QueryDeviceRequest;
import com.ctg.ag.sdk.biz.aep_device_management.UpdateDeviceRequest;
import com.ctg.ag.sdk.biz.aep_device_status.QueryDeviceStatusListRequest;
import com.ctg.ag.sdk.biz.aep_device_status.QueryDeviceStatusRequest;
import com.ctg.ag.sdk.biz.aep_product_management.CreateProductRequest;
import com.ctg.ag.sdk.biz.aep_product_management.DeleteProductRequest;
import com.ctg.ag.sdk.biz.aep_product_management.QueryProductListRequest;
import com.ctg.ag.sdk.biz.aep_product_management.QueryProductRequest;
import com.ctg.ag.sdk.biz.aep_subscribe_north.CreateSubscriptionRequest;
import com.ctg.ag.sdk.biz.aep_subscribe_north.DeleteSubscriptionRequest;
import com.ctg.ag.sdk.biz.aep_subscribe_north.GetSubscriptionRequest;
import com.ctg.ag.sdk.biz.aep_subscribe_north.GetSubscriptionsListRequest;
import com.ctg.ag.sdk.core.model.BaseApiResponse;
import com.haiyu.manager.ManagerApplication;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.common.utils.TimeStamp;
import com.haiyu.manager.dao.BaseAdminDeviceMapper;
import com.haiyu.manager.dao.BaseAdminProductMapper;
import com.haiyu.manager.dao.BaseAdminUserMapper;
import com.haiyu.manager.iot.JsonUtils;
import com.haiyu.manager.nbpojo.*;
import com.haiyu.manager.pojo.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ManagerApplication.class})
@TestPropertySource("classpath:application-iot.properties")
public class ManagerApplicationTests {

	@Autowired
	private BaseAdminUserMapper baseAdminUserMapper;

	@Autowired
	private BaseAdminDeviceMapper baseAdminDeviceMapper;

	@Autowired
	private BaseAdminProductMapper baseAdminProductMapper;

	//#应用的key
	@Value("${APP_KEY}")
	private String APP_KEY;
	//#应用的Secret
	@Value("${APP_SECRET}")
	private String APP_SECRET;
	//#产品的MasterKey
	@Value("${MasterKey}")
	private String MasterKey;
	//#产品ID
	@Value("${productId}")
	private String productId;
	//#设备ID
	@Value("${deviceId}")
	private String deviceId;
	//获取采集到的温湿度数据

	@Test
	public void contextLoads() {
		String  password = "1,9";
		String[] split = password.split(",");
		for (String s:split){
			System.out.println(s);
		}

	}

	@Test
	public void getDeviceList() {
		String  userName = "alice";
		BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(user.getSysUserName());
	}
	/*测试数据能否正确读出*/
	@Test
	public void gotest() throws Exception {
		System.out.println(APP_KEY);
		//System.out.println("asfd"+Global.getAppKey());
	}

	//批量获取设备数据
	@Test
	public void QueryDeviceStatusList() throws Exception {
		AepDeviceStatusClient client = AepDeviceStatusClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		QueryDeviceStatusListRequest request = new QueryDeviceStatusListRequest();
		Map<String,String> params=new HashMap<>();
		params.put("deviceId",deviceId);
		//params.put("deviceId",Global.getDeviceId());
		params.put("productId",productId);
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		//请求数据
		BaseApiResponse response = client.QueryDeviceStatusList(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		//定义map解析json数据
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("deviceStatusList", IotData.class);
		JsonResponse jsonResponse =(JsonResponse)JSONObject.toBean(jsonObject, JsonResponse.class,map);
		System.out.println("用户信息为："+ jsonResponse);
		List<IotData> dataList = jsonResponse.getDeviceStatusList();
		/*获取数据*/
		BaseAdminChart chartData = new BaseAdminChart();
		IotData iotData = new IotData();
		for (int i=0;i<dataList.size();i++) {
			iotData = dataList.get(i);
			if (iotData.getDatasetId().equals("temperature")) {
				//System.out.println("温度信息"+iotData.getValue());
				chartData.setTemperature(iotData.getValue());
			}else if (iotData.getDatasetId().equals("humilevel")) {
				chartData.setHumilevel(iotData.getValue());
				String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
				chartData.setRegTime(regTime);
				//System.out.println("湿度信息"+iotData.getValue());
			}
		}
		System.out.println("获取到的表单数据为："+chartData);
		System.out.println("获取到的ContentType数据为："+response.getContentType());
		System.out.println("获取到的Header数据为："+response.getHeaders());
		//System.out.println(client.QueryDeviceStatusList(request));
		client.shutdown();
	}

	//逐个请求温湿度数据
	//获取设备状态
	@Test
	public void QueryDeviceStatus() throws Exception {
		AepDeviceStatusClient client = AepDeviceStatusClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		QueryDeviceStatusRequest request = new QueryDeviceStatusRequest();
		// set your request params here
		Map<String,String> params=new HashMap<>();
		params.put("deviceId",deviceId);
		params.put("productId",productId);
		//查询温度信息
		params.put("datasetId","temperature");
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		//请求数据
		BaseApiResponse response = client.QueryDeviceStatus(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("deviceStatusList", IotData.class);
		JsonSingleResponse jsonResponse =(JsonSingleResponse)JSONObject.toBean(jsonObject,JsonSingleResponse.class,map);
		System.out.println("用户信息为："+ jsonResponse);
		IotData dataList = jsonResponse.getDeviceStatus();
		System.out.println("获得的数据信息为："+dataList);

		// more requests
		client.shutdown();
	}

	//获取温湿度
	@Test
	public void getChartDataList() throws Exception {
		BaseAdminChart chartData = NBIoTUtils.getIotDataList(APP_KEY,APP_SECRET,productId,deviceId);
		System.out.println(chartData);
	}
	//获取温度
	@Test
	public void getChartData() throws Exception {
		BaseAdminChart chartData = NBIoTUtils.getIotData(APP_KEY,APP_SECRET,productId,deviceId,"temperature");
		System.out.println(chartData);
	}

	//查询设备详情
	@Test
	public void QueryDevice() throws Exception {
		AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		QueryDeviceRequest request = new QueryDeviceRequest();
		// set your request params here
		request.setParamMasterKey(MasterKey);	// single value
		// request.addParamMasterKey(MasterKey);	// or multi values
		request.setParamDeviceId(deviceId);	// single value
		// request.addParamDeviceId(deviceId);	// or multi values
		request.setParamProductId(productId);	// single value
		// request.addParamProductId(productId);	// or multi values
		BaseApiResponse response = client.QueryDevice(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的Message数据为："+response.getBody());
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", DeviceResult.class);
		DeviceResponse deviceResponse =(DeviceResponse)JSONObject.toBean(jsonObject, DeviceResponse.class,map);
		System.out.println("用户信息为："+ deviceResponse);
		DeviceResult result = deviceResponse.getResult();
		System.out.println("获得的设备信息为："+result);
		client.shutdown();
	}

	//获取设备信息
	@Test
	public void getDeviceData() throws Exception {
		DeviceResult result = NBIoTUtils.getIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,deviceId);
		System.out.println(result);
	}

	//创建设备
	@Test
	public void CreateDevice() throws Exception {
		AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		CreateDeviceRequest request = new CreateDeviceRequest();
		// set your request params here
		request.setParamMasterKey(MasterKey);	// single value
/*		{
			"deviceName": "string",
				"deviceSn": "string",
				"imei": "string",
				"operator": "string",
				"other": {"autoObserver":0,
				"imsi":"12545154878451",
				"pskValue":"ADvNWlkcNq9AfKnk"},
			"productId": 0
		}*/
		//创建json对象
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceName","测试设备2");
		//设备编号南向云必填
		params.put("deviceSn","");
		//imei号，LWM2M,NB网关协议必填 为15位
		params.put("imei","123451235656132");
		//操作者，必填
		params.put("operator","username");
		params.put("productId",productId);
		Map<String,Object> other = new HashMap<String,Object>();
		other.put("autoObserver",0);
		other.put("imsi","");
		other.put("pskValue","");
		params.put("other",other);

		System.out.println("请求参数列表"+params);

		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		BaseApiResponse response = client.CreateDevice(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的Message数据为："+response);
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", CreateDeviceResult.class);
		CreateDeviceResponse deviceResponse =(CreateDeviceResponse)JSONObject.toBean(jsonObject, CreateDeviceResponse.class,map);
		System.out.println("用户信息为："+ deviceResponse);
		CreateDeviceResult createresult = deviceResponse.getResult();
		System.out.println("获得的数据信息为："+createresult);
		client.shutdown();
	}

	//创建设备 返回产品ID，设备ID，设备名，imei，创建时间
	@Test
	public void createDeviceTest() throws Exception {
		CreateDeviceResult result = NBIoTUtils.CreateIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,"测试设备3","999995555522231");
		result.setCreateTime(TimeStamp.stampToDate(TimeStamp.getCurrnetTimeStamp()+""));
		System.out.println(result);
	}

	//删除设备
	@Test
	public void DeleteDevice() throws Exception {
		AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		DeleteDeviceRequest request = new DeleteDeviceRequest();
		// set your request params here
		request.setParamMasterKey(MasterKey);	// single value
		request.setParamProductId(productId);	// single value
		request.setParamDeviceIds("9a9a61dbf505423eb65d541792cf2ca4");	// single value
		BaseApiResponse response = client.DeleteDevice(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的Message数据为："+response);
		client.shutdown();
	}
	@Test
	public void DeleteDeviceTest() throws Exception {
		String s = NBIoTUtils.DeleteIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,"36f0df3ba727427e9e993fa5b5fcab63");
		System.out.println("状态信息"+s);
	}

	//修改设备
	@Test
	public void UpdateDevice() throws Exception {
		AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		UpdateDeviceRequest request = new UpdateDeviceRequest();
		request.setParamMasterKey(MasterKey);	// single value
		request.setParamDeviceId("364fc7bf23d94f1aa3a7c88bfe2e9abf");	// single value
		//创建json对象
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceName","测试设备改");
		params.put("operator","username");
		params.put("productId",productId);
		Map<String,Object> other = new HashMap<String,Object>();
		other.put("autoObserver",0);
		other.put("imsi","");
		params.put("other",other);

		System.out.println("请求参数列表"+params);

		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		BaseApiResponse response = client.UpdateDevice(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的Message数据为："+response);
		client.shutdown();
	}

	@Test
	public void UpdateDeviceTest() throws Exception {
		String s = NBIoTUtils.UpdateIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,"364fc7bf23d94f1aa3a7c88bfe2e9abf","测试设备2改",0,"");
		System.out.println("状态信息"+s);
	}


	//查询产品信息 产品为设备直连+非NB网关协议
	@Test
	public void QueryProduct() throws Exception {
		AepProductManagementClient client = AepProductManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		QueryProductRequest request = new QueryProductRequest();
		// set your request params here
		request.setParamProductId(productId);	// single value
		BaseApiResponse response = client.QueryProduct(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", QueryProductResult.class);
		QueryProductResponse queryProductResponse =(QueryProductResponse)JSONObject.toBean(jsonObject, QueryProductResponse.class,map);
		System.out.println("用户信息为："+ queryProductResponse);
		QueryProductResult queryProductResult = queryProductResponse.getResult();
		System.out.println("获得的数据信息为："+queryProductResult);
		client.shutdown();
	}
	//批量查询产品信息
	@Test
	public void QueryProductList() throws Exception {
		AepProductManagementClient client = AepProductManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		QueryProductListRequest request = new QueryProductListRequest();
		request.setParamSearchValue("");	// single value
		request.setParamPageNow(1);	// single value
		request.setParamPageSize(10);	// single value
		//获取返回结果信息
		BaseApiResponse response = client.QueryProductList(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", QueryProductListResult.class);
		map.put("list",QueryProductResult.class);
		QueryProductListResponse queryProductListResponse =(QueryProductListResponse)JSONObject.toBean(jsonObject, QueryProductListResponse.class,map);
		QueryProductListResult queryProductListResult = queryProductListResponse.getResult();
		List<QueryProductResult> listresult = queryProductListResult.getList();
		for (QueryProductResult list:listresult
		) {
			System.out.println("获取的命令为："+list);
		}
		client.shutdown();
	}

	//删除产品
	@Test
	public void DeleteProduct() throws Exception {
		AepProductManagementClient client = AepProductManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		String productId222 = "10069430";
		BaseAdminProduct product = baseAdminProductMapper.getProductByProductId(productId222);
		DeleteProductRequest request = new DeleteProductRequest();
		request.setParamMasterKey(product.getApiKey());	// single value
 		request.setParamProductId(productId222);	// single value
		System.out.println(client.DeleteProduct(request));
		client.shutdown();
	}

	//获取产品信息
	@Test
	public void QueryProductTest() throws Exception {
		QueryProductResult result = NBIoTUtils.QueryIotProduct(APP_KEY,APP_SECRET,"10050633");
		System.out.println("查询的结果信息为："+result);
		System.out.println("查询的结果信息为："+result.getProductName());
	}

	//批量获取产品信息
	@Test
	public void QueryProductListTest() throws Exception {
		List<QueryProductResult> result = NBIoTUtils.QueryIotProductList(APP_KEY,APP_SECRET,"");
		for (QueryProductResult list:result
		) {
			System.out.println("获取的命令为："+list);
		}
	}

	//创建产品
	@Test
	public void CreateProduct() throws Exception {
		AepProductManagementClient client = AepProductManagementClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		CreateProductRequest request = new CreateProductRequest();
		//创建json对象
		Map<String,Object> params = new HashMap<String,Object>();
		//接入类型（必填）: 1.设备直连，2.网关接入，3.南向云接入（只支持1）
		params.put("accessType","1");
		//认证方式 1:特征串认证,2:SM9认证,3:证书认证,4:IMEI认证，5:SIMID认证，6:SM2认证，
		params.put("authType","4");
		//数据加密方式 1:sm1,2:sm2,3:sm4,4:dtls,5:明文（只支持MQTT/LWM2M）
		params.put("dataEncryption","4");
		//deviceModel：JT/T808协议 设备型号
		params.put("deviceModel","a1df5as3f51");
		//安全类型(只支持MQTT,TCP协议) 0:一机一密，1:一型一密
		params.put("encryptionType","1");
		//Endpoint格式（LWM2M协议必填）:1.IMEI2.URN:IMEI:###############3.URN:IMEI-IMSI: ###############-############### 4.URN:IMEI+SM9
		//注：认证方式为SM9认证时，Endpoint格式只能为4
		//认证方式为IMEI认证，SIMID认证，SM2认证时，Endpoint格式为1/2/3
		//认证方式为IPV6标识认证时，Endpoint格式为3
		params.put("endpointFormat","1");
		//eDRX模式时间窗(LWM2M协议,当省电模式为3时,必填):20 ～ 10485.76 间的值
		params.put("lwm2mEdrxTime","55");
		//JT/T808协议 制造商ID
		params.put("manufacturerId","1234565");
		//网络类型（必填）:1.WIFI,2.移动蜂窝数据3.NB-IoT,4.以太网,5.蓝牙,6.ZigBee
		params.put("networkType","3");
		//节点类型（必填）：1.设备 ，2.网关（只支持1）
		params.put("nodeType","1");
		//消息格式 1:json，2:紧凑二进制(选2)
		params.put("payloadFormat","2");
		//省电模式（LWM2M协议必填）：1.PSM 2.DRX 3.eDRX
		params.put("powerModel","1");
		//产品描述（选填）：产品描述最多100个字符
		params.put("productDesc","123");
		//产品名称（必填）
		params.put("productName","java的云端NB设备");
		//产品协议（必填）：只支持 1.T-LINK协议 2.MQTT协议 3.LWM2M协议 5.HTTP协议 6.JT/T808 7.TCP协议
		params.put("productProtocol","3");
		//产品分类（必填）
		params.put("productType","智能生活");
		//二级分类（必填）
		params.put("secondaryType","家居安防");
		//三级分类（必填）
		params.put("thirdType","温感报警器");
		//是否透传：0.透传，1不透传
		params.put("tupIsThrough","1");
		//设备型号，选填，设备直连并且非JT/T808协议产品必填
		params.put("tupDeviceModel","153153");
		System.out.println("请求参数列表"+params);
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		BaseApiResponse response = client.CreateProduct(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);

		//解析嵌套的json数据
		Map<String, Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", CreateProductResult.class);
		CreateProductResponse productResponse = (CreateProductResponse) JSONObject.toBean(jsonObject, CreateProductResponse.class, map);
		System.out.println("用户信息为：" + productResponse);
		CreateProductResult createresult = productResponse.getResult();
		System.out.println("获得的数据信息为：" + createresult);

		client.shutdown();
	}

	//查询指令信息
	@Test
	public void QueryCommand() throws Exception {
		AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		String commandId = "1";
		QueryCommandRequest request = new QueryCommandRequest();
		request.setParamMasterKey(MasterKey);	// single value
		request.setParamCommandId(commandId);	// single value
		request.setParamProductId(productId);	// single value
		request.setParamDeviceId(deviceId);	// single value
		System.out.println();
		BaseApiResponse response = client.QueryCommand(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		client.shutdown();
	}

	//查询指令列表信息
	@Test
	public void QueryCommandList() throws Exception {
		AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		QueryCommandListRequest request = new QueryCommandListRequest();
		request.setParamMasterKey(MasterKey);	// single value
		request.setParamProductId(productId);	// single value
		request.setParamSearchValue("");	// single value
		request.setParamDeviceId(deviceId);	// single value
		request.setParamStatus("");	// single value
		request.setParamStartTime("");	// single value
		request.setParamEndTime("");	// single value
		//当前页数
		request.setParamPageNow(1);	// single value
		//每页记录数
		request.setParamPageSize(5);	// single value
		//群组任务ID
		request.setParamGroupCommandId("");	// single value
		BaseApiResponse response = client.QueryCommandList(request);
		//获取状态信息
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		//获取请求信息
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);

		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", QueryOrderListResult.class);
		map.put("list",QueryOrderListList.class);
		QueryOrderListResponse queryProductResponse =(QueryOrderListResponse)JSONObject.toBean(jsonObject, QueryOrderListResponse.class,map);
		QueryOrderListResult queryOrderListResult = queryProductResponse.getResult();
		List<QueryOrderListList> queryOrderListList = queryOrderListResult.getList();
		for (QueryOrderListList list:queryOrderListList
			 ) {
			System.out.println("获取的命令为："+list);
		}
		client.shutdown();
	}

	//创建指令
	@Test
	public void CreateCommand() throws Exception {
		AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		CreateCommandRequest request = new CreateCommandRequest();
 		request.setParamMasterKey(MasterKey);	// single value
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> content = new HashMap<String,Object>();
		Map<String,Object> innerContent = new HashMap<String,Object>();
		//innerContent.put("status","1");
		innerContent.put("temperature","26");
		//params：指令参数
		content.put("params",innerContent);
		//serviceIdentifier：服务定义时的服务标识
		content.put("serviceIdentifier","order");
		//content: 指令内容，必填，格式为Json。*
		params.put("content",content);
		//操作员必填
		params.put("operator","username");
		//deviceId: 设备ID，（当指令级别为设备级时必填，为设备组级时则不填）
		params.put("deviceId",deviceId);
		//产品ID
		params.put("productId",productId);
		//生存时间
		params.put("ttl","");
		//deviceGroupId：设备组ID，选填，当指令级别为设备级，
		//deviceId不为空，deviceGroupId为空；
		params.put("deviceGroupId","");
		//指令级别，1或2为设备级别,3为设备组级别，选填。不填默认设备级。
		params.put("level","1");
		System.out.println("请求参数列表"+params);
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		System.out.println();
		BaseApiResponse response = client.CreateCommand(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		client.shutdown();
	}
	//取消指令
	@Test
	public void CancelCommand() throws Exception {
		AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();
		CancelCommandRequest request = new CancelCommandRequest();
		request.setParamMasterKey(MasterKey);	// single value
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("commandId","2");
		//deviceId: 设备ID，（当指令级别为设备级时必填，为设备组级时则不填）
		params.put("deviceId",deviceId);
		//产品ID
		params.put("productId",productId);
		System.out.println("请求参数列表"+params);
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		System.out.println();
		BaseApiResponse response = client.CancelCommand(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		client.shutdown();

	}
	@Test
	public void QueryOrderListtest() throws Exception {
		NBIoTUtils.QueryOrderList(APP_KEY,APP_SECRET,deviceId,MasterKey,productId);
	}
	@Test
	public void cancelOrderTest() throws Exception {
		String s = NBIoTUtils.CancelCommand(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,"3");
		System.out.println(s);
	}
	@Test
	public void createOrderTest() throws Exception {
		String s = NBIoTUtils.CreateCommand(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,"hum","11");
		System.out.println(s);
	}
	//创建订阅
	@Test
	public void CreateSubscription() throws Exception {
		AepSubscribeNorthClient client = AepSubscribeNorthClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		CreateSubscriptionRequest request = new CreateSubscriptionRequest();
 		request.setParamMasterKey(MasterKey);	// single value
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceId",deviceId);
		//deviceId: 设备ID，（当指令级别为设备级时必填，为设备组级时则不填）
		params.put("operator","username");
		//产品ID
		params.put("productId",productId);
		//subLevel:订阅级别,必填(1:产品级 2：设备级)
		params.put("subLevel","2");
		//subTypes:消息类型(必填),可填写1个或多个(1表示设备数据变化通知、2表示设备响应命令通知、3表示设备事件上报、4表示设备上下线通知、9表示TUP合并数据上报）
		int a[] = {1};
		params.put("subTypes",a);
		//消息接收路径，接收消息的url(必填),
		params.put("subUrl","www.test2.com");
		System.out.println("请求参数列表"+params);
		//java对象变成json对象
		String body = JsonUtils.objectToJson(params);
		System.out.println("请求对象"+body);
		//json对象转换成json字符串
		request.setBody(body.getBytes());
		BaseApiResponse response = client.CreateSubscription(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		client.shutdown();
	}
	//删除订阅
	@Test
	public void DeleteSubscription() throws Exception {
		AepSubscribeNorthClient client = AepSubscribeNorthClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		DeleteSubscriptionRequest request = new DeleteSubscriptionRequest();
		//订阅的ID
		request.setParamSubId("88729");	// single value
		request.setParamDeviceId(deviceId);	// single value
		request.setParamProductId(productId);	// single value
		//订阅级别,必填(1:产品级 2：设备级)
		request.setParamSubLevel("2");	// single value
		request.setParamMasterKey(MasterKey);	// single value
		BaseApiResponse response = client.DeleteSubscription(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		client.shutdown();
	}
	//查询订阅
	@Test
	public void GetSubscriptionsList() throws Exception {
		AepSubscribeNorthClient client = AepSubscribeNorthClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		GetSubscriptionsListRequest request = new GetSubscriptionsListRequest();
 		request.setParamProductId(productId);	// single value
		request.setParamPageNow("1");	// single value
 		request.setParamPageSize("10");	// single value
 		request.setParamMasterKey(MasterKey);	// single value
 		request.setParamSubType("");	// single value
		request.setParamSearchValue("");	// single value
		BaseApiResponse response = client.GetSubscriptionsList(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);
		//解析嵌套的json数据
		Map<String,Class> map = new HashMap<>();
		//获取json对象
		String json = new String(response.getBody());
		System.out.println("获取的body对象为"+json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		//指定json数据中deviceStatusList的数据类型
		map.put("result", QuerySubscriptionResult.class);
		map.put("list", QuerySubscriptionList.class);
		QuerySubscriptionResponse querySubscriptionResponse =(QuerySubscriptionResponse)JSONObject.toBean(jsonObject, QuerySubscriptionResponse.class,map);
		QuerySubscriptionResult querySubscriptionResult = querySubscriptionResponse.getResult();
		System.out.println(querySubscriptionResponse);
		System.out.println(querySubscriptionResult);
		List<QuerySubscriptionList> list = querySubscriptionResult.getList();
		for (QuerySubscriptionList result:list
			 ) {
			System.out.println("获取的result为："+result);
		}
		client.shutdown();
	}
	//根据ID查询订阅GetSubscription
	@Test
	public void GetSubscriptions() throws Exception {
		AepSubscribeNorthClient client = AepSubscribeNorthClient.newClient()
				.appKey(APP_KEY).appSecret(APP_SECRET)
				.build();

		GetSubscriptionRequest request = new GetSubscriptionRequest();
		request.setParamSubId("88708");
		request.setParamProductId(productId);	// single value
		request.setParamMasterKey(MasterKey);	// single value
		BaseApiResponse response = client.GetSubscription(request);
		System.out.println("获取到的状态信息为："+response.getStatusCode());
		System.out.println("获取到的Message数据为："+response.getMessage());
		System.out.println("获取到的body数据为："+response);

		client.shutdown();
	}
	@Test
	public void createSubscriptionTest() throws Exception {
		int a[] = {1};
		String s = NBIoTUtils.CreateSubscription(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,"2","www.aaa.com",a);
		System.out.println("创建结果"+s);
	}
	@Test
	public void deleteSubscriptionTest() throws Exception {
		String s = NBIoTUtils.DeleteSubscription(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,"2","88925");
		System.out.println("创建结果"+s);
	}

	@Test
	public void getSubscriptionListTest() throws Exception {
		List<QuerySubscriptionList> list = NBIoTUtils.GetSubscriptionsList(APP_KEY,APP_SECRET,MasterKey,productId);
		for (QuerySubscriptionList result:list
			 ) {
			System.out.println(result);
		}
	}

	@Test
	public void getDatatest() throws Exception {
		BaseAdminChart chart = NBIoTUtils.getIotData(APP_KEY,APP_SECRET,productId,deviceId,"battery");
		System.out.println(chart);
	}

	@Test
	public void createProductTest() throws Exception {
		BaseAdminProduct product = NBIoTUtils.CreateProduct(APP_KEY,APP_SECRET,"java的云端设备","智能生活","家居安防","温感报警器");
		System.out.println(product);
	}
}
