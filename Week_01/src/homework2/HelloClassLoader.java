package homework2;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 获取 Hello 这个类
        Class clazz = new HelloClassLoader().findClass("Hello");
        // 获取 hello 方法
        Method m = clazz.getDeclaredMethod("hello");
        // 调用 hello 方法
        m.invoke(clazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("Hello.xlass");
        byte[] data = new byte[(int)file.length()];
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(file));
            dis.readFully(data);
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        decode(data);
        return defineClass(name, data, 0, data.length);
    }
    private void decode(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(255 - data[i]);
        }
    }
}
