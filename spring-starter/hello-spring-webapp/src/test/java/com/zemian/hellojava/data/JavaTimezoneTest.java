package com.zemian.hellojava.data;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class JavaTimezoneTest {
    @Test
    public void jdkDateTimeZone() throws Exception {
        // NOTES:
        // The java.util.Date does not have timezone associated with it, however the toString() method
        // will use system default timezone to render a string with timezone information.
        //
        // To correctly convert a java.utilDate into a different timezone for display, you need to
        // set the SipmleDateFormatter.setTimeZone().
        //
        // TimeZone GMT = GMT = +0000
        //                EST = -0500
        //
        // Epoch = January 1, 1970, 00:00:00 GMT (format = "MMMM dd, yyyy HH:mm:ss z")
        // Epoch = 1970-01-01T00:00:00+0000 (format = "yyyy-MM-dd'T'HH:mm:ssZ")
        // Note that the term "Epoch" means a instant in time that include a timezone (GMT) reference.
        //
        // Technically GMT (Coordinated Universal Time) is not a timezone but a standard, but it
        // is equavalent to GMT. The GMT is an actual timezone name. The "Z" letter is also used to described
        // GMT/UTC timezone in ISO formatting string.
        //

        System.out.println("Local timezone: " + TimeZone.getDefault());

        Date dt = new Date(0);
        System.out.println("dt.getTime()=" + dt.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        System.out.println("dt with local timezone: " + df.format(dt));

        SimpleDateFormat utcDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        utcDf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("dt with GMT timezone: " + utcDf.format(dt));

        // Creating a date in another timezone
        // Note that this is not the true Epoch anymore since pstDt.getTime() is NOT zero!
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        cal.set(1970, 0, 1, 0, 0, 0);
        Date pstDt = cal.getTime();
        System.out.println("psDt.getTime()=" + pstDt.getTime());
        SimpleDateFormat pstDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        pstDf.setTimeZone(TimeZone.getTimeZone("PST"));
        System.out.println("pstDt with PST timezone: " + pstDf.format(pstDt));
        System.out.println("pstDt with GMT timezone: " + utcDf.format(pstDt));
        System.out.println("pstDt with local timezone: " + df.format(pstDt));
    }

    @Test
    public void java8TimeZone() {
        // NOTES:
        //
        // The word "local" does not means local timezone in java.time package! In fact it seems to means without
        // timezone for most cases.
        //
        // The LocalDateTime is same as java.util.Date equivalent, in that it does not keep
        // track of timezone! The equivalent of Calendar is now ZonedDateTime

        LocalDateTime dt = LocalDateTime.now();
        System.out.println("dt=" + dt);
        System.out.println("dt ISO_DATE_TIME=" + dt.format(DateTimeFormatter.ISO_DATE_TIME)); // Print timezone if available
        System.out.println("dt ISO_LOCAL_DATE_TIME=" + dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // Always print without timezone

        // Note that you can't ask to format with timezone pattern!
        //System.out.println("dt ISO_LOCAL_DATE_TIME=" + dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")));
        //System.out.println("dt ISO_OFFSET_DATE_TIME=" + dt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        dt = LocalDateTime.parse("1970-01-01T00:00:00"); // parsing date string is STRICT, use "01" instead of "1".
        System.out.println("dt ISO_LOCAL_DATE_TIME=" + dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // Now new timezone object
        ZonedDateTime dtTz = ZonedDateTime.now();
        System.out.println("dtTz=" + dtTz);
        System.out.println("dtTz ISO_DATE_TIME=" + dtTz.format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("dtTz ISO_OFFSET_DATE_TIME=" + dtTz.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        dtTz = ZonedDateTime.parse("1970-01-01T00:00:00+00:00");
        System.out.println("dtTz ISO_OFFSET_DATE_TIME=" + dtTz.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        dtTz = ZonedDateTime.parse("1970-01-01T00:00:00-05:00[America/New_York]");
        System.out.println("dtTz (EST) ISO_OFFSET_DATE_TIME=" + dtTz.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Test
    public void java8Instant() {
        // NOTES:
        //
        // The Instant has timezone data in it! It is used to track a point in time in a Calendar base time-line
        // By default the timezone is GMT for Instant object. You can force it to to other ZoneId using atZone().
        //
        // An Instant is equivalent of System.currentTimeMillis() + TimeZone.getDefault(), which can be
        // generate by system Clock object. Hence clock contains timezone information as well.
        //
        // The java.util.Date only holds up to milliseconds precision, while Instant can hold up to nanosecon
        // precision.
        //

        Instant inst = Instant.now();
        System.out.println("Instant inst=" + inst);
        System.out.println("Instant GMT inst=" + inst.atZone(ZoneId.of("+00:00")));
        System.out.println("Instant EST inst=" + inst.atZone(ZoneId.of("-05:00")));

        ZonedDateTime dt = ZonedDateTime.ofInstant(inst, ZoneId.of("GMT"));
        System.out.println("ZonedDateTime from Instant ISO_DATE_TIME=" + DateTimeFormatter.ISO_DATE_TIME.format(dt));

        dt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("GMT"));
        System.out.println("ZonedDateTime from Epoch Instant ISO_DATE_TIME=" + DateTimeFormatter.ISO_DATE_TIME.format(dt));

        Clock c = Clock.systemUTC();
        System.out.println("Clock GMT c=" + c);
        System.out.println("Clock GMT c=" + Instant.now(c));
        System.out.println("Clock GMT at zoneId c=" + Instant.now(c).atZone(ZoneId.of("+00:00")));

        c = Clock.system(ZoneId.of("-05:00"));
        System.out.println("Clock EST c=" + c);
        System.out.println("Clock EST c=" + Instant.now(c));
        System.out.println("Clock EST at zoneId c=" + Instant.now(c).atZone(ZoneId.of("-05:00")));
    }

    @Test
    public void listOfTimeZone() {
        int count = 0;
        for (String id : TimeZone.getAvailableIDs()) {
            TimeZone tz = TimeZone.getTimeZone(id);
            System.out.println(tz);
            count++;
        }
        System.out.println(count + " entries found.");
    }

    @Test
    public void listOfZoneIds() {
        int count = 0;
        for (String id : ZoneId.getAvailableZoneIds()) {
            ZoneId zone = ZoneId.of(id);
            System.out.println("Timezone=" + zone + ", normalized=" + zone.normalized());
            count++;
        }
        System.out.println(count + " entries found.");
    }

    @Test
    public void listOfZoneIds2() {
        int count = 0;
        for (Map.Entry<String, String> entry : ZoneId.SHORT_IDS.entrySet()) {
            System.out.println(entry);
            count++;
        }
        System.out.println(count + " entries found.");
    }

    @Test
    public void zoneIds() {
        System.out.println(ZoneId.of("UTC"));
        System.out.println(ZoneId.of("GMT"));
        System.out.println(ZoneId.of("EST", ZoneId.SHORT_IDS));
        System.out.println(ZoneId.of("CST", ZoneId.SHORT_IDS));
        System.out.println(ZoneId.of("MST", ZoneId.SHORT_IDS));
        System.out.println(ZoneId.of("PST", ZoneId.SHORT_IDS));
    }

    @Test
    public void listOfZoneOffsets() {
        for (int i = -18; i < 18; i++) {
            ZoneOffset offset = ZoneOffset.ofHours(i);
            System.out.println("Offset=" + offset + ", displayName=" + offset.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }
    }

    @Test
    public void listOfZoneIdsWithOffsets() {
        for (int i = -18; i < 18; i++) {
            ZoneOffset offset = ZoneOffset.ofHours(i);
            ZoneId zone = ZoneId.ofOffset("UTC", offset);
            System.out.println("zone=" + zone);
        }
    }

    @Test
    public void listOfLocales() {
        int count = 0;
        for (Locale locale : Locale.getAvailableLocales()) {
            System.out.println(
                    "Locale Name=" + locale.getDisplayName() +
                    ", Country=" + locale.getCountry() +
                    ", Language=" + locale.getLanguage());
            count++;
        }
        System.out.println(count + " entries found.");
    }
}
