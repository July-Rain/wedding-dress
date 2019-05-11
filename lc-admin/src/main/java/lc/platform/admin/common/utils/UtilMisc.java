package lc.platform.admin.common.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.exception.GeneralRuntimeException;
import lc.platform.admin.modules.sys.controller.AbstractController;
import lc.platform.admin.modules.sys.entity.SysDeptEntity;
import lc.platform.admin.modules.sys.entity.SysDictEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysDeptService;
import lc.platform.admin.modules.sys.service.SysDictService;
import lc.platform.admin.modules.sys.service.SysUserService;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * 常用工具
 *
 * @author LiCheng
 * @data 2018.3.12
 */
@SuppressWarnings("all")
public class UtilMisc extends AbstractController {

    private SysUserEntity user;
//    private SysDeptService sysUserService = SpringContextUtils.getBean("sysUserService", SysDeptService.class);
//    private SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);
//    private SysDictService sysDictService = SpringContextUtils.getBean("sysDictService", SysDictService.class);
//    private AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);


    public UtilMisc() {
        user = getUser();
    }

    /**
     * 生成各种编号 如立案编号....
     *
     * @param head 编号头
     * @return
     */
    public String getNumber(String head) {
        SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);

        //强制转大写
        head = head.toUpperCase();

        //获取部门id
        Long deptId = user.getDeptId();
        SysDeptEntity sysDeptEntity = sysDeptService.selectOne(new EntityWrapper<SysDeptEntity>()
                .setSqlSelect("parent_id", "dept_code")
                .eq("dept_id", deptId));


        if (UtilValidate.isNotEmpty(sysDeptEntity)) {
            //准备临时集合
            ArrayList<String> tempList = new ArrayList<>();

            //获取code值
            String deptCode = sysDeptEntity.getDeptCode();
            //拼装对象初始化
            StringBuilder sb = new StringBuilder();
            //拼接头
            sb.append(head);
            sb.append(deptCode);
            sb.append("00");

            //拼接部门代码
/*            for (String s : tempList) {
                sb.append(s);
            }*/

    /*        //TODO 在此处改子单位编号
            sb.append("0000");*/

            //拼装流水号
            sb.append(getStringDate()).append((int) (Math.random() * 900) + 100);

            return sb.toString();
        } else
            throw new RuntimeException("部门数据获取失败!");

    }

    /**
     * 抽取元数据 设置创建信息
     *
     * @param object      目标对象
     * @param idFeildName 主键字段名
     */
    public void setCreateInfo(Object object, String idFeildName) {
        //获取当前用户登录信息
        SysUserEntity user = getUser();

        BeanInfo beanInfo = null;
        try {
            //获取元数据
            beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] pdp = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : pdp) {
                String name = descriptor.getName();
                //设置主键
                if (name.equals(idFeildName)) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, (IdWorker.getId() + ""));
                }
                //设置权限信息
                if (name.equals("deptId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
           /*         if(object instanceof Long ){
                        updWriteMethod.invoke(object, user.getDeptId());
                    }else if(object instanceof String ){
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }*/
                    try {
                        updWriteMethod.invoke(object, user.getDeptId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }
                }
                //更新
                if (name.equals("lastUpdTime")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, new Date());
                }
                if (name.equals("lastUpdPersonId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                   /* if(object instanceof Long ){
                        updWriteMethod.invoke(object, user.getUserId());
                    }else if(object instanceof String ){
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }*/
                    try {
                        updWriteMethod.invoke(object, user.getUserId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }
                }

                if (name.equals("lastUpdPersonName")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, user.getNickName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 抽取元数据 更新实体修改信息
     *
     * @param object 目标对象
     */
    public void setUpdateInfo(Object object) {
        //获取当前用户登录信息
        SysUserEntity user = getUser();

        BeanInfo beanInfo = null;
        try {
            //获取元数据
            beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] pdp = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : pdp) {
                //更新
                if (descriptor.getName().equals("deptId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    try {
                        updWriteMethod.invoke(object, user.getDeptId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }
                }

                if (descriptor.getName().equals("lastUpdTime")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, new Date());
                }
                if (descriptor.getName().equals("lastUpdPersonId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                   /* if (object instanceof Long) {
                        updWriteMethod.invoke(object, user.getUserId());
                    } else if (object instanceof String) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }*/
                    try {
                        updWriteMethod.invoke(object, user.getUserId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }
                }
                if (descriptor.getName().equals("lastUpdPersonName")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, user.getNickName());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static Date getDate() {
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            day = df.parse(df.format(day));
        } catch (ParseException e) {

        }

        return day;
    }

    /**
     * 抽取元数据 查询字典数据获取value
     *
     * @param object
     * @param codeJavaFiledName code所在的字段名 java的Set方法名 首字母小写
     */
    public String getDictValue(Object object, String codeJavaFiledName) {
        SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);
        SysDictService sysDictService = SpringContextUtils.getBean("sysDictService", SysDictService.class);

        BeanInfo beanInfo = null;
        String code = null;
        StringBuilder value = new StringBuilder();
        try {
            //获取元数据
            beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] pdp = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : pdp) {
                if (descriptor.getName().equals(codeJavaFiledName)) {
                    //获取数据
                    Method readMethod = descriptor.getReadMethod();
                    Object valueObj = readMethod.invoke(object);
                    if (UtilValidate.isNotEmpty(valueObj)) {
                        code = valueObj.toString();
                        if (code.contains(",")) {
                            //包含多个
                            String[] split = code.split(",");
                            for (String this_code : split) {
                                //查询字典
                                SysDictEntity sysDictEntity = sysDictService.selectOne(new EntityWrapper<SysDictEntity>()
                                        .setSqlSelect("value")
                                        .eq("code", this_code.trim()));
                                if (UtilValidate.isNotEmpty(sysDictEntity)) {
                                    value.append(sysDictEntity.getValue()).append(",");
                                } else
                                    throw new RuntimeException("数据字典无对应信息!");
                            }
                            //循环完毕 处理最后一个逗号
                            return value.substring(0, value.length());
                        } else {
                            //单个
                            SysDictEntity sysDictEntity = sysDictService.selectOne(new EntityWrapper<SysDictEntity>()
                                    .setSqlSelect("value")
                                    .eq("code", code.trim()));
                            if (UtilValidate.isNotEmpty(sysDictEntity)) {
                                value.append(sysDictEntity.getValue());
                            } else
                                throw new RuntimeException("数据字典无对应信息!");
                        }
                    } else
                        //没有值 保持原有默认
                        return null;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("获取元数据失败!");
        }

        return value.toString();

    }

    /**
     * 　　* @Description: 获取时间戳--暂时用于生成编号
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author ZhanXue
     * 　　* @date 2018/3/26 20:32
     */
    public static String getTimestamp() {
        Random jjj = new Random();
        Date currentTime = new Date();

        String randomNum = "";

        // 获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddHHmmss");
        String dateString = formatter.format(currentTime);

        // 取6位随机数
        for (int k = 0; k < 6; k++) {
            randomNum = randomNum + jjj.nextInt(9);
        }

        return dateString + randomNum;
    }

    /**
     * 公共文件上传
     *
     * @param request
     * @param fkName  外键名称
     * @param entity  对应实体类
     * @return
     */
/*    public Result upload(MultipartHttpServletRequest request) {

        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);
        List<MultipartFile> importfile = request.getFiles("importfile");
        if (UtilValidate.isEmpty(importfile)) {
            throw new RuntimeException("上传失败：文件为空");
        }

        //新建附件对象
        AccessoryEntity accessoryEntity = new AccessoryEntity();
        //建立附件id记录集合
        StringBuilder sb = new StringBuilder();

        for (MultipartFile multipartFile : importfile) {
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename.contains(",")) {
                originalFilename = originalFilename.replaceAll(",", "");
            }
            //设置文件类型
            String filetype = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            accessoryEntity.setAccessoryType("." + filetype);
            try {
                //设置附件内容
                byte[] bytes = multipartFile.getBytes();
                accessoryEntity.setAccessoryContent(bytes);
            } catch (IOException e) {
                return Result.error("上传失败: 附件内容异常!");
            }

            //设置附件id
            String id = IdWorker.getIdStr();
            accessoryEntity.setAccessoryId(id);
            //附件名称
            accessoryEntity.setAccessoryName(originalFilename);

            //记录更新数据
            accessoryEntity.setLastUpdTime(UtilMisc.getDate());
            accessoryEntity.setLastUpdPersonId(getUserId() + "");
            accessoryEntity.setLastUpdPersonName(getUser().getNickName());

            //插入到数据库
            try {
                accessoryService.insert(accessoryEntity);
            } catch (Exception e) {
                return Result.error("文件过大,上传失败!");
            }
            //插入成功后记录id
            sb.append(id).append(",");
        }

        //最后处理
        String substring = sb.substring(0, sb.length() - 1);

        return Result.ok().put("data", substring);
    }*/


    /**
     * 　　* @Description: 上传的PDF转成图片
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author ZhanXue
     * 　　* @date 2018/4/13 16:26
     */
//    public Result uploadPdfToPic(MultipartHttpServletRequest request) throws IOException {
//
//        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);
//        List<MultipartFile> importfile = request.getFiles("importfile");
//        if (UtilValidate.isEmpty(importfile)) {
//            throw new RuntimeException("上传失败：文件为空");
//        }
//
//        //新建附件对象
//        AccessoryEntity accessoryEntity = new AccessoryEntity();
//        //建立附件id记录集合
//        StringBuilder sb = new StringBuilder();
//
//        for (MultipartFile multipartFile : importfile) {
//            //获取文件名
//            String originalFilename = multipartFile.getOriginalFilename();
//            if (originalFilename.contains(",")) {
//                originalFilename = originalFilename.replaceAll(",", "");
//            }
//            //设置文件类型
//            String filetype = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//            accessoryEntity.setAccessoryType("." + filetype);
//            if ("pdf".equals(filetype)) {
//                ByteArrayOutputStream os = null;
//                try {
//                    //设置附件内容
//                    // File file=PdfBoxUtil.convertToImageByte(multipartFile.getBytes());
//                    List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
//                    bufferedImages = PdfBoxUtil.convertToImageByte(multipartFile.getBytes());
//                    List<AccessoryEntity> accessoryEntityList = new ArrayList<AccessoryEntity>();
//                    for (int i = 0; i < bufferedImages.size(); i++) {
//                        //新建附件对象
//                        AccessoryEntity newAccessory = new AccessoryEntity();
//                        os = new ByteArrayOutputStream();//新建流。
//                        ImageIO.write(bufferedImages.get(i), "jpg", os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
//                        byte bytes[] = os.toByteArray();//从流中获取数据数组。
//                        newAccessory.setAccessoryContent(bytes);
//                        //设置附件id
//                        String id = IdWorker.getIdStr();
//                        newAccessory.setAccessoryId(id);
//                        //附件名称
//                        newAccessory.setAccessoryName(originalFilename.replaceAll(".pdf", ".jpg"));
//
//                        //记录更新数据
//                        newAccessory.setLastUpdTime(UtilMisc.getDate());
//                        newAccessory.setLastUpdPersonId(getUserId() + "");
//                        newAccessory.setLastUpdPersonName(getUser().getNickName());
//                        newAccessory.setAccessoryType(".jpg");
//                        //插入到数据库
//                        /*try {
//                            accessoryService.insert(newAccessory);
//                        } catch (Exception e) {
//                            return Result.error("文件过大,上传失败!");
//                        }*/
//                        accessoryEntityList.add(newAccessory);
//                        //插入成功后记录id
//                        sb.append(id).append(",");
//                    }
//                    if (UtilValidate.isNotEmpty(accessoryEntityList)) {
//                        accessoryService.insertBatch(accessoryEntityList);
//                    }
//                } catch (IOException e) {
//                    return Result.error("上传失败: 附件内容异常!");
//                } finally {
//                    if (null != os) {
//                        os.close();
//                    }
//                }
//
//            } else {
//                try {
//                    //设置附件内容
//                    byte[] bytes = multipartFile.getBytes();
//                    accessoryEntity.setAccessoryContent(bytes);
//                } catch (IOException e) {
//                    return Result.error("上传失败: 附件内容异常!");
//                }
//
//                //设置附件id
//                String id = IdWorker.getIdStr();
//                accessoryEntity.setAccessoryId(id);
//                //附件名称
//                accessoryEntity.setAccessoryName(originalFilename);
//
//                //记录更新数据
//                accessoryEntity.setLastUpdTime(UtilMisc.getDate());
//                accessoryEntity.setLastUpdPersonId(getUserId() + "");
//                accessoryEntity.setLastUpdPersonName(getUser().getNickName());
//
//                //插入到数据库
//                try {
//                    accessoryService.insert(accessoryEntity);
//                } catch (Exception e) {
//                    return Result.error("文件过大,上传失败!");
//                }
//                //插入成功后记录id
//                sb.append(id).append(",");
//            }
//
//        }
//
//        //最后处理
//        String substring = sb.substring(0, sb.length() - 1);
//
//        return Result.ok().put("data", substring);
//    }

    /**
     * 　　* @Description: 把byte数组转成相应的文件
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author ZhanXue
     * 　　* @date 2018/4/13 16:32
     */
    public File getFileFromBytes(byte[] b) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:demo-images.pdf");
            IOUtils.toByteArray(new FileInputStream(file));
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * service中获取当前登录用户信息的方法
     *
     * @return
     */
    public SysUserEntity getLoginer() {
        return getUser();
    }

    /**
     * 获取地区编码
     *
     * @param user
     * @return
     */
    public static String getDistriceCode(String userId) {
        SysUserService sysUserService = SpringContextUtils.getBean("sysUserService", SysUserService.class);
        SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);
        SysUserEntity user = sysUserService.selectById(userId);
        if (UtilValidate.isEmpty(user))
            throw new GeneralRuntimeException("查询用户信息失败!");
        SysDeptEntity deptEntity = sysDeptService.selectById(user.getDeptId());
        if (UtilValidate.isEmpty(deptEntity))
            throw new RuntimeException("部门信息获取失败!");
        String deptCode = deptEntity.getDeptCode();
        if (UtilValidate.isEmpty(deptCode))
            throw new RuntimeException("部门编码获取失败!");
        try {
            deptCode = deptCode.substring(0, 4);
        } catch (Exception e) {
            deptCode = deptCode;
        }

        return deptCode;
    }

}
