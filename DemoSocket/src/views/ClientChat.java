/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;
import threads.OutputThread;

/**
 *
 * @author Phat
 */
public class ClientChat extends javax.swing.JFrame {
    Socket managerSocket;
    String managerIP;
    int managerPort;
    String staffName;
    BufferedReader br;
    DataOutputStream os;
    OutputThread thread;
    ChatPanel chatPanel;
    
    /**
     * Creates new form ClientChat
     */
    public ClientChat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        staffTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ipTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        portTxt = new javax.swing.JTextField();
        connectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Staff: ");
        jPanel1.add(jLabel1);

        staffTxt.setText("Phat");
        staffTxt.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.add(staffTxt);

        jLabel2.setText("IP: ");
        jPanel1.add(jLabel2);

        ipTxt.setText("127.0.0.1");
        ipTxt.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.add(ipTxt);

        jLabel3.setText("Port: ");
        jPanel1.add(jLabel3);

        portTxt.setText("6334");
        portTxt.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.add(portTxt);

        connectBtn.setBackground(new java.awt.Color(0, 153, 153));
        connectBtn.setForeground(new java.awt.Color(255, 255, 255));
        connectBtn.setText("Connect");
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });
        jPanel1.add(connectBtn);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed
        // TODO add your handling code here:
        managerIP = ipTxt.getText();
        managerPort = Integer.parseInt(portTxt.getText());
        staffName = staffTxt.getText();
        try {
            managerSocket = new Socket(managerIP, managerPort);
            JOptionPane.showMessageDialog(this, "Connection to server establish!");
            if (managerSocket != null) {
                chatPanel = new ChatPanel(managerSocket, staffName, "Manager");
                this.getContentPane().add(chatPanel);
                chatPanel.getLogsTxt().append("Manager is running...\n");
                chatPanel.updateUI();
                OutputThread ot = new OutputThread(managerSocket, chatPanel.getLogsTxt(), staffName, "Manager");
                ot.start();
                br = new BufferedReader(new InputStreamReader(managerSocket.getInputStream()));
                os = new DataOutputStream(managerSocket.getOutputStream());
                os.writeBytes(staffName);
                os.write(13);
                os.write(10);
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to IP " + managerIP + " with port " + managerPort);
        }
    }//GEN-LAST:event_connectBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectBtn;
    private javax.swing.JTextField ipTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField portTxt;
    private javax.swing.JTextField staffTxt;
    // End of variables declaration//GEN-END:variables
}