package cn.hncu.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailPanel extends JFrame {
	private JTextField tfdReceiver = null;
	private JTextField tfdMain = null;
	private JTextArea area=null;
	private JButton btnSend = null;
	public MailPanel() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200, 200, 400, 350);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel subLab = new JLabel("这是军街的qq邮箱app");
		subLab.setFont(new Font("aa", Font.BOLD, 30));
		getContentPane().add(subLab);
		JLabel receiverLab = new JLabel("收件人");
		receiverLab.setFont(new Font("aa", Font.ITALIC, 21));
		getContentPane().add(receiverLab);
		//收件人邮箱
		tfdReceiver = new JTextField(20);
		getContentPane().add(tfdReceiver);
		
		JLabel mainLab = new JLabel("邮件主题");
		mainLab.setFont(new Font("aa", Font.ITALIC, 21));
		getContentPane().add(mainLab);
		//邮箱主题
		tfdMain = new JTextField(20);
		getContentPane().add(tfdMain);
		
		JLabel contentLab = new JLabel("邮件内容");
		contentLab.setFont(new Font("aa", Font.ITALIC, 21));
		getContentPane().add(contentLab);
		//邮件内容
		area = new JTextArea(9,25);
		getContentPane().add(area);
		
		//发送按钮
		btnSend = new JButton("发送");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfdReceiver.getText()==null||tfdReceiver.getText().trim().equals("")){
					JOptionPane.showMessageDialog(MailPanel.this, "请输入对方邮箱");
					return;
				}
				
				try {
					Properties p = new Properties();
					p.setProperty("mail.host", "smtp.qq.com");//指定邮件服务器，默认端口号25
					p.setProperty("mail.smtp.auth", "true");
					p.setProperty("mail.transport.protocol", "smtp");
					
					//开启ssl加密
					MailSSLSocketFactory sf =new MailSSLSocketFactory();
					sf.setTrustAllHosts(true);
					p.put("mail.smtp.ssl.enable", "true");
					p.put("mail.smtp.ssl.socketFactory", sf);
					Session session = Session.getDefaultInstance(p, new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							PasswordAuthentication pa = new PasswordAuthentication("1984157253", "ltcxwsgyqryddcci");
							return pa;
						}
					});
					session.setDebug(true);//设置打开调试状态
					
					//声明一个Message对象(代表一封邮件),从session中创建
					MimeMessage msg = new MimeMessage(session);
					//邮件信息封装
					//1发件人
					msg.setFrom(new InternetAddress("1984157253@qq.com"));
					//2收件人
					msg.setRecipient(RecipientType.TO, new InternetAddress(tfdReceiver.getText()) );
					//3邮件内容:主题、内容
					//主题
					msg.setSubject(tfdMain.getText());
					//内容
					msg.setContent(area.getText(),"text/html;charset=utf-8");//发html格式的文本
					//发送动作
					Transport.send(msg);
				} catch (AddressException e1) {
					JOptionPane.showMessageDialog(MailPanel.this, "对方邮箱输入错误,请确认后重新发送");
					return;
				} catch (GeneralSecurityException e1) {
					JOptionPane.showMessageDialog(MailPanel.this, "发送失败");
					return;
				} catch (MessagingException e1) {
					JOptionPane.showMessageDialog(MailPanel.this, "发送失败");
					return;
				}
			}
		});
		getContentPane().add(btnSend);
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new MailPanel();
	}

}
