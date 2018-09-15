package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

//客户端
public class ClientChatMain extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new ClientChatMain();
    }
    //属性
    private JTextArea jta;
    private JScrollPane jsp;
    private JPanel jp;
    private JTextField jtf;
    private JButton jb;
    //输出流
    private BufferedWriter bw=null;

    //初始化属性，使用构造方法
    public ClientChatMain(){
        jta=new JTextArea();
        //设置文本域不可编辑
        jta.setEditable(false);
        //版本一此处错误
        jsp=new JScrollPane(jta);
        jp=new JPanel();
        jtf=new JTextField(15);
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

        //给按钮绑定一个监听事件
        jb.addActionListener(this);

        /***************TCP客户端开始******************/
        //1.创建客户端的套接字（尝试连接）
        //2.获取socket通道的输入流
        //3.收取socket通道的输出流
        //4.关闭socket通道
        /***************TCP客户端结束******************/
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line=null;
            while ((line=br.readLine())!=null){
                jta.append(line+System.lineSeparator());
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text=jtf.getText();
        text="服务端对客户端说："+text;
        jta.append(text+System.lineSeparator());
        try {
            //发送
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    //行为

}
