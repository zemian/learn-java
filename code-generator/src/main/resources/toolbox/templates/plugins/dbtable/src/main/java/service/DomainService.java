<#assign keyField = domain.keyFields?first>
package ${service.packageName};

import ${domain.packageName}.*;
import ${dao.packageName}.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A Service to access ${domain.className}.
 *
 * This class is generated by Zemian's CodeGen Toolbox on ${todayDt?date}.
 */
@Service
@Transactional
public class ${service.className} {
    @Autowired
    private ${dao.className} ${dao.classVar};

    public void create(${domain.className} ${domain.classVar}) {
        ${dao.classVar}.create(${domain.classVar});
    }

    public void update(${domain.className} ${domain.classVar}) {
        ${dao.classVar}.update(${domain.classVar});
    }

    public ${domain.className} get(${keyField.type} ${keyField.name}) {
        return ${dao.classVar}.get(${keyField.name});
    }

    public void delete(${keyField.type} ${keyField.name}) {
        ${dao.classVar}.delete(${keyField.name});
    }

    public boolean exists(${keyField.type} ${keyField.name}) {
        return ${dao.classVar}.exists(${keyField.name});
    }

    public List<${domain.className}> findAll() {
        return ${dao.classVar}.findAll();
    }

    public PagingList<${domain.className}> find(Paging paging) {
        return ${dao.classVar}.find(paging);
    }
}
