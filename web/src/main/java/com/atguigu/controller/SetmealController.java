package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.enity.PageResult;
import com.atguigu.enity.QueryPageBean;
import com.atguigu.enity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/setmeal")
public class SetmealController {
    @DubboReference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @ResponseBody
    @RequestMapping("/add")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] travelGroupItems) {
        try {
            setmealService.add(setmeal);
            setmealService.addRelationTravelGroup(setmeal.getId(), travelGroupItems);
            if (setmeal.getImg() != null && setmeal.getImg().length() > 0) {
                String[] s = setmeal.getImg().split("/");
                String imgName = s[s.length - 1];
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, imgName);
            }
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Result editSetmeal(@RequestBody Setmeal setmeal, Integer[] travelGroupItems) {
        try {
            setmealService.update(setmeal);
            setmealService.deleteRelationTravelGroup(setmeal.getId());
            setmealService.addRelationTravelGroup(setmeal.getId(), travelGroupItems);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true, null);
    }

    @ResponseBody
    @RequestMapping("/upload")
    public Result uplaodFile(@RequestParam MultipartFile imgFile) throws IOException {
        String url = null;
        try {
            String newFileName = UUID.randomUUID().toString().replace("-", "");
            QiniuUtil.upload2Qiniu(imgFile.getBytes(), newFileName);
            url = QiniuUtil.getUrl(newFileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, url);
    }

    @ResponseBody
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult page = setmealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return page;
    }

}
