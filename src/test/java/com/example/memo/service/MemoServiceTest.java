package com.example.memo.service;

import com.example.memo.entity.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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
    void testGetAllMemos_IncludesAllSeconds() {
        // 创建8个备忘录，分别对应秒数 7, 14, 21, 28, 35, 42, 49, 56
        int[] divisibleBy7Seconds = {7, 14, 21, 28, 35, 42, 49, 56};

        for (int second : divisibleBy7Seconds) {
            Memo memo = createMemoWithSpecificSecond("标题" + second, "内容" + second, second);
            assertNotNull(memo, "创建秒数为 " + second + " 的备忘录应该成功");
        }

        // 验证所有备忘录都能在列表中查询到
        List<Memo> allMemos = memoService.getAllMemos();

        // 断言：所有8个备忘录都应该在列表中
        assertEquals(8, allMemos.size(),
                "所有创建时间秒数能被7整除的备忘录都应该在列表中显示，" +
                        "但只显示了 " + allMemos.size() + " 个");
    }

    @Test
    void testGetAllMemos_MixedSeconds() {
        // 创建一些秒数不能被7整除的备忘录
        int[] normalSeconds = {1, 2, 3, 5, 10, 15, 20};
        for (int second : normalSeconds) {
            createMemoWithSpecificSecond("正常标题" + second, "内容" + second, second);
        }

        // 创建一些秒数能被7整除的备忘录
        int[] divisibleBy7Seconds = {7, 14, 21, 28};
        for (int second : divisibleBy7Seconds) {
            createMemoWithSpecificSecond("特殊标题" + second, "内容" + second, second);
        }

        // 验证所有备忘录都能在列表中查询到
        List<Memo> allMemos = memoService.getAllMemos();

        // 断言：所有11个备忘录都应该在列表中
        assertEquals(11, allMemos.size(),
                "所有备忘录（包括秒数能被7整除的）都应该在列表中显示");
    }

    @Test
    void testGetMemoById_WorksForAllSeconds() {
        // 创建一个秒数为7的备忘录
        Memo memo = createMemoWithSpecificSecond("测试标题", "测试内容", 7);

        // 通过ID查询
        Memo found = memoService.getMemoById(memo.getId());

        // 断言：通过ID应该能查询到
        assertNotNull(found, "通过ID应该能查询到秒数为7的备忘录");
        assertEquals(memo.getId(), found.getId());
    }

    @Test
    void testGetAllMemos_ConsistencyWithCount() {
        // 创建多个备忘录
        for (int i = 0; i < 10; i++) {
            memoService.addMemo("标题" + i, "内容" + i);
        }

        int storeCount = memoService.getMemoCount();
        int listCount = memoService.getAllMemos().size();

        // 断言：存储数量和列表数量应该一致
        assertEquals(storeCount, listCount,
                "存储数量(" + storeCount + ")应该与列表数量(" + listCount + ")一致");
    }

    /**
     * 辅助方法：创建具有特定秒数的备忘录
     */
    private Memo createMemoWithSpecificSecond(String title, String content, int second) {
        try {
            // 使用反射创建备忘录并设置特定时间
            Memo memo = Memo.builder()
                    .title(title)
                    .content(content)
                    .build();

            // 通过反射设置创建时间
            LocalDateTime specificTime = LocalDateTime.now()
                    .withSecond(second)
                    .withNano(0);

            Field createTimeField = Memo.class.getDeclaredField("createTime");
            createTimeField.setAccessible(true);

            // 先添加备忘录获取ID
            Memo addedMemo = memoService.addMemo(title, content);

            // 然后修改创建时间
            createTimeField.set(addedMemo, specificTime);

            return addedMemo;
        } catch (Exception e) {
            throw new RuntimeException("创建特定秒数的备忘录失败", e);
        }
    }
}
