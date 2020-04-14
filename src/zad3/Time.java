/**
 * @author Baka Krzysztof S16696
 */

package zad3;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time {

    public static String passed(String s, String s1) {
        boolean fatalError = false;
        String toRet = "";
        String exceptionMsg = "*** ";

        String fistDateString = "";
        String secondDateString = "";


        Pattern patternDateTime = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}T");
        Pattern patternDate = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}$");

        Matcher matcher = patternDateTime.matcher(s);
        Matcher matcher2 = patternDate.matcher(s);

        Matcher matcher3 = patternDateTime.matcher(s1);
        Matcher matcher4 = patternDate.matcher(s1);

        LocalDateTime firstDateTime = null;
        boolean firstDateTimeBoolean = false;

        LocalDateTime secondDateTime = null;
        boolean secondDateTimeBoolean = false;


        LocalDate fistDate = null;
        boolean fistDateBoolean = false;

        LocalDate secondDate = null;
        boolean secondDateBoolean = false;


        if (matcher.find()) {
            try {
                firstDateTime = LocalDateTime.parse(s);
                firstDateTimeBoolean = true;
                fistDate = firstDateTime.toLocalDate();
                String day = " (" + firstDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + ") ";

                ZonedDateTime zonedDateTimeFirst = ZonedDateTime.of(firstDateTime, ZoneId.of("Europe/Warsaw"));

                String mins = "";
                if (firstDateTime.getMinute() < 10) {
                    mins = "0" + zonedDateTimeFirst.getMinute();
                } else {
                    mins = Integer.toString(zonedDateTimeFirst.getMinute());
                }
                String hour = "godz. " + zonedDateTimeFirst.getHour() + ":" + mins + " ";
                fistDateString = zonedDateTimeFirst.getDayOfMonth() + " " + Month.of(zonedDateTimeFirst.getMonthValue()).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + " " + zonedDateTimeFirst.getYear() + day + hour;
            } catch (DateTimeParseException e) {
                fatalError = true;
                exceptionMsg += e.toString();
            }
        } else if (matcher2.find()) {
            try {
                fistDate = LocalDate.parse(s);
                fistDateBoolean = true;
                String day = " (" + fistDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + ") ";
                fistDateString = fistDate.getDayOfMonth() + " " + Month.of(fistDate.getMonthValue()).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + " " + fistDate.getYear() + day;
            } catch (DateTimeParseException e) {
                fatalError = true;
                exceptionMsg += e.toString();
            }
        }


        if (matcher3.find()) {
            try {
                secondDateTime = LocalDateTime.parse(s1);
                secondDateTimeBoolean = true;
                secondDate = secondDateTime.toLocalDate();
                String day = " (" + secondDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + ") ";
                ZonedDateTime zonedDateTimeSecond = ZonedDateTime.of(secondDateTime, ZoneId.of("Europe/Warsaw"));
                String mins = "";
                if (secondDateTime.getMinute() < 10) {
                    mins = "0" + secondDateTime.getMinute();
                } else {
                    mins = Integer.toString(secondDateTime.getMinute());
                }
                String hour = "godz. " + secondDateTime.getHour() + ":" + mins + " ";
                secondDateString = zonedDateTimeSecond.getDayOfMonth() + " " + Month.of(zonedDateTimeSecond.getMonthValue()).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + " " + zonedDateTimeSecond.getYear() + day + hour;
            } catch (DateTimeParseException e) {
                fatalError = true;
                exceptionMsg += e.toString();
            }


        } else if (matcher4.find()) {
            try {
                secondDate = LocalDate.parse(s1);
                String day = " (" + secondDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + ") ";
                secondDateString = secondDate.getDayOfMonth() + " " + Month.of(secondDate.getMonthValue()).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")) + " " + secondDate.getYear() + day;
                secondDateBoolean = true;
            } catch (DateTimeParseException e) {
                fatalError = true;
                exceptionMsg += e.toString();
            }
        }

        //DateTime
        if (firstDateTimeBoolean && secondDateTimeBoolean) {
            DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
            ZonedDateTime zonedDateTimeFirst = ZonedDateTime.of(firstDateTime, ZoneId.of("Europe/Warsaw"));
            ZonedDateTime zonedDateTimeSecond = ZonedDateTime.of(secondDateTime, ZoneId.of("Europe/Warsaw"));
            Period p = Period.between(zonedDateTimeFirst.toLocalDate(), zonedDateTimeSecond.toLocalDate());

            long days = ChronoUnit.DAYS.between(fistDate, secondDate);
            long hours = Duration.between(zonedDateTimeFirst, zonedDateTimeSecond).toHours();
            long mins = Duration.between(zonedDateTimeFirst, zonedDateTimeSecond).toMinutes();


            double daysCheck = (ChronoUnit.HALF_DAYS.between(zonedDateTimeFirst, zonedDateTimeSecond)) / 2.00;

            double weeks = ChronoUnit.DAYS.between(fistDate, secondDate) / 7.00;
            if (daysCheck != days) {
            }

            String kalendarzowo = "";
            if (p.getYears() != 0) {
                if (p.getYears() == 1) {
                    kalendarzowo += p.getYears() + " rok";
                } else {
                    kalendarzowo += p.getYears() + " lata";
                }
            }

            if (p.getMonths() != 0) {
                if(!kalendarzowo.equals("")){
                    kalendarzowo+= ", ";
                }
                if (p.getMonths() == 1) {
                    kalendarzowo += p.getMonths() + " miesiąc";
                } else {
                    kalendarzowo += p.getMonths() + " miesięcy";
                }
            }

            if (p.getDays() != 0) {
                if(!kalendarzowo.equals("")){
                    kalendarzowo+= ", ";
                }
                if (p.getDays() == 1) {
                    kalendarzowo += p.getDays() + " dzień";
                } else {
                    kalendarzowo += p.getDays() + " dni";
                }
            }

            toRet = "Od " + fistDateString + "do " + secondDateString + "\n" +
                    " - mija: " + days + " dni, tygodni " + df.format(weeks) + "\n" +
                    " - godzin: " + hours + ", minut: " + mins + "\n" +
                    " - kalendarzowo: " + kalendarzowo

            ;
        } else if (fistDateBoolean && secondDateBoolean && fatalError == false) {
            DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
            Period p = Period.between(fistDate, secondDate);
            long days = ChronoUnit.DAYS.between(fistDate, secondDate);

            double weeks = ChronoUnit.DAYS.between(fistDate, secondDate) / 7.00;

            String kalendarzowo = "";
            if (p.getYears() != 0) {
                if (p.getYears() == 1) {
                    kalendarzowo += p.getYears() + " rok";
                } else {
                    kalendarzowo += p.getYears() + " lata";
                }
            }

            if (p.getMonths() != 0) {
                if(!kalendarzowo.equals("")){
                    kalendarzowo+= ", ";
                }
                if (p.getMonths() == 1) {
                    kalendarzowo += p.getMonths() + " miesiąc";
                } else {
                    kalendarzowo += p.getMonths() + " miesięce";
                }
            }

            if (p.getDays() != 0) {
                if(!kalendarzowo.equals("")){
                    kalendarzowo+= ", ";
                }
                if (p.getDays() == 1) {
                    kalendarzowo += p.getDays() + " dzień";
                } else {
                    kalendarzowo += p.getDays() + " dni";
                }
            }

            toRet = "Od " + fistDateString + "do " + secondDateString + "\n" +
                    " - mija: " + days + " dni, tygodni " + df.format(weeks) + "\n" +
                    " - kalendarzowo: " + kalendarzowo;
        }

        if (fatalError == false) {
            return toRet;
        } else {
            return exceptionMsg;
        }
    }

}
