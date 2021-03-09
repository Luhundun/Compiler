import java.util.*;

/**
 * @ClassName: Parsing
 * @Description:
 * @Author: Lu Ning
 * @Date: 2021/1/17 17:41
 * @Version: v1.0
 */
public class Parsing {
    final static int signLength = Compiler.nonTerminals.size() + Compiler.terminals.size() + 1;         //����������곤��
    static ArrayList<State> states = new ArrayList<>();                                                 //״̬����
    static ArrayList<int[]> actionTable = new ArrayList<>(signLength);                                  //������
    static StringBuilder process = new StringBuilder();                                                 //��������


    public static void main(String[] args) {


    }
    public static boolean parsing()throws Exception{
        State I0 = new State(true);
        return true;
    }

    /**
     * @Description: ��ʾһ���ж����Ŀ��״̬
     * @param:
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:36
     */
    static class State{
        static int nums = 0;
        int id;                                                                      //״̬���
        int stateState;                                                             //1Ϊ�ƽ���2Ϊ���飬3Ϊ����
        ArrayList<ParsingProduction> coreList = new ArrayList<>();                  //�˼���
        ArrayList<ParsingProduction> productionsList = new ArrayList<>();            //����ʱ�Ĳ���ʽ����
        HashMap<String, State> stateMap = new HashMap<>();                          //��������Ŀ��ӳ���ϵ
        LinkedList<String> nonTerminalsWait = new LinkedList<>();                   //����չ�ķ��ս��
        LinkedList<String> nonTerminalsUsed = new LinkedList<>();                   //����չ�ķ��ս��


        /**
         * @Description: ��һ��״̬�Ĺ��캯��
         * @param: [first]
         * @return:
         * @auther: Lu Ning
         * @date: 2021/1/22 21:00
         */
        State(boolean first)throws Exception{
            if(first){
                actionTable.add(new int[]{0});
                this.id = nums++;
                this.stateState = 1;
                coreList.add(new ParsingProduction(Compiler.productions.get(0)));
                productionsList.addAll(coreList);
                nonTerminalsWait.add(Compiler.nonTerminals.get(0));
                next();
                states.add(this);
                analysis();

            }
        }

        State(boolean first, ArrayList<ParsingProduction> inputList)throws Exception{
            if(!first){
                actionTable.add(new int[]{0});
                this.id = nums++;
                this.stateState = 1;
                this.coreList.addAll(inputList);
                this.productionsList.addAll(inputList);
                for(ParsingProduction e : inputList){
                    if(e.state==1&&e.ifRightTerminalList.get(e.parsingIndex)==1&&!nonTerminalsWait.contains(String.valueOf(e.rightProduction.charAt(e.parsingIndex)))){
                        nonTerminalsWait.add(String.valueOf(e.rightProduction.charAt(e.parsingIndex)));
                    }
                }
                next();
                states.add(this);
                analysis();



            }
        }

        /**
         * @Description: ���״̬
         * @param: []
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/22 21:32
         */
        public void next(){
            while(!nonTerminalsWait.isEmpty()) {
                for (Production e : Compiler.productions) {
                    if (e.leftProduction.equals(nonTerminalsWait.get(0))) {
                        ParsingProduction newProduction = new ParsingProduction(e);
                        if(!this.ifExistEuqal(newProduction)){
                            productionsList.add(newProduction);
                        }
                        if(e.rightLength > 0 && e.ifRightTerminalList.get(0) == 1 && !nonTerminalsUsed.contains(String.valueOf(e.rightProduction.charAt(0)))){
                            nonTerminalsWait.add(String.valueOf(e.rightProduction.charAt(0)));
                        }
                    }
                }
                nonTerminalsUsed.add(nonTerminalsWait.remove(0));
            }
        }

        /**
         * @Description: �������״̬���������岢��д������
         * @param: []
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/24 13:23
         */
        public void analysis()throws Exception{
            int[] subActionTable = new int[signLength];
            HashMap<String,ArrayList<ParsingProduction>> tempMap = new HashMap<>();
            for (ParsingProduction e : productionsList) {
                if (e.state == 2) {          //������Լ��Ŀ
                    if(subActionTable[0]!=0)
                        throw new Exception("��LR0�ķ�");
                    Arrays.fill(subActionTable,-1 * Compiler.productions.indexOf(e.findOriginal()));
                    //��Լ
                } else if (e.state == 5) {      //����������Ŀ
                    subActionTable[Compiler.terminals.size()] = 100;        //accΪ100
                } else if (e.state == 1 || e.state ==4) {     //�����ƽ���Ŀ
                    ParsingProduction newParsingProduction = e.shift();
                    String nextWord = String.valueOf(e.rightProduction.charAt(e.parsingIndex));

                    if(tempMap.containsKey(nextWord)){
                        tempMap.get(nextWord).add(newParsingProduction);
                    }else{
                        ArrayList<ParsingProduction> newList = new ArrayList<>();
                        newList.add(newParsingProduction);
                        tempMap.put(nextWord,newList);
                    }
                }
            }

            System.out.println(this.id+"half");
            tempMap.forEach((str,list)->{
                System.out.println(str+list+this.coreList);
            });


            for(String key : tempMap.keySet()){                 // ����һ������ͬ����Ŀ�½�һ��״̬
                boolean flag = false;
                for(State state : states){
                    if(compareList(state.coreList,tempMap.get(key))){
                        flag = true;
                        modifyActionTable(subActionTable, key, state);
                        break;
                    }
                }
                if(!flag){
                    State state = new State(false,tempMap.get(key));
                    modifyActionTable(subActionTable, key, state);
                }
            }
            actionTable.remove(this.id);
            actionTable.add(this.id,subActionTable);
        }

        /**
         * @Description: ����DFA���ɷ�����
         * @param: [subActionTable, key, state]
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/24 16:22
         */
        private void modifyActionTable(int[] subActionTable, String key, State state) throws Exception {
            if(Compiler.isNonTerminate(key)){
                if(subActionTable[Compiler.terminals.size() + 1 + Compiler.nonTerminals.indexOf(key)]<0)
                    throw new Exception("��LR0�ķ�");
                subActionTable[Compiler.terminals.size() + 1 + Compiler.nonTerminals.indexOf(key)] = state.id;
            }else{
                if(subActionTable[Compiler.terminals.indexOf(key)]<0)
                    throw new Exception("��LR0�ķ�");
                subActionTable[Compiler.terminals.indexOf(key)] = state.id;
            }
        }

        public static boolean compareList(ArrayList<ParsingProduction> is,ArrayList<ParsingProduction> that){
            if(is.size()!=that.size()){
                return false;
            }
            for (int i=0;i<that.size();i++){
                if(!is.get(i).equals(that.get(i))){
                    return false;
                }
            }
            return true;
        }


        /**
         * @Description: �����ж��Ƿ����еȼ���Ŀ
         * @param: [that]
         * @return: boolean
         * @auther: Lu Ning
         * @date: 2021/1/23 15:39
         */
        public boolean ifExistEuqal(ParsingProduction that){
            for (ParsingProduction e : productionsList){
                if(e.equals(that)){
                    return true;
                }
            }
            return false;
        }

        /**
         * @Description: ��дtoString��������չʾ
         * @param: []
         * @return: java.lang.String
         * @auther: Lu Ning
         * @date: 2021/1/23 15:08
         */
        public String toString(){
            StringBuilder stringBuilder = new StringBuilder('I' + id-73 + ":" + System.lineSeparator());
            for (ParsingProduction e : productionsList){
                stringBuilder.append(e.toString());
                stringBuilder.append(System.lineSeparator());
            }
            return stringBuilder.toString();
        }
//        public boolean isInState(Production production){
//
//        }
    }

    /**
     * @Description: ��Ŀ����ÿһ������ʽ���̳���Production��
     * @param:
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:43
     */
    static class ParsingProduction extends Production{
        final int parsingIndex;           //������Ŀʱ����ʽ�С���λ��
        final int state;                  //1��ʾ�ƽ� 2��ʾ��Լ 4��ʾ�ع��ķ��Ĳ���ʽ 5��ʾ�ع��ķ�����ʽ�ڽ���״̬

        /**
         * @Description: ���캯��
         * @param: [production]
         * @return:
         * @auther: Lu Ning
         * @date: 2021/1/22 19:46
         */
        ParsingProduction(Production production){
            super();
            this.leftProduction = production.leftProduction;
            this.rightProduction = production.rightProduction;
            this.rightLength = production.rightLength;
            this.ifRightTerminalList = production.ifRightTerminalList;
            this.parsingIndex = 0;
            if(leftProduction.equals(Compiler.nonTerminals.get(0) + '\'')){
                state = 4;
            }else if(rightLength == 0){
                state = 2;
            }else{
                state = 1;
            }
        }

        /**
         * @Description: ���ڸ����ƽ������������µ���Ŀ
         * @param: [old]
         * @return:
         * @auther: Lu Ning
         * @date: 2021/1/23 16:41
         */
        ParsingProduction(ParsingProduction old){
            this.leftProduction = old.leftProduction;
            this.rightProduction = old.rightProduction;
            this.rightLength = old.rightLength;
            this.ifRightTerminalList = old.ifRightTerminalList;
            if(old.state == 1){
                this.parsingIndex = old.parsingIndex + 1;
                if(this.parsingIndex == this.rightLength) {
                   this.state = 2;
                }else {
                    this.state = 1;
                }
            }else if(old.state == 4){
                this.parsingIndex = 1;
                this.state = 5;
            }else{
                this.parsingIndex=-1;
                this.state=-1;
            }
        }

        /**
         * @Description: toString  Ϊ�˸�ֱ�۵�չʾ��Ŀ
         * @param: []
         * @return: java.lang.String
         * @auther: Lu Ning
         * @date: 2021/1/22 19:50
         */
        public String toString(){
            StringBuilder buffer = new StringBuilder();
            buffer.append(rightProduction == null?"":rightProduction);
            buffer.insert(parsingIndex,"��");
            return leftProduction + "->" + buffer.toString() + "  state:" + state;
        }

        /**
         * @Description: �ƽ�����
         * @param: []
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/22 19:53
         */
        public ParsingProduction shift(){
            return new ParsingProduction(this);
        }

        /**
         * @Description: ���ڱȽ�������Ŀ�Ƿ�ȼ�
         * @param: [o]
         * @return: boolean
         * @auther: Lu Ning
         * @date: 2021/1/23 15:35
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParsingProduction that = (ParsingProduction) o;
            if(rightProduction == null){
                return that.rightProduction == null;
            }
            return parsingIndex == that.parsingIndex && leftProduction.equals(that.leftProduction) && rightProduction.equals(that.rightProduction);
        }

        /**
         * @Description: �ҵ���Ӧ�Ĳ���ʽ
         * @param: []
         * @return: Production
         * @auther: Lu Ning
         * @date: 2021/1/23 16:20
         */
        public Production findOriginal(){
            for(Production e : Compiler.productions){
                if(leftProduction.equals(e.leftProduction)){
                    if(rightProduction==null){
                        if(e.rightProduction==null){
                            return e;
                        }
                    }else {
                        if(e.rightProduction==null){
                        }
                        else if(e.rightProduction.equals(rightProduction)){
                            return e;
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * @Description: ����������������
     * @param: []
     * @return: java.lang.String
     * @auther: Lu Ning
     * @date: 2021/1/24 16:26
     */
    public static String actionTableToString(){
        StringBuilder builder = new StringBuilder("���ɷ�����"+System.lineSeparator()+"״̬\t");
        Compiler.terminals.forEach(str->{
            builder.append(str);
            builder.append("\t");
        });
        builder.append("#\t");
        Compiler.nonTerminals.forEach(str->{
            builder.append(str);
            builder.append("\t");
        });
        builder.append(System.lineSeparator());
        int j = 0;
        for (int[] e : actionTable){
            builder.append(j++);
            builder.append("\t");
            for(int i : e){
                if(i == 100){
                    builder.append("acc");
                } else if(i > 0){
                    builder.append('s');
                    builder.append(i);
                } else if(i<0){
                    builder.append('r');
                    builder.append(-i);
                }
                builder.append("\t");
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    static class Word {
        String context;
        int markId;
        int location;

        public Word(char ch, int k) {
            context = String.valueOf(ch);
            location = k;
            if (Compiler.terminals.contains(context)) {
                markId = Compiler.terminals.indexOf(context);
            } else if (Compiler.nonTerminals.contains(context)) {
                markId = Compiler.nonTerminals.indexOf(context) + 1 + Compiler.terminals.size();
            } else if (ch == '#') {
                markId = Compiler.terminals.size();
            } else {
//                throw new Exception("Դ������δ����ĵ���");
            }
        }

        @Override
        public String toString() {
            return context + " ";
        }
    }
    public static boolean lrAnalyse() {
        LinkedList<Word> waitWords = new LinkedList<>();
        int k=0;
        process.append(Compiler.context+System.lineSeparator());
        for(char e : Compiler.context.toCharArray()){
            waitWords.add(new Word(e,k++));
        }
        waitWords.add(new Word('#',k));
        int action=0;
        Word topWord;
        int step = 1;

        Compiler.stateStack.push(0);
        Compiler.markStack.push("#");
        topWord = waitWords.remove();  				//��ʼǰ��0��#�����Ӧ��ջ
        while(true) {
            process.append(step++ + ")");
            process.append("��ǰ״̬ջ " + Compiler.stateStack + System.lineSeparator());
            process.append("��ǰ����ջ " + Compiler.markStack + System.lineSeparator());
            process.append("��ǰ�������\t" + topWord);

            action = actionTable.get(Compiler.stateStack.peek())[topWord.markId];  //���ݵ�ǰ״̬�ͷ���ѡ��Ҫִ�еĲ���

            if (action == 0) {                        // 0��Ӧ�﷨��������
                process.append("\n\n\n�﷨��������!!!�������Ϊ" + topWord.context + "����λ��Ϊ" + topWord.location);
                return false;
            } else if (action < 0) {                //<0���й�Լ����
                for (int i = 0; i < Compiler.productions.get(-action).rightLength; i++) {
                    Compiler.stateStack.pop();
                    Compiler.markStack.pop();
                }
                Compiler.markStack.push(Compiler.productions.get(-action).leftProduction);
                Compiler.stateStack.push(actionTable.get(Compiler.stateStack.peek())[Compiler.nonTerminals.indexOf(Compiler.productions.get(-action).leftProduction) + 1 + Compiler.terminals.size()]);
                process.append("�����˹�Լ����:" + Compiler.productions.get(-action) + System.lineSeparator());
            } else if (action == 100) {            //100��ʾ�﷨������ȷ�������뿪ѭ��
                process.append("�﷨������ȷ" + System.lineSeparator());
                return true;
            } else {                            //���������ʾ���������ƽ�����
                Compiler.markStack.push(topWord.context);
                Compiler.stateStack.push(action);
                topWord = waitWords.remove();
                process.append("���˷����ƽ�" + System.lineSeparator());
            }
            process.append("ʣ�����봮" + waitWords + System.lineSeparator() + System.lineSeparator());
        }
    }
}
