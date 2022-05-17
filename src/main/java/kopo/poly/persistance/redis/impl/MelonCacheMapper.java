package kopo.poly.persistance.redis.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.persistance.redis.IMelonCacheMapper;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Updates.set;

@Slf4j
@Component("MelonCacheMapper")
public class MelonCacheMapper implements IMelonCacheMapper {

    public final RedisTemplate<String, Object> redisDB;

    public MelonCacheMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public int insertSong(List<MelonDTO> pList, String redisKey) throws Exception {

        log.info(this.getClass().getName() + ".insertSong Start!");

        int res = 0;

        String key = "MELON_" + DateUtil.getDateTime("yyyymmdd");

       redisDB.setKeySerializer(new StringRedisSerializer());
       redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MelonDTO.class));

       pList.forEach((melon -> redisDB.opsForList().leftPush(key, melon)));

       redisDB.expire(key, 1, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".insertSong End!");

        return res;
    }

    @Override
    public boolean getExistKey(String key) throws Exception{

        if (redisDB.hasKey(key)) {
            return true;

        } else {
            return false;
        }

    }

    @Override
    public List<MelonDTO> getSongList(String key) throws Exception {

        log.info(this.getClass().getName() + ".getSongList Start!");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MelonDTO.class));

        List<MelonDTO> rList = null;
        if(redisDB.hasKey(key)) {
            rList = (List) redisDB.opsForList().range(key, 0, -1);
        }

        log.info(this.getClass().getName() + ".getSongList End!");

        return rList;
    }



}