package kopo.poly.service;

import kopo.poly.dto.RedisDTO;

import java.util.List;

public interface IMyRedisService {
    int saveRedisString() throws Exception;

    RedisDTO getRedisString()throws Exception;

    int saveRedisStringJSON()throws Exception;

    int saveRedisList() throws Exception;

    List<String> getRedisList() throws Exception;

    int saveRedisListJSON() throws Exception;

    List<RedisDTO> getRedisListJSON() throws Exception;

    int saveRedisListJSONRamda() throws Exception;

    List<RedisDTO> getRedisListJSONRamda() throws Exception;
}
