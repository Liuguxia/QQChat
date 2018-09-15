package cn.itcast.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

//客户端(第三版本)
public class ClientChatMain extends JFrame implements ActionListener, KeyListener {
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

    //客务端端口号
    private static int clientPort;
    //客务端IP
    private static String clientIp;

    static {
        //加载外部配置文件
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(("chat.properties")));
            //为属性赋值
            clientPort=Integer.parseInt(prop.getProperty("clientPort"));
            clientIp=prop.getProperty("clientIp");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        jtf.addKeyListener(this);

        /***************TCP客户端开始******************/
        //1.创建客户端的套接字（尝试连接）
        //2.获取socket通道的输入流
        //3.收取socket通道的输出流
        //4.关闭socket通道
        /***************TCP客户端结束******************/
        try {
            Socket socket = new Socket(clientIp, clientPort);
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
        sendMessage();
    }

    //定义一个方法
    private void sendMessage(){
        String text = jtf.getText();
        text="客户端对服务器说："+text;
        jta.append(text+System.lineSeparator());
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==10){
            //System.out.println(e);
            sendMessage();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //行为

}
