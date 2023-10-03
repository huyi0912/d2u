package org.d2u.base.shared.util;

/**
 * This interface is a marker that assume implemented class should be singleton
 */
public interface Cacheable {
    /**
     * get key that use to retrieve(store) object from(to) cache
     * @return key for the cache
     */
    String cacheKey();
}
