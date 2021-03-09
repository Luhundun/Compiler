import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


/**
 * @ClassName: Compiler
 * @Description: 控制编译器运行的文件
 * @Author: Lu Ning
 * @Date: 2021/1/17 19:49
 * @Version: v1.0
 */
public class Compiler {
    static String statement;
    static String context;                                                  //源文件内容
    static ArrayList<String> terminals = new ArrayList<>();                 //终结符集
    static ArrayList<String> nonTerminals = new ArrayList<>();              //非终结符集
    static ArrayList<Production> productions = new ArrayList<>();           //产生式集合
//    static LinkedList<Token> tokens = new LinkedList<>();                   //待输入串
    static Stack<String> markStack = new Stack<>();				            //符号栈
    static Stack<Integer> stateStack = new Stack<>();			            //状态栈
    static Stack<Object> semanticStack = new Stack<>();   			        //语义栈
//    static ArrayList<Temp.Quaternary> quaternaryList = new ArrayList<>();   //生成四元式列表
//    public static void main(String[] args) {
//        readContextFromFile();
//        System.out.println(context);
//        Lexing.showList();
//    }
//    /**
//     * @Description: readContextFromFile 读取源程序的内容
//     * @param: []
//     * @return: void
//     * @auther: Lu Ning
//     * @date: 2021/1/17 20:13
//     */
//    public static void readContextFromFile() {
//        StringBuffer buffer = new StringBuffer("");
//        File inputFile = new File("./src/Input.txt");
//        try {               //从文件读取词法分析信息
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
//            String temp;
//            while((temp = bufferedReader.readLine()) != null) {
//                buffer.append(temp+"\n");
//            }
//            bufferedReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        context = buffer.toString();
//    }

    /**
     * @Description: isNonTerminate  是否是非终结符
     * @param: [statement]
     * @return: boolean
     * @auther: Lu Ning
     * @date: 2021/1/22 15:04
     */
    public static boolean isNonTerminate(String statement){
        for(String e : nonTerminals){
            if(statement.equals(e)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isNonTerminate(char statement){
        for(String e : nonTerminals){
            if(statement == e.charAt(0)) {
                return true;
            }
        }
        return false;
    }
}
