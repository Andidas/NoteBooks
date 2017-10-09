package dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author 刘伟艺(andi)
 *	文件读写工具类
 */
public class FileIOUtil {
	/**
	 * 读取指定路径文本文件，字符流
	 * @param filePath 文件路径
	 * @return 返回读到的文件内容
	 */
	public static String read(String filePath) {  
		StringBuilder str = new StringBuilder();  
		BufferedReader in = null;  
		try {  
			in = new BufferedReader(new FileReader(filePath));  
			String s;  
			try {  
				while ((s = in.readLine()) != null)  
					str.append(s + '\n');  
			} finally {  
				in.close();  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return str.toString();  
	}  
  
	/**
	 * 字符流写入
	 * @param file写入指定的文本文件路径
	 * @param append 为true表示追加，false表示重头开始写，
	 * @param text 写入的文本字符串，text为null时直接返回  
	 */
	public static void write(String filePath, boolean append, String text) {  
		if (text == null)  
			return;  
		try {  
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,  
					append));  
			try {  
				out.write(text);  
				out.flush();
			} finally {  
				out.close();  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}     

	/**
	 * 把二进制文件读入字节数组，如果没有内容，字节数组为null 
	 * @param filePath 文件路径
	 * @return 读到的字节数组
	 */
	public static byte[] readBinary(String filePath) {  
		byte[] data = null;  
		try {  
			BufferedInputStream in = new BufferedInputStream(  
					new FileInputStream(filePath));  
			try {  
				data = new byte[in.available()];  
				in.read(data);  
			} finally {  
				in.close();  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return data;  
	}  
 
	/**
	 * 把字节数组为写入二进制文件，数组为null时直接返回
	 * @param filePath 文件路径
	 * @param data 要写入的字节数组
	 */
	public static void writeBinary(String filePath, byte[] data) {  
		if (data == null)  
			return;  
		try {  
			BufferedOutputStream out = new BufferedOutputStream(  
					new FileOutputStream(filePath));  
			try {  
				out.write(data); 
				out.flush();
			} finally {  
				out.close();  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}  

    /**
     * 把一个对象写入文件
     * @param filePath 文件路径
     * @param o 要写入的对象
     * @param isAppend true表示追加方式写，false表示重新写  
     */
    public static void writeObject(String filePath, Object o, boolean isAppend) {  
        if (o == null)  
            return;  
        try {  
            File f = new File(filePath);  
            MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,  
                    new FileOutputStream(f, isAppend));  
            try {  
                out.writeObject(o);  
                out.flush();
            } finally {  
                out.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /**
     * 把一个对象数组写入文件
     * @param filePath 文件路径
     * @param objects 要写入的一组对象
     * @param isAppend true表示追加方式写，false表示重新写
     */
    public static void writeObjects(String filePath, Object[] objects, boolean isAppend) {  
        if (objects == null)  
            return;  
        try {  
            File f = new File(filePath);  
            MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,  
                    new FileOutputStream(f, isAppend));  
            try {  
                for (Object o : objects)  
                    out.writeObject(o); 
                out.flush();
            } finally {  
                out.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /**
     * 读取对象，返回一个对象  
     * @param filePath 读取的文件路径
     * @return 返回一个object对象
     */
    public static Object readObject(String filePath) {  
        Object o = null;  
        try {  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(  
                    filePath));  
            try {  
                o = in.readObject();  
            } finally {  
                in.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  

    /**
     * 读取对象，返回一个对象数组，
     * @param filePath 文件路径
     * @param count 表示要读的对象的个数
     * @return
     */
    public static Object[] readObjects(String filePath, int count) {  
        Object[] objects = new Object[count];  
        try {  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(  
                    filePath));  
            try {  
                for (int i = 0; i < count; i++) {  
                      
                    objects[i] = in.readObject();  
                }  
            } finally {  
                in.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return objects;  
    }  
}
/** 
 * 此类继承ObjectOutputStream，重写writeStreamHeader()方法,以实现追加写入时去掉头部信息 
 */  
class MyObjectOutputStream extends ObjectOutputStream {  
    private static File f;  
  
    // writeStreamHeader()方法是在ObjectOutputStream的构造方法里调用的  
    // 由于覆盖后的writeStreamHeader()方法用到了f。如果直接用此构造方法创建  
    // 一个MyObjectOutputStream对象，那么writeStreamHeader()中的f是空指针  
    // 因为f还没有初始化。所以这里采用单态模式  
    private MyObjectOutputStream(OutputStream out, File f) throws IOException,  
            SecurityException {  
        super(out);  
    }  
  
    // 返回一个MyObjectOutputStream对象，这里保证了new MyObjectOutputStream(out, f)  
    // 之前f已经指向一个File对象  
    public static MyObjectOutputStream newInstance(File file, OutputStream out)  
            throws IOException {  
        f = file;// 本方法最重要的地方：构建文件对象，两个引用指向同一个文件对象  
        return new MyObjectOutputStream(out, f);  
    }  
  
    @Override  
    protected void writeStreamHeader() throws IOException {  
        // 文件不存在或文件为空,此时是第一次写入文件，所以要把头部信息写入。  
        if (!f.exists() || (f.exists() && f.length() == 0)) {  
            super.writeStreamHeader();  
        } else {  
            // 不需要做任何事情  
        }  
    }  
}  
