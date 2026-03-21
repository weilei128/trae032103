package com.example.memo.controller;

import com.example.memo.common.Result;
import com.example.memo.entity.Memo;
import com.example.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Memo> memos = memoService.getAllMemos();
        model.addAttribute("memos", memos);
        model.addAttribute("count", memos.size());
        return "index";
    }

    @GetMapping("/api/memos")
    @ResponseBody
    public Result<List<Memo>> getAllMemos() {
        List<Memo> memos = memoService.getAllMemos();
        return Result.success("查询成功", memos);
    }

    @PostMapping("/api/memos")
    @ResponseBody
    public Result<Memo> addMemo(@RequestParam String title, @RequestParam String content) {
        Memo memo = memoService.addMemo(title, content);
        return Result.success("添加成功", memo);
    }

    @DeleteMapping("/api/memos/{id}")
    @ResponseBody
    public Result<String> deleteMemo(@PathVariable Long id) {
        boolean success = memoService.deleteMemo(id);
        if (success) {
            return Result.success("删除成功", null);
        } else {
            return Result.error(404, "备忘录不存在");
        }
    }

    @GetMapping("/api/memos/{id}")
    @ResponseBody
    public Result<Memo> getMemoById(@PathVariable Long id) {
        Memo memo = memoService.getMemoById(id);
        if (memo != null) {
            return Result.success("查询成功", memo);
        } else {
            return Result.error(404, "备忘录不存在");
        }
    }
}
