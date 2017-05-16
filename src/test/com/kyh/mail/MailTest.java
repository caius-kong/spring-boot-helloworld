package com.kyh.mail;

import com.kyh.Application;
import com.kyh.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * # 此处简单模拟发送邮件，但是生产环境下需要从2个方面改进：失败扫描，异步发送
 *
 * 发送失败：
 * 因为各种原因，总会有邮件发送失败的情况，比如：邮件发送过于频繁、网络异常等。
 * 在出现这种情况的时候，我们一般会考虑重新重试发送邮件，会分为以下几个步骤来实现：
 * 1、接收到发送邮件请求，首先记录请求并且入库。
 * 2、调用邮件发送接口发送邮件，并且将发送结果记录入库。
 * 3、启动定时系统扫描时间段内，未发送成功并且重试次数小于3次的邮件，进行再次发送
 *
 * 异步发送：
 * 很多时候邮件发送并不是我们主业务必须关注的结果，比如通知类、提醒类的业务可以允许延时或者失败。这个时候可以采用异步的方式来发送邮件，加快主交易执行速度，在实际项目中可以采用MQ发送邮件相关参数，监听到消息队列之后启动发送邮件。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MailTest {
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

	@Test
	public void sendSimpleMail() throws Exception {
        mailService.sendSimpleMail("1032316751@qq.com", "主题：简单", "这是一封简单邮件！");
	}

	@Test
    public void sendAttachmentsMail() throws Exception{
        String[] filePaths = new String[]{"/Users/kongyunhui/Desktop/a.png"};
        mailService.sendAttachmentsMail("1032316751@qq.com", "主题：附件", "这是一份附件邮件", filePaths);
    }

    @Test
    public void sendInlineMail() throws Exception {
        String rscId = "a";
        String content = "<html><body><img src=\"cid:"+rscId+"\" ></body></html>";
        String rscPath = "/Users/kongyunhui/Desktop/a.png";
        mailService.sendInlineMail("1032316751@qq.com", "主题：嵌入静态资源", content, rscPath, rscId);
    }

    @Test
    public void sendTemplateMail(){
        // 创建邮件正文
        Context context = new Context();
        context.setVariable("username", "孔昀晖");
        context.setVariable("id", "006");
        String htmlContent = templateEngine.process("emailTemplate", context);
        System.out.println(htmlContent);
        mailService.sendHtmlMail("1032316751@qq.com", "主题：模板邮件", htmlContent);
    }
}