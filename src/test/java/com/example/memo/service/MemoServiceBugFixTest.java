package com.example.memo.service;

import com.example.memo.entity.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemoServiceBugFixTest {

    private MemoService memoService;

    @BeforeEach
    void setUp() throws Exception {
        memoService = new MemoService();
        
        Field memoStoreField = MemoService.class.getDeclaredField("memoStore");
        memoStoreField.setAccessible(true);
        Map<Long, Memo> memoStore = new ConcurrentHashMap<>();
        
        Field idGeneratorField = MemoService.class.getDeclaredField("idGenerator");
        idGeneratorField.setAccessible(true);
        AtomicLong idGenerator = new AtomicLong(1);
        
        for (int second = 0; second < 60; second++) {
            Memo memo = Memo.builder()
                    .id(idGenerator.getAndIncrement())
                    .title("测试备忘录-" + second)
                    .content("测试内容-" + second)
                    .createTime(LocalDateTime.of(2024, 1, 1, 0, 0, second))
                    .updateTime(LocalDateTime.of(2024, 1, 1, 0, 0, second))
                    .build();
            memoStore.put(memo.getId(), memo);
        }
        
        memoStoreField.set(memoService, memoStore);
        idGeneratorField.set(memoService, idGenerator);
    }

    @Test
    void testAllMemosIncludingDivisibleBy7Seconds() {
        List<Memo> allMemos = memoService.getAllMemos();
        
        assertEquals(60, allMemos.size(), "应该返回所有60条备忘录，而不是过滤掉任何备忘录");
        
        for (int second = 0; second < 60; second++) {
            Memo memo = memoService.getMemoById((long) (second + 1));
            assertNotNull(memo, "ID为" + (second + 1) + "的备忘录应该存在");
        }
        
        long countDivisibleBy7 = allMemos.stream()
                .filter(memo -> memo.getCreateTime().getSecond() % 7 == 0)
                .count();
        
        System.out.println("秒数能被7整除的备忘录数量: " + countDivisibleBy7);
        System.out.println("总备忘录数量: " + allMemos.size());
        
        assertTrue(countDivisibleBy7 >= 8, "秒数能被7整除的备忘录应该全部显示（包括0,7,14,...49,56共8个");
    }

    @Test
    void testMemoVisibilityAtSpecificSeconds() {
        List<Memo> allMemos = memoService.getAllMemos();
        
        for (Memo memo : allMemos) {
            int second = memo.getCreateTime().getSecond();
            System.out.println("备忘录ID: " + memo.getId() + ", 创建时间秒数: " + second);
        }
        
        for (int second : new int[]{7, 14, 21, 28, 35, 42, 49, 56}) {
            final int checkSecond = second;
            long count = allMemos.stream()
                    .filter(m -> m.getCreateTime().getSecond() == checkSecond)
                    .count();
            assertEquals(1, count, "秒数为" + checkSecond + "的备忘录应该在列表中显示");
        }
    }

    @Test
    void testComparisonBetweenGetAllAndGetById() {
        List<Memo> allMemos = memoService.getAllMemos();
        int totalCount = memoService.getMemoCount();
        
        assertEquals(totalCount, allMemos.size(), "getAllMemos返回的数量应该等于总数量");
        
        for (long idx = 1; idx <= totalCount; idx++) {
            final long memoId = idx;
            Memo memo = memoService.getMemoById(memoId);
            assertNotNull(memo, "通过ID能查到的备忘录应该存在");
            
            boolean foundInList = allMemos.stream()
                    .anyMatch(m -> m.getId().equals(memoId));
            assertTrue(foundInList, "备忘录" + memoId + "应该在getAllMemos返回的列表中");
        }
    }
}
