package org.example.teaappbackend.mail.sender;
/*

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MailSenderTest {

    private static GreenMail greenMail;
    @Autowired
    private MailSender mailSender;

    @BeforeAll
    public static void setUp(){
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.start();
    }
    @AfterAll
    public static void tearDown() {
        if (greenMail != null) {
            greenMail.stop();
        }
    }

    //todo: починить тест, т.к сообщения приходят на реальное мыло
    @Disabled
    @Test
    public void testSendSimpleMessage() throws Exception {
        String to = "vlad.fox2002@mail.ru";
        String subject = "Test Subject";
        String text = "Test Text";


        mailSender.sendMessage(
                to, subject, text);

        // Wait for the email to arrive

        assertTrue(greenMail.waitForIncomingEmail(10000, 1));

        // Retrieve and assert the email content
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        assertEquals(subject, receivedMessages[0].getSubject());
        assertEquals(text, GreenMailUtil.getBody(receivedMessages[0]));
    }
}*/
