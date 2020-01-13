package servlet.commitForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * 下载还原后的文件
 * 参考网站：https://www.iteye.com/blog/hbiao68-2397805
 * @author wen
 * @date 2020/1/7 0007-11:00
 */
@WebServlet("/downLoadServlet")
public class DownLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream;charset=utf-8");

        /*
         * 获取session中的数据
         */
        HttpSession session = request.getSession();
        String savePath = (String) session.getAttribute("savePath");
        String reverseFileName = (String) session.getAttribute("reverseFileName");

        try{
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            long fileLength = new File(savePath + "\\" + reverseFileName).length();
            response.setHeader("Content-disposition","attachment;filename="+
            new String(reverseFileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(savePath + "\\" + reverseFileName));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
