package servlet.testCountShow;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.ResultService;
import service.ResultServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取数据库中的所有记录，以数组的格式保存rtnData；rtnData数组中的元素为Map对象
 * @author wen
 * @date 2019/12/26 0026-11:10
 */
@WebServlet("/showTestCountServlet")
public class ShowTestCountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //1、读数数据表testCount中的所有记录
        ResultService resultService = new ResultServiceImpl();
        ArrayList<Map<String,Object>> rtnData = resultService.selectAllNetResult("test_count");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg","");
        map.put("count", 1000);
        map.put("data", rtnData);
        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
