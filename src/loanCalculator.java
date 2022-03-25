import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class loanCalculator extends JFrame{
    private JTextField loanAmount;
    private JTextField processingCharge;
    private JTextField interestRate;
    private JTextField time;
    private JTextField emi;
    private JRadioButton loan;
    private JRadioButton personalLoan;
    private JRadioButton educationLoan;
    private JButton calculateEMIButton;
    private JButton clearAllButton;
    private JButton exitButton;
    private JPanel home;

    public loanCalculator() {
        setTitle("EMI Calculator");
        setSize(450,350);
        setContentPane(home);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ButtonGroup b1=new ButtonGroup();
       b1.add(loan);
       b1.add(personalLoan);
       b1.add(educationLoan);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loanAmount.setText("");
                processingCharge.setText("");
                interestRate.setText("");
                time.setText("");
                emi.setText("");
            }
        });
        calculateEMIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateEMI();
            }

            private void calculateEMI() {
                String amountSt=loanAmount.getText();
                String processST=processingCharge.getText();
                String interstST=interestRate.getText();
                String timeSt=time.getText();
                String loanType= loanCalculator.this.loan.getText();
                int amount= Integer.parseInt(amountSt);
                int processCharge=Integer.parseInt(processST);
                int interestRate=Integer.parseInt(interstST);
                int time=Integer.parseInt(timeSt);
                if(amount<0) {
                    JOptionPane.showMessageDialog(home, " INVALID AMOUNT Please Enter Loan Amount more than 0",
                            "EMI Calculator", JOptionPane.ERROR_MESSAGE);
                }
               int compountdInterest= (int) Math.pow(processCharge*(1+(interestRate/100)),time);
                int emiamt=(compountdInterest+amount)/(time*12);
                String emiTxt = Integer.toString(emiamt);
                emi.setText(emiTxt);
                sendtodatabase(amountSt,processST,interstST,timeSt,loanType,emiTxt);
            }

            private void sendtodatabase(String amount,String prcessCharge,String intRate,String time,String loanType,String emi)
            {
                final String Db_URL="jdbc:mysql://localhost:3306/emiCalc";
                final String USERNAME="root";
                final String PASSWORD="06yuvraj@SINGH";


                String amountdata = amount;
                String processData = prcessCharge;
                String intdata = intRate;
                String timedata = time;
                String loanData = loanType;
                String emiData = emi;

                // Inserting data using SQL query
                String sql = "insert into emiDetails values('" + amountdata
                        + "'," + processData + ",'" + intdata + "','"+ timedata +"','"+ loanData +"','"+emiData+"')";
//
//        // Connection class object
//        Connection con = null;

                // Try block to check for exceptions
                try {
                    Connection conn = DriverManager.getConnection(Db_URL, USERNAME, PASSWORD);
                    Statement stat = conn.createStatement();

                    int m = stat.executeUpdate(sql);
                    if (m == 1)
                        System.out.println(
                                "inserted successfully : " + sql);
                    else
                        System.out.println("insertion failed");
                }
                catch (Exception ex) {
                    // Display message when exceptions occurs
                    System.err.println(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        loanCalculator l=new loanCalculator();
    }
}
