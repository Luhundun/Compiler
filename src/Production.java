import java.util.ArrayList;

/**
 * @ClassName: Production
 * @Description:
 * @Author: Lu Ning
 * @Date: 2021/1/17 19:53
 * @Version: v1.0
 */
public class Production {
    String leftProduction;          //产生式左部
    String rightProduction;         //产生式右部
    int rightLength;                //右部长度
    ArrayList<Integer> ifRightTerminalList = new ArrayList<>();        //产生式右部符号类型的标记,1 非终结符 0 终结符

    /**
     * @Description: 有参构造函数，根据GUI上的产生式构造出对象
     * @param: [production]
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:38
     */
    public Production(String production)throws Exception{
        String[] temp = production.split("->");
        if(temp.length != 2){
            throw new Exception("格式错误");
        }
        leftProduction = temp[0];
        rightProduction = temp[1];
//        System.out.println(temp[0]+"->"+temp[1]+":"+production);
        if(!Compiler.isNonTerminate(leftProduction)){
            throw new Exception("左部不为非终结符");
        }
        rightLength = rightProduction.length();

        if(rightProduction.equals(" ")){              //处理右部为空的特殊情况
            rightProduction = null;
            rightLength = 0;
        }

        for(int i = 0; i < rightLength; i++){          //根据右部符号生成标记信息
            ifRightTerminalList.add((Compiler.isNonTerminate(rightProduction.charAt(i)) ? 1 : 0 ));
        }

    }

    /**
     * @Description: 默认的构造函数用于构造拓广文法
     * @param: [first]
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 15:37
     */
    public Production(boolean first){
        leftProduction = Compiler.nonTerminals.get(0) + '\'';
        rightProduction = Compiler.nonTerminals.get(0);     //默认第一个非终结符是开始符
        rightLength = 1;
        ifRightTerminalList.add(1);
    }

    public Production(){
        ;
    }

    public String toString(){
        return leftProduction + "->" + rightProduction;
    }
}
