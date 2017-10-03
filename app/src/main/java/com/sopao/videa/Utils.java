package com.sopao.videa;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class Utils {
    private static byte[] HexString2Bytes(String arg7)
    {
        byte[] v2;
        try {
            int v3 = arg7.length();
            v2 = new byte[v3 / 2];
            byte[] v4 = arg7.getBytes("GBK");
            int v1;
            for(v1 = 0; v1 < v3 / 2; ++v1) {
                v2[v1] = uniteBytes(v4[v1 * 2], v4[v1 * 2 + 1]);
            }
        }
        catch(Exception v0) {
            v0.printStackTrace();
            v2 = new byte[0];
        }
        return v2;
    }
    private static byte[] RC4Base(byte[] arg9, String arg10) {
        int v4 = 0;
        int v6 = 0;
        byte[] v1 = initKey(arg10);
        byte[] v2 = new byte[arg9.length];
        int v0;
        for(v0 = 0; v0 < arg9.length; ++v0) {
            v4 = v4 + 1 & 255;
            v6 = (v1[v4] & 255) + v6 & 255;
            byte v3 = v1[v4];
            v1[v4] = v1[v6];
            v1[v6] = v3;
            v2[v0] = ((byte)(arg9[v0] ^ v1[(v1[v4] & 255) + (v1[v6] & 255) & 255]));
        }
        return v2;
    }
    private static byte[] initKey(String arg11) {
        byte[] v7 = null;
        int v10 = 256;
        try {
            byte[] v0 = arg11.getBytes("GBK");
            byte[] v5 = new byte[256];
            int v2;
            for(v2 = 0; v2 < v10; ++v2) {
                v5[v2] = ((byte)v2);
            }
            int v3 = 0;
            int v4 = 0;
            if(v0 != null && v0.length != 0) {
                v2 = 0;
            }
            else {
                return v7;
            } while(v2 < v10) {
                v4 = (v0[v3] & 255) + (v5[v2] & 255) + v4 & 255;
                byte v6 = v5[v2];
                v5[v2] = v5[v4];
                v5[v4] = v6;
                v3 = (v3 + 1) % v0.length;
                ++v2;
            }return v5;
        }
        catch(Exception v1) {
            v1.printStackTrace();
            return v7;
        }
    }


    private static byte uniteBytes(byte arg8, byte arg9) {
        return ((byte)((((char)((((char)Byte.decode("0x" + new String(new byte[]{arg8})).byteValue())) << 4))) ^ (((char)
                Byte.decode("0x" + new String(new byte[]{arg9})).byteValue()))));
    }
    public static String RC4encode(String arg10, String arg11) {
        String v9;
        if(arg10 != null && arg11 != null) {
            try {
                byte[] v0 = RC4Base(arg10.getBytes("GBK"), arg11);
                char[] v3 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
                int v5 = v0.length;
                char[] v8 = new char[v5 * 2];
                int v4 = 0;
                int v7 = 0;
                while(v4 < v5) {
                    int v1 = v0[v4];
                    int v6 = v7 + 1;
                    v8[v7] = v3[v1 >>> 4 & 15];
                    v7 = v6 + 1;
                    v8[v6] = v3[v1 & 15];
                    ++v4;
                }
                v9 = new String(v8);
            }
            catch(Exception v2) {
                v2.printStackTrace();
                v9 = "";
            }
        }
        else {
            v9 = "";
        }return v9;
    }
    public static String RC4decode(String arg4, String arg5) {
        String v1;
        if(arg4 != null && arg5 != null) {
            try {
                v1 = new String(RC4Base(HexString2Bytes(arg4), arg5), "GBK");
            }
            catch(Exception v0) {
                v0.printStackTrace();
                v1 = "";
            }
        }
        else {
            v1 = "";
        }


        return v1;
    }
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
    public static String[] match(String arg5, String arg6) {


        Matcher v1 = Pattern.compile(arg6, 40).matcher(((CharSequence)arg5));
        ArrayList v0 = new ArrayList();
        while(v1.find()) {
            ((List)v0).add(v1.group());
        }
        return (String[]) ((List)v0).toArray(new String[((List)v0).size()]);
    }
    public static String[] getmidtext(String arg2, String arg3, String arg4) {
        String[] v0 = ("".equals(arg2)) || ("".equals(arg3)) || ("".equals(arg4)) ? new String[0] : match(arg2, "(?<=\\Q" + arg3 + "\\E).*?(?=\\Q" + arg4 + "\\E)");
        return v0;
    }

    public static String txtresult(String uri) {


        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader bufr = new BufferedReader(new InputStreamReader(in,"ISO-8859-1"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = bufr.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            return new String(response.toString().getBytes("ISO-8859-1"),"GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}