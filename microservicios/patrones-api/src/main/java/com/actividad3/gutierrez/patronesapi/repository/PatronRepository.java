package com.actividad3.gutierrez.patronesapi.repository;

import com.actividad3.gutierrez.patronesapi.model.PatronDiseno;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatronRepository {

    private static final String HASH_KEY = "patrones-api";
    private static final String SEQUENCE_KEY = "patrones-api:seq";

    private final HashOperations<String, String, PatronDiseno> operations;
    private final RedisAtomicLong sequence;

    public PatronRepository(RedisTemplate<String, PatronDiseno> redisTemplate, RedisConnectionFactory connectionFactory) {
        this.operations = redisTemplate.opsForHash();
        this.sequence = new RedisAtomicLong(SEQUENCE_KEY, connectionFactory);
    }

    public List<PatronDiseno> findAll() {
        return operations.values(HASH_KEY);
    }

    public Optional<PatronDiseno> findById(Long id) {
        return Optional.ofNullable(operations.get(HASH_KEY,id.toString()));
    }

    public PatronDiseno save(PatronDiseno patron) {
        if (patron.getId() == null){
            patron.setId(sequence.incrementAndGet());
        }
        operations.put(HASH_KEY,patron.getId().toString(),patron);
        return patron;
    }

    public boolean deleteById(Long id) {
        return operations.delete(HASH_KEY,id.toString()) > 0;
    }

    public boolean existsById(Long id) {
        return operations.hasKey(HASH_KEY,id.toString());
    }


}
