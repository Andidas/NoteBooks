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
 * @author ��ΰ��(andi)
 *	�ļ���д������
 */
public class FileIOUtil {
	/**
	 * ��ȡָ��·���ı��ļ����ַ���
	 * @param filePath �ļ�·��
	 * @return ���ض������ļ�����
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
	 * �ַ���д��
	 * @param fileд��ָ�����ı��ļ�·��
	 * @param append Ϊtrue��ʾ׷�ӣ�false��ʾ��ͷ��ʼд��
	 * @param text д����ı��ַ�����textΪnullʱֱ�ӷ���  
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
	 * �Ѷ������ļ������ֽ����飬���û�����ݣ��ֽ�����Ϊnull 
	 * @param filePath �ļ�·��
	 * @return �������ֽ�����
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
	 * ���ֽ�����Ϊд��������ļ�������Ϊnullʱֱ�ӷ���
	 * @param filePath �ļ�·��
	 * @param data Ҫд����ֽ�����
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
     * ��һ������д���ļ�
     * @param filePath �ļ�·��
     * @param o Ҫд��Ķ���
     * @param isAppend true��ʾ׷�ӷ�ʽд��false��ʾ����д  
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
     * ��һ����������д���ļ�
     * @param filePath �ļ�·��
     * @param objects Ҫд���һ�����
     * @param isAppend true��ʾ׷�ӷ�ʽд��false��ʾ����д
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
     * ��ȡ���󣬷���һ������  
     * @param filePath ��ȡ���ļ�·��
     * @return ����һ��object����
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
     * ��ȡ���󣬷���һ���������飬
     * @param filePath �ļ�·��
     * @param count ��ʾҪ���Ķ���ĸ���
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
 * ����̳�ObjectOutputStream����дwriteStreamHeader()����,��ʵ��׷��д��ʱȥ��ͷ����Ϣ 
 */  
class MyObjectOutputStream extends ObjectOutputStream {  
    private static File f;  
  
    // writeStreamHeader()��������ObjectOutputStream�Ĺ��췽������õ�  
    // ���ڸ��Ǻ��writeStreamHeader()�����õ���f�����ֱ���ô˹��췽������  
    // һ��MyObjectOutputStream������ôwriteStreamHeader()�е�f�ǿ�ָ��  
    // ��Ϊf��û�г�ʼ��������������õ�̬ģʽ  
    private MyObjectOutputStream(OutputStream out, File f) throws IOException,  
            SecurityException {  
        super(out);  
    }  
  
    // ����һ��MyObjectOutputStream�������ﱣ֤��new MyObjectOutputStream(out, f)  
    // ֮ǰf�Ѿ�ָ��һ��File����  
    public static MyObjectOutputStream newInstance(File file, OutputStream out)  
            throws IOException {  
        f = file;// ����������Ҫ�ĵط��������ļ�������������ָ��ͬһ���ļ�����  
        return new MyObjectOutputStream(out, f);  
    }  
  
    @Override  
    protected void writeStreamHeader() throws IOException {  
        // �ļ������ڻ��ļ�Ϊ��,��ʱ�ǵ�һ��д���ļ�������Ҫ��ͷ����Ϣд�롣  
        if (!f.exists() || (f.exists() && f.length() == 0)) {  
            super.writeStreamHeader();  
        } else {  
            // ����Ҫ���κ�����  
        }  
    }  
}  
