package org.thd.Models;

import org.thd.DAO.CertificateDAO;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class CertificateTableModel extends AbstractTableModel {
    private List<Certificate> certificateList;
    private String[] columnNames = {"ID", "Title", "Score", "Issued Date", "Expiration Date"};

    public CertificateTableModel(String studentId) {
        CertificateDAO dao = new CertificateDAO();
        certificateList = dao.readAllByStudentId(studentId);
    }
    @Override
    public int getRowCount() {
        return certificateList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Certificate certificate = certificateList.get(rowIndex);
        String pattern = "MMMM d, yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        switch (columnIndex) {
            case 0:
                return certificate.getCertificateId();
            case 1:
                return certificate.getTitle();
            case 2:
                return certificate.getScore();
            case 3:
                return dateFormat.format(certificate.getIssuedDate());
            case 4:
                return certificate.getExpirationDate() == null
                        ? "No Expiration" : dateFormat.format(certificate.getExpirationDate());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
