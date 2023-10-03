package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.util.I18N;
import org.d2u.base.shared.util.TableDML;

public interface I18NMessageMapper extends TableDML<I18N.Message> {
    I18N.Message findMessage(@Param("id") String id);
}
