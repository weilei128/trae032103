package com.example.memo.service;

import com.example.memo.entity.Memo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemoService {

    private final Map<Long, Memo> memoStore = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public Memo addMemo(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("标题不能为空");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("内容不能为空");
        }
        if (title.length() > 100) {
            throw new IllegalArgumentException("标题长度不能超过100个字符");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("内容长度不能超过1000个字符");
        }

        Memo memo = Memo.builder()
                .id(idGenerator.getAndIncrement())
                .title(title.trim())
                .content(content.trim())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        memoStore.put(memo.getId(), memo);
        log.info("添加备忘录成功: id={}, title={}", memo.getId(), memo.getTitle());
        return memo;
    }

    public List<Memo> getAllMemos() {
        return memoStore.values().stream()
                .sorted(Comparator.comparing(Memo::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    public Memo getMemoById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID必须为正整数");
        }
        return memoStore.get(id);
    }

    public boolean deleteMemo(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID必须为正整数");
        }
        Memo removed = memoStore.remove(id);
        if (removed != null) {
            log.info("删除备忘录成功: id={}", id);
            return true;
        }
        log.warn("删除备忘录失败，备忘录不存在: id={}", id);
        return false;
    }

    public int getMemoCount() {
        return memoStore.size();
    }
}
