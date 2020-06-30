package com.haiyu.manager.common.utils;


import com.ctg.ag.sdk.biz.*;
import com.ctg.ag.sdk.biz.aep_device_command.CancelCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_command.CreateCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_command.QueryCommandListRequest;
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
import com.ctg.ag.sdk.biz.aep_subscribe_north.GetSubscriptionsListRequest;
import com.ctg.ag.sdk.core.model.BaseApiResponse;
import com.haiyu.manager.iot.JsonUtils;
import com.haiyu.manager.nbpojo.*;
import com.haiyu.manager.pojo.*;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NB-IoT云端操作类
 */
public class NBIoTUtils {

    //查询云端数据(温度+湿度)(参数应用账号和密码，产品ID和设备ID)
    public static BaseAdminChart getIotDataList(String APP_KEY, String APP_SECRET, String productId
    , String deviceId) throws Exception{
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
        //获取温湿度数据
        BaseAdminChart chartData = new BaseAdminChart();
        IotData iotData = new IotData();
        for (int i=0;i<dataList.size();i++) {
            iotData = dataList.get(i);
            if (iotData.getDatasetId().equals("temperature")) {
                chartData.setTemperature(iotData.getValue());
                String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
                chartData.setRegTime(regTime);
            }else if (iotData.getDatasetId().equals("humilevel")) {
                chartData.setHumilevel(iotData.getValue());
                String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
                chartData.setRegTime(regTime);
            }else if (iotData.getDatasetId().equals("message")) {
                chartData.setMessage(iotData.getValue());
                String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
                chartData.setRegTime(regTime);
            }else if (iotData.getDatasetId().equals("battery")) {
                chartData.setBattery(iotData.getValue());
                String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
                chartData.setRegTime(regTime);
            }else if (iotData.getDatasetId().equals("signalStrength")) {
                chartData.setSignalStrength(iotData.getValue());
                String regTime = TimeStamp.stampToDate(iotData.getTimestamp().toString());
                chartData.setRegTime(regTime);
            }
        }
        client.shutdown();
        return  chartData;
    }


    //查询云端数据(温度或湿度)(参数应用账号和密码，产品ID和设备ID)
    public static BaseAdminChart getIotData(String APP_KEY, String APP_SECRET, String productId
            , String deviceId, String searchData) throws Exception{
        AepDeviceStatusClient client = AepDeviceStatusClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();

        QueryDeviceStatusRequest request = new QueryDeviceStatusRequest();
        // set your request params here
        Map<String,String> params=new HashMap<>();
        params.put("deviceId",deviceId);
        params.put("productId",productId);
        //查询温度信息
        params.put("datasetId",searchData);
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
        BaseAdminChart chartData = new BaseAdminChart();
        if (searchData.equals("temperature")) {
            chartData.setTemperature(dataList.getValue());
            chartData.setRegTime(TimeStamp.stampToDate(dataList.getTimestamp().toString()));
        }else if (searchData.equals("humilevel")) {
            chartData.setHumilevel(dataList.getValue());
            chartData.setRegTime(TimeStamp.stampToDate(dataList.getTimestamp().toString()));
        }else if (searchData.equals("message")) {
            chartData.setMessage(dataList.getValue());
            chartData.setRegTime(TimeStamp.stampToDate(dataList.getTimestamp().toString()));
        }else if (searchData.equals("battery")) {
            chartData.setBattery(dataList.getValue());
            chartData.setRegTime(TimeStamp.stampToDate(dataList.getTimestamp().toString()));
        }else if (searchData.equals("signalStrength")) {
            chartData.setSignalStrength(dataList.getValue());
            chartData.setRegTime(TimeStamp.stampToDate(dataList.getTimestamp().toString()));
        }
        // more requests
        client.shutdown();
        return chartData;
    }
    //查询云端设备信息
    public static DeviceResult getIotDevice(String APP_KEY, String APP_SECRET, String productId
            , String MasterKey, String deviceId) throws Exception{
        AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();

        QueryDeviceRequest request = new QueryDeviceRequest();
        // set your request params here
        request.setParamMasterKey(MasterKey);	// single value
        request.setParamDeviceId(deviceId);	// single value
        request.setParamProductId(productId);	// single value
        BaseApiResponse response = client.QueryDevice(request);
        //获取状态信息
        System.out.println("获取到的状态信息为："+response.getStatusCode());
        //获取请求信息
        System.out.println("获取到的Message数据为："+response.getMessage());
        //解析嵌套的json数据
        Map<String,Class> map = new HashMap<>();
        //获取json对象
        String json = new String(response.getBody());
        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        //指定json数据中deviceStatusList的数据类型
        map.put("result", DeviceResult.class);
        DeviceResponse deviceResult =(DeviceResponse)JSONObject.toBean(jsonObject,DeviceResponse.class,map);
        //System.out.println("用户信息为："+ deviceResult);
        DeviceResult result = deviceResult.getResult();
        //System.out.println("获得的数据信息为："+result);
        client.shutdown();
        return result;
    }

    //创建云端设备 返回设备名、设备ID、产品ID、IMEI、自动订阅关闭autoObserver=0
    public static CreateDeviceResult CreateIotDevice(String APP_KEY, String APP_SECRET, String productId
            , String MasterKey,String deviceName,String imei) throws Exception {
        AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        CreateDeviceRequest request = new CreateDeviceRequest();
        request.setParamMasterKey(MasterKey);    // single value
        //创建json对象
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceName", deviceName);
        //设备编号南向云必填
        params.put("deviceSn", "");
        //imei号，LWM2M,NB网关协议必填 为15位
        params.put("imei", imei);
        //操作者，必填
        params.put("operator", "username");
        params.put("productId", productId);
        Map<String, Object> other = new HashMap<String, Object>();
        other.put("autoObserver", 0);
        other.put("imsi", "");
        other.put("pskValue", "");
        params.put("other", other);
        System.out.println("请求参数列表" + params);
        //java对象变成json对象
        String body = JsonUtils.objectToJson(params);
        System.out.println("请求对象" + body);
        //json对象转换成json字符串
        request.setBody(body.getBytes());
        BaseApiResponse response = client.CreateDevice(request);
        //获取状态信息
        System.out.println("获取到的状态信息为：" + response.getStatusCode());
        //获取请求信息
        System.out.println("获取到的Message数据为：" + response.getMessage());
        System.out.println("获取到的Message数据为：" + response);
        //解析嵌套的json数据
        Map<String, Class> map = new HashMap<>();
        //获取json对象
        String json = new String(response.getBody());
        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        //指定json数据中deviceStatusList的数据类型
        map.put("result", CreateDeviceResult.class);
        CreateDeviceResponse deviceResponse = (CreateDeviceResponse) JSONObject.toBean(jsonObject, CreateDeviceResponse.class, map);
        System.out.println("用户信息为：" + deviceResponse);
        CreateDeviceResult createresult = deviceResponse.getResult();
        System.out.println("获得的数据信息为：" + createresult);
        client.shutdown();
        return createresult;
    }

    //删除云端设备  返回值为success表示删除成功
    public static String DeleteIotDevice(String APP_KEY, String APP_SECRET, String productId
            , String MasterKey,String deviceId) throws Exception {
        AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();

        DeleteDeviceRequest request = new DeleteDeviceRequest();
        // set your request params here
        request.setParamMasterKey(MasterKey);	// single value
        request.setParamProductId(productId);	// single value
        request.setParamDeviceIds(deviceId);	// single value
        BaseApiResponse response = client.DeleteDevice(request);
        //获取状态信息
        System.out.println("获取到的状态信息为："+response.getStatusCode());
        //获取请求信息
        System.out.println("获取到的Message数据为："+response.getMessage());
        System.out.println("获取到的Message数据为："+response);
        if (response.getMessage().equals("OK") && response.getStatusCode() == 200) {
            return "success";
        }
        client.shutdown();
        return "false";
    }

    //修改云端设备  返回值为success表示修改成功
    public static String UpdateIotDevice(String APP_KEY, String APP_SECRET, String productId
            , String MasterKey,String deviceId,String deviceName,Integer autoObserver,String imsi) throws Exception {
        AepDeviceManagementClient client = AepDeviceManagementClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();

        UpdateDeviceRequest request = new UpdateDeviceRequest();
        request.setParamMasterKey(MasterKey);	// single value
        request.setParamDeviceId(deviceId);	// single value
        //创建json对象
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deviceName",deviceName);
        params.put("operator","username");
        params.put("productId",productId);
        Map<String,Object> other = new HashMap<String,Object>();
        other.put("autoObserver",autoObserver);
        other.put("imsi",imsi);
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
        if (response.getMessage().equals("OK") && response.getStatusCode() == 200) {
            return "success";
        }
        client.shutdown();
        return "false";
    }

    //查询产品信息
    public static QueryProductResult QueryIotProduct(String APP_KEY, String APP_SECRET, String productId
            ) throws Exception {
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
        return queryProductResult;
    }

    //批量查询产品信息
    public static List<QueryProductResult> QueryIotProductList(String APP_KEY, String APP_SECRET, String productId
    ) throws Exception {
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
        List<QueryProductResult> listResult = queryProductListResult.getList();
        client.shutdown();
        return listResult;
    }

    //删除产品
    public static void DeleteProduct(String APP_KEY, String APP_SECRET, String productId ,String MasterKey
    ) throws Exception {
        AepProductManagementClient client = AepProductManagementClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        DeleteProductRequest request = new DeleteProductRequest();
        request.setParamMasterKey(MasterKey);	// single value
        request.setParamProductId(productId);	// single value
        System.out.println(client.DeleteProduct(request));
        client.shutdown();
    }

    //创建产品
    public static BaseAdminProduct CreateProduct(String APP_KEY, String APP_SECRET ,String productName,String productType,String secondaryType,String thirdType
    ) throws Exception {
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
        params.put("productName",productName);
        //产品协议（必填）：只支持 1.T-LINK协议 2.MQTT协议 3.LWM2M协议 5.HTTP协议 6.JT/T808 7.TCP协议
        params.put("productProtocol","3");
        //产品分类（必填）
        params.put("productType",productType);
        //二级分类（必填）
        params.put("secondaryType",secondaryType);
        //三级分类（必填）
        params.put("thirdType",thirdType);
/*        params.put("productType","智能生活");
        //二级分类（必填）
        params.put("secondaryType","家居安防");
        //三级分类（必填）
        params.put("thirdType","温感报警器");*/
        //是否透传：0.透传，1不透传
        params.put("tupIsThrough","1");
        //设备型号，选填，设备直连并且非JT/T808协议产品必填
        params.put("tupDeviceModel","153153");
        //System.out.println("请求参数列表"+params);
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
        BaseAdminProduct product  = new BaseAdminProduct();
        product.setProductName(createresult.getProductName());
        product.setProductId(createresult.getProductId());
        product.setProductType(createresult.getProductTypeValue()+"/"+createresult.getSecondaryTypeValue()+"/"+createresult.getThirdTypeValue());
        product.setApiKey(createresult.getApiKey());
        product.setProductStatus(1);
        product.setRegTime(TimeStamp.stampToDate(createresult.getCreateTime()));
        client.shutdown();
        return product;
    }

    //查询命令下发日志
    public static List<QueryOrderListList> QueryOrderList(String APP_KEY, String APP_SECRET, String deviceId, String MasterKey,String productId
    ) throws Exception {
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
        //每页记录数,最大40
        request.setParamPageSize(40);	// single value
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
/*        for (QueryOrderListList list:queryOrderListList
        ) {
            System.out.println("获取的命令为："+list);
        }*/
        client.shutdown();
        return queryOrderListList;
    }
    //取消命令
    public static String CancelCommand(String APP_KEY, String APP_SECRET, String deviceId, String MasterKey,String productId,String commandId
    ) throws Exception {
        AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        CancelCommandRequest request = new CancelCommandRequest();
        request.setParamMasterKey(MasterKey);	// single value
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("commandId",commandId);
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
        if (response.getStatusCode() == 200) {
            return "success";
        }
        return "false";
    }
    //创建命令
    public static String CreateCommand(String APP_KEY, String APP_SECRET, String deviceId, String MasterKey,String productId,
                                       String orderInfo,String orderValue
    ) throws Exception {
        AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        CreateCommandRequest request = new CreateCommandRequest();
        request.setParamMasterKey(MasterKey);	// single value
        Map<String,Object> params = new HashMap<String,Object>();
        Map<String,Object> content = new HashMap<String,Object>();
        Map<String,Object> innerContent = new HashMap<String,Object>();
        //innerContent.put("status","1");
        innerContent.put("orderInfo",orderInfo);
        innerContent.put("orderValue",orderValue);
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
        if (response.getStatusCode() == 200) {
            return "success";
        }
        return "false";
    }
    //创建订阅
    public static String CreateSubscription(String APP_KEY, String APP_SECRET, String deviceId, String MasterKey,String productId,String subLevel,String subUrl,int subTypes[]
    ) throws Exception {
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
        params.put("subLevel",subLevel);
        //subTypes:消息类型(必填),可填写1个或多个(1表示设备数据变化通知、2表示设备响应命令通知、3表示设备事件上报、4表示设备上下线通知、9表示TUP合并数据上报）
        params.put("subTypes",subTypes);
        //消息接收路径，接收消息的url(必填),
        params.put("subUrl",subUrl);
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
        if (response.getStatusCode() == 200) {
            return "success";
        }
        return "false";
    }
    //查询所有订阅
    public static List<QuerySubscriptionList> GetSubscriptionsList(String APP_KEY, String APP_SECRET,String MasterKey,String productId
    ) throws Exception {
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
        JSONObject jsonObject = JSONObject.fromObject(json);
        //指定json数据中deviceStatusList的数据类型
        map.put("result", QuerySubscriptionResult.class);
        map.put("list", QuerySubscriptionList.class);
        QuerySubscriptionResponse querySubscriptionResponse =(QuerySubscriptionResponse)JSONObject.toBean(jsonObject, QuerySubscriptionResponse.class,map);
        QuerySubscriptionResult querySubscriptionResult = querySubscriptionResponse.getResult();
        System.out.println(querySubscriptionResponse);
        System.out.println(querySubscriptionResult);
        List<QuerySubscriptionList> list = querySubscriptionResult.getList();
        client.shutdown();
        return list;
    }
    //根据订阅ID查询订阅

    //删除订阅
    public static String DeleteSubscription(String APP_KEY, String APP_SECRET, String deviceId, String MasterKey,String productId,String subLevel,String subId
    ) throws Exception {
        AepSubscribeNorthClient client = AepSubscribeNorthClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        DeleteSubscriptionRequest request = new DeleteSubscriptionRequest();
        //订阅的ID
        request.setParamSubId(subId);	// single value
        request.setParamDeviceId(deviceId);	// single value
        request.setParamProductId(productId);	// single value
        //订阅级别,必填(1:产品级 2：设备级)
        request.setParamSubLevel(subLevel);	// single value
        request.setParamMasterKey(MasterKey);	// single value
        BaseApiResponse response = client.DeleteSubscription(request);
        System.out.println("获取到的状态信息为："+response.getStatusCode());
        System.out.println("获取到的Message数据为："+response.getMessage());
        System.out.println("获取到的body数据为："+response);
        client.shutdown();
        if (response.getStatusCode() == 200) {
            return "success";
        }
        return "false";
    }
}
