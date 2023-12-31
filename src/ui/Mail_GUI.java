package ui;

import java.io.IOException;
import smtp.SMTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import static javax.swing.JOptionPane.showMessageDialog;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Mail_GUI extends JPanel {
    //스킨
    JLabel la_send, la_password, la_receive, la_title,la_file,la_path;
    JLabel line1,line2,line3,line4,line5;

    JPasswordField passwordField;
    JTextField tf_send, tf_receive, tf_title;
    JTextArea content;
    JButton b1, b2;

    String str_send,str_password,str_receive,str_title,str_filepath,str_content;

    public Mail_GUI()
    {
        Color backgroundColor1=new Color(200,200,200,10);
        Color backgroundColor2=new Color(100,100,100,40);


        setLayout(null); //직접배치
        la_send=new JLabel("보내는 사람",JLabel.LEFT);
        la_send.setBounds(20, 3, 60, 30);
        tf_send=new JTextField();
        tf_send.setBounds(100, 6, 410, 21);
        tf_send.setBorder(BorderFactory.createEmptyBorder());
        add(la_send);
        add(tf_send);

        line1=new JLabel();
        line1.setBounds(20, 35, 500, 1);
        line1.setOpaque(true);
        line1.setBackground(backgroundColor2);
        add(line1);

        la_password=new JLabel("비밀번호",JLabel.LEFT);
        la_password.setBounds(20, 38, 50, 30);
        passwordField =new JPasswordField();
        passwordField.setBounds(100,41,410,21);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        add(la_password);
        add(passwordField);

        line2=new JLabel();
        line2.setBounds(20, 70, 500, 1);
        line2.setOpaque(true);
        line2.setBackground(backgroundColor2);
        add(line2);



        la_receive=new JLabel("받는 사람",JLabel.LEFT);
        la_receive.setBounds(20, 73, 50, 30);
        tf_receive=new JTextField();
        tf_receive.setBounds(100, 76, 410, 21);
        tf_receive.setBorder(BorderFactory.createEmptyBorder());
        add(la_receive);
        add(tf_receive);

        line3=new JLabel();
        line3.setBounds(20, 105, 500, 1);
        line3.setOpaque(true);
        line3.setBackground(backgroundColor2);
        add(line3);



        la_title=new JLabel("제목",JLabel.LEFT);
        la_title.setBounds(20, 108, 50, 30);
        tf_title=new JTextField();
        tf_title.setBounds(100, 111, 410, 21);
        tf_title.setBorder(BorderFactory.createEmptyBorder());
        add(la_title);
        add(tf_title);

        line4=new JLabel();
        line4.setBounds(20, 140, 500, 1);
        line4.setOpaque(true);
        line4.setBackground(backgroundColor2);
        add(line4);


        la_file=new JLabel("파일 첨부",JLabel.LEFT);
        la_file.setBounds(20, 144, 50, 30);
        la_path=new JLabel();
        la_path.setBounds(90, 144, 330, 30);
        la_path.setBorder(BorderFactory.createEmptyBorder());
        add(la_file);
        add(la_path);

        JButton deleteFileButton= new JButton("X");
        deleteFileButton.setBorder(BorderFactory.createEmptyBorder(0 , 0, 0 , 0));
        deleteFileButton.setBounds(420,150,20,20);
        deleteFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                la_path.setText("");
            }
        });
        add(deleteFileButton);


        JButton browseButton= new JButton("찾아보기");
        browseButton.setBounds(450,144,80,35);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                int returnValue=fileChooser.showOpenDialog(null);
                if (returnValue==JFileChooser.APPROVE_OPTION){
                    File selectedFile=fileChooser.getSelectedFile();
                    la_path.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        add(browseButton);


        line5=new JLabel();
        line5.setBounds(20, 178, 420, 1);
        line5.setOpaque(true);
        line5.setBackground(backgroundColor2);
        add(line5);

        content=new JTextArea(10,30);
        JScrollPane scrollPane=new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVisible(true);
        scrollPane.setBounds(20, 195, 500, 330);

        add(scrollPane);

        b1=new JButton("보내기");
        b1.setBounds(20,535,80,35);
        add(b1);

        b2=new JButton("취소");
        b2.setBounds(110,535,80,35);
        add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                str_send=tf_send.getText();
                char[] passwordChars= passwordField.getPassword();
                str_password=new String(passwordChars);
                str_receive= tf_receive.getText();
                str_title=tf_title.getText();
                str_filepath=la_path.getText();
                str_content=content.getText();

                String TempContentArr[]= str_content.split("\n");

                boolean isSendEmail = false;
                if(str_send.isEmpty()){
                    isSendEmail=false;
                }

                String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
                Pattern p = Pattern.compile(regex);
                Matcher m1 = p.matcher(str_send);
                if(m1.matches()) {
                    isSendEmail = true;
                }
                int idx1=str_send.indexOf("@");
                String send_domain=str_send.substring(idx1+1);
                if (!send_domain.equals("naver.com")){
                    if (!send_domain.equals("gmail.com")) {
                        isSendEmail = false;
                    }
                }

                if (!isSendEmail){
                    JOptionPane.showMessageDialog(null,"올바른 이메일 형식을 적어주세요.\n (네이버, 구글 계정 도메인만 지원합니다.)");
                }
                else {
                    List<String> toEmails = List.of(str_receive.split(","));

                    SMTPClient smtp = new SMTPClient(str_send, str_password, toEmails, str_title, str_filepath, TempContentArr);
                    try {
                        smtp.SMTPFunc();
                        JOptionPane.showMessageDialog(null, "이메일 전송이 완료되었습니다.");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
