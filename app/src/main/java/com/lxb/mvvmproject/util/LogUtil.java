package com.lxb.mvvmproject.util;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * User: Tenz Liu
 * Date: 2017-08-21
 * Time: 12-10
 * Description: Log日志打印工具类
 */

public class LogUtil {

    public static boolean debug = false;
    public static String Tag = "";

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 初始化
     *
     * @param debug
     */
    public static void init(boolean debug, String Tag) {
        LogUtil.debug = debug;
        LogUtil.Tag = Tag;
    }

    /**
     * 打印日志(Verbose)
     *
     * @param msg 内容
     */
    public static void v(String msg) {
        if (debug) {
            Log.v(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(Debug)
     *
     * @param msg 内容
     */
    public static void d(String msg) {
        if (debug) {
            Log.d(Tag, "" + msg);
        }
    }


    /**
     * 打印日志(Info)
     *
     * @param msg 内容
     */
    public static void i(String msg) {
        if (debug) {
            Log.i(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(Warm)
     *
     * @param msg 内容
     */
    public static void w(String msg) {
        if (debug) {
            Log.w(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(wtf)
     *
     * @param msg 内容
     */
    public static void wtf(String msg) {
        if (debug) {
            Log.wtf(Tag, "" + msg);
        }
    }


    /**
     * 打印日志(Error)
     *
     * @param msg 内容
     */
    public static void e(String msg) {
        if (debug) {
            Log.e(Tag, "" + msg);
        }
    }

    /**
     * 打印Json的辅助线
     * @param isTop 是否顶部
     */
    public static void printLine( boolean isTop) {
        if (isTop) {
           e( "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            e("╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    /**
     * 打印json内容
     * @param msg 内容
     */
    public static void json(String msg) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(true);
        message = "Json:" + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            e( "║ " + line);
        }
        printLine(false);
    }

    /**
     *  打印xml
     * @param xml 内容
     */
    public static void xml(String xml) {
        if (xml != null) {
            xml =formatXML(xml);
            xml = "Xml:" + "\n" + xml;
        } else {
            xml = "Xml:End";
        }

        printLine( true);
        String[] lines = xml.split(LINE_SEPARATOR);
        for (String line : lines) {
            if (!TextUtils.isEmpty(line)) {
                e("║ " + line);
            }
        }
        printLine( false);
    }

    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }


}
