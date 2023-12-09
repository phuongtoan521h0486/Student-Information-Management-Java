package org.thd.Controllers;

import org.thd.DAO.CertificateDAO;
import org.thd.Models.Certificate;
import java.util.List;

public class CertificateController {
    private CertificateDAO certificateDAO;

    public CertificateController() {
        certificateDAO = new CertificateDAO();
    }

    public int addCertificate(Certificate certificate) {
        return certificateDAO.add(certificate);
    }

    public List<Certificate> getAllCertificates() {
        return certificateDAO.readAll();
    }

    public Certificate getCertificateById(int certificateId) {
        return certificateDAO.read(certificateId);
    }

    public boolean updateCertificate(Certificate certificate) {
        return certificateDAO.update(certificate);
    }

    public boolean deleteCertificate(int certificateId) {
        return certificateDAO.delete(certificateId);
    }
}

