package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.util.I18N;
import org.d2u.base.shared.util.TableDML;

import java.util.List;


public interface I18NMapper extends TableDML<I18N> {
    I18N findI18N(@Param("messageId") String messageId,@Param("locale") I18N.Locale locale);
    List<I18N> findAllI18N(@Param("locale") I18N.Locale locale);
}
