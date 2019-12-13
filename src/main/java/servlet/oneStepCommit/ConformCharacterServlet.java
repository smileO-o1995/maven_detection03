package servlet.oneStepCommit;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.NetInfo;
import service.FeatureExact;
import util.NetListRead;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 判断特征向量选择是否正确
 * 一种：没有提交特征向量，返回{sta：false，msg:"请先选择特征向量"}
 返回的结果分为量中，一种：提交的特征向量没有对应的方法处理，则返回会{sta：false，msg:"抱歉，还没有实现对应的检测方法"}
 一种为：提交的特征向量有对应的方法处理，且处理成功，则返回{sta：true，特征向量的数据集合}
 * @author wen
 * @date 2019/12/13 0013-19:24
 */
@WebServlet("/conformCharacterServlet")
public class ConformCharacterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //1、设置返回数据格式
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        //2、获取提交的表单数据，即多选框对象
        String[] characterNames = request.getParameterValues("characterName");
        if(characterNames == null || characterNames.length == 0){
            //***测试运行位置的代码
            System.out.println("提交的数据集为空");

            //（1）选择特征向量{sta：false，msg:"请先选择特征向量"}
            map.put("sta", false);
            map.put("msg", "请先选择特征向量");
            String json = mapper.writeValueAsString(map);
            response.getWriter().write(json);
            return;
        }
        //***测试运行位置的的代码
        for(String str : characterNames){
            System.out.println(str);
        }

        //4、根据characterNames的不同，进行不同的数据处理（这里需要后序再来完善，这里只有一种，即提取CC0/CC1/CO的值）
        if(characterNames.length == 3 && contain(characterNames,"CC0") &&
                contain(characterNames, "CC1") && contain(characterNames, "CO")){

            map.put("sta", true);
        }else{

            //放回数据给用户，告诉用户所选择的特征向量所对应的检测功能还未完善{sta：false，msg:"抱歉，还没有实现对应的检测方法"}
            map.put("sta", false);
            map.put("msg","抱歉，还没有实现对应的检测方法");
        }

        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    public boolean contain(String[] arr, String str){
        for(String item: arr){
            if(item.equals(str)){
                return true;
            }
        }
        return false;
    }
}
