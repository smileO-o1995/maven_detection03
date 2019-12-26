layui.use(['upload', 'layer'], function(){
    var $ = layui.jquery,
        upload = layui.upload,
        layer = layui.layer;

    //执行实例
    var uploadInst = upload.render({
        elem: '#upFile' //绑定元素
        ,url: 'uploadFileServlet2' //上传接口
        ,accept:'file'
        ,exts:'v'
        ,done: function(rtnData){
            console.info("此文件上传成功：");
            //上传成功后回调函数
            /*
            1、先将数据转换为json格式
            2、显示需要打印“测试结果”区域的代码
             */
            var obj;
            //判断是否为json格式
            if((typeof rtnData=='object')&&rtnData.constructor==Object){
                obj=rtnData;
            }else{
                obj  = eval("("+rtnData+")");
            }

            // $(".rstCardJs").load(location.href + " .rstCardJs");
            //
            $(".uploadNote").html(obj.fileName);
        }
        ,error: function(){
            layer.alert('文件上传异常，请刷新重试', {
                skin: 'layui-layer-molv' //样式类名
            });
        }
    });
});