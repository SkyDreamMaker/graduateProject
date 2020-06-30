/**
 * 登录
 */
$(function(){
     layui.use(['form' ,'layer'], function() {
         var form = layui.form;
         var layer = layui.layer;
         form.on("submit(login)",function () {
             login();
             return false;
         });
/*         form.on('submit(subscriptionSubmit)', function(){
             // TODO 校验
             register();
             return false;
         });*/
         var path=window.location.href;
         if(path.indexOf("kickout")>0){
             layer.alert("您的账号已在别处登录；若不是您本人操作，请立即修改密码！",function(){
                 window.location.href="/login";
             });
         }
     })
 })

function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    var rememberMe = $("#rememberMe").val();
    $.post("/user/login",$("#useLogin").serialize(),function(data){
        if(data.code == 1){
            window.location.href=data.url;
        }else{
            layer.alert(data.message,function(){
                layer.closeAll();//关闭所有弹框
            });
        }
    });
}

function register(){
    console.log("进入到注册页面");
    window.open('http://localhost:8102/registerPage','top');
    /*#在当前页面打开baidu地址 和 <a href='https://www.baidu.com/'></a> 效果相同*/
    /*window.location.href = 'http://localhost:8102/registerPage';*/
    /*#在新窗口打开baidu地址 和 <a href='https://www.baidu.com/' target='_blank'></a> 效果相同*/
}

