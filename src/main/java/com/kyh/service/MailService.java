package com.kyh.service;

/**
 * Created by kongyunhui on 2017/5/16.
 */
public interface MailService {
    void sendSimpleMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String[] filePaths);

    void sendInlineMail(String to, String subject, String content, String rscPath, String rscId);

    void sendHtmlMail(String to, String subject, String content);
}
