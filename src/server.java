
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class server extends javax.swing.JFrame {

    /**
     * Creates new form server
     */
    ServerSocket _serverSocket;
    HashMap clientColl = new HashMap();
    public server() {
        try {
            initComponents();
            _serverSocket = new ServerSocket(2089);
            this.lb_status_server.setText("Server start success!");
            new ClientAccept().start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    class ClientAccept extends Thread {
        public void run () {
            while(true) {
                try {
                    Socket _socket = _serverSocket.accept();
                    String str = new DataInputStream(_socket.getInputStream()).readUTF();
                    if(clientColl.containsKey(str)) {
                        DataOutputStream _dout = new DataOutputStream(_socket.getOutputStream());
                        _dout.writeUTF("You are already register....");
                    } else {
                        clientColl.put(str, _socket);
                        area_listClient.append(str + ":Joined \n");
                        DataOutputStream _dout = new DataOutputStream(_socket.getOutputStream());
                        _dout.writeUTF("");
                        new MsgRead(_socket,str).start();
                        new PrepareClientList().start();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    class MsgRead extends Thread {
        Socket _socket;
        String iD;
        private MsgRead (Socket _s, String i) {
            this._socket = _s;
            this.iD = i;
        }
        public void run() {
            while(!clientColl.isEmpty()) {
                try {
                    String str = new DataInputStream(_socket.getInputStream()).readUTF();
                    if(str.equals("closingclient")) {
                        clientColl.remove(iD);
                        area_listClient.append(iD + ":Remove \n");
                        new PrepareClientList().start();
                        Set key = clientColl.keySet();
                        Iterator itr = key.iterator();
                        while(itr.hasNext()) {
                            String _key = itr.next().toString();
                            if(_key.equalsIgnoreCase(iD)) {
                                try {
                                    new DataOutputStream(((Socket)clientColl.get(_key)).getOutputStream()).writeUTF("XX");
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if(str.contains("1234@@@@")) {
                        str = str.substring(8);
                        StringTokenizer _st = new StringTokenizer(str, ":");
                        String id = _st.nextToken();
                        str = _st.nextToken();
                        try {
                        DataOutputStream _dout = new DataOutputStream(((Socket)clientColl.get(id)).getOutputStream());
                        _dout.writeUTF("<" +iD+ " to You>: " + str);
                        } catch (Exception e) {
                            clientColl.remove(id);
                            area_listClient.append(id + ":Removed");
                            new PrepareClientList().start();
                        }
                    } 
//                    chat all
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class PrepareClientList extends Thread {
         public void run() {
             try{
                 String ids = "";
                 Set key = clientColl.keySet();
                 Iterator itr = key.iterator();
                 while(itr.hasNext()) {
                     String _key = itr.next().toString();
                     ids += _key + ",";
                 }
                 if(ids.length() != 0) {
                     ids = ids.substring(0, ids.length() - 1);
                 }
                 itr = key.iterator();
                 while(itr.hasNext()) {
                     String _key = (String)itr.next();
                     try {
                        DataOutputStream _dout = new DataOutputStream(((Socket)clientColl.get(_key)).getOutputStream());
                        String tamp = "key@@"+ids;
                        _dout.writeUTF(tamp);
                     } catch(Exception e) {
                        clientColl.remove(_key);
                        area_listClient.append(_key + ":Removed");
                     }
                 }
             } catch(Exception e) {
                 e.printStackTrace();
             }
        }
    }
    
    class PrepareFileClientList extends Thread {
        public void run() { 
            try{
                
            }
            catch (Exception e) { e.printStackTrace();}
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lb_status_server = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_listClient = new javax.swing.JTextArea();
        txt_registerClient = new javax.swing.JTextField();
        btn_registerClient = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel1.setText("Status server: ");

        lb_status_server.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_status_server.setForeground(new java.awt.Color(0, 153, 51));
        lb_status_server.setText(".......................................");

        area_listClient.setEditable(false);
        area_listClient.setColumns(20);
        area_listClient.setRows(5);
        jScrollPane1.setViewportView(area_listClient);

        txt_registerClient.setToolTipText("Your ID");
        txt_registerClient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_registerClient.setText("Register Client");
        btn_registerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerClientActionPerformed(evt);
            }
        });

        jLabel2.setText("Your ID ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_registerClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_status_server)))
                        .addComponent(txt_registerClient))
                    .addComponent(jLabel2))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_status_server))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addComponent(txt_registerClient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_registerClient, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_registerClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerClientActionPerformed
        // TODO add your handling code here:
         try {
            String id = txt_registerClient.getText();
            Socket _socket = new Socket("localhost", 2089);
            DataInputStream _din = new DataInputStream(_socket.getInputStream());
            DataOutputStream _dout = new DataOutputStream(_socket.getOutputStream());
            _dout.writeUTF(id);
            String str = new DataInputStream(_socket.getInputStream()).readUTF();
            if (str.equals("You are already register....")) {
                JOptionPane.showMessageDialog(this, "You are already register....\n");
            } else {
                new MyClient(id, _socket).setVisible(true);
                txt_registerClient.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_registerClientActionPerformed

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
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area_listClient;
    private javax.swing.JButton btn_registerClient;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_status_server;
    private javax.swing.JTextField txt_registerClient;
    // End of variables declaration//GEN-END:variables
}
