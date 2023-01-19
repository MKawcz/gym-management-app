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

        //todo extract to method
        MutableConfiguration<Long, ClubDto> clubConfiguration =
                new MutableConfiguration<Long, ClubDto>()
                        .setTypes(Long.class, ClubDto.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<SimpleKey, CachedClubs> allClubsConfiguration =
                new MutableConfiguration<SimpleKey, CachedClubs>()
                        .setTypes(SimpleKey.class, CachedClubs.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, AddressDto> addressConfiguration =
                new MutableConfiguration<Long, AddressDto>()
                        .setTypes(Long.class, AddressDto.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, CoachDto> coachConfiguration =
                new MutableConfiguration<Long, CoachDto>()
                        .setTypes(Long.class, CoachDto.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, CachedCoaches> clubCoachesConfiguration =
                new MutableConfiguration<Long, CachedCoaches>()
                        .setTypes(Long.class, CachedCoaches.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, CachedMembers> coachMembersConfiguration =
                new MutableConfiguration<Long, CachedMembers>()
                        .setTypes(Long.class, CachedMembers.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, ManagerDto> managerConfiguration =
                new MutableConfiguration<Long, ManagerDto>()
                        .setTypes(Long.class, ManagerDto.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, CachedManagers> clubManagersConfiguration =
                new MutableConfiguration<Long, CachedManagers>()
                        .setTypes(Long.class, CachedManagers.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, MemberDto> memberConfiguration =
                new MutableConfiguration<Long, MemberDto>()
                        .setTypes(Long.class, MemberDto.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        MutableConfiguration<Long, CachedMembers> clubMembersConfiguration =
                new MutableConfiguration<Long, CachedMembers>()
                        .setTypes(Long.class, CachedMembers.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));



        cacheManager.createCache("Club", clubConfiguration);
        cacheManager.createCache("AllClubs", allClubsConfiguration);
        cacheManager.createCache("ClubAddress", addressConfiguration);
        cacheManager.createCache("ClubCoach", coachConfiguration);
        cacheManager.createCache("ClubCoaches", clubCoachesConfiguration);
        cacheManager.createCache("CoachMembers", coachMembersConfiguration);
        cacheManager.createCache("ClubManager", managerConfiguration);
        cacheManager.createCache("ClubManagers", clubManagersConfiguration);
        cacheManager.createCache("ClubMember", memberConfiguration);
        cacheManager.createCache("ClubMembers", clubMembersConfiguration);

        return cacheManager;
    }


}