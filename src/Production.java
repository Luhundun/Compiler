import java.util.ArrayList;

/**
 * @ClassName: Production
 * @Description:
 * @Author: Lu Ning
 * @Date: 2021/1/17 19:53
 * @Version: v1.0
 */
public class Production {
    String leftProduction;          //����ʽ��
    String rightProduction;         //����ʽ�Ҳ�
    int rightLength;                //�Ҳ�����
    ArrayList<Integer> ifRightTerminalList = new ArrayList<>();        //����ʽ�Ҳ��������͵ı��,1 ���ս�� 0 �ս��

    /**
     * @Description: �вι��캯��������GUI�ϵĲ���ʽ���������
     * @param: [production]
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:38
     */
    public Production(String production)throws Exception{
        String[] temp = production.split("->");
        if(temp.length != 2){
            throw new Exception("��ʽ����");
        }
        leftProduction = temp[0];
        rightProduction = temp[1];
//        System.out.println(temp[0]+"->"+temp[1]+":"+production);
        if(!Compiler.isNonTerminate(leftProduction)){
            throw new Exception("�󲿲�Ϊ���ս��");
        }
        rightLength = rightProduction.length();

        if(rightProduction.equals(" ")){              //�����Ҳ�Ϊ�յ��������
            rightProduction = null;
            rightLength = 0;
        }

        for(int i = 0; i < rightLength; i++){          //�����Ҳ��������ɱ����Ϣ
            ifRightTerminalList.add((Compiler.isNonTerminate(rightProduction.charAt(i)) ? 1 : 0 ));
        }

    }

    /**
     * @Description: Ĭ�ϵĹ��캯�����ڹ����ع��ķ�
     * @param: [first]
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 15:37
     */
    public Production(boolean first){
        leftProduction = Compiler.nonTerminals.get(0) + '\'';
        rightProduction = Compiler.nonTerminals.get(0);     //Ĭ�ϵ�һ�����ս���ǿ�ʼ��
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
