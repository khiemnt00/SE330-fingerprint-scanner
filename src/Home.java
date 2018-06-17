
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hardware.FingerData;
import hardware.FingerHW;
import httpserivce.HttpService;
import httpserivce.responemodel.AttendenceClass;
import httpserivce.responemodel.CreateClassRespone;
import httpserivce.responemodel.GetAllStudentResponse;
import httpserivce.responemodel.GetClassAllResponse;
import httpserivce.responemodel.GetCurrentRollRespone;
import httpserivce.responemodel.GetRollReportResponse;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HYU81HC
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public List<AttendenceClass> rootClasses=new ArrayList<AttendenceClass>();
    public Home() {
        initComponents();
        showdate();
        showtime();
            // Runs outside of the Swing UI thread
        new Thread(new Runnable() {
          public void run() {
              // Runs inside of the Swing UI thread
              SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                Date d = new Date();
//                SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy   hh:mm:ss");
//                 DefaultTableModel model = (DefaultTableModel) rollTable.getModel();
//                model.addRow(new Object[]{"Column 1", "Column 2", s.format(d)});

                }
              });

          }
        }).start();
        
        new Thread(new Runnable() {
            public void run() {
      //        connectHardware();
            }
        }).start();
        GetAllClass();
//        InitStudentByClassID(9);
//        GetCurrentRollByClassID(9);
       
    }
    
    void GetAllClass(){
            HttpService httpsv=new HttpService();
        try {
            String rsp=new String(httpsv.GetClassAll());
            Gson gson = new GsonBuilder().create();
            GetClassAllResponse r=new GetClassAllResponse();
            r=gson.fromJson(rsp, GetClassAllResponse.class);
            System.out.println(r.toString());
            DefaultTableModel model = (DefaultTableModel) tb_class.getModel();
            rootClasses.clear();
            for (int i=0;i<r.getData().getClasses().size();i++){
                rootClasses.add(r.getData().getClasses().get(i));
                model.addRow(new Object[]{r.getData().getClasses().get(i).getName(), r.getData().getClasses().get(i).getCode(), r.getData().getClasses().get(i).getSemester(),r.getData().getClasses().get(i).getYear()});
                dashboard_combox_chooseclass.addItem(r.getData().getClasses().get(i).getCode()+ " HK" + r.getData().getClasses().get(i).getSemester()+ " " + r.getData().getClasses().get(i).getYear());
                student_combobox_class.addItem(r.getData().getClasses().get(i).getCode()+ " HK" + r.getData().getClasses().get(i).getSemester()+ " " + r.getData().getClasses().get(i).getYear());
                statistic_class_combobox.addItem(r.getData().getClasses().get(i).getCode()+ " HK" + r.getData().getClasses().get(i).getSemester()+ " " + r.getData().getClasses().get(i).getYear());
            }
//            dashboard_combox_chooseclass.setSelectedIndex(-1);
//                combobox_classidstudent.setSelectedIndex(-1);
//                statistic_class_combobox.setSelectedIndex(-1);

        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    void RenderCurrentRollByClassID(int class_id){
            HttpService httpsv=new HttpService();
            System.out.println("render current roll:"+String.valueOf(class_id));
            try {
            String rsp=httpsv.GetCurentRolls(class_id);
            Gson gson = new GsonBuilder().create();
            GetCurrentRollRespone r=new GetCurrentRollRespone();
            r=gson.fromJson(rsp, GetCurrentRollRespone.class);

            DefaultTableModel model = (DefaultTableModel) rollTable.getModel();
            model.setRowCount(0);
            for (int i=0;i<r.getData().getRolls().size();i++){
                model.addRow(new Object[]{r.getData().getRolls().get(i).getName(), r.getData().getRolls().get(i).getMssv(),"Date: " + r.getData().getRolls().get(i).getTime().substring(5,10)+" | Time: "+r.getData().getRolls().get(i).getTime().substring(12,19)});
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
        void RenderStudentByClassID(int class_id){
             HttpService httpsv=new HttpService();
            try {
            String rsp=httpsv.GetAllStudent(class_id);
            Gson gson = new GsonBuilder().create();
            GetAllStudentResponse r=new GetAllStudentResponse();
            r=gson.fromJson(rsp, GetAllStudentResponse.class);
            DefaultTableModel model = (DefaultTableModel) student_tb_student.getModel();
            model.setRowCount(0);
            for (int i=0;i<r.getData().getStudents().size();i++){
                model.addRow(new Object[]{r.getData().getStudents().get(i).getName(), r.getData().getStudents().get(i).getMssv(), r.getData().getStudents().get(i).getAutheticated()});
            }

        }
        catch(Exception e){
            System.err.println(e);
        }
        }
        
    GetAllStudentResponse GetAllStudentByClassID(int class_id){
             HttpService httpsv=new HttpService();
            try {
            String rsp=httpsv.GetAllStudent(9);
            Gson gson = new GsonBuilder().create();
            GetAllStudentResponse r=new GetAllStudentResponse();
            r=gson.fromJson(rsp, GetAllStudentResponse.class);
//            DefaultTableModel model = (DefaultTableModel) tb_student.getModel();
//            for (int i=0;i<r.getData().getStudents().size();i++){
//                model.addRow(new Object[]{r.getData().getStudents().get(i).getName(), r.getData().getStudents().get(i).getMssv(), r.getData().getStudents().get(i).getAutheticated()});
//            }
            return r;

        }
        catch(Exception e){
            System.err.println(e);
        }
            
            return null;
        }
        int CreateClass(AttendenceClass attendenceClass){
             HttpService httpsv=new HttpService();
            try {
            String rsp=httpsv.CreateClass(attendenceClass);
            Gson gson = new GsonBuilder().create();
            CreateClassRespone r=new CreateClassRespone();
            r=gson.fromJson(rsp, CreateClassRespone.class);
            return r.getRcode();

        }
        catch(Exception e){
            System.err.println(e);
        }
            return 0;
      }
    
        void StatisticGetRollTimeStudent(int class_id,String mssv){
            System.out.println(String.valueOf(class_id));
            System.out.println(String.valueOf(mssv));
            HttpService httpsv=new HttpService();
            try {
            String rsp=httpsv.GetRolltimesStudent(class_id,mssv);
            Gson gson = new GsonBuilder().create();
            GetCurrentRollRespone r=new GetCurrentRollRespone();
            r=gson.fromJson(rsp, GetCurrentRollRespone.class);
            statistic_time_joined.setText("");
            for (int i=0;i<r.getData().getRolls().size();i++){
                statistic_time_joined.append("Date: " + r.getData().getRolls().get(i).getTime().substring(5,10)+" | Time: "+r.getData().getRolls().get(i).getTime().substring(12,19)+"\n");
            }

        }
        catch(Exception e){
            System.err.println(e);
        }
        }
        
    GetRollReportResponse StatisticGetRollReport(int class_id){
        System.out.println(String.valueOf(class_id));
        HttpService httpsv=new HttpService();
        try {
            String rsp=httpsv.GetRollReport(class_id);
            Gson gson = new GsonBuilder().create();
            GetRollReportResponse r=new GetRollReportResponse();
            r=gson.fromJson(rsp, GetRollReportResponse.class);

            return r;

        }
        catch(Exception e){
            System.err.println(e);
        }
        
        return null;
    }
    void showdate(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        lb_date.setText(s.format(d));
    }
    void showtime(){
        new Timer(0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                lb_time.setText(s.format(d));
            }
        
        }).start();
    }
    void connectHardware(){
        FingerHW fingerHW=new FingerHW();
        fingerHW.connect();

        try {
            fingerHW.serial.getSerialPort().setComPortTimeouts(fingerHW.serial.getSerialPort().TIMEOUT_READ_BLOCKING, 50, 0);
            InputStreamReader is = new InputStreamReader(fingerHW.serial.getSerialPort().getInputStream());
            BufferedReader reader = new BufferedReader(is);
            int i=0;
            while (true) {
                String nextLine;
                try {
                    nextLine = reader.readLine();
                    System.out.println(nextLine);
                    System.out.println(i++);
                    Gson gson = new GsonBuilder().create(); 
                    FingerData fingerData = gson.fromJson(nextLine, FingerData.class);
                    System.out.println("parse:"+fingerData.toString());
                } catch (Exception e) {}
            }
        } 
        catch (Exception e) { e.printStackTrace(); }
//                Date d = new Date();
//                SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy   hh:mm:ss");
//                 DefaultTableModel model = (DefaultTableModel) rollTable.getModel();
//                model.addRow(new Object[]{"Column 1", "Column 2", s.format(d)});

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frame_scanfinger = new javax.swing.JFrame();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        lb_statusicon = new javax.swing.JLabel();
        lb_statustxt = new javax.swing.JLabel();
        lb_canclescan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_name = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_dashboard = new rojerusan.RSButtonIconI();
        btn_class = new rojerusan.RSButtonIconI();
        btn_student = new rojerusan.RSButtonIconI();
        btn_settings = new rojerusan.RSButtonIconI();
        btn_statistic = new rojerusan.RSButtonIconI();
        pnl_menu = new javax.swing.JPanel();
        pnl_dashboard = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_start = new javax.swing.JLabel();
        togbtn_start = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        lb_status = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rollTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        lb_date = new javax.swing.JLabel();
        dashboard_combox_chooseclass = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lb_time = new javax.swing.JLabel();
        pnl_class = new javax.swing.JPanel();
        btn_addclass = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        btn_importclass = new javax.swing.JButton();
        txt_nameclass = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_classcode = new javax.swing.JTextField();
        combobox_semester = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        combobox_year = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_class = new javax.swing.JTable();
        btn_editclass = new javax.swing.JButton();
        btn_deleteclass = new javax.swing.JButton();
        pnl_student = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_namestudent = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_mssv = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btn_scan = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        btn_addstudent = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        student_tb_student = new javax.swing.JTable();
        btn_deletestudent = new javax.swing.JButton();
        btn_editstudent = new javax.swing.JButton();
        student_combobox_class = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnl_settings = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jButton8 = new javax.swing.JButton();
        pnl_statistic = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        statistic_class_combobox = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        statistic_tb_student = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnl_infostudent = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        statistic_time_joined = new javax.swing.JTextArea();
        lb_namefinal = new javax.swing.JLabel();
        lb_studentidfinal = new javax.swing.JLabel();
        lb_classidfinal = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 78, 146));
        kGradientPanel2.setkStartColor(new java.awt.Color(0, 4, 40));

        lb_statusicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_statusicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/press-your-finger-fingerprint-icon-256.png"))); // NOI18N

        lb_statustxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lb_statustxt.setForeground(new java.awt.Color(255, 255, 255));
        lb_statustxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_statustxt.setText("Press your finger");

        lb_canclescan.setText("Cancle");
        lb_canclescan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lb_canclescanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lb_canclescan))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lb_statustxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_statusicon, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_statusicon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_statustxt)
                .addGap(18, 18, 18)
                .addComponent(lb_canclescan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout frame_scanfingerLayout = new javax.swing.GroupLayout(frame_scanfinger.getContentPane());
        frame_scanfinger.getContentPane().setLayout(frame_scanfingerLayout);
        frame_scanfingerLayout.setHorizontalGroup(
            frame_scanfingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        frame_scanfingerLayout.setVerticalGroup(
            frame_scanfingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(250, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setkEndColor(new java.awt.Color(28, 181, 224));
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 0, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-fingerprint-filled-50.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WELCOME,");

        lb_name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lb_name.setForeground(new java.awt.Color(255, 255, 255));
        lb_name.setText("Tan");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btn_dashboard.setBackground(new java.awt.Color(102, 0, 255));
        btn_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-speed-100.png"))); // NOI18N
        btn_dashboard.setText("Dashboard");
        btn_dashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        btn_class.setBackground(new java.awt.Color(204, 0, 255));
        btn_class.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-class-100.png"))); // NOI18N
        btn_class.setText("Class");
        btn_class.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classActionPerformed(evt);
            }
        });

        btn_student.setBackground(new java.awt.Color(255, 0, 102));
        btn_student.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-student-male-80.png"))); // NOI18N
        btn_student.setText("Student");
        btn_student.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_studentActionPerformed(evt);
            }
        });

        btn_settings.setBackground(new java.awt.Color(0, 0, 204));
        btn_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-settings-filled-100.png"))); // NOI18N
        btn_settings.setText("Settings");
        btn_settings.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_settingsActionPerformed(evt);
            }
        });

        btn_statistic.setBackground(new java.awt.Color(51, 255, 204));
        btn_statistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-ecg-filled-50.png"))); // NOI18N
        btn_statistic.setText("Statistic");
        btn_statistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_statisticActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel1))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_name))))
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_class, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(btn_student, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_settings, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(btn_statistic, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(44, 44, 44))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lb_name))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_student, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btn_statistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_settings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jPanel1.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 500));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 500));

        pnl_menu.setPreferredSize(new java.awt.Dimension(750, 500));
        pnl_menu.setLayout(new java.awt.CardLayout());

        pnl_dashboard.setBackground(new java.awt.Color(255, 255, 255));
        pnl_dashboard.setMinimumSize(new java.awt.Dimension(750, 500));
        pnl_dashboard.setPreferredSize(new java.awt.Dimension(750, 500));

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        lb_start.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lb_start.setText("Start roll-call");

        togbtn_start.setBackground(new java.awt.Color(255, 0, 0));
        togbtn_start.setForeground(new java.awt.Color(255, 255, 255));
        togbtn_start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-play-filled-30.png"))); // NOI18N
        togbtn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togbtn_startActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(togbtn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lb_start)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lb_start))
                    .addComponent(togbtn_start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Status:");

        lb_status.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lb_status.setForeground(new java.awt.Color(51, 255, 51));
        lb_status.setText("Ready");

        rollTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Student ID", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(rollTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel9.setText("Time:");

        lb_date.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        dashboard_combox_chooseclass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dashboard_combox_chooseclass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dashboard_combox_chooseclassItemStateChanged(evt);
            }
        });
        dashboard_combox_chooseclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboard_combox_chooseclassActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Choose Class :");

        lb_time.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        javax.swing.GroupLayout pnl_dashboardLayout = new javax.swing.GroupLayout(pnl_dashboard);
        pnl_dashboard.setLayout(pnl_dashboardLayout);
        pnl_dashboardLayout.setHorizontalGroup(
            pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9)
                        .addGap(27, 27, 27)
                        .addComponent(lb_date)
                        .addGap(18, 18, 18)
                        .addComponent(lb_time)
                        .addContainerGap(593, Short.MAX_VALUE))
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dashboard_combox_chooseclass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_dashboardLayout.setVerticalGroup(
            pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lb_date)
                    .addComponent(lb_time))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lb_status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(dashboard_combox_chooseclass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pnl_menu.add(pnl_dashboard, "card2");

        pnl_class.setBackground(new java.awt.Color(255, 255, 255));
        pnl_class.setMinimumSize(new java.awt.Dimension(750, 500));
        pnl_class.setPreferredSize(new java.awt.Dimension(750, 500));

        btn_addclass.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btn_addclass.setText("Add");
        btn_addclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addclassActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Add Class:");

        btn_importclass.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btn_importclass.setText("Import");
        btn_importclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importclassActionPerformed(evt);
            }
        });

        txt_nameclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameclassActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel12.setText("Name:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel13.setText("Code:");

        txt_classcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_classcodeActionPerformed(evt);
            }
        });

        combobox_semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        combobox_semester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_semesterActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel14.setText("Semester:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel15.setText("Year:");

        combobox_year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017-2018", "2018-2019", "2019-2020", "2020-2021" }));

        tb_class.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "ClassID", "Semester", "Year"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_class.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_classMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_class);

        btn_editclass.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btn_editclass.setText("Edit");
        btn_editclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editclassActionPerformed(evt);
            }
        });

        btn_deleteclass.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btn_deleteclass.setText("Delete");
        btn_deleteclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteclassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_classLayout = new javax.swing.GroupLayout(pnl_class);
        pnl_class.setLayout(pnl_classLayout);
        pnl_classLayout.setHorizontalGroup(
            pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_classLayout.createSequentialGroup()
                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_classLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_classLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_classLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel6))
                            .addGroup(pnl_classLayout.createSequentialGroup()
                                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_classLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel12))
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnl_classLayout.createSequentialGroup()
                                        .addComponent(txt_nameclass, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45))
                                    .addGroup(pnl_classLayout.createSequentialGroup()
                                        .addComponent(txt_classcode)
                                        .addGap(135, 135, 135)))
                                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addGap(33, 33, 33)
                                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combobox_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combobox_semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnl_classLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(btn_addclass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_editclass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_deleteclass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_importclass))))
                    .addGroup(pnl_classLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnl_classLayout.setVerticalGroup(
            pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_classLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nameclass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(combobox_semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_classcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(combobox_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addclass)
                    .addComponent(btn_importclass)
                    .addComponent(btn_editclass)
                    .addComponent(btn_deleteclass))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pnl_menu.add(pnl_class, "card3");

        pnl_student.setBackground(new java.awt.Color(255, 255, 255));
        pnl_student.setMinimumSize(new java.awt.Dimension(750, 500));
        pnl_student.setPreferredSize(new java.awt.Dimension(750, 500));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setText("Add Student");

        txt_namestudent.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel18.setText("Name:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel19.setText("Mssv:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel20.setText("ClassID");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btn_scan.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_scan.setText("Scan");
        btn_scan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_scanActionPerformed(evt);
            }
        });

        btn_addstudent.setText("Add");
        btn_addstudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addstudentActionPerformed(evt);
            }
        });

        student_tb_student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Mssv", "Verified"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        student_tb_student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                student_tb_studentMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(student_tb_student);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btn_deletestudent.setText("Delete");
        btn_deletestudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletestudentActionPerformed(evt);
            }
        });

        btn_editstudent.setText("Edit");
        btn_editstudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editstudentActionPerformed(evt);
            }
        });

        student_combobox_class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_combobox_classActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel5.setText("Verify");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Student list");

        javax.swing.GroupLayout pnl_studentLayout = new javax.swing.GroupLayout(pnl_student);
        pnl_student.setLayout(pnl_studentLayout);
        pnl_studentLayout.setHorizontalGroup(
            pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_studentLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_studentLayout.createSequentialGroup()
                        .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(student_combobox_class, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_mssv)))
                    .addGroup(pnl_studentLayout.createSequentialGroup()
                        .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_addstudent)
                            .addComponent(jLabel5))
                        .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_studentLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btn_editstudent)
                                .addGap(18, 18, 18)
                                .addComponent(btn_deletestudent))
                            .addGroup(pnl_studentLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btn_scan)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel17)
                    .addGroup(pnl_studentLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_namestudent, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(21, 21, 21))
        );
        pnl_studentLayout.setVerticalGroup(
            pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_studentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_studentLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addGroup(pnl_studentLayout.createSequentialGroup()
                        .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_studentLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(45, 45, 45)
                                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_namestudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txt_mssv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(student_combobox_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_scan)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_addstudent)
                                    .addComponent(btn_editstudent)
                                    .addComponent(btn_deletestudent)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pnl_menu.add(pnl_student, "card4");

        pnl_settings.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setText("Change Password");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel23.setText("Current Password");

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel24.setText("New Password");

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel25.setText("Retype Password");

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jButton8.setBackground(new java.awt.Color(255, 0, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-exit-26.png"))); // NOI18N
        jButton8.setText("Logout\n");

        javax.swing.GroupLayout pnl_settingsLayout = new javax.swing.GroupLayout(pnl_settings);
        pnl_settings.setLayout(pnl_settingsLayout);
        pnl_settingsLayout.setHorizontalGroup(
            pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnl_settingsLayout.createSequentialGroup()
                                    .addComponent(jLabel23)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_settingsLayout.createSequentialGroup()
                                    .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel25)
                                        .addComponent(jLabel24))
                                    .addGap(21, 21, 21)
                                    .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                        .addComponent(jTextField10))))
                            .addComponent(jButton8))
                        .addGap(0, 19, Short.MAX_VALUE))))
        );
        pnl_settingsLayout.setVerticalGroup(
            pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        pnl_menu.add(pnl_settings, "card5");

        pnl_statistic.setBackground(new java.awt.Color(255, 255, 255));
        pnl_statistic.setPreferredSize(new java.awt.Dimension(750, 500));

        jLabel7.setText("ClassID");

        statistic_class_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statistic_class_comboboxItemStateChanged(evt);
            }
        });
        statistic_class_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statistic_class_comboboxActionPerformed(evt);
            }
        });

        statistic_tb_student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Mssv", "Attendance"
            }
        ));
        statistic_tb_student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statistic_tb_studentMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(statistic_tb_student);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel27.setText("Students list");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Student Information");

        jButton1.setText("Export");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel28.setText("Name:");

        jLabel29.setText("Student ID:");

        jLabel30.setText("Class:");

        jLabel31.setText("Joined:");

        statistic_time_joined.setColumns(20);
        statistic_time_joined.setRows(5);
        jScrollPane3.setViewportView(statistic_time_joined);

        javax.swing.GroupLayout pnl_infostudentLayout = new javax.swing.GroupLayout(pnl_infostudent);
        pnl_infostudent.setLayout(pnl_infostudentLayout);
        pnl_infostudentLayout.setHorizontalGroup(
            pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_infostudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_infostudentLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnl_infostudentLayout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addGap(44, 44, 44)
                            .addComponent(lb_namefinal, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_infostudentLayout.createSequentialGroup()
                            .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(18, 18, 18)
                            .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb_classidfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb_studentidfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_infostudentLayout.setVerticalGroup(
            pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_infostudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_namefinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_studentidfinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_classidfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_infostudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_infostudentLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel32.setText("Statistic");

        javax.swing.GroupLayout pnl_statisticLayout = new javax.swing.GroupLayout(pnl_statistic);
        pnl_statistic.setLayout(pnl_statisticLayout);
        pnl_statisticLayout.setHorizontalGroup(
            pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_statisticLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_statisticLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_statisticLayout.createSequentialGroup()
                        .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_statisticLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(statistic_class_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(54, 54, 54)
                        .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_infostudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_statisticLayout.createSequentialGroup()
                                .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(70, 70, 70))))
        );
        pnl_statisticLayout.setVerticalGroup(
            pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_statisticLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(statistic_class_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(pnl_statisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_infostudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnl_menu.add(pnl_statistic, "card6");

        getContentPane().add(pnl_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 750, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        // TODO add your handling code here:
        if(!this.btn_dashboard.isSelected()){
           this.btn_dashboard.setSelected(true);
           this.btn_class.setSelected(false);
           this.btn_student.setSelected(false);
           this.btn_settings.setSelected(false);
           this.btn_statistic.setSelected(false);
           switchPanel(pnl_dashboard);
        }
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_classActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classActionPerformed
        // TODO add your handling code here:
        if(!this.btn_class.isSelected()){
           this.btn_dashboard.setSelected(false);
           this.btn_class.setSelected(true);
           this.btn_student.setSelected(false);
           this.btn_settings.setSelected(false);
           this.btn_statistic.setSelected(false);
           switchPanel(pnl_class);
        }
    }//GEN-LAST:event_btn_classActionPerformed

    private void btn_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_studentActionPerformed
        // TODO add your handling code here:
        if(!this.btn_student.isSelected()){
           this.btn_dashboard.setSelected(false);
           this.btn_statistic.setSelected(false);
           this.btn_class.setSelected(false);
           this.btn_student.setSelected(true);
           this.btn_settings.setSelected(false);
           switchPanel(pnl_student);
        }
    }//GEN-LAST:event_btn_studentActionPerformed

    private void btn_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_settingsActionPerformed
        // TODO add your handling code here:
        if(!this.btn_settings.isSelected()){
           this.btn_dashboard.setSelected(false);
           this.btn_class.setSelected(false);
           this.btn_statistic.setSelected(false);
           this.btn_student.setSelected(false);
           this.btn_settings.setSelected(true);
           switchPanel(pnl_settings);
        }
    }//GEN-LAST:event_btn_settingsActionPerformed

    private void txt_nameclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameclassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameclassActionPerformed

    private void txt_classcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_classcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_classcodeActionPerformed

    private void combobox_semesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_semesterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox_semesterActionPerformed

    private void btn_scanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_scanActionPerformed
        // TODO add your handling code here:
        frame_scanfinger.pack();
        frame_scanfinger.setLocationRelativeTo(null);
        frame_scanfinger.setVisible(true);
    }//GEN-LAST:event_btn_scanActionPerformed

    private void btn_deletestudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletestudentActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)student_tb_student.getModel();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showConfirmDialog(null,"Delete student: " + model.getValueAt(student_tb_student.getSelectedRow(), 0).toString() + " ?", "Warning", dialogButton);
        if(dialogButton == JOptionPane.YES_OPTION)
        {
            model.removeRow(student_tb_student.getSelectedRow());
            txt_nameclass.setText("");
            txt_classcode.setText("");
        }
    }//GEN-LAST:event_btn_deletestudentActionPerformed
    
    private void togbtn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togbtn_startActionPerformed
        // TODO add your handling code here:
        int mode; //mode 1: Start . 0. Stop
        if(togbtn_start.isSelected()==true) {
            Image pause;
            try {
                pause = ImageIO.read(getClass().getResource("images/icons8-pause-30.png"));
                togbtn_start.setIcon(new ImageIcon(pause));
                lb_start.setText("Stop roll-call");
            } catch (IOException ex) {
                System.out.println("Image not found");
            }
        }
        else
        {
            Image play;
            try {
                play = ImageIO.read(getClass().getResource("images/icons8-play-filled-30.png"));
                togbtn_start.setIcon(new ImageIcon(play));
                lb_start.setText("Start roll-call");
            } catch (IOException ex) {
                System.out.println("Image not found");
            }
        }    
    }//GEN-LAST:event_togbtn_startActionPerformed

    private void btn_statisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_statisticActionPerformed
        // TODO add your handling code here:
        if(!this.btn_statistic.isSelected()){
           this.btn_dashboard.setSelected(false);
           this.btn_class.setSelected(false);
           this.btn_student.setSelected(false);
           this.btn_settings.setSelected(false);
           this.btn_statistic.setSelected(true);
           switchPanel(pnl_statistic);
        }
    }//GEN-LAST:event_btn_statisticActionPerformed

    private void btn_addclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addclassActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tb_class.getModel();
        if(txt_nameclass.getText().isEmpty()==true||txt_classcode.getText().isEmpty()==true){
            JOptionPane.showMessageDialog(null,"Name or Class ID is empty. \n Please Input");
        }
        else
        {
            String checkdupid = txt_classcode.getText();
            AttendenceClass c = new AttendenceClass(txt_nameclass.getText(),txt_classcode.getText(), (String) combobox_year.getSelectedItem(),Integer.parseInt((String) combobox_semester.getSelectedItem()));
            int rows = tb_class.getRowCount();
            boolean check=true;
            for(int i=0; i< rows; i++){
                if(txt_classcode.getText().equals(tb_class.getValueAt(i,1))&&Integer.parseInt((String) combobox_semester.getSelectedItem())==Integer.parseInt((String) tb_class.getValueAt(i, 2))){
                        check=false;
                        JOptionPane.showMessageDialog(null,"Class with ID : " + checkdupid + " is existed. Please input again.","Message", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
            }
            if(check==true){
            Object row[] = {txt_nameclass.getText(),txt_classcode.getText(),combobox_semester.getSelectedItem(),combobox_year.getSelectedItem()};
            AttendenceClass attendenceClass=new AttendenceClass();
            attendenceClass.setName(txt_nameclass.getText());
            attendenceClass.setCode(txt_classcode.getText());
            attendenceClass.setSemester(Integer.parseInt(combobox_semester.getSelectedItem().toString()));
            attendenceClass.setYear(combobox_year.getSelectedItem().toString());
            int rcode=CreateClass(attendenceClass);
            System.out.println(rcode);
            if (rcode==200) {
                rootClasses.add(attendenceClass);
                model.addRow(row);
                dashboard_combox_chooseclass.addItem(txt_classcode.getText() + " HK" + combobox_semester.getSelectedItem() + " " + combobox_year.getSelectedItem());
                student_combobox_class.addItem(txt_classcode.getText() + " HK" + combobox_semester.getSelectedItem() + " " + combobox_year.getSelectedItem());
            }
}
        }
    }//GEN-LAST:event_btn_addclassActionPerformed

    private void tb_classMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_classMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tb_class.getModel();
        txt_nameclass.setText(model.getValueAt(tb_class.getSelectedRow(), 0).toString());
        txt_classcode.setText(model.getValueAt(tb_class.getSelectedRow(), 1).toString());
        combobox_semester.setSelectedItem(model.getValueAt(tb_class.getSelectedRow(), 2).toString());
        combobox_year.setSelectedItem(model.getValueAt(tb_class.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_tb_classMouseClicked

    private void btn_importclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importclassActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)student_tb_student.getModel();
        DefaultTableModel model2 = (DefaultTableModel)tb_class.getModel();
        String hocky = new String();
        String namhoc = new String();
        String tenmonhoc = new String();
        String mamonhoc = new String();
        String tensv;
        String mssv;
        AttendenceClass newClass = new AttendenceClass();
        JFileChooser openfile = new JFileChooser();
        openfile.setDialogTitle("Open");
        openfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = openfile.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){                   
            try {
                File file = openfile.getSelectedFile();   
                FileInputStream excelFile = new FileInputStream(file);
                Workbook workbook;
                workbook = new XSSFWorkbook(excelFile);            
                Sheet datatypeSheet = workbook.getSheetAt(0);
                DataFormatter fmt = new DataFormatter();
                Iterator<Row> iterator = datatypeSheet.iterator();
                Row firstRow = iterator.next();
                Cell firstCell = firstRow.getCell(0);
                System.out.println(firstCell.getStringCellValue());
                while (iterator.hasNext()) {
                    Row currentrow = iterator.next();
                    int posRow = currentrow.getRowNum();
                    //Loại bỏ những dòng không quan trọng
                    if((posRow>=0 && posRow<3)||posRow==6||posRow==7||posRow==8)
                        continue;
                    else { 
                        if(posRow==4){
                            hocky = currentrow.getCell(0).getStringCellValue();
                            hocky = hocky.substring(8);
                            namhoc = currentrow.getCell(3).getStringCellValue();
                            namhoc = namhoc.substring(9);
                            System.out.println(hocky);
                            System.out.println(namhoc);
                            newClass.setSemester(Integer.parseInt(hocky));
                            newClass.setYear(namhoc);
                            
                        }
                        else if(posRow==5){
                            tenmonhoc = currentrow.getCell(0).getStringCellValue();
                            mamonhoc = currentrow.getCell(3).getStringCellValue();
                            tenmonhoc = tenmonhoc.substring(9);
                            mamonhoc = mamonhoc.substring(5);
                            System.out.println(tenmonhoc);
                            System.out.println(mamonhoc);
                            newClass.setName(tenmonhoc);
                            newClass.setCode(mamonhoc);
                        }
                        else if(posRow>8) // Lấy thông tin sinh viên
                        {
                            if(currentrow.getCell(1)==null) continue;
                            mssv = currentrow.getCell(1).getStringCellValue();
                            tensv = currentrow.getCell(2).getStringCellValue();
                            student currentstudent = new student(tensv,mssv);
                            model.addRow(new Object[]{tensv,mssv,"none"});
//                            newClass.sinhvien.add(currentstudent);
                        }
                    }
                    
               
                }
                
                workbook.close();
                model2.addRow(new Object[]{tenmonhoc,mamonhoc,hocky,namhoc});
                dashboard_combox_chooseclass.addItem(mamonhoc);
                student_combobox_class.addItem(mamonhoc);
                dashboard_combox_chooseclass.addItem(mamonhoc + " HK" + hocky + " " + namhoc);
                student_combobox_class.addItem(mamonhoc  + " HK" + hocky + " " + namhoc);

                statistic_class_combobox.addItem(mamonhoc);
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_importclassActionPerformed

    private void statistic_class_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_statistic_class_comboboxItemStateChanged
        // TODO add your handling code here:
//                System.out.println(statistic_class_combobox.getSelectedIndex());
//        DefaultTableModel model = (DefaultTableModel) statistic_tb_student.getModel();
////        GetRollReportResponse reportData=StatisticGetRollReport(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getId());
//        model.setRowCount(0);
//        for(int i=0;i<reportData.getData().getRolls().size();i++){
//            model.addRow(new Object[]{reportData.getData().getRolls().get(i).getName(),reportData.getData().getRolls().get(i).getMssv(),reportData.getData().getRolls().get(i).getCount()});
//        }
    }//GEN-LAST:event_statistic_class_comboboxItemStateChanged

    private void statistic_class_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statistic_class_comboboxActionPerformed
        // TODO add your handling code here
        new Thread(new Runnable() {
          public void run() {
        System.out.println("he");
        DefaultTableModel model = (DefaultTableModel) statistic_tb_student.getModel();
        GetRollReportResponse reportData=StatisticGetRollReport(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getId());
        model.setRowCount(0);
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                
                    System.out.println(reportData==null);
        for(int i=0;i<reportData.getData().getRolls().size();i++){
            model.addRow(new Object[]{reportData.getData().getRolls().get(i).getName(),reportData.getData().getRolls().get(i).getMssv(),reportData.getData().getRolls().get(i).getCount()});
        }
                }
              });

          }
        }).start();

    }//GEN-LAST:event_statistic_class_comboboxActionPerformed

    private void btn_editclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editclassActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tb_class.getModel();
        String checkdupid = txt_classcode.getText();
        int rows=tb_class.getRowCount();
        boolean check=true;
            for(int i=0; i< rows; i++){
                if(txt_classcode.equals(tb_class.getValueAt(i,1))&&Integer.parseInt((String) combobox_semester.getSelectedItem())==Integer.parseInt((String) tb_class.getValueAt(i, 2))){
                        check=false;
                        JOptionPane.showMessageDialog(null,"Class with ID : " + checkdupid + " is existed. Please input again.","Message", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
            }
        if(check == true){
//            Class c = new Class(txt_nameclass.getText(),txt_classid.getText(),Integer.parseInt((String) combobox_semester.getSelectedItem()), (String) combobox_year.getSelectedItem());
//            model.setValueAt(c.getname(),tb_class.getSelectedRow(), 0);
//            model.setValueAt(c.getclassid(),tb_class.getSelectedRow(), 1);
//            model.setValueAt(c.gethocky(),tb_class.getSelectedRow(), 2);
//            model.setValueAt(c.getnamhoc(),tb_class.getSelectedRow(), 3);
        }
    }//GEN-LAST:event_btn_editclassActionPerformed

    private void btn_deleteclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteclassActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tb_class.getModel();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showConfirmDialog(null,"Delete class: " + model.getValueAt(tb_class.getSelectedRow(), 1).toString() + " ?", "Warning", dialogButton);
        if(dialogButton == JOptionPane.YES_OPTION)
        {
            model.removeRow(tb_class.getSelectedRow());
            txt_nameclass.setText("");
            txt_classcode.setText("");
        }
    }//GEN-LAST:event_btn_deleteclassActionPerformed

    private void btn_addstudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addstudentActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)student_tb_student.getModel();
        if(txt_namestudent.getText().isEmpty()==true||txt_mssv.getText().isEmpty()==true){
            JOptionPane.showMessageDialog(null,"Missing field ! \nPlease input again");
        }
        else
        {           
            String checkdupid = txt_mssv.getText();
            boolean check = true;
            int rows = model.getRowCount();
            for(int i=0; i<rows; i++){
                if(checkdupid.equals(model.getValueAt(i, 2))){
                    check = false;
                    JOptionPane.showMessageDialog(null,"Student with ID : " + checkdupid + " is existed. Please input again.","Message", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if(check){
                student student = new student(txt_namestudent.getText(),txt_mssv.getText());
                model.addRow(new Object[]{txt_namestudent.getText(),txt_mssv.getText(),"none"});
            }
        }
        
            
            
        
        
    }//GEN-LAST:event_btn_addstudentActionPerformed

    private void btn_editstudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editstudentActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)student_tb_student.getModel();
        String checkdupid = txt_mssv.getText();
            boolean check = true;
            int rows = model.getRowCount();
            for(int i=0; i<rows; i++){
                if(checkdupid.equals(model.getValueAt(i, 2))){
                    check = false;
                    JOptionPane.showMessageDialog(null,"Student with ID : " + checkdupid + " is existed. Please input again.","Message", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if(check){
                student student = new student(txt_namestudent.getText(),txt_mssv.getText());
                model.addRow(new Object[]{txt_namestudent.getText(),txt_mssv.getText(),"none"});
            }
    }//GEN-LAST:event_btn_editstudentActionPerformed

    private void student_tb_studentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_tb_studentMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)student_tb_student.getModel();
        txt_namestudent.setText(model.getValueAt(student_tb_student.getSelectedRow(),0).toString());
        txt_mssv.setText(model.getValueAt(student_tb_student.getSelectedRow(),1).toString());
        
    }//GEN-LAST:event_student_tb_studentMouseClicked

    private void statistic_tb_studentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statistic_tb_studentMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)statistic_tb_student.getModel();
        lb_namefinal.setText(model.getValueAt(statistic_tb_student.getSelectedRow(),0).toString());
        lb_studentidfinal.setText(model.getValueAt(statistic_tb_student.getSelectedRow(),1).toString());
        lb_classidfinal.setText(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getCode());
        StatisticGetRollTimeStudent(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getId(), model.getValueAt(statistic_tb_student.getSelectedRow(),1).toString());
    }//GEN-LAST:event_statistic_tb_studentMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        GetRollReportResponse reportData=StatisticGetRollReport(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getId());
        //System.out.println(reportData);
        //System.out.println(reportData.getData().getDates().size());
        //System.out.println(reportData.getData().getDates());
        //System.out.println(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getName());
  
        
        System.out.println(reportData.getData().getDates().get(0));
        FileFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
        JFileChooser savefile = new JFileChooser();
        savefile.setDialogTitle("Save a File");
        savefile.setFileFilter(filter);
    
        int result = savefile.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
         
            File file = savefile.getSelectedFile();
            //Kiểm tra file có tồn tại không
            if(file.exists() && !file.isDirectory())
                {
         
                    int dialogButton = JOptionPane.OK_CANCEL_OPTION;
                    JOptionPane.showConfirmDialog(null,"File: "+ file.getName() + " existed. Do you want to Overwrite it ?", "Warning", dialogButton);
                    if(dialogButton == JOptionPane.OK_OPTION)
                    {
        
                    XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getCode());
                int rowNum = 0;
                //Lấy tên lớp và mã lớp
                Row firstRow = sheet.createRow(rowNum++);
                Cell firstCell = firstRow.createCell(0);
                firstCell.setCellValue(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getName());
                Cell secondCell = firstRow.createCell(1);
                secondCell.setCellValue(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getCode());
                Row row = sheet.createRow(rowNum++);
                Cell currentCell;
                currentCell = row.createCell(0);
                currentCell.setCellValue("Tên");
                currentCell = row.createCell(1);
                currentCell.setCellValue("MSSV");
                currentCell = row.createCell(2);
                currentCell.setCellValue("Tổng buổi tham gia");
                for(int i=0;i<reportData.getData().getDates().size();i++){
                    currentCell = row.createCell(i+3);
                    String time_filter = reportData.getData().getDates().get(i).getTime().substring(0,10);
                    currentCell.setCellValue(time_filter);
                }
                for(int j=0;j<reportData.getData().getRolls().size();j++){
                    row = sheet.createRow(rowNum++);
                    currentCell = row.createCell(0);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getName());
                    currentCell = row.createCell(1);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getMssv());
                    currentCell = row.createCell(2);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getCount());
                    for(int k=0;k<reportData.getData().getRolls().get(j).getRolls().size();k++)
                    {
                        currentCell = row.createCell(k+3);
                        System.out.println(reportData.getData().getRolls().get(j).getRolls().get(k).getChecked());
                        if(reportData.getData().getRolls().get(j).getRolls().get(k).getChecked()==true)
                            currentCell.setCellValue("x");                        
                        else currentCell.setCellValue("");
                    }                                       
                }
                
                    
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file+".xlsx");
                        workbook.write(outputStream);
                        workbook.close();
               
                    } catch (IOException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                JOptionPane.showMessageDialog(null,"Export to Excel successfully");
                    }
                }
            else{

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getCode());
                int rowNum = 0;
                //Lấy tên lớp và mã lớp
                Row firstRow = sheet.createRow(rowNum++);
                Cell firstCell = firstRow.createCell(0);
                firstCell.setCellValue(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getName());
                Cell secondCell = firstRow.createCell(1);
                secondCell.setCellValue(rootClasses.get(statistic_class_combobox.getSelectedIndex()).getCode());
                Row row = sheet.createRow(rowNum++);
                Cell currentCell;
                currentCell = row.createCell(0);
                currentCell.setCellValue("Tên");
                currentCell = row.createCell(1);
                currentCell.setCellValue("MSSV");
                currentCell = row.createCell(2);
                currentCell.setCellValue("Tổng buổi tham gia");
                for(int i=0;i<reportData.getData().getDates().size();i++){
                    currentCell = row.createCell(i+3);
                    String time_filter = reportData.getData().getDates().get(i).getTime().substring(0,10);
                    currentCell.setCellValue(time_filter);
                }
                for(int j=0;j<reportData.getData().getRolls().size();j++){
                    row = sheet.createRow(rowNum++);
                    currentCell = row.createCell(0);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getName());
                    currentCell = row.createCell(1);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getMssv());
                    currentCell = row.createCell(2);
                    currentCell.setCellValue(reportData.getData().getRolls().get(j).getCount());
                    for(int k=0;k<reportData.getData().getRolls().get(j).getRolls().size();k++)
                    {
                        currentCell = row.createCell(k+3);
                        System.out.println(reportData.getData().getRolls().get(j).getRolls().get(k).getChecked());
                        if(reportData.getData().getRolls().get(j).getRolls().get(k).getChecked()==true)
                            currentCell.setCellValue("x");                        
                        else currentCell.setCellValue("");
                    }                                       
                }
                
                    
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file+".xlsx");
                        workbook.write(outputStream);
                        workbook.close();
               
                    } catch (IOException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                JOptionPane.showMessageDialog(null,"Export to Excel successfully");
            }
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dashboard_combox_chooseclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_combox_chooseclassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboard_combox_chooseclassActionPerformed

    private void dashboard_combox_chooseclassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dashboard_combox_chooseclassItemStateChanged
        // TODO add your handling code here:

        System.out.println("sle:"+dashboard_combox_chooseclass.getSelectedIndex());
        if(dashboard_combox_chooseclass.getSelectedIndex()>=0)
        RenderCurrentRollByClassID(rootClasses.get(dashboard_combox_chooseclass.getSelectedIndex()).getId());
    }//GEN-LAST:event_dashboard_combox_chooseclassItemStateChanged

    private void student_combobox_classActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_combobox_classActionPerformed
        // TODO add your handling code here:
        if(student_combobox_class.getSelectedIndex()>=0)
            RenderStudentByClassID(rootClasses.get(student_combobox_class.getSelectedIndex()).getId());
    }//GEN-LAST:event_student_combobox_classActionPerformed

    private void lb_canclescanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lb_canclescanActionPerformed
        // TODO add your handling code here:
        frame_scanfinger.setVisible(false);
    }//GEN-LAST:event_lb_canclescanActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public void switchPanel(javax.swing.JPanel x){
        //Xóa panel hiện tại
        pnl_menu.removeAll();
        pnl_menu.repaint();
        pnl_menu.revalidate();
        //Thêm panel mới vào
        pnl_menu.add(x);
        pnl_menu.repaint();
        pnl_menu.revalidate();
    }
    
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addclass;
    private javax.swing.JButton btn_addstudent;
    private rojerusan.RSButtonIconI btn_class;
    private rojerusan.RSButtonIconI btn_dashboard;
    private javax.swing.JButton btn_deleteclass;
    private javax.swing.JButton btn_deletestudent;
    private javax.swing.JButton btn_editclass;
    private javax.swing.JButton btn_editstudent;
    private javax.swing.JButton btn_importclass;
    private javax.swing.JButton btn_scan;
    private rojerusan.RSButtonIconI btn_settings;
    private rojerusan.RSButtonIconI btn_statistic;
    private rojerusan.RSButtonIconI btn_student;
    private javax.swing.JComboBox<String> combobox_semester;
    private javax.swing.JComboBox<String> combobox_year;
    private javax.swing.JComboBox<String> dashboard_combox_chooseclass;
    private javax.swing.JFrame frame_scanfinger;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JButton lb_canclescan;
    private javax.swing.JLabel lb_classidfinal;
    private javax.swing.JLabel lb_date;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_namefinal;
    private javax.swing.JLabel lb_start;
    private javax.swing.JLabel lb_status;
    private javax.swing.JLabel lb_statusicon;
    private javax.swing.JLabel lb_statustxt;
    private javax.swing.JLabel lb_studentidfinal;
    private javax.swing.JLabel lb_time;
    private javax.swing.JPanel pnl_class;
    private javax.swing.JPanel pnl_dashboard;
    private javax.swing.JPanel pnl_infostudent;
    private javax.swing.JPanel pnl_menu;
    private javax.swing.JPanel pnl_settings;
    private javax.swing.JPanel pnl_statistic;
    private javax.swing.JPanel pnl_student;
    private javax.swing.JTable rollTable;
    private javax.swing.JComboBox<String> statistic_class_combobox;
    private javax.swing.JTable statistic_tb_student;
    private javax.swing.JTextArea statistic_time_joined;
    private javax.swing.JComboBox<String> student_combobox_class;
    private javax.swing.JTable student_tb_student;
    private javax.swing.JTable tb_class;
    private javax.swing.JToggleButton togbtn_start;
    private javax.swing.JTextField txt_classcode;
    private javax.swing.JTextField txt_mssv;
    private javax.swing.JTextField txt_nameclass;
    private javax.swing.JTextField txt_namestudent;
    // End of variables declaration//GEN-END:variables
}
