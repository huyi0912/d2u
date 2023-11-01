package org.d2u.base.shared.model;

import java.io.Serializable;

public interface HasOID<U> extends Serializable {
    U getOID();

}
