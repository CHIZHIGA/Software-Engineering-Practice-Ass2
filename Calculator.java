package Test;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

import javax.swing.*;

//Calculator�࣬�̳�JFrame��ܣ�ʵ���¼��������ӿ�
public class Calculator extends JFrame implements ActionListener {
    private final String[] KEYS = {"7", "8", "9", "AC", "4", "5", "6", "-", "1", "2", "3", "+", "0", "e", "pi", "/", "sqrt", "%", "x*x", "*", "(", ")", ".", "="};
    private JButton keys[] = new JButton[KEYS.length];
    private JTextArea resultText = new JTextArea("0.0");    // �ı������TextArea�����ɶ����ı����ı������ݳ�ʼֵ��Ϊ0.0
    private JTextArea History = new JTextArea();    // ��ʷ��¼�ı����ʼֵ��Ϊ��
    private JPanel jp2 = new JPanel();
    private JScrollPane gdt1 = new JScrollPane(resultText);     //��������ʾ���ı����½�һ����ֱ��������
    private JScrollPane gdt2 = new JScrollPane(History);    //����ʷ��¼�ı����½�һ����ֱ��������
    private JLabel label = new JLabel("��ʷ��¼");
    private String input = "";   //�����ı����������׺���ʽ

    // ���췽��
    public Calculator() {
        super("Caculator");//�������ؼ��֣���ʾ���ø���Ĺ��캯����
        resultText.setBounds(20, 18, 255, 115);     // �����ı����С
        resultText.setAlignmentX(RIGHT_ALIGNMENT);  // �ı��������Ҷ���
        resultText.setEditable(false);  // �ı��������޸Ľ��
        resultText.setFont(new Font("monospaced", Font.PLAIN, 18));    //���ý���ı����������ֵ����塢���͡���С
        History.setFont(new Font("monospaced", Font.PLAIN, 18));    //������ʷ��¼�ı����������ֵ����塢���͡���С
        History.setBounds(290, 40, 250, 370);   // �����ı����С
        History.setAlignmentX(LEFT_ALIGNMENT);  // �ı��������Ҷ���
        History.setEditable(false);     // �ı��������޸Ľ��
        label.setBounds(300, 15, 100, 20);  //���ñ�ǩλ�ü���С
        jp2.setBounds(290, 40, 250, 370);   //������崰��λ�ü���С
        jp2.setLayout(new GridLayout());
        JPanel jp1 = new JPanel();
        jp1.setBounds(20, 18, 255, 115);    //������崰��λ�ü���С
        jp1.setLayout(new GridLayout());
        resultText.setLineWrap(true);   // �����Զ����й���
        resultText.setWrapStyleWord(true);  // ������в����ֹ���
        resultText.setSelectedTextColor(Color.RED);
        History.setLineWrap(true);  //�Զ�����
        History.setWrapStyleWord(true);
        History.setSelectedTextColor(Color.blue);
        gdt1.setViewportView(resultText);   //ʹ��������ʾ����
        gdt2.setViewportView(History);
        gdt1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);     //�����ô�ֱ������һֱ��ʾ
        gdt2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);     //�����ô�ֱ������һֱ��ʾ
        gdt2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);     //������ˮƽ������һֱ��ʾ
        jp1.add(gdt1);  //���������������崰����
        jp2.add(gdt2);
        this.add(jp1);  //�������ӵ��ܴ�����
        this.add(jp2);  //�������ӵ��ܴ�����
        this.setLayout(null);
        this.add(label);    // �½�����ʷ��¼����ǩ

        // ���ð�ť x,yΪ��ť�ĺ�������
        int x = 20, y = 150;
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton();
            keys[i].setText(KEYS[i]);
            keys[i].setBounds(x, y, 60, 40);
            if (x < 215) {
                x += 65;
            } else {
                x = 20;
                y += 45;
            }
            this.add(keys[i]);
        }
        for (int i = 0; i < KEYS.length; i++)   // ÿ����ť��ע���¼�������
        {
            keys[i].addActionListener(this);
        }
        this.setResizable(false);
        this.setBounds(500, 200, 567, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // �¼�����
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();    //����¼�Դ�ı�ǩ
        if (Objects.equals(label, "AC"))    //��հ�ť��������ʾ���ı���ǰ�����е�����ͽ��
        {
            input = "";
            resultText.setText("0.0");      //�����ı������ʾ����ʾ��ʼֵ;
        } else if (Objects.equals(label, "sqrt")) {
            String n;
            if(input.isEmpty()) n="error!";     //���жϣ����������������뿪�����Ų��ǺϷ���
            else n = String.valueOf(kfys(input));
            resultText.setText("sqrt" + "(" + input + ")" + "=" + n);       //ʹ������ʽ��ʾ���������
            History.setText(History.getText() + resultText.getText() + "\n");       //��ȡ��������������ʽ��ʹ����ʾ����ʷ��¼�ı���
            input = n;
        } else if (Objects.equals(label, "x*x")) {
            String m;
            if(input.isEmpty()) m="error!";
            else m = String.valueOf(pfys(input));
            resultText.setText(input + "^2" + "=" + m);
            History.setText(History.getText() + resultText.getText() + "\n");
            input = m;
        } else if (Objects.equals(label, "=")) {
            if (input.isEmpty()) return;
            String[] s = houzhui(input);    //����׺���ʽת��Ϊ��׺���ʽ
            double result = Result(s);     //�����׺���ʽ�ó�������ʽ���
            resultText.setText(input + "=" + result);
            History.setText(History.getText() + resultText.getText() + "\n");
        } else {
            if (Objects.equals(label, "e")) {
                String m = String.valueOf(2.71828);     //��e��ֵ���ַ�������ʽ����m
                label = m;
            } else if (Objects.equals(label, "pi")) {
                String m = String.valueOf(3.14159);
                label = m;
            }
            input = input + label;
            resultText.setText(input);
        }
    }

    //����׺���ʽת��Ϊ��׺���ʽ
    private String[] houzhui(String infix) {  //infix ��׺
        String s = "";// ���ڳнӶ�λ�����ַ���
        Stack<String> opStack = new Stack<String>();    // ��������̬ջ,���û�����Ĳ��������д������ڴ洢�����
        Stack<String> postQueue = new Stack<String>();   // ��׺���ʽ��Ϊ�˽���λ���洢Ϊ�������ַ���
        System.out.println("��׺��" + infix);
        for (int i = 0; i < infix.length(); i++)    // ������׺���ʽ
        // indexof�����������ִ��״γ��ֵ�λ�ã�charAt��������indexλ�ô����ַ���
        {
            if ("1234567890.".indexOf(infix.charAt(i)) >= 0) {    // ���������ַ�ֱ�����
                //�жϲ���¼��λ�����������磬��׺���ʽ��234+4*2������ɨ������ַ�����ʱ��s�������൱�������洢����Ϊ3���ַ��Ĳ�������234
                s = "";// ��Ϊ�н��ַ���ÿ�ο�ʼʱ��Ҫ���
                for (; i < infix.length() && "0123456789.".indexOf(infix.charAt(i)) >= 0; i++) {
                    s = s + infix.charAt(i);
                }
                i--;   //���������Է������ַ��Ĵ���
                postQueue.push(s);  // �����ַ�ֱ�Ӽ����׺���ʽ
            } else if ("(".indexOf(infix.charAt(i)) >= 0) {   // ����������
                opStack.push(String.valueOf(infix.charAt(i)));   // ��������ջ
            } else if (")".indexOf(infix.charAt(i)) >= 0) {   // ����������
                while (!opStack.peek().equals("(")) {    // ջ��Ԫ��ѭ����ջ��ֱ������������Ϊֹ
                    postQueue.push(opStack.pop());
                }
                opStack.pop();     //ɾ��������
            } else if ("*%/+-".indexOf(infix.charAt(i)) >= 0)   // ���������
            {
                if (opStack.empty() || "(".contains(opStack.peek())) {   // ��ջΪ�ջ�ջ��Ԫ��Ϊ��������ֱ����ջ
                    opStack.push(String.valueOf(infix.charAt(i)));
                } else {
                    // ��ջ��Ԫ��Ϊ�����ȼ���ͬ�������ʱ,��ջ��Ԫ�س�ջ�����׺���ʽ��,ֱ�����Ϲ���󣬵�ǰ���������ջ
                    boolean rule = ("*%/+-".contains(opStack.peek()) && "+-".indexOf(infix.charAt(i)) >= 0) || ("*%/".contains(opStack.peek()) && "*%/".indexOf(infix.charAt(i)) >= 0);
                    while (!opStack.empty() && rule) {
                        postQueue.push(opStack.peek());  //peek()����������ջ����Ԫ�ص����Ƴ���
                        opStack.pop();
                    }
                    opStack.push(String.valueOf(infix.charAt(i)));   // ��ǰԪ����ջ
                }
            }
        }
        while (!opStack.empty()) {// ����������ջ��ʣ��Ԫ�����γ�ջ�����׺���ʽ
            postQueue.push(opStack.pop());
        }
        //����׺���ʽջת��Ϊ�ַ��������ʽ
        String[] suffix = new String[postQueue.size()];
        for (int i = postQueue.size() - 1; i >= 0; i--) {
            suffix[i] = postQueue.pop();
        }
        System.out.println("��׺��" + Arrays.toString(suffix.clone()));
        return suffix;
    }

    //�������㷽��
    public double kfys(String str) {
        double a = Double.parseDouble(str);
        return Math.sqrt(a);
    }

    //ƽ�����㷽��
    public double pfys(String str) {
        double a = Double.parseDouble(str);
        return Math.pow(a, 2);
    }

    // �����׺���ʽ�����������ս��
    public double Result(String[] suffix) {  //suffix ��׺
        Stack<String> Result = new Stack<>();// ˳��洢��ջ����������Ϊ�ַ���
        int i;
        for (i = 0; i < suffix.length; i++) {
            if ("1234567890.".indexOf(suffix[i].charAt(0)) >= 0) {  //�������֣�ֱ����ջ
                Result.push(suffix[i]);
            } else {    // ����������ַ�����ջ������Ԫ�س�ջ���㲢���������ջ��
                double x, y, n = 0;
                x = Double.parseDouble(Result.pop());   // ˳���ջ���������ַ�������ת��Ϊdouble����
                y = Double.parseDouble(Result.pop());
                switch (suffix[i]) {
                    case "*":
                        n = y * x;
                        break;
                    case "/":
                        if (x == 0) return 000000;
                        else n = y / x;
                        break;
                    case "%":
                        if (x == 0) return 000000;
                        else n = y % x;
                        break;
                    case "-":
                        n = y - x;
                        break;
                    case "+":
                        n = y + x;
                        break;
                }
                Result.push(String.valueOf(n)); // ��������������ջ
            }
        }

        System.out.println("return:" + Result.peek());
        return Double.parseDouble(Result.peek());   // �������ս��
    }

    // ������
    public static void main(String[] args) {
        Calculator a = new Calculator();
    }
}


