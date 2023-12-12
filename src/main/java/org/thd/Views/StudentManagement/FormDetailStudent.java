package org.thd.Views.StudentManagement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.JDatePicker;
import org.thd.Controllers.CertificateController;
import org.thd.Models.*;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class FormDetailStudent extends JFrame{
    private JPanel panelMain;
    private JTabbedPane tabbedPane;
    private JTable tableCertificates;
    private JLabel labelName;
    private JLabel picture;
    private JLabel labelID;
    private JLabel labelEmail;
    private JLabel labelGender;
    private JLabel labelMajor;
    private JLabel labelGpaNTrainingPoint;
    private JTextField textFieldTitle;
    private JTextField textFieldScore;
    private JCheckBox noExpirationCheckBox;
    private JButton importButton;
    private JButton exportButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton saveButton;
    private JTable tableCertificatesManagement;
    private JDatePicker datePickerIssued;
    private JDatePicker datePickerExpiration;

    private CertificateController certificateController;
    private boolean isEditing;
    private int currentId;

    public FormDetailStudent(Student student, Account user) {
        if (user.getRole().equals("Employee")) {
            importButton.setVisible(false);
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
            saveButton.setVisible(false);
            textFieldTitle.setEnabled(false);
            textFieldScore.setEnabled(false);
            datePickerIssued.setEnabled(false);
            datePickerExpiration.setEnabled(false);
        }

        setTitle("Detail of " + student.getName());
        setSize(720, 720);
        setLocationRelativeTo(null);

        certificateController = new CertificateController();
        isEditing = false;

        labelName.setText(student.getName());
        labelID.setText(student.getStudentId());
        labelEmail.setText("Email: " + student.getEmail());
        labelGender.setText("Gender: " + (student.isGender() ? "Male": "Female"));
        labelMajor.setText("Major: " + student.getMajor());
        labelGpaNTrainingPoint.setText(String.format("GPA: %.2f Training Point: %d",
                student.getGpa(), student.getTrainingPoint()));

        ImageIcon originalIcon = new ImageIcon(student.getPicture());
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(145, 193, Image.SCALE_SMOOTH);
        picture.setIcon(new ImageIcon(resizedImage));

        CertificateTableModel model = new CertificateTableModel(student.getStudentId());
        tableCertificates.setModel(model);
        tableCertificatesManagement.setModel(model);

        LocalDateTime currentDate = LocalDateTime.now();
        datePickerIssued.getJDateInstantPanel()
                .getModel()
                .setDate( currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
        datePickerIssued.getJDateInstantPanel()
                .getModel()
                .setSelected(true);
        datePickerExpiration.getJDateInstantPanel()
                .getModel()
                .setDate(currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
        datePickerExpiration.getJDateInstantPanel()
                .getModel()
                .setSelected(true);

        add(panelMain);
        noExpirationCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //datePickerExpiration.setEnabled(!noExpirationCheckBox.isSelected());
            }
        });
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("data", "csv", "txt", "xlsx", "xlsm");
                fileChooser.setFileFilter(csvFilter);
                fileChooser.setMultiSelectionEnabled(false);

                int isSelected = fileChooser.showDialog(null, "Select file");
                if (isSelected == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        XSSFWorkbook myExcelFile = new XSSFWorkbook(new FileInputStream(file));
                        XSSFSheet ws = myExcelFile.getSheetAt(0);
                        Iterator<Row> rowIterator = ws.iterator();

                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();


                            Certificate certificate = new Certificate(
                                    row.getCell(0).getStringCellValue(),
                                    row.getCell(1).getNumericCellValue(),
                                    row.getCell(2).getDateCellValue(),
                                    row.getCell(3) != null ?
                                            row.getCell(3).getDateCellValue() : null,
                                    student.getStudentId()
                            );
                            certificateController.addCertificate(certificate);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                CertificateTableModel model = new CertificateTableModel(student.getStudentId());
                tableCertificatesManagement.setModel(model);
            }
        });
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CertificateTableModel model = new CertificateTableModel(student.getStudentId());
                tableCertificates.setModel(model);
                tableCertificatesManagement.setModel(model);
            }
        });

        exportButton.addActionListener(new ActionListener() {
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
                    Sheet sheet = workbook.createSheet("Certificates");

                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(model.getColumnName(i));
                    }


                    for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                        Row dataRow = sheet.createRow(rowIndex + 1);

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
                                }
                                else if (value instanceof Date) {
                                    cell.setCellValue((Integer) value);
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = textFieldTitle.getText();
                double score = 0;
                if (textFieldScore.getText().isEmpty() || textFieldScore.getText() == null ||
                textFieldTitle.getText().isEmpty() || textFieldTitle.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Please submit enough valid information", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else {
                    try {
                        score = Double.parseDouble(textFieldScore.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Not valid score", "Error", JOptionPane.ERROR_MESSAGE);
                        textFieldScore.setText("");
                        return;
                    }
                }
                Date issuedDate = new Date(
                        datePickerIssued.getJDateInstantPanel().getModel().getYear() - 1900,
                        datePickerIssued.getJDateInstantPanel().getModel().getMonth(),
                        datePickerIssued.getJDateInstantPanel().getModel().getDay()
                );


                Date expirationDate = null;
                if (!noExpirationCheckBox.isSelected()) {
                    expirationDate = new Date(
                            datePickerExpiration.getJDateInstantPanel().getModel().getYear() - 1900,
                            datePickerExpiration.getJDateInstantPanel().getModel().getMonth(),
                            datePickerExpiration.getJDateInstantPanel().getModel().getDay()
                    );

                    if (expirationDate.getTime() < issuedDate.getTime()) {
                        JOptionPane.showMessageDialog(null, "Expiration Date must greater than Issued Date", "Error", JOptionPane.ERROR_MESSAGE);
                        datePickerIssued.getJDateInstantPanel()
                                .getModel()
                                .setDate( currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
                        datePickerIssued.getJDateInstantPanel()
                                .getModel()
                                .setSelected(true);
                        datePickerExpiration.getJDateInstantPanel()
                                .getModel()
                                .setDate(currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
                        datePickerExpiration.getJDateInstantPanel()
                                .getModel()
                                .setSelected(true);
                        return;
                    }
                }

                Certificate certificate = new Certificate(title, score, issuedDate, expirationDate, student.getStudentId());
                Integer certificateId = certificateController.addCertificate(certificate);

                if (certificateId != null) {
                    JOptionPane.showMessageDialog(null, "Certificate added successfully. Certificate ID: " + certificateId);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to added certificate.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                CertificateTableModel model = new CertificateTableModel(student.getStudentId());
                tableCertificates.setModel(model);
                tableCertificatesManagement.setModel(model);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableCertificatesManagement.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please chose an certificate you want to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "You want to delete this certificate");
                    if (confirm == JOptionPane.YES_OPTION) {
                        int certificateId = Integer.parseInt(String.valueOf(tableCertificatesManagement.getValueAt(row, 0)));
                        certificateController.deleteCertificate(certificateId);
                        CertificateTableModel model = new CertificateTableModel(student.getStudentId());
                        tableCertificates.setModel(model);
                        tableCertificatesManagement.setModel(model);
                    }
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentId = editCertificate();
                isEditing = true;
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveButton.getText().equals("Save")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "You want to save this certificate");
                    if (confirm == JOptionPane.YES_OPTION) {
                        String title = textFieldTitle.getText();
                        double score = 0;
                        if (textFieldScore.getText().isEmpty() || textFieldScore.getText() == null ||
                                textFieldTitle.getText().isEmpty() || textFieldTitle.getText() == null) {
                            JOptionPane.showMessageDialog(null, "Please submit enough valid information", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else {
                            try {
                                score = Double.parseDouble(textFieldScore.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Not valid score", "Error", JOptionPane.ERROR_MESSAGE);
                                textFieldScore.setText("");
                                return;
                            }
                        }
                        Date issuedDate = new Date(
                                datePickerIssued.getJDateInstantPanel().getModel().getYear() - 1900,
                                datePickerIssued.getJDateInstantPanel().getModel().getMonth(),
                                datePickerIssued.getJDateInstantPanel().getModel().getDay()
                        );


                        Date expirationDate = null;
                        if (!noExpirationCheckBox.isSelected()) {
                            expirationDate = new Date(
                                    datePickerExpiration.getJDateInstantPanel().getModel().getYear() - 1900,
                                    datePickerExpiration.getJDateInstantPanel().getModel().getMonth(),
                                    datePickerExpiration.getJDateInstantPanel().getModel().getDay()
                            );

                            if (expirationDate.getTime() < issuedDate.getTime()) {
                                JOptionPane.showMessageDialog(null, "Expiration Date must greater than Issued Date", "Error", JOptionPane.ERROR_MESSAGE);
                                datePickerIssued.getJDateInstantPanel()
                                        .getModel()
                                        .setDate( currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
                                datePickerIssued.getJDateInstantPanel()
                                        .getModel()
                                        .setSelected(true);
                                datePickerExpiration.getJDateInstantPanel()
                                        .getModel()
                                        .setDate(currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
                                datePickerExpiration.getJDateInstantPanel()
                                        .getModel()
                                        .setSelected(true);
                                return;
                            }
                        }

                        Certificate certificate = new Certificate(currentId,title, score, issuedDate, expirationDate, student.getStudentId());
                        boolean result = certificateController.updateCertificate(certificate);
                        if (result) {
                            JOptionPane.showMessageDialog(null, "Certificate updated successfully" );
                            clearForm();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to updated certificate.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    clearForm();
                    CertificateTableModel model = new CertificateTableModel(student.getStudentId());
                    tableCertificates.setModel(model);
                    tableCertificatesManagement.setModel(model);
                }

            }


        });
    }

    private void clearForm() {
        textFieldTitle.setText("");
        textFieldScore.setText("");
        LocalDateTime currentDate = LocalDateTime.now();
        datePickerIssued.getJDateInstantPanel()
                .getModel()
                .setDate( currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
        datePickerIssued.getJDateInstantPanel()
                .getModel()
                .setSelected(true);
        datePickerExpiration.getJDateInstantPanel()
                .getModel()
                .setDate(currentDate.getYear(), currentDate.getMonthValue() -1, currentDate.getDayOfMonth());
        datePickerExpiration.getJDateInstantPanel()
                .getModel()
                .setSelected(true);
    }

    private int editCertificate() {
        int selectedRow = tableCertificatesManagement.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableCertificatesManagement.getValueAt(selectedRow, 0);
            Certificate certificate = certificateController.getCertificateById(id);
            textFieldTitle.setText(certificate.getTitle());
            textFieldScore.setText(certificate.getScore().toString());

            datePickerIssued.getJDateInstantPanel().getModel().setDay(certificate.getIssuedDate().getYear());
            datePickerIssued.getJDateInstantPanel().getModel().setMonth(certificate.getIssuedDate().getMonth());
            datePickerIssued.getJDateInstantPanel().getModel().setYear(certificate.getIssuedDate().getYear() + 1900);

            datePickerIssued.getJDateInstantPanel()
                    .getModel()
                    .setSelected(true);

            if (certificate.getExpirationDate() == null) {
                noExpirationCheckBox.setSelected(true);
                datePickerExpiration.setEnabled(false);
            } else {
                noExpirationCheckBox.setSelected(false);
                datePickerExpiration.setEnabled(true);

                datePickerExpiration.getJDateInstantPanel().getModel().setDay(certificate.getExpirationDate().getYear());
                datePickerExpiration.getJDateInstantPanel().getModel().setMonth(certificate.getExpirationDate().getMonth());
                datePickerExpiration.getJDateInstantPanel().getModel().setYear(certificate.getExpirationDate().getYear() + 1900);

            }
            return id;

        }
        return -1;
    }
}
