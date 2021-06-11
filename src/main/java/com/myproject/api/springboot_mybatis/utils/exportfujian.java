package com.myproject.api.springboot_mybatis.utils;

import io.lettuce.core.ScriptOutputType;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipInputStream;


public class exportfujian {
    public static String getTextFromPDF(String pdfFilePath)
    {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        File file = new File(pdfFilePath);
        try {
            is = new FileInputStream(pdfFilePath);
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"rw"));
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static String readZip(String fileName) throws IOException{
        //设置字符集 防止读取文件名乱码
        ZipFile zip = new ZipFile(fileName, String.valueOf(Charset.forName("gbk")));
        File file = new File(fileName);
        String parentZipParent = file.getParent();//获取上级文件夹解压到这里
        System.out.println(parentZipParent);
        File temp = file;
        //String code = (String)System.getProperties().get("sun.jnu.encoding");
        PDDocument document = null;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        ZipInputStream zis = new ZipInputStream(bis);
        ZipEntry entry ;//用于获取压缩文件中的文件或文件夹
        StringBuffer sb = new StringBuffer();
        Enumeration e = zip.getEntries();
        while(e.hasMoreElements()) {
            entry = (ZipEntry)e.nextElement();
            if(entry.isDirectory()){
                //System.out.println("文件夹");
            }else{
                //System.out.println("file:"+entry.getName());
                if(entry.getName().endsWith("txt")){
                    sb.append("\n"+new String(entry.getName().getBytes("UTF-8"),"UTF-8")+"\n");

//                    BufferedReader reader = new BufferedReader(new InputStreamReader(zip.getInputStream(entry),"UTF-8"));
//                    String line = null;
//                    while((line = reader.readLine())!=null){
//                        //System.out.println(line);
//                        sb.append(line);
//                    }
                    sb.append("\n");
                } else if(entry.getName().endsWith("docx")){
                    sb.append("\n"+new String(entry.getName().getBytes("UTF-8"),"UTF-8")+"\n");

                    sb.append("\n");
                } else if(entry.getName().endsWith("doc")){
                    sb.append("\n"+new String(entry.getName().getBytes("UTF-8"),"UTF-8")+"\n");

                    sb.append("\n");
                }  else if(entry.getName().endsWith("pdf")){
                    sb.append("\n"+new String(entry.getName().getBytes("UTF-8"),"UTF-8")+"\n");
                    //将pdf文件解压出来 位置为zip文件同级
                    String outputPath = parentZipParent+"/"+entry.getName();
                    File outputFile = new File(outputPath);
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    byte[] buf1 = new byte[2048];
                    int len;
                    InputStream in = zip.getInputStream(entry);
                    while((len=in.read(buf1))>0){
                        fileOutputStream.write(buf1,0,len);
                    }
                    in.close();
                    fileOutputStream.close();

                    String textpdf = getTextFromPDF(outputPath);
                    sb.append(textpdf);
                    sb.append("\n");
                }
                else if (entry.getName().endsWith("zip")){  //判断是否为压缩包，若是则将其解压出再读取
                    temp = new File(parentZipParent + "\\" + entry.getName());
                    //System.out.println(temp.getAbsolutePath());
                    if (!temp.getParentFile().exists()) {
                        temp.getParentFile().mkdirs();
                    }
                    OutputStream os = new FileOutputStream(temp);
                    //通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
                    InputStream is = zip.getInputStream(entry);
                    int len = 0;
                    while ((len = is.read()) !=-1) {
                        os.write(len);
                    }
                    sb.append(readZip(temp.getAbsolutePath()));
                }
            }
        }
        return sb.toString();
    }
}
