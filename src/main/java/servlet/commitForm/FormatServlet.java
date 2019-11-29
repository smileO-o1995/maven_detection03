package servlet.commitForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.FileExtract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * step1_2格式化网表
 * @author wen
 * @date 2019/11/26 0026-10:40
 */
@WebServlet("/formatServlet")
public class FormatServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //1、获取session中的数据filePath
        HttpSession session = request.getSession();
        String fileName = (String)session.getAttribute("fileName");
        String savePath = (String)session.getAttribute("savePath");

        //***测试运行位置的代码
        System.out.println(savePath);
        System.out.println(fileName + "运行到servlet");

        //2、调用service包中类完成对网表文件格式化
        /*
        返回的结果updata包括
        upData.put("reFile", reFile); String类型（需要写入session、返回前端）
        upData.put("netList", netList); NetListRead的实例对象（需要存入session中的数据）
        upData.put("trojanNetSet", trojanNetSet); List<String>类型（返回给前端）
        upData.put("trojanPayLoad", trojanPayLoad);List<String>类型（返回给前端）
         */
        FileExtract extract = new FileExtract();
        Map<String, Object> upData = extract.gateExtract(fileName, savePath);

        //3、将格式化后的文件名放入到session中
        session.setAttribute("reFileName", upData.get("reFile"));
        session.setAttribute("netList",upData.get("netList"));

        //4、将upData删除netList之后作为返回给前端的数据
        upData.remove("netList");

        //5、返回给前端的数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(upData);
        response.getWriter().write(json);
    }
}
