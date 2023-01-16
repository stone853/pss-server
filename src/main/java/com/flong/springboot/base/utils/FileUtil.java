package com.flong.springboot.base.utils;


import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author jinshi
 * @Date 2021/2/18 17:13
 * @Version 1.0
 */
public class FileUtil {
    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public String downloadFile (HttpServletRequest request, HttpServletResponse response,String downloadPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //设置文件路径
            File file = new File(downloadPath);
            String fileName = file.getName();
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 清空response
            response.reset();


            response.setHeader("Content-disposition", "attachment;filename="+fileName+";"+"filename*=utf-8''"+fileName);
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", String.valueOf(file.length()));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");

            FileInputStream fi = new FileInputStream(file);
            bis = new BufferedInputStream(fi);
            int len = 0;
            byte[] buffer = new byte[4 * 1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, len);
            }

            bos.flush();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"文件下载失败");
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bis.close();
                }
            } catch (IOException e) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,e.getMessage());
            }
        }
        return "文件下载失败";
    }


    /**
     * 预览
     * @param filePath
     * @param response
     */
    public String showImg(HttpServletRequest request,HttpServletResponse response,String filePath){

        try {

            //type. p预览，d下载
            String type = request.getParameter("type");
            response.reset();
            response.setCharacterEncoding("UTF-8");

            File file = new File(filePath);
            String fileName = file.getName();
            FileInputStream fis = new FileInputStream(file);

            if ("d".equalsIgnoreCase(type)) {
                //下载
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf(".")));

            } else {
                //预览时不需设置Content-Disposition
//            response.setContentType("application/octet-stream");
//            response.setContentType("text/html;charset=UTF-8");
//            response.setHeader("Content-Disposition","inline;filename=" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf(".")));
            }
            OutputStream os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = fis.read(bytes)) != -1) {
                os.write(bytes, 0, length);
            }
            os.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "预览失败";
    }





}
