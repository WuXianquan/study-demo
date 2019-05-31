package com.study.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author: Lon
 * @Date: 2019/5/30 17:41
 * @Description: 文件工具类
 */
@Slf4j
public class FileUtil {

    public static void upload(MultipartFile file, String path) throws IOException {
        if (null == file || file.isEmpty()) {
            throw new RuntimeException("file can not be empty");
        }
        OutputStream os = null;
        InputStream is = null;
        int temp;
        try {
            os = new FileOutputStream(path);
            is = file.getInputStream();
            while ((temp = is.read()) != (-1)) {
                os.write(temp);
            }
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    public static void uploads(MultipartFile[] files, String path) throws IOException {
        if (null == files || files.length < 1) {
            throw new RuntimeException("files can not be empty");
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] byt = new byte[20 * 1024];
        int n;
        try {
            for (MultipartFile file : files) {
                bis = new BufferedInputStream(file.getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
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
}
