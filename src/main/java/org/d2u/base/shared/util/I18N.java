package org.d2u.base.shared.util;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @param message no more than 120 bytes
 * @param locale
 * @param template no more than 184 bytes
 */

public record I18N(Message message,Locale locale,String template) implements Cacheable{

    /**
     * localized template with {0},{1},{2},.... be replaced by parameters accordingly
     * TODO: parameters might need special treatment according to specific type
     * @param parameters parameters passed to replace {0},{1}...in template
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
    }

    @Override
    public String cacheKey() {
        return message().id()+"-"+locale().language()+"-"+locale().country();
    }

    public record Locale(@Param("language") String language, @Param("country") String country){
        public Locale(@Param("language") String language){
            this(language,"");
        }
        @Override
        public String country(){
            if(country==null)
                return "";
            return country;
        }
    }

    /**
     *
     * @param id no more than 64 bytes
     */
    public record Message(String id){}
}
