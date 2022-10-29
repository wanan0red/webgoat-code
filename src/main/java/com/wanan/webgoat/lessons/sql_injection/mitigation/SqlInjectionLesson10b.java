package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-mitigation-10b-1", "SqlStringInjectionHint-mitigation-10b-2", "SqlStringInjectionHint-mitigation-10b-3", "SqlStringInjectionHint-mitigation-10b-4", "SqlStringInjectionHint-mitigation-10b-5"})
public class SqlInjectionLesson10b extends AssignmentEndpoint {
    @PostMapping("/SqlInjectionMitigations/attack10b")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor){
        try {
            if (editor.isEmpty()) return failed(this).feedback("sql-injection.10b.no-code").build();
            editor = editor.replace("\\<.*?>","");
            
            String regexSetsUpConnection = "(?=.*getConnection.*)";
            String regexUsesPreparedStatement = "(?=.*PreparedStatement.*)";
            String regexUsesPlaceholder = "(?=.*\\=\\?.*|.*\\=\\s\\?.*)";
            String regexUsesSetString = "(?=.*setString.*)";
            String regexUsesExecute = "(?=.*execute.*)";
            String regexUsesExecuteUpdate = "(?=.*executeUpdate.*)";

            String codeline = editor.replace("\n","").replace("\r","");
//            去除 \r \n

            boolean setsUpConnection = this.check_text(regexSetsUpConnection,codeline);
//            这里与代码进行正则匹配
            boolean usesPreparedStatement = this.check_text(regexUsesPreparedStatement,codeline);
            boolean useSetString = this.check_text(regexUsesSetString,codeline);
            boolean usesPlaceholder = this.check_text(regexUsesPlaceholder, codeline);
            boolean usesExecute = this.check_text(regexUsesExecute, codeline);
            boolean usesExecuteUpdate = this.check_text(regexUsesExecuteUpdate, codeline);

            boolean hasImportant = (setsUpConnection && usesPreparedStatement && usesPlaceholder && useSetString && (usesExecute || usesExecuteUpdate));
//          如果都正则通过了
            List<Diagnostic> hasCompiled = this.compileFromString(editor);
//            返回已编译
            if (hasImportant && hasCompiled.size() < 1){
//                如果已经编译完成并且 没有错误
                return success(this).feedback("sql-injection.10b.success").build();
            } else if (hasCompiled.size()>0) {
//                如果编译有错误
                String errors = "";
                for (Diagnostic d : hasCompiled){
                    errors += d.getMessage(null) + "<br>";
                }
                return failed(this).feedback("sql-injection.10b.compiler-errors").output(errors).build();
            }else {
                return failed(this).feedback("sql-injection.10b.failed").build();
            }
        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private List<Diagnostic> compileFromString(String s) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //        这里是通过命令行工具获取了一个Java的编译器
        DiagnosticCollector diagnosticCollector = new DiagnosticCollector();
//        提供一种在列表中收集诊断信息的简单方法
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector,null,null);
//      通过编译器获取一个标准文件管理器 并传入一个 收集诊断信息的对象
        JavaFileObject javaObjectFromString = getJavaFileContentsAsString(s);
//        获取了文件对象
        Iterable fileObjects = Arrays.asList(javaObjectFromString);
//        将字符串转换成文件数组
        JavaCompiler.CompilationTask task = compiler.getTask(null,fileManager,diagnosticCollector,null,null,fileObjects);
//        null 表示使用err输出信息 fileManager 文件管理器 diagnosticCollector 诊断器 fileObjects要编译的编译单元
        Boolean result = task.call();
//        执行此编译任务
        List<Diagnostic> diagnostics = diagnosticCollector.getDiagnostics();
//        返回诊断的结果
        return diagnostics;
    }

    private SimpleJavaFileObject getJavaFileContentsAsString(String s) {
        StringBuilder javaFileContents = new StringBuilder("import java.sql.*; public class TestClass { static String DBUSER; static String DBPW; static String DBURL; public static void main(String[] args) {" + s + "}}");
//        新建了StringBuilder 对象
        JavaObjectFromString javaFileObject = null;
//        文件操作对象
        try {
            javaFileObject = new JavaObjectFromString("TestClass.java", javaFileContents.toString());
//            第一个为文件路径 第二个为文件内容
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return javaFileObject;
    }
    class JavaObjectFromString extends SimpleJavaFileObject {
//        这里收件继承了 SimpleJavaFileObject 对象
        private String contents = null;
//      新建了一个 contents
        public JavaObjectFromString(String className, String contents) throws Exception {
            super(new URI(className), Kind.SOURCE);
//            这里调动SimpleJavaFileObject 的构造方法 其中第一个参数为路径信息 第二个为文件类型 这里的source 代表.java结尾的常规文件
            this.contents = contents;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//            这里获取字符内容
            return contents;
        }
    }

    private boolean check_text(String regex, String text) {
        Pattern p  = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
//        不区分大小写的正则匹配
        Matcher m = p.matcher(text);
//        匹配的文本
        if (m.find())
//            如果有发现
            return true;
        else
            return false;
    }
}
