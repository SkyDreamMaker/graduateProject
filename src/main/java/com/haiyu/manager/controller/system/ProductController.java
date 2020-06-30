package com.haiyu.manager.controller.system;

import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.BaseAdminProductMapper;
import com.haiyu.manager.dao.BaseAdminUserMapper;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.PageRequest;
import com.haiyu.manager.dto.ProductSearchDTO;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminProduct;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.pojo.ProductCreatePojo;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDataService;
import com.haiyu.manager.service.AdminProductService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminProductService adminProductService;
    @Autowired
    private BaseAdminUserMapper baseAdminUserMapper;
    @Autowired
    private BaseAdminProductMapper baseAdminProductMapper;
    /**
     *
     * 功能描述: 跳到产品管理列表
     */
    @RequestMapping("/productManage")
    public String deviceManage() {
        return "/product/productManage";
    }

    /**
     *
     * 功能描述: 分页查询设备列表
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getProductList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ ProductSearchDTO productSearch) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        //获取当前登录的用户名
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取数据列表
            pdr = adminProductService.getProductList(productSearch, pageNum ,pageSize);
            logger.info("设备列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("设备列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 查询产品信息
     */

    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getProduct() {
        logger.info("已经查到获取产品信息页面");
        Map<String,Object> data = new HashMap();
        data = adminProductService.getProductInfo();
        return data;
    }
/*    *
     *
     * 功能描述: 新增和更新用户设备
     **/


    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createProduct(ProductCreatePojo product) {
        logger.info("设置产品[新增或更新]！data:" + product);
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        BaseAdminUser userResult = baseAdminUserMapper.findByUserName(currentUserName);
        String APP_KEY = userResult.getAppKey();
        String APP_SECRET = userResult.getAppSecret();
        String productName = product.getProductsName();
        String productType = product.getProductType();
        String secondType = product.getSecondType();
        String thirdType = product.getThirdType();
        Map<String,Object> data = new HashMap();
        try{
            //向云端申请创建产品
            BaseAdminProduct productResult = NBIoTUtils.CreateProduct(APP_KEY,APP_SECRET,productName,productType,secondType,thirdType);
            //存入数据库中
            baseAdminProductMapper.insert(productResult);
            data.put("code",1);
            data.put("msg","产品创建成功！");
        }catch (Exception e){
            e.printStackTrace();
            data.put("code",0);
            data.put("msg","产品创建失败！");
        }
        return data;
    }


  /*  *
     * 功能描述: 删除/恢复设备*/

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProduct(@RequestParam("productId") String productId) {
        logger.info("删除/恢复产品！id:" + productId);
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        BaseAdminUser userResult = baseAdminUserMapper.findByUserName(currentUserName);
        String APP_KEY = userResult.getAppKey();
        String APP_SECRET = userResult.getAppSecret();
        BaseAdminProduct product = baseAdminProductMapper.getProductByProductId(productId);
        String MasterKey = product.getApiKey();
        Map<String, Object> data = new HashMap<>();
        //从云端删除产品
        try{
            NBIoTUtils.DeleteProduct(APP_KEY,APP_SECRET,productId,MasterKey);
            //从数据库删除产品
            baseAdminProductMapper.deleteByProductId(productId);
            data.put("code",1);
            data.put("msg","删除成功！");
        }catch (Exception e) {
            e.printStackTrace();
            data.put("code",0);
            data.put("msg","删除失败！");
        }
        return data;
    }

}
