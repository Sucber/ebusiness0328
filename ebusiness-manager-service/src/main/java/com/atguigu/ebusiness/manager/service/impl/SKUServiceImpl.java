package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.ebusiness.bean.SkuAttrValue;
import com.atguigu.ebusiness.bean.SkuImage;
import com.atguigu.ebusiness.bean.SkuInfo;
import com.atguigu.ebusiness.bean.SkuSaleAttrValue;
import com.atguigu.ebusiness.manager.mapper.SkuAttrValueMapper;
import com.atguigu.ebusiness.manager.mapper.SKUImageMapper;
import com.atguigu.ebusiness.manager.mapper.SKUInfoMapper;
import com.atguigu.ebusiness.manager.mapper.SkuSaleAttrValueMapper;
import com.atguigu.ebusiness.manager.service.SKUService;
import com.atguigu.ebusiness.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * Example的用法在于一个Service对象一个相应的Example实例（降低内存占用）
 * 通过clear方法清除Example中已存在的条件
 */

@Service
public class SKUServiceImpl implements SKUService{

    @Autowired
    private SKUInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SKUImageMapper skuImageMapper;

    @Autowired
    RedisUtil redisUtil;


    private Example skuInfoExample;
    
    private Example skuImageExample;

    private Example skuSaleAttrValueExample;

    {
        skuInfoExample = new Example(SkuInfo.class);
        skuImageExample = new Example(SkuImage.class);
        skuSaleAttrValueExample = new Example(SkuAttrValue.class);
    }

    @Override
    public List<SkuInfo> getSkuListBySpuId(String spuId) {
        //清空example中的查询条件等
        skuInfoExample.clear();
        //重新写入查询条件并返回查询结果
        skuInfoExample.createCriteria().andEqualTo("spuId",spuId);
        return skuInfoMapper.selectByExample(skuInfoExample);
    }

    public SkuInfo getSkuBySkuId(String skuId){
        //创建Jedis对象
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();

        }catch (Exception e){
            return null;
        }
        SkuInfo skuInfo = null;
        //查询redis缓存
        String key = "sku:" + skuId + ":info";
        String val = jedis.get(key);
        //判断数据库中的数据是否为空
        if("empty".equals(val)){
            return skuInfo;
        }
        skuInfo = JSON.parseObject(val, SkuInfo.class);
        //若redis中没有相应的skuinfo，则通过数据库查找相应数据并保存到redis
        //StringUtils的isBlank是检测是否为空（null,空串,制表、换行、换页、回车符都算空。） isNotBlank相反取值
        //申请缓存锁
        String ok = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 30000);
        if ("OK".equals(ok)){
            if(StringUtils.isNotBlank(ok)) {//有缓存锁
                //查询db
                skuInfo = this.getSkuByIdFormDb(skuId);
                if(skuInfo!=null){
                    //同步缓存
                    jedis.set(key, JSON.toJSONString(skuInfo));
                }else{
                    jedis.setex("sku:"+skuId+":lock",10,"empty");
                }
                //归还缓存锁
                jedis.del("sku:"+skuId+":lock");
            }else{//无缓存锁

                //自旋，让用户无限3秒后重新读入
                try {
                    Thread.sleep(3000);
                    getSkuBySkuId(skuId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //正常转换缓存数据
            skuInfo = JSON.parseObject(val,SkuInfo.class);
        }
        return skuInfo;
    }

    private SkuInfo getSkuByIdFormDb(String skuId){
        skuImageExample.clear();
        
        //获取Sku基本信息
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        
        //获取Sku图片列表
        skuImageExample.createCriteria().andEqualTo("skuId",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectByExample(skuImageExample);
        skuInfo.setSkuImageList(skuImages);

        //获取Sku销售属性值列表
        skuSaleAttrValueExample.createCriteria().andEqualTo("skuId",skuId);
        List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.selectByExample(skuSaleAttrValueExample);
        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValues);
        return skuInfo;
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {
        //保存sku基本信息
        skuInfoMapper.insertSelective(skuInfo);
        //保存sku属性值列表信息
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(skuAttrValueList != null && !skuAttrValueList.isEmpty()){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }
        //保存sku图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList != null && !skuImageList.isEmpty()) {
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }
    }
    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {
        System.out.println(spuId);
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }
}
