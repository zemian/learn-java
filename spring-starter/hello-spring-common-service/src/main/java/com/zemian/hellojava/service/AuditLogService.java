package com.zemian.hellojava.service;

import com.zemian.hellojava.data.dao.*;
import com.zemian.hellojava.data.domain.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A Service to access AuditLog.
 *
 * This class is generated by Zemian's CodeGen Toolbox on Jan 2, 2018.
 */
@Service
@Transactional
public class AuditLogService {
    @Autowired
    private AuditLogDAO auditLogDAO;

    public void create(AuditLog auditLog) {
        auditLogDAO.create(auditLog);
    }

    public void update(AuditLog auditLog) {
        auditLogDAO.update(auditLog);
    }

    public AuditLog get(Integer logId) {
        return auditLogDAO.get(logId);
    }

    public void delete(Integer logId) {
        auditLogDAO.delete(logId);
    }

    public boolean exists(Integer logId) {
        return auditLogDAO.exists(logId);
    }

    public List<AuditLog> findAll() {
        return auditLogDAO.findAll();
    }

    public PagingList<AuditLog> find(Paging paging) {
        return auditLogDAO.find(paging);
    }
}
