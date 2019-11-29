package servlet.commitForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.FileInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.DealFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wen
 * @date 2019/11/25 0025-11:30
 */
@WebServlet("/uploadFileServlet2")
public class UploadFileServlet2 extends HttpServlet {
    // 上传配置
//    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        /*
         * 获取想要保存文件的路径
         */
        String parentPath = getServletContext().getRealPath("WEB-INF/saveData");
//        System.out.println(savePath);
        //当前用户名 在session中获取
        String userName = "\\admin";
        String savePath = parentPath + userName;
        File realPath = new File(savePath);
        if(realPath.exists()){
            DealFile.delAllFile(savePath);
        }
        realPath.mkdir();

        //创建json容器
        ObjectMapper mapper = new ObjectMapper();
        FileInfo info = new FileInfo();

        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            info.setFileState("Error:上传格式不正确；");
            String json = mapper.writeValueAsString(info);
            response.getWriter().write(json);
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
//        factory.setSizeThreshold(MEMORY_THRESHOLD);
//        // 设置临时存储目录
//        String tempFileName = parentPath + "/tempFile";
//        File tempDirectory = new File(tempFileName);
//        if(!tempDirectory.exists()){
//            tempDirectory.mkdir();
//        }
//        factory.setRepository(tempDirectory);
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName1 = item.getName();
                        InputStream in = item.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        String fileName = savePath + "\\" + fileName1;
                        OutputStream out = new FileOutputStream(fileName);

                        //将文件写入磁盘中
                        while ((len = in.read(buffer)) != -1){
                            out.write(buffer, 0, len);
                        }
                        out.close();
                        in.close();
                        //将文件名写入json中
                        info.setFileName(fileName1);
                        //将文件路径写入到session中
                        HttpSession session = request.getSession();
                        session.setAttribute("fileName", fileName1);
                        session.setAttribute("savePath", savePath);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            info.setFileState("Error:上传过程中出现错误；");
            String json = mapper.writeValueAsString(info);
            response.getWriter().write(json);
        }
        info.setFileState("Error:上传过程中出现错误；");
        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
