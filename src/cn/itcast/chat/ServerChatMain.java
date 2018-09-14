package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;

//服务端
public class ServerChatMain extends JFrame{
    //主方法
    public static void main(String[] args) {
        new ServerChatMain();
    }
    //属性
    private JTextArea jta;
    private JScrollPane jsp;
    private JPanel jp;
    private JTextField jtf;
    private JButton jb;

    //初始化属性，使用构造方法
    public ServerChatMain(){
        //初始化组件
        jta=new JTextArea();
        //设置文本域不可编辑
        jta.setEditable(false);
        //把文本框加到滚动条中，实现滚动效果
        jsp=new JScrollPane(jta);
        jp=new JPanel();
        jtf=new JTextField(15);
        jb=new JButton("发送");
        //将文本框和按钮添加到面板中
        jp.add(jtf);
        jp.add(jb);

        //需要将滚动条和面板全部添加到窗体中
        this.add(jsp,BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);

        //设置窗体属性
        this.setTitle("QQ聊天服务端");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    //行为
}
