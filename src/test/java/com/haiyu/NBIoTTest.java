package com.haiyu;

import com.ctg.ag.sdk.biz.AepDeviceCommandClient;
import com.ctg.ag.sdk.biz.AepDeviceStatusClient;
import com.ctg.ag.sdk.biz.aep_device_command.QueryCommandRequest;
import com.ctg.ag.sdk.biz.aep_device_status.QueryDeviceStatusListRequest;
import com.ctg.ag.sdk.biz.aep_device_status.QueryDeviceStatusRequest;
import com.ctg.ag.sdk.core.model.BaseApiResponse;
import com.haiyu.manager.common.utils.TimeStamp;
import com.haiyu.manager.iot.JsonUtils;
import com.haiyu.manager.nbpojo.IotData;
import com.haiyu.manager.nbpojo.JsonResponse;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-iot.properties")
public class NBIoTTest {

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
    public void gotest() throws Exception {
        System.out.println(APP_KEY);
        //System.out.println("asfd"+Global.getAppKey());
    }

    //批量获取设备状态
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
        for (IotData data:dataList
        ) {
            System.out.println("获得的数据信息为："+data);
        }
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
        params.put("productId","productId");
        //查询温度信息
        params.put("datasetId","temperature");
        //params.put("end_timestamp","10045641");
        //java对象变成json对象
        String body = JsonUtils.objectToJson(params);
        //json对象转换成json字符串
        request.setBody(body.getBytes());
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
        JsonResponse jsonResponse =(JsonResponse)JSONObject.toBean(jsonObject, JsonResponse.class,map);
        System.out.println("用户信息为："+ jsonResponse);
        List<IotData> dataList = jsonResponse.getDeviceStatusList();
        for (IotData data:dataList
        ) {
            System.out.println("获得的数据信息为："+data);
        }
        // more requests
        client.shutdown();
    }
    //查询指令信息
    @Test
    public void QueryCommand() throws Exception {
        AepDeviceCommandClient client = AepDeviceCommandClient.newClient()
                .appKey(APP_KEY).appSecret(APP_SECRET)
                .build();
        String commandId = "8010";
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
        client.shutdown();
    }

    //java时间字符串转换为时间戳
    @Test
    public void timeTest() throws ParseException {
        //时间戳转换
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time="2018-09-29 16:39:00";
        Date date = format.parse(time);
        //日期转时间戳（毫秒）
        long timeResult=date.getTime();
        System.out.print("Format To times:"+timeResult);
    }

    //时间戳转换为java时间
    @Test
    public void timeTest2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(); //日期格式化
        String res = simpleDateFormat.format(date);
        System.out.println("Format To times:"+res );
        //获取当前系统时间戳
        System.out.println("对应时间的时间戳："+System.currentTimeMillis());

    }

    @Test
    public void timeTest3() throws Exception {
        System.out.println("日期转时间戳"+ TimeStamp.dateToStamp("2020-04-15 10:43:11"));
        System.out.println("时间戳转日期"+TimeStamp.stampToDate("1586918591564"));
        System.out.println("当前时间戳"+TimeStamp.getCurrnetTimeStamp());
    }
}
