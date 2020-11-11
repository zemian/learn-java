package com.zemian.toolbox.app;

import com.zemian.toolbox.support.CmdOpts;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple grep tool to filter output by time gap between two lines. We assume
 * the time YYYY-mm-dd HH:MM:SS format is used in beginning of each line. This
 * tool is useful for analysing log output to try identify problem within a timeframe.
 *
 * Usage example:
 *
 * # Print logs that are greater or equal to 1 sec gap
 * GrepTimeGap --timeGap=1000 my.log
 *
 * # Or you can grep from STDIN input
 * cat my.log | GrepTimeGap --timeGap=1000
 *
 * # If you have log file that has just TIME prefix, you can change the time format
 * # The timeFormatIndex is zero based index to the time data by spliting line with
 * # space.
 * GrepTimeGap --timeGap=1000 --timeFormat=HH:mm --timeFormatIndex=0
 *
 * Created by Zemian Deng on 2017-07-01
 */
public class GrepTimeGap implements Command {
    @Override
    public void run(CmdOpts opts) throws Exception {
        long timeGap = opts.getLongOpt("timeGap", 1000L);
        String timeFormat = opts.getOpt("timeFormat", "HH:mm:ss");
        boolean timeParserLenient = opts.getBooleanOpt("timeParserLenient", false);
        int timeFormatIndex = opts.getIntOpt("timeFormatIndex", 1);

        BufferedReader inputReader;
        if (opts.getArgsSize() == 0) {
            inputReader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            String file = opts.getArg(0);
            inputReader = new BufferedReader(new FileReader(file));
        }

        try {
            String line;
            SimpleDateFormat df = new SimpleDateFormat(timeFormat);
            df.setLenient(timeParserLenient);
            Date lastDt = new Date(0); // compare to first time entry as epoch
            while ((line = inputReader.readLine()) != null) {
                String[] words = StringUtils.split(line, " ");
                long logTimeGap = 0L;
                if (words.length > timeFormatIndex) {
                    String time = words[timeFormatIndex];
                    try {
                        Date dt = df.parse(time);
                        logTimeGap = dt.getTime() - lastDt.getTime();
                        lastDt = dt;
                    } catch (Exception e) {
                        // Ignore. it means 2nd word is not time format
                    }
                }
                if (logTimeGap >= timeGap) {
                    String gap = DurationFormatUtils.formatDurationHMS(logTimeGap);
                    System.out.println(gap + "|" + line);
                }
            }
        } finally {
            inputReader.close();
        }
    }
}
