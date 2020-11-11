package com.zemian.toolbox.app;

import com.zemian.toolbox.support.CmdOpts;

public interface Command {
    void run(CmdOpts opts) throws Exception;
}
