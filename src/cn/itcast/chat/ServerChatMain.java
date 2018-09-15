package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

//点击回车键实现发送功能，其实回车键在文本框中，故绑定文本框
//服务端
//使用网络编程实现数据的传输（TCP）
public class ServerChatMain extends JFrame implements ActionListener, KeyListener {
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

    //服务端端口号
    private static int serverPort;

    //一个类只会加载一次，使用静态代码块读取外部配置文件
    static {
        Properties prop = new Properties();
        //加载
        try {
            prop.load(new FileInputStream("chat.properties"));
            //给属性赋值
            serverPort=Integer.parseInt(prop.getProperty("serverPort"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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

        //给文本框绑定一个键盘点击事件
        jtf.addKeyListener(this);

        /***************TCP服务端开始******************/
        //1.创建服务端的套接字
        //2.等待客户端连接
        //3.获取socket通道的输入流
        //4.获取socket通道的输出流
        //5.关闭socket通道
        /***************TCP服务端结束******************/


        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
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
        sendMessage();
    }

    //行为
    //定义一个方法，将数据发送到socket通道中
    private void sendMessage(){
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

    @Override
    public void keyPressed(KeyEvent e) {
        //回车键(keyCode=10,keyText=Enter)，测试
        //System.out.println(e);
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            sendMessage();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
