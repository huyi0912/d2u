package org.d2u.base.shared.util;

import org.apache.ibatis.annotations.Param;

import java.util.Locale;

/**
 *
 * @param message no more than 120 bytes
 * @param locale
 * @param template no more than 184 bytes
 */
public record I18N(Message message,Locale locale,String template) {
    public record Locale(@Param("language") String language,@Param("country") String country){}

    /**
     *
     * @param id no more than 64 bytes
     */
    public record Message(String id){}

    /**
     * localized template with {0},{1},{2},.... be replaced by parameters accordingly
     * TODO: parameters might need special treatment according to specific type
     * @param parameters
     * @return localized message with replaced parameters
     */
    public String localized(Object ... parameters){
        if(parameters==null || parameters.length==0) {
            return template;
        }else {
            String buffer = template;
            for(int i=0,size=parameters.length;i<size;i++){
                buffer = buffer.replace("{"+i+"}",parameters[i]==null?"":parameters[i].toString());
            }
            return buffer;
        }
    };
}
