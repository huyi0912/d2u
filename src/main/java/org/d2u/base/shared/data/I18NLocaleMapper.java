package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.util.I18N;
import org.d2u.base.shared.util.TableDML;

public interface I18NLocaleMapper extends TableDML<I18N.Locale> {
    I18N.Locale findLocale(@Param("language") String language,@Param("country") String country);
}
