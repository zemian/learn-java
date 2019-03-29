package com.zemian.hellojava.freemarker;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

import java.time.LocalDateTime;

public class Java8DatesWrapper extends DefaultObjectWrapper {
    public Java8DatesWrapper(Version incompatibleImprovements) {
        super(incompatibleImprovements);
    }

    @Override
    protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
        if (obj instanceof LocalDateTime) {
            return new Java8DatesAdapter(this, (LocalDateTime)obj);
        }

        return super.handleUnknownType(obj);
    }
}
