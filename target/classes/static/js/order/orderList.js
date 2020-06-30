/**
 * 用户管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;
        //监听工具条
        table.on('tool(orderTable)', function(obj){
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
        form.on('submit(orderSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });
        /*监听查询命令工具条*/
        form.on('submit(querySubmit)', function(data){
            // TODO 校验
            queryOrder2(data);
            return false;
        });
        form.on('submit(cancelSubmit)', function(data){
            // TODO 校验
            cancelOrder2(data);
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

//查询命令
function queryOrder(){

    $("#productId2").val('10045641');
    $("#deviceId2").val('506de691ecbc45259914515bc39df452');
    layer.open({
        type:1,
        title: "查询命令",
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#queryOrderList'),
        end:function(){
            cleanDevice();
        }
    });

}

//提交表单
function queryOrder2(obj){
    var data = $("#orderForm2").serialize();
    console.log("传递的数据为："+data);
    $.ajax({
        type: "POST",
        data: $("#orderForm2").serialize(),
        url: "/order/getOrderList2",
        success: function (data) {
            if (data == 'success') {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    /*显示表格*/
                    showTable();
                });
            } else {
                layer.alert(data.msg);
            }
            /*刷新页面*/
            /*location.reload();*/
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
function showTable(){
    var table = layui.table;
    form = layui.form;
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;
        tableIns=table.render({
            elem: '#orderList',
            url:'/order/getOrderList',
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
                ,{field:'commandId', title:'命令ID',align:'center'}
                ,{field:'productId', title:'产品ID',align:'center'}
                ,{field:'deviceId', title:'设备ID',align:'center'}
                ,{field:'commandStatus', title:'命令状态',align:'center'}
                ,{field:'imei', title:'IMEI',align:'center'}
                ,{field:'createTime', title:'创建时间',align:'center'}
            ]],
            done: function(res, curr, count){
                console.log(curr);
                pageCurr=curr;
            }
        });

        layer.closeAll();
    });
}
//提交表单
function formSubmit(obj){
    var data = $("#orderForm").serialize();
    console.log(data)
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/order/createOrder",
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

function cancelOrder(){

    $("#productId3").val('10045641');
    $("#deviceId3").val('506de691ecbc45259914515bc39df452');
    layer.open({
        type:1,
        title: "取消指令",
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#cancelOrderList'),
        end:function(){
            cleanDevice();
        }
    });
}
function cancelOrder2(){
    $.ajax({
        type: "POST",
        data: $("#cancelOrderForm").serialize(),
        url: "/order/cancelOrder",
        success: function (data) {
            if (data=='success') {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    //load(obj);
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
function addOrder(){
    openOrder(null,"创建订阅");
}
function openOrder(data,title){

    $("#productId2").val('10045641');
    $("#deviceId2").val('506de691ecbc45259914515bc39df452');
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setOrder'),
        end:function(){
            cleanDevice();
        }
    });
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
    /*$("#deviceId").val("");*/
    /*$('#roleId').html("");*/
}
