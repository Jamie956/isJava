package com.jamie;

import cn.hutool.http.HtmlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegexpTest {
    private static final String[] words = {"a", "1", "abc", "abcde987321", "abcdefg", "defghij", "", "abc", "abc 123", "abc123", "abc  123", "a", "rt", "C", "+", "RtS", "8", "abcdefg", "a+\\", "kava", "sava", "lava", "tava", "java", "Robert", "book", "cat", "job", "Todor", "+ob 123? @ p", "Boooob", "jjobss", "abc135gf", "string '123'", "int a = 5247;", "int A = 5247;", "int b = 5247;", "1int a = 5247;", "string '", "Marko is a good boy.", "Our Marko, is a good boy!", "Nobody is as good as our Marko is!", "2.345,56", "-52.678.110", "235", "128m", "2020-08-03"};

    /**
     * \w               一个  数字或字母
     * \w*              0或以上个   数字或字母
     * \w+              1或以上个   数字或字母
     * abc\w*           abc     连接  0或以上个   数字字母
     * \d               匹配一个数字字符。等价于[0-9]
     * \s               匹配一个任何空白字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。
     * ?                贪婪模式则尽可能多的匹配所搜索的字符串
     * \s*              0或以上个   空格
     * \s+              1或以上个   空格
     * \s?              0或1个空格
     * \D               匹配一个非数字字符。等价于[^0-9]。
     * \W               匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。
     * {n}              n是一个非负整数。匹配确定的n次
     * {n,m}            m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次。
     * [a-zA-Z0-9]      一个a-z的字母或A-Z的字母或0-9的数字，[]字符范围。匹配指定范围内的任意字符。
     * w{1}             一个字母或数字
     * \w{1,2}          1-2个字母或数字
     * \w{1,3}          1-3个字母或数字
     * \w{1,7}          1-7个字母或数字
     * \w{0,7}          0-7个字母或数字
     * [xyz]            字符集合。匹配所包含的任意一个字符
     * [^xyz]           负值字符集合。匹配未包含的任意字符。
     * [kj]ava          k 或 j 连接ava
     * [^kj]ava         除了 k 或 j 连接ava
     * .                匹配除“\n”之外的任何单个字符
     * .[o][b].*        第1个任意，第2个是o，第3个是b，后面0或多个任意
     * .ob.*            同上
     * .*               0个或多个 任意
     * \w+\s\'.+        一个或多个字母或数字 + 一个空格 + 一个' + 一个或多个任意
     * \w+\sa.+         一个或多个字母或数字 + 一个空格 + 一个字母a + 一个或多个任意
     * \D+\sa.+         1个或多个(排除数字) + 一个空格 + 一个字母a + 一个或多个任意
     * \D+\s.+          1个或多个(排除数字) + 一个空格 + 一个或多个任意
     * \w+\s?\'?\d{0,3}\'?          一个或多个字母或数字 + 0或1个空格 + 0或1个' + 0-3个数字 + 0或1个'
     * \w+(\s\')?\d{0,3}\'?         同上
     * \w+(\s\'(\d*)\')?
     * .+(\\d)$        数字结尾
     */
    @Test
    public void isMatch() {
        String[] patterns = {"\\w", "\\w*", "\\w+", "abc\\w*", "\\w+\\s\\d+", "\\w+\\s*\\d+", "\\w+\\s+\\d+", "\\w+\\s?\\d+", "\\D+", "\\W", "[a-zA-Z0-9]", "\\w{1}", "\\w{1,2}", "\\w{1,3}", "\\w{1,7}", "\\w{0,7}", "[kj]ava", "[^kj]ava", ".[o][b].*", ".ob.*", ".*", "\\w+", "\\w+\\s\\'.+", "\\w+\\sa.+", "\\D+\\sa.+", "\\D+\\s.+", "\\w+\\s?\\'?\\d{0,3}\\'?", "\\w+(\\s\\')?\\d{0,3}\\'?", "\\w+(\\s\\'(\\d*)\\')?", "M.+", "^M.+", ".+(\\d)$", "\\d{4}-\\d{2}-\\d{2}"};

        for (String pattern : patterns) {
            System.out.println(">>>>>>>>>>>>>> pattern: " + pattern + " <<<<<<<<<<<<<<");
            for (String word : words) {
                if (word.matches(pattern)) {
                    System.out.println(String.format("MATCHES!          word: %s, pattern: %s", word, pattern));
                } else {
                    System.out.println(String.format("NOT MATCHES!      word: %s, pattern: %s", word, pattern));
                }
            }
        }
    }

    /**
     * 是否匹配正则
     */
    @Test
    public void matchTest() {
        String word = "aa啊的";
        String pattern = "\\w*[\\u4E00-\\u9FA5]+";
        if (word.matches(pattern)) {
            System.out.println("match");
        } else {
            System.out.println("not match");
        }
    }

    /**
     * 根据字符 或正则 替换
     */
    @Test
    public void replaceTest() {
        String content = "<p>asdasdas</p><p>sdf45156321sd</p><p>bbb</p><p>cccc</p>";

        String a = content.replace("<p>", "");
        System.out.println("a=" + a);

        //替换第一个<p>
        String b = content.replaceFirst("<p>", "");
        System.out.println("b=" + b);

        //正则替换html 标签
        String c = content.replaceAll("<[^>]*>", "");
        System.out.println("c=" + c);

        //正则替换数组
        String d = content.replaceAll("\\d", "");
        System.out.println("d=" + d);

    }

    /**
     * 使用正则，获取匹配字符串和索引
     * <p>
     * 找出文本的全部中文                         [\u4E00-\u9FA5]+
     * 找出文本的全部数字                        \d+
     * 找出指定开头x和结尾y的文本                 x.+.y
     * 取PIN=为开头的内容                          (?<=PIN=).\S*
     * 取以=开头 以&结尾 取得的中间的内容            (?<==).*?(?=(&|$))
     * 取"key" : "开头，",结尾的中间的内容            (?<="key" : ").*?(?=(",|$))
     * 取某个字符串|开头的内容                      (\|.*)
     */
    private static final Pattern DIGITAL_PATTERN = Pattern.compile("\\d+");

    @Test
    public void findMatch() {
        String text = "asa9jbjb1";
        Matcher matcher = DIGITAL_PATTERN.matcher(text);
        while (matcher.find()) {
            System.out.println(String.format("start=%s, end=%s, group=%s", matcher.start(), matcher.end(), matcher.group()));
        }
    }


    /**
     * 截取字符串的开头到第n个中文
     */
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]+");

    @Test
    public void chineseMatch() {
//        String str = "as!@#@a9阿松大jbjb的1123";
//        String str = "阿松大asdasd啊实打实大苏打";
//        String str = "阿松大啊实打实大苏打sadads";
        String str = "asdasd阿松大啊实打实大苏打";
        Matcher matcher = CHINESE_PATTERN.matcher(str);
        int n = 2;
        int len = 0;
        int cur = -1;
        while (matcher.find()) {
            int curLen = matcher.end() - matcher.start();
            if (len + curLen > n) {
                cur = matcher.start() + n - len;
                break;
            }
            len += curLen;
        }
        System.out.println(str.substring(0, cur));
    }

    /**
     * 正则表达式解析sql
     */
    @Test
    public void sqlRegParse() throws IOException {
        //匹配整个ddl，将ddl分为表名，列sql部分，表注释
        String ddlReg = "\\s*create\\s+table\\s+(?<tableName>\\S+)[^\\(]*\\((?<columnsSQL>[\\s\\S]+)\\)[^\\)]+?(comment\\s*(=|on\\s+table)\\s*'(?<tableComment>.*?)'\\s*;?)?$";
        //匹配列sql部分，分别解析每一列的列名 类型 和列注释
        String colReg = "\\s*(?<fieldName>\\S+)\\s+(?<fieldType>\\w+)\\s*(?:\\([\\s\\d,]+\\))?((?!comment).)*(comment\\s*'(?<fieldComment>.*?)')?\\s*(,|$)";

        Pattern ddlPattern = Pattern.compile(ddlReg, Pattern.CASE_INSENSITIVE);
        Pattern colPattern = Pattern.compile(colReg, Pattern.CASE_INSENSITIVE);
        String sql = FileUtils.readFileToString(new File("src/main/resources/myddl"), "UTF-8");

        Matcher matcher = ddlPattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group("tableName");
            String tableComment = matcher.group("tableComment");

            System.out.println(tableName + "\t\t" + tableComment);
            System.out.println("--------------------");

            String columnsSql = matcher.group("columnsSQL");

            if (columnsSql != null && columnsSql.length() > 0) {
                Matcher colMatcher = colPattern.matcher(columnsSql);
                while (colMatcher.find()) {
                    String fieldName = colMatcher.group("fieldName");
                    String fieldType = colMatcher.group("fieldType");
                    String fieldComment = colMatcher.group("fieldComment");
                    if (!"key".equalsIgnoreCase(fieldType)) {
                        System.out.println(fieldName + "\t\t" + fieldType + "\t\t" + fieldComment);
                    }
                }
            }
        }
    }

    /**
     * 移除html 标签
     */
    @Test
    public void replaceHtml() {
        String str = "<p>去除所有html标签,<img/><My-Tag class=\"abc\" value=\"test\">自定     义标签\n也\r可以\t去除哦</My-Tag></p>";
        str = str.replaceAll("<[^>]+>", "").replaceAll("\\s", "");
        System.out.println(str);
    }

    @Test
    public void replaceDate() {
        String str = "CompanyNotice_2021-03-05.txt";
        str = str.replaceAll("_\\d{4}-\\d{2}-\\d{2}", "");
        System.out.println(str);
    }

    @Test
    public void replaceDigital() {
        String s = "recall20210616142329".replaceAll("\\d", "");
        System.out.println(s);
    }

    @Test
    public void rmHtmlAttr() {
        String content = "<p align=\"center\"  width=100 style=width:100px;height:152px;>";
        content = HtmlUtil.removeHtmlAttr(content, "align", "style", "src", "class");
        System.out.println(content);
    }


    /**
     * 移除html标签的指定属性, 能够移除不带双引号的属性
     * <[^>]+>
     * (?i)<p[^>]*?>
     */
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("(?i)<[^>]*?>");
    @Test
    public void matchAppendReplace() {
//        String text = "sdfsdfsdf<p align=\"center\" width=100 style=width:100px;height:152px;>fdsfdsfsd<a class=\"center\" width=100 style=width:100px;height:152px; width=100>saaa</a>sdfasd";
        String text = "aaaaa<tr powered-by=xiumi.us opera-tn-ra-comp=_$.pages:0.layers:0.comps:27.col1:0.classicTable1:0>bbbbbbbbbbbb";
        Matcher matcher = HTML_TAG_PATTERN.matcher(text);

        String[] rmAttrs = {"style", "class"};

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String matchStr = matcher.group();
            String[] attrs = StringUtils.split(matchStr, " ");

            StringBuilder stringBuilder = new StringBuilder();

            for (String attr : attrs) {
                boolean noneMatch = Stream.of(rmAttrs).noneMatch(e -> attr.startsWith(e) || attr.contains("$"));
                if (noneMatch) {
                    stringBuilder.append(attr).append(" ");
                }
            }

            if (stringBuilder.indexOf(">") == -1) {
                stringBuilder.append(">");
            }
            matcher.appendReplacement(sb, stringBuilder.toString());
        }
        matcher.appendTail(sb);

        System.out.println(sb.toString());
    }


}
