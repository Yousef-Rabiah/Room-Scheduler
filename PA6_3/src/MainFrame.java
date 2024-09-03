import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;






public class MainFrame extends javax.swing.JDialog {
    
    private void FillCombo() {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"NAME\" FROM JAVA.FACULTY FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String facnames = rs.getString("name");
            facultyResCB.addItem(facnames);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }

    boolean Flag = false;
    
    private boolean reserveChk(String Rom, String selecteddate){
        
        try{
    
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.RESERVATIONS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String room = rs.getString("ROOM");
                String date = rs.getString("DATE");
                if (room.equals(Rom) && date.equals(selecteddate)) return false;
     
                
                
                
            }
    }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
    
    return true;
    }
    
    private int getCapacity(String room){    
        
        try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.ROOMS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int seats = rs.getInt("SEATS");
            String Rom = (String) rs.getString("NAME");
            
            if (room.equals(Rom) ){
            
                return seats;
            }
            
        }
        
       
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    return 0;
    }
    
    public void FillCombo2() {
        try{
        dateCB.removeAllItems();
        resDateCB.removeAllItems();
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"DATES\" FROM JAVA.DATES FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String dates = rs.getString("DATES");
                
                dateCB.addItem(dates);
                resDateCB.addItem(dates);
            }
    }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
        
    
    }
    
    public void FillCombo3() {
        try{
    
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"ROOMS\" FROM JAVA.DATES FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String dates = rs.getString("DATES");
                dateCB.addItem(dates);
                
            }
    }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
        
    
    }
    
    private void waitChk(){
    
       int capacity= (int) Integer.parseInt(sizeTF.getText());
           String Rom = (String) roomTF.getText();


        try{

        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.WAITLIST FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            
            int seatsreq = (int) seatreqS.getValue();
            
            String Fac = rs.getString("FACULTY");
            String date = rs.getString("DATE");
            int seats = (int) Integer.parseInt(rs.getString("SEATS"));

            if ( seatsreq <= capacity){
                if (reserveChk(Rom,date)){
                PreparedStatement ps2 = dbconnect.prepareStatement("INSERT INTO RESERVATIONS (FACULTY,ROOM,DATE,SEATS,TIMESTAMP) VALUES (?,?,?,?,?)");
                PreparedStatement ps3 = dbconnect.prepareStatement("DELETE FROM WAITLIST WHERE FACULTY ='"+ Fac +"'");

                ps2.setInt(4, seats);
                ps2.setTimestamp(5,getCurrentTimeStamp());
                ps2.setString(3, date);
                ps2.setString(1,Fac);
                ps2.setString(2, Rom);
                ps2.executeUpdate();
                ps3.executeUpdate();
                
                Filltable1();
                Filltable2();
                Filltable3();
                resStatuslabel.setText("Reservation added successfully");


                }
            }

        }

    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    
    
    }
    
    private void Filltable1() {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.RESERVATIONS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        DefaultTableModel tblmodel = (DefaultTableModel) jTable1.getModel();
        tblmodel.setRowCount(0);
        while(rs.next()){
            String rooms = rs.getString("ROOM");
            String date = rs.getString("DATE");
            String seats = rs.getString("SEATS");
            String tbData[] = {rooms,date,seats};
            
            tblmodel.addRow(tbData);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
    private void Filltable3() {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.WAITLIST FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        DefaultTableModel tblmodel = (DefaultTableModel) jTable4.getModel();
        tblmodel.setRowCount(0);
        while(rs.next()){
            String Fac = rs.getString("Faculty");
            String date = rs.getString("DATE");
            String seats = rs.getString("SEATS");
            
            String tbData[] = {Fac,date,seats};
            
            tblmodel.addRow(tbData);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
    private void Filltable2() {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.RESERVATIONS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        DefaultTableModel tblmodel = (DefaultTableModel) jTable3.getModel();
        tblmodel.setRowCount(0);
        while(rs.next()){
            String facname = rs.getString("FACULTY");
            String rooms = rs.getString("ROOM");
            String date = rs.getString("DATE");
            String seats = rs.getString("SEATS");
            
           
            
            String tbData[] = {facname,date,rooms,seats};
            
            tblmodel.addRow(tbData);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
    private void Filltable4() {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.ROOMS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        DefaultTableModel tblmodel = (DefaultTableModel) jTable5.getModel();
        tblmodel.setRowCount(0);
        while(rs.next()){
            String room = rs.getString("NAME");
         
            String tbData[] = {room};
            
            tblmodel.addRow(tbData);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
    
    public MainFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        FillCombo();
        FillCombo2();
        Filltable2();
        Filltable3();
        Filltable4();
    }


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jSpinner1 = new javax.swing.JSpinner();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addFacultyTF = new javax.swing.JTextField();
        addFacultyButton = new javax.swing.JButton();
        facultyStatusLabel = new javax.swing.JLabel();
        roomSubmit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        roomTF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        sizeTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        monthS = new javax.swing.JSpinner();
        dayS = new javax.swing.JSpinner();
        yearS = new javax.swing.JSpinner();
        dateSubmit = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        facultyResCB = new javax.swing.JComboBox<>();
        dateCB = new javax.swing.JComboBox<>();
        roomResButton = new javax.swing.JButton();
        resStatuslabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        seatreqS = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        resDateCB = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dateSearchButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        cancelResButton = new javax.swing.JButton();
        waitLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        Cancelwaitlist = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        delRoom = new javax.swing.JButton();
        delroomlabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setText("Faculty Name:");

        addFacultyTF.setBackground(new java.awt.Color(204, 204, 204));
        addFacultyTF.setColumns(20);
        addFacultyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFacultyTFActionPerformed(evt);
            }
        });

        addFacultyButton.setText("Submit");
        addFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFacultyButtonActionPerformed(evt);
            }
        });

        facultyStatusLabel.setText("  ");

        roomSubmit.setText("Submit");
        roomSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomSubmitActionPerformed(evt);
            }
        });

        jLabel7.setText("Room Number:");

        roomTF.setBackground(new java.awt.Color(204, 204, 204));

        jLabel8.setText("Size:");

        sizeTF.setBackground(new java.awt.Color(204, 204, 204));
        sizeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeTFActionPerformed(evt);
            }
        });

        jLabel9.setText("Date:");

        monthS.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        dayS.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        yearS.setModel(new javax.swing.SpinnerNumberModel(20, 0, 99, 1));

        dateSubmit.setText("Submit");
        dateSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateSubmitActionPerformed(evt);
            }
        });

        jLabel10.setText("MM:");

        jLabel11.setText("DD:");

        jLabel12.setText("YY:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addFacultyTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(roomTF, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(facultyStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dayS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yearS, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roomSubmit)
                    .addComponent(addFacultyButton)
                    .addComponent(dateSubmit))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(addFacultyTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFacultyButton))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(roomTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(sizeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomSubmit))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(monthS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateSubmit)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(facultyStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Faculty, Rooms, & Date", jPanel1);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel3.setText("Faculty");

        jLabel4.setText("Date");

        jLabel5.setText("Seats Required");

        facultyResCB.setBackground(new java.awt.Color(153, 153, 153));

        dateCB.setBackground(new java.awt.Color(153, 153, 153));

        roomResButton.setText("Submit");
        roomResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomResButtonActionPerformed(evt);
            }
        });

        resStatuslabel.setText("  ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Room", "Date", "Seats"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(facultyResCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(seatreqS, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(resStatuslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(206, 206, 206)
                            .addComponent(roomResButton))))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(facultyResCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(dateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(seatreqS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roomResButton)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(resStatuslabel)
                .addGap(70, 70, 70))
        );

        jTabbedPane1.addTab("Room Reservation", jPanel3);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        resDateCB.setBackground(new java.awt.Color(153, 153, 153));

        jLabel6.setText("Date:");

        dateSearchButton.setText("Search");
        dateSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateSearchButtonActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Name", "Date", "Room", "Seats", "Capacity"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        cancelResButton.setText("Cancel Reservation");
        cancelResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelResButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(36, 36, 36)
                .addComponent(resDateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(dateSearchButton)
                .addGap(131, 131, 131))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(cancelResButton))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(waitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateSearchButton)
                    .addComponent(resDateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(waitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelResButton)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Reserved Rooms", jPanel4);

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Date", "Seats"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        Cancelwaitlist.setText("Cancel Waitlist");
        Cancelwaitlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelwaitlistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Cancelwaitlist)
                .addGap(244, 244, 244))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cancelwaitlist)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Waitlist", jPanel5);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Room"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        delRoom.setText("Delete room");
        delRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(228, 228, 228)
                                .addComponent(delroomlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(250, 250, 250)
                                .addComponent(delRoom)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delroomlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delRoom))
        );

        jTabbedPane1.addTab("Rooms", jPanel7);

        jLabel2.setFont(new java.awt.Font("PT Mono", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Room Scheduler");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(166, 166, 166))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFacultyButtonActionPerformed

        String facname = addFacultyTF.getText();

        Faculty.addFaculty(facname);

        facultyStatusLabel.setText("Faculty Name Added Successfully");
        facultyResCB.addItem(facname);
        

    }//GEN-LAST:event_addFacultyButtonActionPerformed

    private void addFacultyTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFacultyTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFacultyTFActionPerformed

    private void sizeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sizeTFActionPerformed

    private void cancelResButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelResButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        int row = jTable3.getSelectedRow();
        String room = (String) jTable3.getModel().getValueAt(row,2);
        
        
        String rDate = (String) jTable3.getModel().getValueAt(row,1);

        String delRow = (String) "DELETE FROM RESERVATIONS WHERE ROOM ='"+ room + "'";
        
        
        int rSeats = getCapacity(room);
        
        try {
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");

            
            
            PreparedStatement ps = dbconnect.prepareStatement(delRow);
            ps.execute();
            Filltable2();
            Filltable1();
            PreparedStatement ps2 = dbconnect.prepareStatement("SELECT * FROM JAVA.WAITLIST FETCH FIRST 100 ROWS ONLY");
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
            int seats = rs.getInt("SEATS");
            String date = rs.getString("DATE");
            String fac = rs.getString("FACULTY");
            String delWait = (String) "DELETE FROM WAITLIST WHERE FACULTY ='"+ fac + "'";
            PreparedStatement ps5 = dbconnect.prepareStatement(delWait);
            ps5.execute();
            
            
                if (date.equals(rDate) && (seats <= rSeats)){
                
                PreparedStatement ps3 = dbconnect.prepareStatement("INSERT INTO RESERVATIONS (FACULTY,ROOM,DATE,SEATS,TIMESTAMP) VALUES (?,?,?,?,?)");
                
                
                ps3.setInt(4, seats);
                ps3.setTimestamp(5,getCurrentTimeStamp());
                ps3.setString(3, date);
                ps3.setString(1,fac);
                ps3.setString(2, room);
                ps3.executeUpdate();

                Filltable1();
                Filltable2();
                waitLabel.setText(fac+" has been moved from waitlist");

                Filltable3();
                Flag = true;
                break;
                
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }

    }//GEN-LAST:event_cancelResButtonActionPerformed

    private void dateSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateSubmitActionPerformed

        int day = (int) dayS.getValue();
        int month = (int) monthS.getValue();
        int year = (int) yearS.getValue();
        year = year + 2000;
        String dmy = month + "/"+ day + "/" + year;
        
        facultyStatusLabel.setText("Date " + dmy + " Added Successfully");
        Date.addDate(dmy);
        FillCombo2();
    }//GEN-LAST:event_dateSubmitActionPerformed

    private void roomSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomSubmitActionPerformed
        
        
        String room = roomTF.getText();
        String size = sizeTF.getText();
        try {
            
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
            PreparedStatement ps = dbconnect.prepareStatement("INSERT INTO ROOMS (NAME,SEATS) VALUES (?,?)");
            ps.setString(1, room);
            ps.setString(2, size);
            ps.executeUpdate();
            
            facultyStatusLabel.setText("Room " + room + " Added Successfully");
            waitChk();
            
            Filltable4();
            FillCombo2();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }//GEN-LAST:event_roomSubmitActionPerformed

    private void roomResButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomResButtonActionPerformed
        int seatsreq = (int) seatreqS.getValue();
        String selecteddate = (String) dateCB.getSelectedItem();
        String selectedFac = (String) facultyResCB.getSelectedItem();
        
        
        try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM ROOMS ORDER BY SEATS");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int seats = rs.getInt("SEATS");
            String Rom = (String) rs.getString("NAME");
            
            
            if ( seatsreq <= seats ){
                if (reserveChk(Rom,selecteddate)){
                PreparedStatement ps2 = dbconnect.prepareStatement("INSERT INTO RESERVATIONS (FACULTY,ROOM,DATE,SEATS,TIMESTAMP) VALUES (?,?,?,?,?)");
                
                
                ps2.setInt(4, seatsreq);
                ps2.setTimestamp(5,getCurrentTimeStamp());
                ps2.setString(3, selecteddate);
                ps2.setString(1,selectedFac);
                ps2.setString(2, Rom);
                ps2.executeUpdate();

                Filltable1();
                Filltable2();
                resStatuslabel.setText("Reservation added successfully");
                
                Flag = true;
                break;
                }
            }
            
        }
        if (!Flag) {

            PreparedStatement ps3 = dbconnect.prepareStatement("INSERT INTO WAITLIST (FACULTY,DATE,SEATS,TIMESTAMP) VALUES (?,?,?,?)");
            ps3.setInt(3, seatsreq);
            ps3.setTimestamp(4,getCurrentTimeStamp());
            ps3.setString(2, selecteddate);
            ps3.setString(1,selectedFac);
            ps3.executeUpdate();
            resStatuslabel.setText("Waitlist added successfully");
            Filltable3();
            
        }
        Flag = false;
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_roomResButtonActionPerformed

    private void dateSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateSearchButtonActionPerformed
        String selecteddate = (String) resDateCB.getSelectedItem();
        String datesearch = (String) "SELECT * FROM RESERVATIONS WHERE DATE ='"+ selecteddate + "'";
        try {
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
            PreparedStatement ps = dbconnect.prepareStatement(datesearch);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tblmodel = (DefaultTableModel) jTable3.getModel();
            tblmodel.setRowCount(0);
        while(rs.next()){
            String facname = rs.getString("FACULTY");
            String rooms = rs.getString("ROOM");
            String date = rs.getString("DATE");
            String seats = rs.getString("SEATS");
            String tbData[] = {facname,date,rooms,seats};
            
            tblmodel.addRow(tbData);
        }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }
    }//GEN-LAST:event_dateSearchButtonActionPerformed

    private void CancelwaitlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelwaitlistActionPerformed

        int row = jTable4.getSelectedRow();
       
        String fac = (String) jTable4.getModel().getValueAt(row, 0);

        try {
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");

            Filltable2();
            Filltable1();
            String delWait = (String) "DELETE FROM WAITLIST WHERE FACULTY ='"+ fac + "'";
            PreparedStatement ps = dbconnect.prepareStatement(delWait);
            ps.execute();
            Filltable3();
            
            
                
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }
        
    }//GEN-LAST:event_CancelwaitlistActionPerformed

    private void delRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delRoomActionPerformed
        
        int row = jTable5.getSelectedRow();
       
        String room = (String) jTable5.getModel().getValueAt(row, 0);

        try {
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");

            Filltable2();
            Filltable1();
            PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM RESERVATIONS WHERE ROOM ='"+ room + "'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            String fac = (String) rs.getString("FACULTY");
            int seats = rs.getInt("SEATS");
            String dates = rs.getString("DATE");
            
            
            PreparedStatement ps2 = dbconnect.prepareStatement("INSERT INTO WAITLIST (FACULTY,DATE,SEATS,TIMESTAMP) VALUES (?,?,?,?)");
            ps2.setString(1, fac);
            ps2.setString(2, dates);
            ps2.setInt(3, seats);
            ps2.setTimestamp(4,getCurrentTimeStamp());
            ps2.executeUpdate();
            PreparedStatement ps3 = dbconnect.prepareStatement("DELETE FROM RESERVATIONS WHERE FACULTY ='"+ fac + "'");
            ps3.execute();
            }
            
            
            String delRoom = (String) "DELETE FROM ROOMS WHERE NAME ='"+ room + "'";
            PreparedStatement ps4 = dbconnect.prepareStatement(delRoom);
            ps4.execute();
            delroomlabel.setText("Room deleted successfully");
            Filltable2();
            Filltable4();
            Filltable3();
            
            
            
                
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }
        
        
    }//GEN-LAST:event_delRoomActionPerformed

    
    

    

    
    public static void main(String args[]) {

        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                MainFrame dialog = new MainFrame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
        
        
    }


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelwaitlist;
    private javax.swing.JButton addFacultyButton;
    private javax.swing.JTextField addFacultyTF;
    private javax.swing.JButton cancelResButton;
    private javax.swing.JComboBox<String> dateCB;
    private javax.swing.JButton dateSearchButton;
    private javax.swing.JButton dateSubmit;
    private javax.swing.JSpinner dayS;
    private javax.swing.JButton delRoom;
    private javax.swing.JLabel delroomlabel;
    private javax.swing.JComboBox<String> facultyResCB;
    private javax.swing.JLabel facultyStatusLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JSpinner monthS;
    private javax.swing.JComboBox<String> resDateCB;
    private javax.swing.JLabel resStatuslabel;
    private javax.swing.JButton roomResButton;
    private javax.swing.JButton roomSubmit;
    private javax.swing.JTextField roomTF;
    private javax.swing.JSpinner seatreqS;
    private javax.swing.JTextField sizeTF;
    private javax.swing.JLabel waitLabel;
    private javax.swing.JSpinner yearS;
    // End of variables declaration//GEN-END:variables

    private Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}
