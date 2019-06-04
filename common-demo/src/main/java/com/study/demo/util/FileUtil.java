package com.study.demo.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * @Author: Lon
 * @Date: 2019/5/30 17:41
 * @Description: 文件工具类
 */
public class FileUtil {

    /**
     * 上传文件
     * @param file 文件
     * @param path 保存路径
     * @throws IOException
     */
    public static void upload(MultipartFile file, String path) throws IOException {
        if (null == file || file.isEmpty()) {
            throw new RuntimeException("file can not be empty");
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] byt = new byte[20 * 1024];
        int n;
        try {
            bis = new BufferedInputStream(file.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
            while ((n = bis.read(byt)) != -1) {
                bos.write(byt, 0, n);
            }
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
    }

    /**
     * 上传多个文件
     * @param files 文件数组
     * @param path  保存路径
     * @throws IOException
     */
    public static void uploads(MultipartFile[] files, String[] path) throws IOException {
        if (null == files || files.length < 1) {
            throw new RuntimeException("files can not be empty");
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] byt = new byte[20 * 1024];
        int n;
        try {
            for (int i = 0; i < files.length; i++) {
                bis = new BufferedInputStream(files[i].getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(new File(path[i])));
                while ((n = bis.read(byt)) != -1) {
                    bos.write(byt, 0, n);
                }
            }
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
    }

    /**
     * 是否为图片
     * @param file 文件
     * @return
     * @throws IOException
     */
    public static boolean isImage(File file) throws IOException {
        Image image = ImageIO.read(file);
        if (image == null) {
            return false;
        }
        return true;
    }

    /**
     * 是否为图片
     * @param multipartFile 文件
     * @return
     * @throws IOException
     */
    public static boolean isImage(MultipartFile multipartFile) throws Exception {
        File file = multipartFileToFile(multipartFile);
        Image image = ImageIO.read(file);
        if (image == null) {
            return false;
        }
        file.delete();// 删除复制保存在项目下的文件
        return true;
    }

    /**
     * MultipartFile转File
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        return file;
    }
}
