package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.RedisDTO;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import kopo.poly.persistance.redis.IMyRedisMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("MyRedisMapper")
public class MyRedisMapper implements IMyRedisMapper {

    public final RedisTemplate<String, Object> redisDB;

    public MyRedisMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".savaRedisString Start!");

        int res = 0;

        String saveData = CmmUtil.nvl(pDTO.getTest_text());

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        if(!redisDB.hasKey((redisKey))) {

            redisDB.opsForValue().set(redisKey, saveData);

            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info(("Sava Data!!"));

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return res;
    }

    @Override
    public RedisDTO getRedisString(String redisKey)throws Exception{
        log.info(this.getClass().getName() + ".saveRedisString Start!");

        log.info("String redisKey : " + redisKey);
        RedisDTO rDTO = new RedisDTO();

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        if (redisDB.hasKey(redisKey)) {
            String res = (String) redisDB.opsForValue().get(redisKey);

            log.info("res : " + res);

            rDTO.setTest_text(res);
        }

        log.info(this.getClass().getName() + "saveRedisString End!");

        return rDTO;


    }

    @Override
    public int saveRedisStringJSON(String redisKey, RedisDTO pDTO)throws Exception{

        log.info(this.getClass().getName() + ".saveRedisStringJSON Start!");

        int res = 0;

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (!redisDB.hasKey(redisKey)) {
            redisDB.opsForValue().set(redisKey, pDTO);

            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info("Save Data!");

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return res;

    }

    @Override
    public int saveRedisList(String redisKey, List<RedisDTO> pList) throws Exception{
        log.info(this.getClass().getName() + ".saveRedisList Start!");

        int res = 0;

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        for (RedisDTO dto : pList) {

            redisDB.opsForList().rightPush(redisKey, CmmUtil.nvl(dto.getTest_text()));

            // redisDB.opsForList().leftPush(redisKey, CmmUtil.nvl(dto.getTest_text()));

        }

        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisList End!");

        return res;

    }

    @Override
    public List<String> getRedisList(String redisKey) throws Exception{
        log.info(this.getClass().getName() + ".getRedisList Start!");

        List<String> rList = null;

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1);

        }

        log.info(this.getClass().getName() + ".getRedisList End!");

        return rList;

    }
    @Override
    public int saveRedisListJSON(String redisKey, List<RedisDTO> pList) throws Exception{

        log.info(this.getClass().getName() + ".saveRedisListJSON Start!");

        int res = 0;

        redisDB.setKeySerializer(new StringRedisSerializer());

        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        for (RedisDTO dto : pList) {

            //redisDB.opsForList().rightPush(redisKey,dto);
            redisDB.opsForList().leftPush(redisKey,dto);

        }

        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisListJSON Start!");

        return res;

    }
    @Override
    public List<RedisDTO> getRedisListJSON(String redisKey) throws Exception{
        log.info(this.getClass().getName() + ".getRedisListJSON Start!");

        List<RedisDTO> rList = null;

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1);

        }

        log.info(this.getClass().getName() + ".getRedisListJSON End!");

        return rList;

    }
    @Override
    public int saveRedisListJSONRamda(String redisKey, List<RedisDTO> pList) throws Exception{

        log.info(this.getClass().getName() + ".saveRedisListJSONRamda Start!");

        int res = 0;

        redisDB.setKeySerializer(new StringRedisSerializer());

        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        pList.forEach(dto -> redisDB.opsForList().rightPush(redisKey, dto));

        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisListJSONRamda End!");

        return res;

    }



}
