package org.thd.Views.StudentManagement;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.thd.Components.ImageRenderer;
import org.thd.Controllers.StudentController;
import org.thd.Models.Student;
import org.thd.Models.StudentTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class FormStudentManagement extends JFrame{
    private JPanel panelMain;
    private JTable tableStudents;
    private JButton importButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField studentIDField;
    private JTextField studentEmail;
    private JTextField studentName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField major;
    private JButton uploadButton;
    private JScrollPane myScroll;
    private JButton exportExcelButton;
    private JButton detailButton;
    private JButton searchStudentsButton;
    private JTextField studentGPA;
    private JTextField studentPoint;
    private JButton buttonSave;
    private JLabel image;
    private JButton buttonAdd;

    private StudentController studentController;

    public FormStudentManagement() {
        setTitle("Student Management Form");
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
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("data", "csv", "txt", "xlsx", "xlsm");
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

                        int count = 0;
                        for (XSSFPictureData picture : pictures) {
                            pictureMap.put(count++, picture.getData());
                        }

                        Iterator<Row> rowIterator = ws.iterator();
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();

                            String gender = row.getCell(4).getStringCellValue();

                            Student student = new Student(
                                    row.getCell(1).getStringCellValue(),
                                    row.getCell(2).getStringCellValue(),
                                    row.getCell(3).getStringCellValue(),
                                    gender.equals("Nam") || gender.equals("Male"),
                                    row.getCell(5).getStringCellValue(),
                                    row.getCell(6).getNumericCellValue(),
                                    (int) row.getCell(7).getNumericCellValue(),
                                    pictureMap.get(row.getRowNum())
                                    );
                            studentController.addStudent(student);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
                tableStudents.setModel(model);
                tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));
            }
        });
        exportExcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Excel/CSV: csv,xlsx,...", "csv", "txt", "xlsx", "xlsm");
                fileChooser.setFileFilter(csvFilter);
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setDialogTitle("Specify a file to save");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("Students");
                    sheet.setColumnWidth(0, 6400);

                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(model.getColumnName(i));
                    }


                    for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                        Row dataRow = sheet.createRow(rowIndex + 1);

                        dataRow.setHeightInPoints(193);

                        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
                            Cell cell = dataRow.createCell(columnIndex);
                            Object value = model.getValueAt(rowIndex, columnIndex);

                            if (value != null) {
                                if (value instanceof String ) {
                                    cell.setCellValue((String) value);
                                } else if (value instanceof Double) {
                                    cell.setCellValue((Double) value);
                                } else if (value instanceof Integer) {
                                    cell.setCellValue((Integer) value);
                                } else if (value instanceof ImageIcon) {
                                    addImageToCell(workbook, sheet, rowIndex + 1, columnIndex, (ImageIcon) value);
                                }
                            }
                        }
                    }

                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                        JOptionPane.showMessageDialog(null, "Data exported to " + filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error exporting data to Excel");
                    } finally {
                        try {
                            workbook.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });
        searchStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormSearchNSort().setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableStudents.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(FormStudentManagement.this, "Please chose an student you want to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(FormStudentManagement.this, "You want to delete this student");
                    if (confirm == JOptionPane.YES_OPTION) {
                        String studentID = String.valueOf(tableStudents.getValueAt(row, 1));
                        studentController.deleteStudent(studentID);
                        StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
                        tableStudents.setModel(model);
                        tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));
                    }
                }
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormDetailStudent().setVisible(true);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableStudents.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(FormStudentManagement.this, "Please chose an student you want to edit", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String studentID = String.valueOf(tableStudents.getValueAt(row, 1));
                    Student student = studentController.getStudentById(studentID);
                    studentIDField.setText(student.getStudentId());
                    studentIDField.setEnabled(false);
                    studentEmail.setText(student.getEmail());
                    studentName.setText(student.getName());
                    if (student.isGender()) {
                        maleRadioButton.setSelected(true);
                    } else {
                        femaleRadioButton.setSelected(true);
                    }
                    major.setText(student.getMajor());
                    studentGPA.setText(String.valueOf(student.getGpa()));
                    studentPoint.setText(String.valueOf(student.getTrainingPoint()));
                    image.setIcon(new ImageIcon(student.getPicture()));
                }
                StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
                tableStudents.setModel(model);
                tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonSave.getText().equals("Save")) {
                    int confirm = JOptionPane.showConfirmDialog(FormStudentManagement.this, "You want to save this student");
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            Student student = new Student(studentIDField.getText(),
                                                            studentEmail.getText(),
                                                            studentName.getText(),
                                                            maleRadioButton.isSelected(),
                                                            major.getText(),
                                                            Double.parseDouble(studentGPA.getText()),
                                                            Integer.parseInt(studentPoint.getText()),
                                                            getImageBytes((ImageIcon)image.getIcon())
                                                            );
                            studentController.updateStudent(student);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    clearField();
                    StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
                    tableStudents.setModel(model);
                    tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));
                }

            }
        });
    }
    private void addImageToCell(Workbook workbook, Sheet sheet, int rowIndex, int columnIndex, ImageIcon imageIcon) {
        try {

            byte[] imageBytes = getImageBytes(imageIcon);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
            anchor.setCol1(columnIndex);
            anchor.setRow1(rowIndex);
            anchor.setCol2(columnIndex + 1);
            anchor.setRow2(rowIndex + 1);

            Picture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getImageBytes(ImageIcon imageIcon) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Image image = imageIcon.getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();

            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        }
    }

    private void clearField() {
        studentIDField.setText("");
        studentIDField.setEnabled(true);
        studentEmail.setText("");
        studentName.setText("");
        major.setText("");
        studentGPA.setText("");
        studentPoint.setText("");
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("static/images/add-icon.png");
        image.setIcon(new ImageIcon(resource));
    }
}


