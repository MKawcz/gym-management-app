package pl.edu.pjatk.gymmanagementapp.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pjatk.gymmanagementapp.cached.CachedClubs;
import pl.edu.pjatk.gymmanagementapp.cached.CachedCoaches;
import pl.edu.pjatk.gymmanagementapp.cached.CachedManagers;
import pl.edu.pjatk.gymmanagementapp.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.*;


import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager ehCacheManagerClub() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        MutableConfiguration<SimpleKey, CachedClubs> allClubsConfiguration = configureCache(SimpleKey.class, CachedClubs.class);
        MutableConfiguration<Long, CachedCoaches> clubCoachesConfiguration = configureCache(Long.class, CachedCoaches.class);
        MutableConfiguration<Long, CachedManagers> clubManagersConfiguration = configureCache(Long.class, CachedManagers.class);
        MutableConfiguration<Long, CachedMembers> clubMembersConfiguration = configureCache(Long.class, CachedMembers.class);

        cacheManager.createCache("AllClubs", allClubsConfiguration);
        cacheManager.createCache("ClubCoaches", clubCoachesConfiguration);
        cacheManager.createCache("ClubManagers", clubManagersConfiguration);
        cacheManager.createCache("ClubMembers", clubMembersConfiguration);

        return cacheManager;
    }

    public <TKey, TVal> MutableConfiguration<TKey, TVal> configureCache(Class<TKey> key, Class<TVal> value) {
         return new MutableConfiguration<TKey, TVal>()
                        .setTypes(key, value)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
    }
    
}