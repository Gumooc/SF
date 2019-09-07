package com.run.service.Impl;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.run.entity.User;
import com.run.service.EmailService;
import com.sun.mail.util.MailSSLSocketFactory;

import net.sf.json.JSONObject;

@Service
public class EmailServiceImpl implements EmailService {
	@Override
	public JSONObject sendmail(User user, String content) {
		 // 1.�������Ӷ���javax.mail.Session
        // 2.�����ʼ����� javax.mail.Message
        // 3.����һ�⼤���ʼ�
        String from = "1456199702@qq.com";// �����˵�������
        String host = "smtp.qq.com"; // ָ�������ʼ�������smtp.qq.com(QQ)|smtp.163.com(����)
 
        Properties properties = System.getProperties();// ��ȡϵͳ����
 
        properties.setProperty("mail.smtp.host", host);// �����ʼ�������
        properties.setProperty("mail.smtp.auth", "true");// ����֤
 
        try {
            //QQ������Ҫ������δ��룬163���䲻��Ҫ
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
 
            
            // 1.��ȡĬ��session����
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("1456199702@qq.com", "eqcigyfycvoihjjb"); // �����������˺š���Ȩ��
                }
            });
 
            // 2.�����ʼ�����
            Message message = new MimeMessage(session);
            // 2.1���÷�����
            message.setFrom(new InternetAddress(from));
            // 2.2���ý�����
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            // 2.3�����ʼ�����
            message.setSubject("�˺ż���");
            // 2.4�����ʼ�����
            
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.�����ʼ�
            Transport.send(message);
            System.out.println("�ʼ��ɹ�����!");
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
}
