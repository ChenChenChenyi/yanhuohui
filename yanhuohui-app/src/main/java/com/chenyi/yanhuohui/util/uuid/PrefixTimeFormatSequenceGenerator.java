package com.chenyi.yanhuohui.util.uuid;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrefixTimeFormatSequenceGenerator implements FormatSequenceGenerator{
    private SimpleDateFormat simpleDateFormat;

    @Setter
    @Getter
    private NumberSequenceGenerator generator;

    @Setter
    @Getter
    private String prefix;

    @Setter
    @Getter
    private int validLength;

    @Getter
    private String timeFormat;

    public void setTimeFormat(String timeFormat){
        this.timeFormat = timeFormat;
        this.simpleDateFormat = new SimpleDateFormat(timeFormat);
    }

    private String printNext(String prefix, int validLength, NumberSequenceGenerator generator){
        if(validLength <=0){
            throw new IllegalArgumentException("validLength should greater than 0!");
        }
        String timeStamp = simpleDateFormat.format(new Date());
        String numberSequenceStr = String.valueOf(generator.getNumberSequence());
        StringBuilder sequence = new StringBuilder();
        sequence.append(prefix).append(timeStamp);
        if(numberSequenceStr.length() < validLength){
            int distance = validLength - numberSequenceStr.length();
            while (distance-- > 0){
                sequence.append(0);
            }
        }
        if(numberSequenceStr.length() > validLength){
            numberSequenceStr = numberSequenceStr.substring(numberSequenceStr.length() - validLength);
        }
        sequence.append(numberSequenceStr);
        return sequence.toString();
    }

    @Override
    public String getSequence() {
        return printNext(prefix, validLength, generator);
    }
}
