import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import javax.swing.event.*;
/*
 * Created by JFormDesigner on Wed Jan 20 11:31:16 CST 2021
 */



/**
 * @author Lu Ning
 */
public class GUI extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUI() {
        initComponents();
    }

    public void openGrammerActionPerformed(ActionEvent e) {
        openGrammer();
    }

    public void loadGrammerActionPerformed(ActionEvent e) {
        setLoadGrammer();
    }

    public void openProgramActionPerformed(ActionEvent e) {
        openProgram();
    }

    public void compileActionPerformed(ActionEvent e) {
        setCompile();
    }

    public void openGrammerStateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    public void clearActionPerformed(ActionEvent e) {
        readMe();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        openGrammer = new JButton();
        loadGrammer = new JButton();
        clear = new JButton();
        openProgram = new JButton();
        compile = new JButton();
        panel3 = new JPanel();
        l1 = new JLabel();
        scrollPane4 = new JScrollPane();
        nonTerminal = new JTextArea();
        label6 = new JLabel();
        scrollPane5 = new JScrollPane();
        production = new JTextArea();
        label2 = new JLabel();
        scrollPane6 = new JScrollPane();
        terminal = new JTextArea();
        panel4 = new JPanel();
        label3 = new JLabel();
        label7 = new JLabel();
        scrollPane1 = new JScrollPane();
        program = new JTextArea();
        scrollPane2 = new JScrollPane();
        parse = new JTextArea();
        scrollPane3 = new JScrollPane();
        states = new JTextArea();
        label1 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- openGrammer ----
            openGrammer.setText("\u6253\u5f00\u6587\u6cd5\u6587\u4ef6");
            openGrammer.addActionListener(e -> openGrammerActionPerformed(e));
            panel1.add(openGrammer);

            //---- loadGrammer ----
            loadGrammer.setText("\u52a0\u8f7d\u6587\u6cd5\u6587\u4ef6");
            loadGrammer.addActionListener(e -> loadGrammerActionPerformed(e));
            panel1.add(loadGrammer);

            //---- clear ----
            clear.setText("\u67e5\u770b\u6ce8\u610f\u4e8b\u9879");
            clear.addActionListener(e -> clearActionPerformed(e));
            panel1.add(clear);

            //---- openProgram ----
            openProgram.setText("\u6253\u5f00\u6e90\u6587\u4ef6");
            openProgram.addActionListener(e -> openProgramActionPerformed(e));
            panel1.add(openProgram);

            //---- compile ----
            compile.setText("\u7f16\u8bd1");
            compile.addActionListener(e -> compileActionPerformed(e));
            panel1.add(compile);
        }
        contentPane.add(panel1, BorderLayout.PAGE_START);

        //======== panel3 ========
        {

            //---- l1 ----
            l1.setText("\u975e\u7ec8\u7ed3\u7b26(\u4e0d\u80fd\u6709\u7a7a\u884c)");

            //======== scrollPane4 ========
            {
                scrollPane4.setViewportView(nonTerminal);
            }

            //---- label6 ----
            label6.setText("\u4ea7\u751f\u5f0f");

            //======== scrollPane5 ========
            {
                scrollPane5.setViewportView(production);
            }

            //---- label2 ----
            label2.setText("\u7ec8\u7ed3\u7b26");

            //======== scrollPane6 ========
            {
                scrollPane6.setViewportView(terminal);
            }

            //======== panel4 ========
            {

                //---- label3 ----
                label3.setText("\u6e90\u7a0b\u5e8f");

                //---- label7 ----
                label7.setText("\u8bed\u6cd5\u5206\u6790\u7ed3\u679c");

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(program);
                }

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(parse);
                }

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 582, Short.MAX_VALUE)))
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel4Layout.createParallelGroup()
                                .addComponent(scrollPane1)
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 35, Short.MAX_VALUE))
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)))
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label3)
                                .addGap(739, 739, 739)))
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label7)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE))
                );
            }

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(states);
            }

            //---- label1 ----
            label1.setText("\u96c6\u65cf");

            GroupLayout panel3Layout = new GroupLayout(panel3);
            panel3.setLayout(panel3Layout);
            panel3Layout.setHorizontalGroup(
                panel3Layout.createParallelGroup()
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel3Layout.createParallelGroup()
                            .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(label6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                .addComponent(scrollPane4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(l1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                .addComponent(scrollPane6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup()
                            .addComponent(label1)
                            .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel3Layout.setVerticalGroup(
                panel3Layout.createParallelGroup()
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label2)
                                    .addComponent(label1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel3Layout.createParallelGroup()
                                    .addGroup(panel3Layout.createSequentialGroup()
                                        .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(l1)
                                        .addGap(12, 12, 12)
                                        .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(label6)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                                    .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)))
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
            );
        }
        contentPane.add(panel3, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JPanel panel1;
    public JButton openGrammer;
    public JButton loadGrammer;
    public JButton clear;
    public JButton openProgram;
    public JButton compile;
    public JPanel panel3;
    public JLabel l1;
    public JScrollPane scrollPane4;
    public JTextArea nonTerminal;
    public JLabel label6;
    public JScrollPane scrollPane5;
    public JTextArea production;
    public JLabel label2;
    public JScrollPane scrollPane6;
    public JTextArea terminal;
    public JPanel panel4;
    public JLabel label3;
    public JLabel label7;
    public JScrollPane scrollPane1;
    public JTextArea program;
    public JScrollPane scrollPane2;
    public JTextArea parse;
    public JScrollPane scrollPane3;
    public JTextArea states;
    public JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void openGrammer() {
        JFileChooser fileChooser = new JFileChooser("./");
        // fileChooser 是 JFileChooser 的实例
        // 显示文件选取的对话框
        int option = fileChooser.showDialog(null, null);
        // 使用者按下确认键
        if(option == JFileChooser.APPROVE_OPTION) {
            try {

                // 开启选取的文件
                BufferedReader buf =new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                // 设定文件标题
                this.setTitle(fileChooser.getSelectedFile().toString());
                // 清除前一次文件
                terminal.setText("");
                nonTerminal.setText("");
                production.setText("");
                // 设定状态栏
                String lineSeparator = System.getProperty("line.separator");
                // 读取文件并附加至文字编辑区
                String text;
                while((text = buf.readLine()) != null) {
                    if(text.equals("Terminal:"))
                        break;
                }
                while((text = buf.readLine()) != null) {
                    if(text.equals("NonTerminal:"))
                        break;
                    terminal.append(text);
                    terminal.append(lineSeparator);
                }
                while((text = buf.readLine()) != null) {
                    if(text.equals("Production:"))
                        break;
                    nonTerminal.append(text);
                    nonTerminal.append(lineSeparator);
                }
                while((text = buf.readLine()) != null) {
                    production.append(text);
                    production.append(lineSeparator);
                }
                if(production.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"文件格式错误","文件格式错误",JOptionPane.ERROR_MESSAGE);
                    terminal.setText("");
                    nonTerminal.setText("");
                    production.setText("");
                }
                buf.close();
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null,e.toString(),"开启文件失败 ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void openProgram() {
        JFileChooser fileChooser = new JFileChooser("./");
        // fileChooser 是 JFileChooser 的实例
        // 显示文件选取的对话框
        int option = fileChooser.showDialog(null, null);
        // 使用者按下确认键
        if(option == JFileChooser.APPROVE_OPTION) {
            try {

                // 开启选取的文件
                BufferedReader buf =new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                // 设定文件标题
                this.setTitle(fileChooser.getSelectedFile().toString());
                // 清除前一次文件
                program.setText("");
                // 设定状态栏
                String lineSeparator = System.getProperty("line.separator");
                // 读取文件并附加至文字编辑区
                String text;
                while((text = buf.readLine()) != null) {
                    program.append(text);
                    program.append(lineSeparator);
                }
                Compiler.context = program.getText();
                buf.close();
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null,e.toString(),"开启文件失败 ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * @Description: 加载语法文件 
     * @param: []
     * @return: void
     * @auther: Lu Ning
     * @date: 2021/1/22 15:33
     */
    public void setLoadGrammer(){
        Compiler.terminals.clear();
        Compiler.nonTerminals.clear();
        Compiler.productions.clear();

        String lineSeperate = System.getProperty("line.separator");

        String[] terminals = terminal.getText().split(lineSeperate);
        Compiler.terminals.addAll(Arrays.asList(terminals));
//        Collections.sort(Compiler.terminals);

        String[] nonTerminals = nonTerminal.getText().split(lineSeperate);
        Compiler.nonTerminals.addAll(Arrays.asList(nonTerminals));
//        Collections.sort(Compiler.nonTerminals);

        String temp = production.getText().replaceAll("\n","").replaceAll("\r","");
        String[] productions = temp.split("#");
        try{
            Compiler.productions.add(new Production(true));     //拓广文法
            for(String pro : productions){
                Compiler.productions.add(new Production(pro));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"产生式不合法", JOptionPane.ERROR_MESSAGE);
        }

        try {
            Parsing.State I0 = new Parsing.State(true);
//            Parsing.parsing();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"非LR0文法", JOptionPane.ERROR_MESSAGE);
        }
        states.append(String.valueOf(Parsing.states));
        parse.append(Parsing.actionTableToString());
    }

    /**
     * @Description: 语法分析
     * @param: []
     * @return: void
     * @auther: Lu Ning
     * @date: 2021/1/24 17:56
     */
    public void setCompile(){
        Compiler.context = program.getText();
        try {
            if(Parsing.lrAnalyse()){
                parse.append(Parsing.process.toString());
            }else {
                JOptionPane.showMessageDialog(null,"分析出错","分析出错", JOptionPane.ERROR_MESSAGE);
                parse.append(Parsing.process.toString());
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"源文件包含未知符号", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void readMe(){
        String message =
                "1. 暂时只支持LR0文法，发生冲突会报错，可拓展为SLR0" + System.lineSeparator() +
                "2. 可以用文件导入也可以手动输入文法信息，文件格式见Language.txt，手动输入时符号不能有空行，产生式用#分隔" + System.lineSeparator() +
                "3. 点击加载即可生成LR集族和分析表，输入源程序后点击编译可以进行语法分析";
        JOptionPane.showMessageDialog(null,message);
    }
}
