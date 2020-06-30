/**
 * 登录
 */
$(function(){
     layui.use(['form' ,'layer'], function() {
         var form = layui.form;
         var layer = layui.layer;
         form.on("submit(register)",function () {
             register();
             return false;
         });
     })
 })

function register(){
    console.log("asdfasdfdfsasdfa");
    var sysUserName=$("#sysUserName").val();
    var sysUserPwd=$("#sysUserPwd").val();
    var password2=$("#password2").val();
    if (sysUserPwd!=password2){
        alert("两次密码不一致");
    }
    var appKey = $("#appKey").val();
    var appSecret = $("#appSecret").val();

    $.post("/user/register",$("#userRegister").serialize(),function(data){
        if(data.code == 1){
            //window.location.href=data.url;
            console.log(data);
            layer.alert(data.msg);
        }else{
            layer.alert(data.msg,function(){
                layer.closeAll();//关闭所有弹框
            });
        }
    });

}

