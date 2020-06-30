/**
 * 用户管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;

        tableIns=table.render({
            elem: '#subscriptionList',
            url:'/subscription/getSubscriptionList',
            method: 'post', //默认：get请 求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type:'numbers'}
                ,{field:'subId', title:'订阅ID',align:'center'}
                ,{field:'subLevel', title:'订阅级别',align:'center'}
                ,{field:'subType', title:'订阅类型',align:'center'}
                ,{field:'deviceId', title:'设备ID',align:'center'}
                ,{field:'subUrl', title: '订阅方地址',align:'center'}
                ,{field:'regTime', title: '注册时间',align:'center'}
                ,{title:'操作',align:'center', toolbar:'#optBar'}
            ]],
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='deviceStatus']").children().each(function(){
                    if($(this).text()=='1'){
                        $(this).text("有效")
                    }else if($(this).text()=='0'){
                        $(this).text("失效")
                    }
                });
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(subscriptionTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                delSubscription(data,data.id,data.sysUserName);
            } else if(obj.event === 'edit'){
                //编辑
                openDevice(data,"编辑");
            }else if(obj.event === 'recover'){
                //恢复
                recoverDevice(data,data.id);
            }else if(obj.event === 'destroy'){
                //销毁
                destroyDevice(data,data.id);
            }
        });
        /*给要创建的订阅赋予初始producId和deviceId*/
        $("#productId").val("10045641");
        $("#deviceId").val("506de691ecbc45259914515bc39df452");
        //监听提交
        form.on('submit(subscriptionSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });
    });

    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
});

//提交表单
function formSubmit(obj){
    var data = $("#subscriptionForm").serialize();
    console.log(data)
    $.ajax({
        type: "POST",
        data: $("#subscriptionForm").serialize(),
        url: "/subscription/createSubscription",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);

                });
            } else {
                layer.alert(data.msg);
            }
            /!*刷新页面*!/
            location.reload();
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}
function updateSubscription(){
    alert("成功");
    $.ajax({
        type: "POST",
        data: "",
        url: "/subscription/getSubscription",
        success: function (data) {
            if (data=='success') {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.msg);
            }
            /*刷新页面*/
            location.reload();
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}
//开通用户
function addSubscription(){
    openDevice(null,"创建订阅");
}
function openDevice(data,title){
    var imei = null;
    if(data==null || data==""){
        $("#id").val("");
    }else{
        $("#id").val(data.id);
        $("#username").val(data.sysUserName);
        $("#device").val(data.device);
        imei = data.imei;
    }
    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setSubscription'),
        end:function(){
            cleanDevice();
        }
    });
}

//销毁
function delSubscription(obj,id,name) {
    console.log("获取到的id"+id);
    var currentUser=$("#currentUser").html();
    if(null!=id){
        layer.confirm('您确定要彻底删除此订阅吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/subscription/deleteSubscription",{"id":id},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}
//恢复
function recoverDevice(obj,id) {
    if(null!=id){
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/device/updateDeviceStatus",{"id":id,"status":1},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}

function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function cleanDevice(){
    $("#username").val("");
    $("#deviceName").val("");
    $("#deviceId").val("");
    /*$('#roleId').html("");*/
}
