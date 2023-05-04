package cn.hao.common.cloud.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class ObjectToBytesTool {
    protected static final Logger logger = LoggerFactory.getLogger(ObjectToBytesTool.class);


    public static <T> byte[] transfer(T data) {
        try {
            //创建一个ByteArrayOutputStream对象输出流
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);


            //将对象写入输出流
            out.writeObject(data);

            //关闭输出流
            out.close();
            byteOut.close();

            //将字节流转换为字节数组
            byte[] bytes = byteOut.toByteArray();

            //字符集为utf-8
            String newStr = new String(bytes, StandardCharsets.UTF_8);
            return newStr.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("ObjectToBytesTool transfer error……");
        }
        return null;
    }
}
