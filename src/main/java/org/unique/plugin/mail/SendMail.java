package org.unique.plugin.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.unique.common.tools.StringUtils;
import org.unique.web.core.Const;
import org.unique.web.util.BaseKit;

/**
 * send mail tool
 * @author L.cm
 * @date 2013-6-2 下午3:15:55
 */
public class SendMail {

	private static final Logger logger = Logger.getLogger(SendMail.class);

	private static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

	private static final String HOST = Const.CONST_MAP.get("mail.smtp.host"); // 邮箱的smtp服务器
	private static final String FROM = Const.CONST_MAP.get("mail.stmp.username"); // 我的企业邮箱地址
	private static final int PORT = BaseKit.getInt(Const.CONST_MAP.get("mail.smtp.port"), 25); // 邮件端口
	private static final int TIMEOUT = BaseKit.getInt(Const.CONST_MAP.get("mail.smtp.timeout"), 2000); // 邮件超时
	private static final String MAIL_NAME = Const.CONST_MAP.get("mail.stmp.title"); // 邮件发送者
	private static final String PASSWORD = Const.CONST_MAP.get("mail.stmp.pwd"); // 密码
	private static final boolean AUTH = BaseKit.getBoolean(Const.CONST_MAP.get("mail.smtp.auth"), true); // 是否校验
	private static final boolean DEBUG = BaseKit.getBoolean(Const.CONST_MAP.get("mail.smtp.debug"), false);// 是否debug

	private static final int FAILED_RESEND_TIMES = 3; // 重试次数

	private static final int THREAD_SLEEP = 3000; // 休眠时间 threadsleep
	
	/* 函数共用字段 */
	private static Message message = initMessage();
	
	private static final Message initMessage() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.port", PORT);
		props.put("mail.stmp.timeout", TIMEOUT);
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PASSWORD);
			}
		});
		session.setDebug(DEBUG);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(MimeUtility.encodeText(MAIL_NAME) + "<" + FROM + ">"));
		} catch (AddressException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
		return message;
	}

	/**
	 * synchronous sending mail
	 * @param subject
	 * @param content
	 * @param to
	 * @throws MessagingException 
	 */
	private static final synchronized void sendMail(String subject, String content, String... to)
			throws MessagingException {
		to = validMails(to);
		// check filter results
		if (null == to || to.length == 0) {
			return;
		}
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=UTF-8");
		List<Address> list = new ArrayList<Address>();
		for (String mail : to) {
			list.add(new InternetAddress(mail));
		}
		Transport.send(message, list.toArray(new Address[0]));
	}

	/**
	 * check email
	 * @param mails
	 * @return
	 */
	public static String[] validMails(String... mails) {
		// null
		if (null == mails || mails.length == 0) {
			return null;
		}
		// validation email format legitimacy
		Pattern pattern = Pattern.compile(emailAddressPattern);
		List<String> mailList = new ArrayList<String>();
		for (String tmp : mails) {
			if (StringUtils.isEmpty(tmp)) {
				continue;
			}
			Matcher matcher = pattern.matcher(tmp);
			if (matcher.matches()) {
				mailList.add(tmp);
			}
		}
		// just send email to legal address
		if (mailList.size() != mails.length) {
			if (mailList.size() == 0) {
				return null;
			}
			return mailList.toArray(new String[0]);
		}
		return mails;
	}

	/**
	 * send mail thread
	 */
	static final class MailSendThend extends Thread {
		private String subject;
		private String content;
		private String[] mails;

		public MailSendThend(String subject, String content, String[] mails) {
			this.subject = subject;
			this.content = content;
			this.mails = mails;
		}

		@Override
		public void run() {
			int sendTimes = 0; // retry count
			boolean isTryAgain = false, isFirst = true;
			do {
				try {
					while (isFirst || isTryAgain) {
						sendMail(subject, content, mails);
						isTryAgain = isFirst = false;
						Thread.sleep(THREAD_SLEEP);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Thread.sleep(THREAD_SLEEP);
					} catch (Exception e1) {
						logger.error(e1.getMessage(), e1);
					}
					isTryAgain = true;
				}
			} while (sendTimes++ < FAILED_RESEND_TIMES);
		}
	}

	/**
	 * synchronous sending mail
	 * @param subject
	 * @param content
	 * @param to
	 */
	public static void syncSend(String subject, String content, String... to) {
		int sendTimes = 0; // retry count
		boolean isTryAgain = false, isFirst = true;
		do {
			try {
				while (isFirst || isTryAgain) {
					sendMail(subject, content, to);
					isTryAgain = isFirst = false;
					Thread.sleep(THREAD_SLEEP);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(THREAD_SLEEP);
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				isTryAgain = true;
			}
		} while (sendTimes++ < FAILED_RESEND_TIMES);
	}

	/**
	 * asynchronous send mail
	 * @param subject
	 * @param content
	 * @param to
	 */
	public static void asynSend(String subject, String content, String... to) {
		new MailSendThend(subject, content, to).start();
	}

}