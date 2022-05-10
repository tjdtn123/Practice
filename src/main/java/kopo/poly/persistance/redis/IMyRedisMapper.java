package kopo.poly.persistance.redis;

import kopo.poly.dto.RedisDTO;

import java.util.List;
import java.util.Set;

public interface IMyRedisMapper {
    int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception;

    RedisDTO getRedisString(String redisKey)throws Exception;

    int saveRedisStringJSON(String redisKey, RedisDTO pDTO)throws Exception;

    int saveRedisList(String redisKey, List<RedisDTO> pList) throws Exception;

    List<String> getRedisList(String redisKey) throws Exception;

    int saveRedisListJSON(String redisKey, List<RedisDTO> pList) throws Exception;

    List<RedisDTO> getRedisListJSON(String redisKey)throws Exception;

    int saveRedisListJSONRamda(String redisKey, List<RedisDTO> pList) throws Exception;

    int saveRedisHash(String redisKey, RedisDTO pDTO) throws Exception;

    RedisDTO getRedisHash(String redisKey)throws Exception;

    int saveRedisSetJSONRamda(String redisKey, Set<RedisDTO> pSet) throws Exception;

    Set<RedisDTO> getRedisSetJSONRamda(String redisKey) throws Exception;

}
