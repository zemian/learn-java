package com.zemian.hellojava.freemarker;

import freemarker.template.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Java8DatesAdapter extends WrappingTemplateModel implements TemplateScalarModel, AdapterTemplateModel {
    private LocalDateTime localDateTime;

    public Java8DatesAdapter(ObjectWrapper objectWrapper, LocalDateTime localDateTime) {
        super(objectWrapper);
        this.localDateTime = localDateTime;
    }

    @Override
    public Object getAdaptedObject(Class<?> hint) {
        return localDateTime;
    }

    @Override
    public String getAsString() throws TemplateModelException {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
