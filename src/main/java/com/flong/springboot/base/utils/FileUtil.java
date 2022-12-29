package com.flong.springboot.base.utils;


import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;

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
                throw new ServiceException(e.getMessage());
            }
        }
        return "文件下载失败";
    }







}
