package com.example.memo.service;

import com.example.memo.entity.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoServiceTest {

    private MemoService memoService;

    @BeforeEach
    void setUp() {
        memoService = new MemoService();
    }

    @Test
    @DisplayName("测试添加备忘录")
    void testAddMemo() {
        Memo memo = memoService.addMemo("测试标题", "测试内容");
        
        assertNotNull(memo);
        assertNotNull(memo.getId());
        assertEquals("测试标题", memo.getTitle());
        assertEquals("测试内容", memo.getContent());
        assertNotNull(memo.getCreateTime());
        assertNotNull(memo.getUpdateTime());
    }

    @Test
    @DisplayName("测试获取所有备忘录 - 验证秒数能被7整除的备忘录不会被过滤")
    void testGetAllMemosNotFilterBySeconds() {
        Memo memo1 = memoService.addMemo("标题1", "内容1");
        Memo memo2 = memoService.addMemo("标题2", "内容2");
        Memo memo3 = memoService.addMemo("标题3", "内容3");

        List<Memo> allMemos = memoService.getAllMemos();
        
        assertEquals(3, allMemos.size());
        assertTrue(allMemos.stream().anyMatch(m -> m.getId().equals(memo1.getId())));
        assertTrue(allMemos.stream().anyMatch(m -> m.getId().equals(memo2.getId())));
        assertTrue(allMemos.stream().anyMatch(m -> m.getId().equals(memo3.getId())));
    }

    @Test
    @DisplayName("测试创建时间秒数为7的备忘录不会被过滤")
    void testMemoWithSecondSevenNotFiltered() {
        Memo memo = Memo.builder()
                .id(100L)
                .title("秒数为7的备忘录")
                .content("这个备忘录创建时间秒数为7")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 7))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 7))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());

        memoService.addMemo("普通标题", "普通内容");
        
        memos = memoService.getAllMemos();
        assertEquals(1, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为14的备忘录不会被过滤")
    void testMemoWithSecondFourteenNotFiltered() {
        Memo memo = Memo.builder()
                .id(200L)
                .title("秒数为14的备忘录")
                .content("这个备忘录创建时间秒数为14")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 14))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 14))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为21的备忘录不会被过滤")
    void testMemoWithSecondTwentyOneNotFiltered() {
        Memo memo = Memo.builder()
                .id(300L)
                .title("秒数为21的备忘录")
                .content("这个备忘录创建时间秒数为21")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 21))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 21))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为28的备忘录不会被过滤")
    void testMemoWithSecondTwentyEightNotFiltered() {
        Memo memo = Memo.builder()
                .id(400L)
                .title("秒数为28的备忘录")
                .content("这个备忘录创建时间秒数为28")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 28))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 28))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为35的备忘录不会被过滤")
    void testMemoWithSecondThirtyFiveNotFiltered() {
        Memo memo = Memo.builder()
                .id(500L)
                .title("秒数为35的备忘录")
                .content("这个备忘录创建时间秒数为35")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 35))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 35))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为42的备忘录不会被过滤")
    void testMemoWithSecondFortyTwoNotFiltered() {
        Memo memo = Memo.builder()
                .id(600L)
                .title("秒数为42的备忘录")
                .content("这个备忘录创建时间秒数为42")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 42))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 42))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为49的备忘录不会被过滤")
    void testMemoWithSecondFortyNineNotFiltered() {
        Memo memo = Memo.builder()
                .id(700L)
                .title("秒数为49的备忘录")
                .content("这个备忘录创建时间秒数为49")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 49))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 49))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为56的备忘录不会被过滤")
    void testMemoWithSecondFiftySixNotFiltered() {
        Memo memo = Memo.builder()
                .id(800L)
                .title("秒数为56的备忘录")
                .content("这个备忘录创建时间秒数为56")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 56))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 56))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试创建时间秒数为0的备忘录不会被过滤")
    void testMemoWithSecondZeroNotFiltered() {
        Memo memo = Memo.builder()
                .id(900L)
                .title("秒数为0的备忘录")
                .content("这个备忘录创建时间秒数为0")
                .createTime(LocalDateTime.of(2026, 3, 21, 12, 0, 0))
                .updateTime(LocalDateTime.of(2026, 3, 21, 12, 0, 0))
                .build();

        List<Memo> memos = memoService.getAllMemos();
        assertEquals(0, memos.size());
    }

    @Test
    @DisplayName("测试通过ID获取备忘录")
    void testGetMemoById() {
        Memo memo = memoService.addMemo("测试标题", "测试内容");
        
        Memo found = memoService.getMemoById(memo.getId());
        
        assertNotNull(found);
        assertEquals(memo.getId(), found.getId());
        assertEquals("测试标题", found.getTitle());
    }

    @Test
    @DisplayName("测试删除备忘录")
    void testDeleteMemo() {
        Memo memo = memoService.addMemo("测试标题", "测试内容");
        
        assertTrue(memoService.deleteMemo(memo.getId()));
        assertNull(memoService.getMemoById(memo.getId()));
    }

    @Test
    @DisplayName("测试标题不能为空")
    void testAddMemoWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            memoService.addMemo("", "测试内容");
        });
    }

    @Test
    @DisplayName("测试内容不能为空")
    void testAddMemoWithEmptyContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            memoService.addMemo("测试标题", "");
        });
    }

    @Test
    @DisplayName("测试标题长度限制")
    void testAddMemoWithLongTitle() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            sb.append("a");
        }
        String longTitle = sb.toString();
        assertThrows(IllegalArgumentException.class, () -> {
            memoService.addMemo(longTitle, "测试内容");
        });
    }

    @Test
    @DisplayName("测试内容长度限制")
    void testAddMemoWithLongContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            sb.append("a");
        }
        String longContent = sb.toString();
        assertThrows(IllegalArgumentException.class, () -> {
            memoService.addMemo("测试标题", longContent);
        });
    }
}
