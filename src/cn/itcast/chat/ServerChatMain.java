package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//服务端
//使用网络编程实现数据的传输（TCP）
public class ServerChatMain extends JFrame implements ActionListener {
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
    //输出流
    private BufferedWriter bw=null;

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

        //给发送按钮绑定一个监听点击事件
        jb.addActionListener(this);

        /***************TCP服务端开始******************/
        //1.创建服务端的套接字
        //2.等待客户端连接
        //3.获取socket通道的输入流
        //4.获取socket通道的输出流
        //5.关闭socket通道
        /***************TCP服务端结束******************/


        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket=serverSocket.accept();
            //3.获取socket通道的输入流(输入流实现读取数据，一行一行读)
            //InputStream in=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line=null;
            while ((line=br.readLine())!=null){
                //将读取的数据拼接到文本域中显示
                jta.append(line+System.lineSeparator());
            }
            //4.获取socket通道的输出流（输出流写出数据）
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听事件
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("发送按钮被点击了");
        //1.获取文本框发送的的内容
        String text=jtf.getText();
        //2.拼接收据内容
        text="服务端对客户端说："+text;
        //3.自己也需要显示
        jta.append(text+System.lineSeparator());
        //4.发送
        try {
            bw.write(text);//写进去
            bw.newLine();//换行
            bw.flush();//刷新
            //5.清空文本框内容
            jtf.setText("");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    //行为
}
