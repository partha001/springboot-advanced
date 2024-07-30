package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.repositories.BondInterestPayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.Locale;
import java.util.stream.IntStream;


@Log4j2
@Service
public class MyService {

    @Autowired
    BondInterestPayoutRepository bondInterestPayoutRepository;

    public void test(){
        Double totalInvestmentAmt = 2413412.85;
        log.info("total investment amt:{}", totalInvestmentAmt);

        int fromYear= 2023;
        int toYear = 2024;

        IntStream.rangeClosed(2024,2027).forEach(year -> {

            IntStream.rangeClosed(1,12).forEach(monthNumber -> {
                int daysInMonth = daysInMonth(year,monthNumber);

                String inclusiveDateRange  = String.valueOf(year) +
                        "-" + String.format("%02d", monthNumber) +
                        "-" + String.format("%02d", daysInMonth);
                //log.info("year:{} month:{} daysInMonth:{} endDate:{}",year, monthNumber,daysInMonth,inclusiveDateRange);


                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = format.parse(inclusiveDateRange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

                Double principalPaidTillDate =  bondInterestPayoutRepository.getPrincipalPaidTillDate(date);

                String formattedPrincipalPaidTillDate  = String.format("%.2f", principalPaidTillDate);
                String formattedBalanceInvestedAmount = String.format("%.2f",totalInvestmentAmt-principalPaidTillDate);
                log.info("endDate{}  totalInvestedAmt:{} principalPaidBackTillDate:{} balanceInvestedAmount:{}",
                        inclusiveDateRange,
                        String.format("%.2f",totalInvestmentAmt),
                        formattedPrincipalPaidTillDate,
                        (formattedBalanceInvestedAmount));
            });
        });

        //findLengthOfMonthByYearAndMonth(2020,2);

    }



    private static boolean isLeapYear(int year){
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }


    private static int daysInMonth(int year, int monthNumber){
        boolean isLeapYear =  isLeapYear(year);
        if(monthNumber==1)
            return 31;
        if(monthNumber==2 && !isLeapYear)
            return 28;
        if(monthNumber == 2)
            return 29;
        if(monthNumber==3)
            return 31;
        if(monthNumber==4)
            return 30;
        if(monthNumber==5)
            return 31;
        if(monthNumber==6)
            return 30;
        if(monthNumber==7)
            return 31;
        if(monthNumber==8)
            return 31;
        if(monthNumber==9)
            return 30;
        if(monthNumber==10)
            return 31;
        if(monthNumber==11)
            return 30;
        if(monthNumber==12)
            return 31;
        throw new RuntimeException("not a vaid month number");

    }


}
