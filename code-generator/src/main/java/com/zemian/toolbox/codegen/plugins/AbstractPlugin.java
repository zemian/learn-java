package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.app.CodeGen;
import com.zemian.toolbox.codegen.TemplateContext;

public abstract class AbstractPlugin implements Plugin {
    protected CodeGen codeGen;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public String getHelp() {
        return "";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void init(CodeGen codeGen) throws Exception {
        this.codeGen = codeGen;
        init();
    }

    protected void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void generate(TemplateContext templateContext) throws Exception {

    }

    @Override
    public void beforeTemplateSet() throws Exception {

    }

    @Override
    public void afterTemplateSet() throws Exception {

    }

    @Override
    public String toString() {
        return "Plugin{" + getName() + "}";
    }
}
