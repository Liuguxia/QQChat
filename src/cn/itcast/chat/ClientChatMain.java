package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;

//客户端
public class ClientChatMain extends JFrame{
    public static void main(String[] args) {
        new ClientChatMain();
    }
    //属性
    private JTextArea jta;
    private JScrollPane jsp;
    private JPanel jp;
    private JTextField jtf;
    private JButton jb;

    //初始化属性，使用构造方法
    public ClientChatMain(){
        jta=new JTextArea();
        //设置文本域不可编辑
        jta.setEditable(false);
        jsp=new JScrollPane();
        jsp.add(jta);
        jp=new JPanel();
        jtf=new JTextField(10);
        jb=new JButton("发送");
        jp.add(jtf);
        jp.add(jb);

        this.add(jsp,BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);

        this.setTitle("QQ聊天客户端");
        this.setSize(300,300);
        this.setLocation(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    //行为

}
