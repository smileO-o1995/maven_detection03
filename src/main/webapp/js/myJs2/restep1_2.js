layui.use([ 'form', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        step = layui.step,
        layer = layui.layer;

    /**wen编辑
     * Step1_2步骤条中
     * 1、点击“格式化网表”，通过get方式，提交ajax请求，在此之前弹出加载层layer.load(icon,option)
     *  返回的数据有：
     *  upData.put("reFile", reFile); String类型（需要写入session、返回前端）
     upData.put("trojanNetSet", trojanNetSet); List<String>类型（返回给前端）
     upData.put("trojanPayLoad", trojanPayLoad);List<String>类型（返回给前端）
     * 2、点击“下一步”按钮跳转步骤条，需要确认session中reFile和file中的数据是否相同
     *  需要查看“.noteStep1_2”中的内容和session中reFileName的内容，并且和session中的fileName前缀相同；才可以进行跳转。
     *  否则弹出层提示
     * 3、点击“上一步”按钮跳转步骤条
     */
    $(".step1_2CommitBtn").click(function () {

        var index = layer.msg("<i class='layui-icon layui-icon-loading'></i>正在格式化网表，请耐心等待",{time:-1});
        $.get("formatServlet", function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }

            var trojanNetSet = obj.trojanNetSet;
            var netLength = trojanNetSet.length - 1;
            var trojanPayLoad = obj.trojanPayLoad;
            var payLoadLength = trojanPayLoad.length - 1;
            if(netLength >= 0){
                $(".trStep1_2").show();//显示测试结果栏
                $(".netType").html("被木马感染");
                $(".trStep1_2_1").show();
                $(".trStep1_2_2").show();
                $(".tdStep1_2_1").html("");
                for(var key in trojanNetSet){
                    if(key != netLength ){
                        $(".tdStep1_2_1").append(trojanNetSet[key] + ",  ");
                    }else {
                        $(".tdStep1_2_1").append(trojanNetSet[key]);
                    }
                }
                $(".tdStep1_2_2").html("");
                for(var key in trojanPayLoad){
                    if(key != payLoadLength ){
                        $(".tdStep1_2_2").append(trojanPayLoad[key] + ",  ");
                    }else {
                        $(".tdStep1_2_2").append(trojanPayLoad[key]);
                    }
                }
            }else{//为无木马网表
                $(".trStep1_2").show();//显示测试结果栏
                $(".netType").html("无木马");
            }

            //提示信息
            $(".noteStep1_2").html(obj.reFile);
            layer.close(index);
        });
    });
    // 2、点击“下一步”跳转按钮data 为true 或 false数据
    form.on('submit(formStep1_2)', function (data) {
        var reFileName = $(".noteStep1_2").html();
        $.get("conformReFileServlet",{reFileName:reFileName},function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }
            if(obj.accord){
                //满足跳转条件
                step.next('#stepForm');
            }else{
                layer.alert('请先格式化网表', {
                    skin: 'layui-layer-molv' //样式类名
                });
            }
        });
        return false;
    });
});