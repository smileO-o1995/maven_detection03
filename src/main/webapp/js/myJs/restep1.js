layui.use([ 'form', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        step = layui.step,
        layer = layui.layer;

    /**wen编辑
     Step1:下一步 按钮点击后事件
     返回的数据为json数据：{"existFile":true,"msg":可以进行下一步}
     {"existFile":false,"msg":请先选择需要测试的网表}
     */
    form.on('submit(formStep1)', function (data) {
        var fileName = $(".netTitleJs").html();
        $.get("conformFileServlet",{fileName:fileName},function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }
            if(obj.existFile){
                step.next('#stepForm');
            }else{
                layer.alert(obj.msg, {
                    skin: 'layui-layer-molv' //样式类名
                });
            }
        });
        return false;
    });

    //点击"重新选择"按钮
    $(".reChoose").click(function () {
       location.reload();
    });
});