import java.util.*;

/**
 * @ClassName: Parsing
 * @Description:
 * @Author: Lu Ning
 * @Date: 2021/1/17 17:41
 * @Version: v1.0
 */
public class Parsing {
    final static int signLength = Compiler.nonTerminals.size() + Compiler.terminals.size() + 1;         //分析表横坐标长度
    static ArrayList<State> states = new ArrayList<>();                                                 //状态集合
    static ArrayList<int[]> actionTable = new ArrayList<>(signLength);                                  //分析表
    static StringBuilder process = new StringBuilder();                                                 //分析过程


    public static void main(String[] args) {


    }
    public static boolean parsing()throws Exception{
        State I0 = new State(true);
        return true;
    }

    /**
     * @Description: 表示一个有多个项目的状态
     * @param:
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:36
     */
    static class State{
        static int nums = 0;
        int id;                                                                      //状态序号
        int stateState;                                                             //1为移进，2为待归，3为接受
        ArrayList<ParsingProduction> coreList = new ArrayList<>();                  //核集合
        ArrayList<ParsingProduction> productionsList = new ArrayList<>();            //分析时的产生式集合
        HashMap<String, State> stateMap = new HashMap<>();                          //与其他项目的映射关系
        LinkedList<String> nonTerminalsWait = new LinkedList<>();                   //待拓展的非终结符
        LinkedList<String> nonTerminalsUsed = new LinkedList<>();                   //已拓展的非终结符


        /**
         * @Description: 第一个状态的构造函数
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
         * @Description: 求出状态
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
         * @Description: 分析这个状态，建立集族并填写分析表
         * @param: []
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/24 13:23
         */
        public void analysis()throws Exception{
            int[] subActionTable = new int[signLength];
            HashMap<String,ArrayList<ParsingProduction>> tempMap = new HashMap<>();
            for (ParsingProduction e : productionsList) {
                if (e.state == 2) {          //遇到归约项目
                    if(subActionTable[0]!=0)
                        throw new Exception("非LR0文法");
                    Arrays.fill(subActionTable,-1 * Compiler.productions.indexOf(e.findOriginal()));
                    //归约
                } else if (e.state == 5) {      //遇到接受项目
                    subActionTable[Compiler.terminals.size()] = 100;        //acc为100
                } else if (e.state == 1 || e.state ==4) {     //遇到移进项目
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


            for(String key : tempMap.keySet()){                 // 对下一符号相同的项目新建一个状态
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
         * @Description: 根据DFA生成分析表
         * @param: [subActionTable, key, state]
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/24 16:22
         */
        private void modifyActionTable(int[] subActionTable, String key, State state) throws Exception {
            if(Compiler.isNonTerminate(key)){
                if(subActionTable[Compiler.terminals.size() + 1 + Compiler.nonTerminals.indexOf(key)]<0)
                    throw new Exception("非LR0文法");
                subActionTable[Compiler.terminals.size() + 1 + Compiler.nonTerminals.indexOf(key)] = state.id;
            }else{
                if(subActionTable[Compiler.terminals.indexOf(key)]<0)
                    throw new Exception("非LR0文法");
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
         * @Description: 用于判断是否已有等价项目
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
         * @Description: 重写toString方法用于展示
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
     * @Description: 项目集中每一条产生式，继承了Production类
     * @param:
     * @return:
     * @auther: Lu Ning
     * @date: 2021/1/22 19:43
     */
    static class ParsingProduction extends Production{
        final int parsingIndex;           //构造项目时产生式中·的位置
        final int state;                  //1表示移进 2表示归约 4表示拓广文法的产生式 5表示拓广文法产生式在接受状态

        /**
         * @Description: 构造函数
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
         * @Description: 用于根据移进后的情况构造新的项目
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
         * @Description: toString  为了更直观地展示项目
         * @param: []
         * @return: java.lang.String
         * @auther: Lu Ning
         * @date: 2021/1/22 19:50
         */
        public String toString(){
            StringBuilder buffer = new StringBuilder();
            buffer.append(rightProduction == null?"":rightProduction);
            buffer.insert(parsingIndex,"·");
            return leftProduction + "->" + buffer.toString() + "  state:" + state;
        }

        /**
         * @Description: 移进操作
         * @param: []
         * @return: void
         * @auther: Lu Ning
         * @date: 2021/1/22 19:53
         */
        public ParsingProduction shift(){
            return new ParsingProduction(this);
        }

        /**
         * @Description: 用于比较两个项目是否等价
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
         * @Description: 找到对应的产生式
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
     * @Description: 更形象地输出分析表
     * @param: []
     * @return: java.lang.String
     * @auther: Lu Ning
     * @date: 2021/1/24 16:26
     */
    public static String actionTableToString(){
        StringBuilder builder = new StringBuilder("生成分析表"+System.lineSeparator()+"状态\t");
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
//                throw new Exception("源程序有未定义的单词");
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
        topWord = waitWords.remove();  				//开始前将0和#置入对应的栈
        while(true) {
            process.append(step++ + ")");
            process.append("当前状态栈 " + Compiler.stateStack + System.lineSeparator());
            process.append("当前符号栈 " + Compiler.markStack + System.lineSeparator());
            process.append("当前输入符号\t" + topWord);

            action = actionTable.get(Compiler.stateStack.peek())[topWord.markId];  //根据当前状态和符号选择要执行的操作

            if (action == 0) {                        // 0对应语法分析出错
                process.append("\n\n\n语法分析错误!!!错误符号为" + topWord.context + "出错位置为" + topWord.location);
                return false;
            } else if (action < 0) {                //<0进行规约动作
                for (int i = 0; i < Compiler.productions.get(-action).rightLength; i++) {
                    Compiler.stateStack.pop();
                    Compiler.markStack.pop();
                }
                Compiler.markStack.push(Compiler.productions.get(-action).leftProduction);
                Compiler.stateStack.push(actionTable.get(Compiler.stateStack.peek())[Compiler.nonTerminals.indexOf(Compiler.productions.get(-action).leftProduction) + 1 + Compiler.terminals.size()]);
                process.append("进行了规约动作:" + Compiler.productions.get(-action) + System.lineSeparator());
            } else if (action == 100) {            //100表示语法分析正确结束，离开循环
                process.append("语法分析正确" + System.lineSeparator());
                return true;
            } else {                            //其他情况表示正常进行移进动作
                Compiler.markStack.push(topWord.context);
                Compiler.stateStack.push(action);
                topWord = waitWords.remove();
                process.append("将此符号移进" + System.lineSeparator());
            }
            process.append("剩余输入串" + waitWords + System.lineSeparator() + System.lineSeparator());
        }
    }
}
