package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.app.CodeGen;
import com.zemian.toolbox.codegen.TemplateContext;

public interface Plugin {
    String getName();
    String getHelp();
    boolean isEnabled();
    void init(CodeGen codeGen) throws Exception;
    void destroy() throws Exception;
    void generate(TemplateContext templateContext) throws Exception;
    void beforeTemplateSet() throws Exception;
    void afterTemplateSet() throws Exception;
}
