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

                            boolean gender = row.getCell(4).getStringCellValue().equals("Nam")
                                    || row.getCell(4).getStringCellValue().equals("Male");

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
                        filePath += ".xlsx"; // Ensure the file has the .xlsx extension
                    }

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("Students");
                    sheet.setColumnWidth(0, 6400);
                    // Create header row
                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(model.getColumnName(i));
                    }

                    // Populate data
                    for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                        Row dataRow = sheet.createRow(rowIndex + 1);

                        dataRow.setHeightInPoints(193);

                        // Populate cells with student data
                        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
                            Cell cell = dataRow.createCell(columnIndex);
                            Object value = model.getValueAt(rowIndex, columnIndex);

                            if (value != null) {
                                if (value instanceof String) {
                                    cell.setCellValue((String) value);
                                } else if (value instanceof ImageIcon) {
                                    addImageToCell(workbook, sheet, rowIndex + 1, columnIndex, (ImageIcon) value);
                                }
                                // Add more conditions for other data types if necessary
                            }
                        }
                    }

                    // Save the workbook to the specified file path
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
}


