import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


/**
 * @ClassName: Compiler
 * @Description: ���Ʊ��������е��ļ�
 * @Author: Lu Ning
 * @Date: 2021/1/17 19:49
 * @Version: v1.0
 */
public class Compiler {
    static String statement;
    static String context;                                                  //Դ�ļ�����
    static ArrayList<String> terminals = new ArrayList<>();                 //�ս����
    static ArrayList<String> nonTerminals = new ArrayList<>();              //���ս����
    static ArrayList<Production> productions = new ArrayList<>();           //����ʽ����
//    static LinkedList<Token> tokens = new LinkedList<>();                   //�����봮
    static Stack<String> markStack = new Stack<>();				            //����ջ
    static Stack<Integer> stateStack = new Stack<>();			            //״̬ջ
    static Stack<Object> semanticStack = new Stack<>();   			        //����ջ
//    static ArrayList<Temp.Quaternary> quaternaryList = new ArrayList<>();   //������Ԫʽ�б�
//    public static void main(String[] args) {
//        readContextFromFile();
//        System.out.println(context);
//        Lexing.showList();
//    }
//    /**
//     * @Description: readContextFromFile ��ȡԴ���������
//     * @param: []
//     * @return: void
//     * @auther: Lu Ning
//     * @date: 2021/1/17 20:13
//     */
//    public static void readContextFromFile() {
//        StringBuffer buffer = new StringBuffer("");
//        File inputFile = new File("./src/Input.txt");
//        try {               //���ļ���ȡ�ʷ�������Ϣ
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
     * @Description: isNonTerminate  �Ƿ��Ƿ��ս��
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
