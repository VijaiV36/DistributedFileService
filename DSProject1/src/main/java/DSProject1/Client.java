// Name : Vishal Sreeram
// UTA ID: 1001535749
// NetID: vxs5750

// References:
// 1.https://www.geeksforgeeks.org/multi-threaded-chat-application-set-1/
// 2.https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/
// 3.https://www.javadevjournal.com/java/java-copy-file-directory/
// 4.https://github.com/NishadKumar/string-matching-plagiarism-detect/blob/master/PlagiarismDetection/src/DetectPlagiarism.java
// 5.https://www.rgagnon.com/javadetails/java-0490.html
// 6.http://www.avajava.com/tutorials/lessons/whats-a-quick-way-to-tell-if-the-contents-of-two-files-are-identical-or-not.html
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSProject1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Vijai.V
 */
public class Client extends javax.swing.JFrame {
    static String username;
    static InetAddress ip;
    static Socket s;
    static DataOutputStream dos;
    static DataInputStream dis;
    final static int ServerPort = 7654; 
    static int y=0,n;
    int m=0;
    static String d;
    static int count=0,ucount=0;
    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
        // readMessage Thread is used to read any incoming message which is sent to the Client. 
        System.out.println("m is "+m);
        
    Timer timer = new Timer();
              Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        if(msg.equals("already exits")){
                            JOptionPane.showMessageDialog(null,"This username already exits, try again later");
                            System.exit(0);
                        }
                        else if(msg.equals("Welcome "+username)){
                        jTextArea1.append(msg);
                        jTextArea1.append("\nList of files present in directory : \nfile.txt");
                        System.out.println();
                        TimerTask task = new DirWatcher("/Users/vijai/NetBeansProjects/mavenproject1/DSProject1/"+ username, "txt" ) {
      protected void onChange( File file, String action ) {
        // here we code the action on a change
        System.out.println
           ( "File "+ file.getName() +" action: " + action );
        jTextArea1.append("\n"+"File Name :"+ file.getName() +"\nAction Performed: " + action);
        if(action.equals("modify")){
            try {
                dos.writeUTF("modify||"+"/Users/vijai/NetBeansProjects/mavenproject1/DSProject1/"+ username+"||"+file.getName());
            } catch (IOException ex) {
               
            }
        }
        else if(action.equals("delete")){
           try {
                dos.writeUTF("delete||"+username+"||"+file.getName());
            } catch (IOException ex) {
               
            } 
        }
      }
    };
                        timer.schedule( task , new Date(), 1000 );
                        }
                        else if(msg.equals("logout")){
                            System.exit(0);
                        }
                        else if(msg.contains("is requesting to delete the file")){
                            jTextArea1.append(msg);
                            StringTokenizer st = new StringTokenizer(msg, "#");
                            String content=st.nextToken();
                            String user=st.nextToken();
                            String content2=st.nextToken();
                            String fileName=st.nextToken();
                            int max = 10; 
                            int min = 1; 
                            int range = max - min + 1; 
                            int rand = (int)(Math.random() * range) + min; 
                            System.out.println(rand);
                            if(rand%2==0){
                            jTextArea1.append("\nVote is Yes\n");
                           dos.writeUTF("Vote is #Yes#"+fileName+"#"+user);
                            }
                      else{
                            jTextArea1.append("\nVote is No\n");
                           dos.writeUTF("Vote is #No#"+fileName+"#"+user);     
                            }
                           
                        }
                        else if(msg.contains(" has voted ")){
                           jTextArea1.append(msg);
                            StringTokenizer st = new StringTokenizer(msg, "#");
                            String user=st.nextToken();
                            String content2=st.nextToken();
                            String decision=st.nextToken();
                            String file=st.nextToken();
                            ucount++;
                            if(decision.equals("Yes")){
                                count++;
                                jTextArea1.append("\n Yes counts - "+count);
                                jTextArea1.append("\n User count - "+ucount);
                            }
                            else if(decision.equals("No")){
                                count--;
                                jTextArea1.append("\n No counts "+count);
                                jTextArea1.append("\n User count - "+ucount);
                            }
                            
                            if(ucount>1 && count>1){
                                count=0;
                                ucount=0;
                                jTextArea1.append("\nDecision is #delete#"+file);
                                dos.writeUTF("Decision is #delete#"+file);
                            }
                            else if(ucount>1 && count<=1){
                                count=0;
                                ucount=0;
                                jTextArea1.append("\nDecision is #No delete#"+file);
                                dos.writeUTF("Decision is #No delete#"+file);
                            }
                            
                        }
                      else{
                        jTextArea1.append(msg);
                      
                    }
                    } catch (IOException e) { 
      //                  e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
        
        readMessage.start();// Start of the thread 
        
//        if(!jButton1.isEnabled()){
//            listfolders.start();
//        }

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
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Enter Username");

        jButton1.setText("Add Client");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Upload File");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // The Client registers the username here.
        username = jTextField1.getText();
        if(!username.isEmpty())
        {
   try{
       dos.writeUTF(username);// write to the output stream
   }
   catch(Exception e){
       System.out.println(e);
   }}
        else
        {
            JOptionPane.showMessageDialog(null,"Enter a valid Username First");
        }
           
    
//       jButton1.setEnabled(false);
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // The logout process is done at this event, if a user has logged out of the System, an exit action is performed.
    try { 
            String d="logout";
            System.out.println(username);
            
            dos.writeUTF(d);                    // write on the output stream
            jTextArea1.append("\n"+"Client "+jTextField1.getText()+" has been disconnected");
            
            } catch (IOException e) { 
                        e.printStackTrace(); 
            }
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       JFileChooser chooser = new JFileChooser(); // The File Chooser Pop Up Window.
chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
chooser.setAcceptAllFileFilterUsed(false);
  if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
  { 
     jTextArea1.append("\nFile Selected and Uploading...");
  }
  else 
  {
      System.out.println("No Selection ");
  }
  File path = chooser.getSelectedFile();
  Path sourceDirectory = Paths.get(path.toString());
  Path targetDirectory = Paths.get("/Users/vijai/NetBeansProjects/mavenproject1/DSProject1/"+ username+"/"+path.getName());

        try {
            // File is uploaded into the client's directory
            Files.copy(sourceDirectory, targetDirectory,StandardCopyOption.REPLACE_EXISTING);
            jTextArea1.append("\nUpload Complete!");
        } catch (Exception ex) {
          
        }
        try {
            dos.writeUTF(path.toString()+"#"+path.getName());
        }
        catch (Exception ex) {
            
        }
        jTextArea1.append("\nList of files present in directory\n");
        listFilesForFolder(new File(username));
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public void listFilesForFolder(File folder) 
{
    if(folder.list().length>0)
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            jTextArea1.append("\n"+fileEntry.getName());
        }
    }
    else{
        System.out.println("No file found");
    }
}  
    public static void main(String args[]) throws Exception{
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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
        ip = InetAddress.getByName("localhost"); 
        s = new Socket(ip, ServerPort); 
        // obtaining input and output Streams 
        dis = new DataInputStream(s.getInputStream()); 
        dos = new DataOutputStream(s.getOutputStream()); 
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
abstract class DirWatcher extends TimerTask {
  private String path;
  private File filesArray [];
  private HashMap dir = new HashMap();
  private DirFilterWatcher dfw;

  public DirWatcher(String path) {
    this(path, "");
  }

  public DirWatcher(String path, String filter) {
    this.path = path;
    dfw = new DirFilterWatcher(filter);
    filesArray = new File(path).listFiles(dfw);

    // transfer to the hashmap be used a reference and keep the
    // lastModfied value
    for(int i = 0; i < filesArray.length; i++) {
       dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
    }
  }

  public final void run() {
    HashSet checkedFiles = new HashSet();
    filesArray = new File(path).listFiles(dfw);

    // scan the files and check for modification/addition
    for(int i = 0; i < filesArray.length; i++) {
      Long current = (Long)dir.get(filesArray[i]);
      checkedFiles.add(filesArray[i]);
      if (current == null) {
        // new file
        dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
        onChange(filesArray[i], "add");
      }
      else if (current.longValue() != filesArray[i].lastModified()){
        // modified file
        dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
        onChange(filesArray[i], "modify");
      }
    }

    // now check for deleted files
    Set ref = ((HashMap)dir.clone()).keySet();
    ref.removeAll((Set)checkedFiles);
    Iterator it = ref.iterator();
    while (it.hasNext()) {
      File deletedFile = (File)it.next();
      dir.remove(deletedFile);
      onChange(deletedFile, "delete");
    }
  }

  protected abstract void onChange( File file, String action );
}
class DirFilterWatcher implements FileFilter {
  private String filter;

  public DirFilterWatcher() {
    this.filter = "";
  }

  public DirFilterWatcher(String filter) {
    this.filter = filter;
  }

  public boolean accept(File file) {
    if ("".equals(filter)) {
      return true;
    }
    return (file.getName().endsWith(filter));
  }
}
