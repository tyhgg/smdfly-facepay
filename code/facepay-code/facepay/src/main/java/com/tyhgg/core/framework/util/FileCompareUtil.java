package com.tyhgg.core.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**@文件对比(简单对比)
 * @author maliang
 *
 */
public final class FileCompareUtil {

 // 计算文件的 MD5 值
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest =MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      
    }


    // 计算文件的 SHA-1 值
    public static String getFileSha1(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest =MessageDigest.getInstance("SHA-1");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static int getFileSize(File file){
        FileInputStream inFile = null;  
        int fileSize=0;
        try  
        {  
            inFile = new FileInputStream(file);  
            fileSize=inFile.available(); 
             
        }  
        catch (FileNotFoundException e)  
        {  
            System.out.println("File can't find..");  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        finally  
        {  
              
            try  
            {  
                if(inFile != null)  
                    inFile.close();  
            
            }  
            catch (IOException e)  
            {  
                System.out.println(e);  
            }  
        }  
        return fileSize;
    }  
    
    public static Boolean compareFileWithMD5(File afile,File bFile){
        if(getFileSize(afile)==getFileSize(bFile)){
            if(getFileMD5(afile).equals(getFileMD5(bFile))){
              return true;
            }
            return false;
        }else{
            return false;
        }
    }
    
    public static Boolean compareFileWithSha1(File afile,File bFile){
        if(getFileSize(afile)==getFileSize(bFile)){
            if(getFileSha1(afile).equals(getFileSha1(bFile))){
              return true;
            }
            return false;
        }else{
            return false;
        }
    }
}
