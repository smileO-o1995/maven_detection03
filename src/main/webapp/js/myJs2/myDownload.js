layui.use('form', function () {
    var $ = layui.$,
        form = layui.form;

    $(".loadReverse").click(function () {
        var form=$("<form>");//定义form表单,通过表单发送请求
        form.attr("style","display:none");//设置为不显示
        form.attr("target","");
        form.attr("method","get");//设置请求类型
        form.attr("action","downLoadServlet");//设置请求路径
        $(".rstCardJs").append(form);//添加表单到页面(body)中
        form.submit();//表单提交
    });
});