package org.thd.Views.StudentManagement;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.thd.Components.ImageRenderer;
import org.thd.Controllers.StudentController;
import org.thd.Models.Student;
import org.thd.Models.StudentTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class FormStudentManagement extends JFrame{
    private JPanel panelMain;
    private JTable tableStudents;
    private JButton importButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField textFieldID;
    private JTextField textFieldEmail;
    private JTextField textFieldName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField textFieldMajor;
    private JButton uploadButton;
    private JScrollPane myScroll;
    private JButton exportExcelButton;
    private JButton detailButton;

    private StudentController studentController;

    public FormStudentManagement() {
        setTitle("Student Management Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        studentController = new StudentController();
        StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
        tableStudents.setModel(model);

        tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));

        add(panelMain);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("data", "csv", "txt", "xlsx");
                fileChooser.setFileFilter(csvFilter);
                fileChooser.setMultiSelectionEnabled(false);

                int isSelected = fileChooser.showDialog(FormStudentManagement.this, "Select file");
                if (isSelected == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        XSSFWorkbook myExcelFile = new XSSFWorkbook(new FileInputStream(file));
                        List<XSSFPictureData> pictures = myExcelFile.getAllPictures();
                        XSSFSheet ws = myExcelFile.getSheetAt(0);
                        Map<Integer, byte[]> pictureMap = new HashMap<>();

                        for (XSSFPictureData picture : pictures) {
                            String ext = picture.suggestFileExtension();
                            if (ext.equals("jpeg") || ext.equals("png")) {
                                XSSFPictureData xssfPicture = (XSSFPictureData) picture;
                                XSSFDrawing drawing = ws.createDrawingPatriarch();
                                for (XSSFShape shape : drawing.getShapes()) {
                                    if (shape instanceof XSSFPicture) {
                                        XSSFPicture pic = (XSSFPicture) shape;
                                        if (pic.getPictureData() == xssfPicture) {
                                            XSSFClientAnchor anchor = pic.getPreferredSize();
                                            int row = anchor.getRow1();
                                            pictureMap.put(row, picture.getData());
                                        }
                                    }
                                }
                            }
                        }

                        Iterator<Row> rowIterator = ws.iterator();
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();
                            boolean gender = false;
                            if(row.getCell(4).getStringCellValue().equals("Nam")) {
                                gender = true;
                            }
                            byte[] pictureData = pictureMap.get(row.getRowNum());
                            Student student = new Student(
                                    row.getCell(1).getStringCellValue(),
                                    row.getCell(2).getStringCellValue(),
                                    row.getCell(3).getStringCellValue(),
                                    gender,
                                    row.getCell(5).getStringCellValue(),
                                    pictureData
                                    );
                            studentController.addStudent(student);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
                tableStudents.setModel(model);
            }
        });
    }
}


