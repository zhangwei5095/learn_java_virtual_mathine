package xyz.anduo.jvm.ch06;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义classloader，实现热替换
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午4:22
 */
public class MyClassLoader extends ClassLoader {
    private String fileName;

    public MyClassLoader(String baseDir) {
        this.fileName = baseDir;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // first ,check if the class has already been loaded
        Class re = findClass(name);
        if (re == null) {
            //System.out.println("I can't load the class : " + name + " need help from parent!");
            return super.loadClass(name, resolve);
        }
        return re;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        Class clazz = this.findLoadedClass(className);
        if (null == clazz) {
            byte[] bytes;
            try {
                bytes = loadClassBytes(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
            if (bytes == null) return null;

            Class theClass = defineClass(className, bytes, 0, bytes.length);//A
            if (theClass == null)
                throw new ClassFormatError();
            return theClass;
        }
        return clazz;

    }

    private byte[] loadClassBytes(String className) throws
            ClassNotFoundException {
        try {
            String classFile = getClassFile(className);
            FileInputStream fis = new FileInputStream(classFile);
            FileChannel fileC = fis.getChannel();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel outC = Channels.newChannel(baos);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = fileC.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outC.write(buffer);
                buffer.clear();
            }
            fis.close();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException(className);
        } catch (IOException e) {
            throw new ClassNotFoundException(className);
        }
    }

    private String getClassFile(String className) {
        StringBuffer sb = new StringBuffer(fileName);
        className = className.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + className);
        return sb.toString();
    }
}
