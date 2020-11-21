package bank1;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Bank1 extends JFrame implements ActionListener
{
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12;
    JTextField t1,t3,t4,t5,t6;
    JPasswordField t2,t7,t8;
    JButton b1,b2,b3,b4,b5,b6,b7,b9,b10;
    UIManager UI=new UIManager();
    Bank1()
    {                        
        setLayout(null);
        l9=new JLabel("WELCOME TO ATM");
        l1=new JLabel("Enter Card Number");
        l2=new JLabel("Enter Pin");
        t1=new JTextField(20);
        t2=new JPasswordField(20);
        b1=new JButton("Login");
        
        l1.setFont(new Font("System",Font.BOLD,30));
        l2.setFont(new Font("System",Font.BOLD,30));
        l9.setFont(new Font("System",Font.BOLD,40));
        b1.setFont(new Font("System",Font.BOLD,30));
        t1.setFont(new Font("System",Font.PLAIN,30));
        t2.setFont(new Font("System",Font.PLAIN,30));
        
        b1.addActionListener(this);
        l1.setBounds(150, 150, 300, 40);
        t1.setBounds(450, 150, 200, 40);
        l2.setBounds(200, 250, 300, 40);
        t2.setBounds(450, 250, 200, 40);
        l9.setBounds(250 , 50 , 400,40);
        b1.setBounds(300, 350, 250, 50);
        
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(b1);
        add(l9);
        
        setSize(900,600);
        setTitle("LOGIN");        
        setVisible(true);   
        getContentPane().setBackground(new Color(131,202,255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent ae)
    {
        
        if(t1.getText().equals("") || t2.getText().equals(""))
        {
            UI.put("OptionPane.background", new Color(242,179,160));
            UI.put("Panel.background",new Color(242,179,160));
            JOptionPane.showMessageDialog(null,"Please Enter Card number and Pin");                
            return;
        }
        
        if(ae.getSource()==b1 || ae.getSource()==b4 || ae.getSource()==b5 || ae.getSource()==b7 || ae.getSource()==b10)
        {
        try
        {                        
            Class.forName("com.mysql.jdbc.Driver");
            Connection c=DriverManager.getConnection("jdbc:mysql://localhost/BANK","root","root");
            Statement s=c.createStatement();   
            
            ResultSet rs=s.executeQuery("select * from user where CARD_NO ='"+t1.getText()+"'");
            
            String get_pin="";
            String get_username="";
            int get_account_no = 0;
            double get_balance = 0;
                                    
            while(rs.next())
            {      
                get_username=rs.getString("USERNAME");
                get_pin = rs.getString("PIN");
                get_account_no = rs.getInt("ACCOUNT_NO");
                get_balance = rs.getDouble("BALANCE");
            }
            
            if(ae.getSource()==b4)
            {
                Double b=Double.parseDouble(t3.getText());
                get_balance=get_balance - b;
                                                
                if(get_balance>=0)
                {
                    s.executeUpdate("update user set BALANCE='"+get_balance+"' where CARD_NO='"+t1.getText()+"'");
                    UI.put("OptionPane.background", new Color(220,255,123));
                    UI.put("Panel.background",new Color(220,255,123));
                    JOptionPane.showMessageDialog(null,"Rs."+b+" withdrawn succesfully");                                    
                }
                else
                {
                    get_balance=get_balance + b;
                    UI.put("OptionPane.background", new Color(242,179,160));
                    UI.put("Panel.background",new Color(242,179,160));
                    JOptionPane.showMessageDialog(null,"Sorry, you have insufficient balance");                                    
                }
            }
            
            if(ae.getSource()==b5)
            {
                Double b=Double.parseDouble(t4.getText());
                get_balance=get_balance + b;                                                
                s.executeUpdate("update user set BALANCE='"+get_balance+"' where CARD_NO='"+t1.getText()+"'");
                UI.put("OptionPane.background", new Color(220,255,123));
                UI.put("Panel.background",new Color(220,255,123));
                JOptionPane.showMessageDialog(null,"Rs."+b+" deposited succesfully");
            }
            
            if(ae.getSource()==b7)
            {
                Double a=Double.parseDouble(t5.getText());
                Double b=Double.parseDouble(t6.getText());
                get_balance=get_balance - b;                
                double get_balance2=0;
                ResultSet rs2=s.executeQuery("select * from user where ACCOUNT_NO ='"+t5.getText()+"'");
                while(rs2.next())
                {
                    get_balance2=rs2.getDouble("BALANCE");
                }
                if(get_balance >= 0)
                {                    
                    get_balance2=get_balance2 + b;                                                
                    s.executeUpdate("update user set BALANCE='"+get_balance+"' where CARD_NO='"+t1.getText()+"'");
                    s.executeUpdate("update user set BALANCE='"+get_balance2+"' where ACCOUNT_NO='"+a+"'");
                    UI.put("OptionPane.background", new Color(220,255,123));
                    UI.put("Panel.background",new Color(220,255,123));
                    JOptionPane.showMessageDialog(null,"Rs."+b+" sent succesfully");
                }
                else
                {
                    get_balance = get_balance + b;
                    UI.put("OptionPane.background", new Color(242,179,160));
                    UI.put("Panel.background",new Color(242,179,160));
                    JOptionPane.showMessageDialog(null,"Sorry , you have insufficient balance");
                }
            }
            if(ae.getSource()==b10)
            {
                if(t7.getText().equals("") || t8.getText().equals(""))
                {
                    UI.put("OptionPane.background", new Color(242,179,160));
                    UI.put("Panel.background",new Color(242,179,160));
                    JOptionPane.showMessageDialog(null,"Enter PIN");
                    return;
                }
                if(t7.getText().equals(t8.getText()))
                {
                    s.executeUpdate("update user set PIN='"+t7.getText()+"' where CARD_NO='"+t1.getText()+"'");
                    UI.put("OptionPane.background", new Color(220,255,123));
                    UI.put("Panel.background",new Color(220,255,123));
                    JOptionPane.showMessageDialog(null,"PIN changed succesfully");
                }
                else
                {
                    UI.put("OptionPane.background", new Color(242,179,160));
                    UI.put("Panel.background",new Color(242,179,160));
                    JOptionPane.showMessageDialog(null,"Check PIN and enter again");
                    return;
                }
            }
            
            if(get_pin.equals(t2.getText()))
            {
                dispose();  
                JFrame frame=new JFrame("Home");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(900,600);
                frame.getContentPane().setBackground(new Color(131,202,255));
                
                l3=new JLabel("Account Number:"+String.valueOf(get_account_no));                
                l4=new JLabel("Balance:"+String.valueOf(get_balance));
                l12=new JLabel("WELCOME "+get_username);
                                                
                b2=new JButton("WITHDRAW");
                b3=new JButton("DEPOSIT");
                b6=new JButton("SEND MONEY");                 
                b9=new JButton("CHANGE PIN");
                                
                b2.addActionListener(this);
                b3.addActionListener(this);
                b6.addActionListener(this);
                b9.addActionListener(this);
                                
                l3.setFont(new Font("System",Font.BOLD,30));                
                l4.setFont(new Font("System",Font.BOLD,30));
                l12.setFont(new Font("System",Font.BOLD,40));
                                
                b2.setFont(new Font("System",Font.BOLD,30));
                b3.setFont(new Font("System",Font.BOLD,30));
                b6.setFont(new Font("System",Font.BOLD,30));                
                b9.setFont(new Font("System",Font.BOLD,30));
                
                l3.setBounds(250, 100, 400, 40);                
                l4.setBounds(325, 150,300,40);
                l12.setBounds(300,50,400,45);
                
                b2.setBounds(200,300,250,40);
                b3.setBounds(500,300,250,40);
                b6.setBounds(200,400,250,40);                
                b9.setBounds(500,400,250,40);
                                
                frame.add(l3);                
                frame.add(l4);
                frame.add(l12);
                
                frame.setLayout(null);
                frame.add(b2);
                frame.add(b3);
                frame.add(b6);                
                frame.add(b9);                                                                
            }
            else
            {
                UI.put("OptionPane.background", new Color(242,179,160));
                UI.put("Panel.background",new Color(242,179,160));
                JOptionPane.showMessageDialog(null,"Card Number or Pin is wrong");                
            }
            rs.close();
            s.close();
            c.close();
        }
        catch(Exception e)
        {
            System.out.println("Exception occured");
        }
        }
        else if(ae.getSource()==b2)
        {           
            dispose();
            JFrame frame2=new JFrame("WITHDRAW");
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setVisible(true);
            frame2.setSize(900,600);
            frame2.getContentPane().setBackground(new Color(131,202,255));
            
            l5=new JLabel("Enter Amount To Withdraw");
            
            t3=new JTextField(20);
            
            b4=new JButton("WITHDRAW");
            b4.addActionListener(this);
            
            l5.setBounds(50, 50, 400, 40);
            
            t3.setBounds(500,50,200,40);
            
            b4.setBounds(300,200,300,40);
            
            l5.setFont(new Font("System",Font.BOLD,30));
            
            t3.setFont(new Font("System",Font.PLAIN,30));
            
            b4.setFont(new Font("System",Font.BOLD,30));
            
            frame2.setLayout(null);
            frame2.add(l5);
            frame2.add(t3);
            frame2.add(b4);
        }
        else if(ae.getSource()==b3)
        {
            JFrame frame3=new JFrame("DEPOSIT");
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setVisible(true);
            frame3.setSize(900,600);
            frame3.getContentPane().setBackground(new Color(131,202,255));
            
            l6=new JLabel("Enter Amount to Deposit");
            
            t4=new JTextField(20);
            
            b5=new JButton("DEPOSIT");
            b5.addActionListener(this);
            
            l6.setBounds(50, 50, 400, 40);
            
            t4.setBounds(500,50,200,40);
            
            b5.setBounds(300,200,300,40);
            
            l6.setFont(new Font("System",Font.BOLD,30));
            
            t4.setFont(new Font("System",Font.PLAIN,30));
            
            b5.setFont(new Font("System",Font.BOLD,30));
            
            frame3.setLayout(null);
            frame3.add(l6);
            frame3.add(t4);
            frame3.add(b5);
        }
        else if(ae.getSource()==b6)
        {
            JFrame frame4=new JFrame("SEND MONEY");
            frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame4.setVisible(true);
            frame4.setSize(900,600);
            frame4.getContentPane().setBackground(new Color(131,202,255));
            
            l7=new JLabel("Enter Account number of receiver");
            l8=new JLabel("Enter amount");
            
            t5=new JTextField(20);
            t6=new JTextField(20);
            
            b7=new JButton("SEND");
            b7.addActionListener(this);
            
            l7.setBounds(50, 50, 500, 40);
            l8.setBounds(50,100,400,40);
            t5.setBounds(600,50,200,40);
            t6.setBounds(600,100,200,40);
            b7.setBounds(300,300,300,40);
            
            l7.setFont(new Font("System",Font.BOLD,30));
            l8.setFont(new Font("System",Font.BOLD,30));
            b7.setFont(new Font("System",Font.BOLD,30));
            t5.setFont(new Font("System",Font.BOLD,30));
            t6.setFont(new Font("System",Font.BOLD,30));
            
            frame4.setLayout(null);
            frame4.add(l7);
            frame4.add(l8);
            frame4.add(t5);
            frame4.add(t6);
            frame4.add(b7);
        }
        
        else if(ae.getSource()==b9)
        {
            JFrame frame6=new JFrame("CHANGE PIN");
            frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame6.setVisible(true);
            frame6.setSize(900,600);
            frame6.getContentPane().setBackground(new Color(131,202,255));
            
            l10=new JLabel("Enter New PIN");
            l11=new JLabel("Re-Enter New PIN");
            
            t7=new JPasswordField(20);
            t8=new JPasswordField(20);
            
            b10=new JButton("CHANGE PIN");
            b10.addActionListener(this);
            
            l10.setFont(new Font("System",Font.BOLD,30));
            l11.setFont(new Font("System",Font.BOLD,30));
            b10.setFont(new Font("System",Font.BOLD,30));
            t7.setFont(new Font("System",Font.BOLD,30));
            t8.setFont(new Font("System",Font.BOLD,30));
            
            l10.setBounds(50, 50, 500, 40);
            l11.setBounds(50,100,400,40);
            t7.setBounds(550,50,200,40);
            t8.setBounds(550,100,200,40);
            b10.setBounds(300,200,400,40);
            
            frame6.setLayout(null);
            frame6.add(l10);
            frame6.add(l11);
            frame6.add(t7);
            frame6.add(t8);
            frame6.add(b10);
        }
    }
    
    public static void main(String[] args)
    {
        Bank1 ba=new Bank1();        
    }
    
}

