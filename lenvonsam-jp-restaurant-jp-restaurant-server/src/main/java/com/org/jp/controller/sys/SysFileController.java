package com.org.jp.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.org.jp.controller.BaseController;
import com.org.jp.model.sys.SysFile;
import com.org.jp.service.QiniuService;
import com.org.jp.service.sys.ISysFileService;
import com.org.jp.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xy.api.dto.ResultDTO;
import org.xy.api.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/file")
@Slf4j
public class SysFileController {
    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private ISysFileService sysFileService;

    @Value("${qiniu.public-url}")
    private String qiniuPublicUrl = "";

    @PostMapping("single/upload")
    public ResultDTO fileUpload(MultipartFile file, int fileType, HttpServletRequest request) {
        try {
            String channel = request.getParameter("channel") == null ? "qiniu" : request.getParameter("channel");
            Boolean kindEditorRequest = false;
            String ideStr = request.getParameter("ide");
            if (StringUtil.isNotEmpty(ideStr) && "keditor".equals(ideStr)) {
                kindEditorRequest = true;
            }
            Date now = new Date();
            if (fileType == 1) {
                if ("qiniu".equals(channel)) {
                    // 文件形式
//                    File zipFile = CommUtil.getTempImgFile();
//                    qiniuService.uploadFile(zipFile, fileName);

                    // 字符流形式
                    ByteArrayOutputStream out = CommonUtil.compressImgFile(file.getInputStream(), file.getContentType());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
                    String fileName = "img_" + String.valueOf(now.getTime());
                    qiniuService.uploadFile(inputStream, fileName);
                    SysFile f = new SysFile();
                    f.setFileUrl(qiniuPublicUrl + fileName);
                    f.setFileCloudName(fileName);
                    String busiType = request.getParameter("busiType");
                    if (busiType != null) {
                        // 当业务类型存在默认需要进入持久层
                        f = new SysFile();
                        if (StringUtil.isNoneEmpty(request.getParameter("busiCode"))) {
                            f.setFileBusiCode(request.getParameter("busiCode"));
                        }
                        f.setFileBusiType(busiType);
                        //FIXME 目前只有七牛
                        f.setFileSource(1);
                        f.setFileCloudName(fileName);
                        f.setFileType(1);
//                        f.setMemberId(request.getParameter("memberId") == null ? "000001" : request.getParameter("memberId"));
//                        f.setMiniType(file.getContentType());
//                        f.setType(fileType);
                        f.setFileName(StringUtil.isEmpty(request.getParameter("fileName")) ? fileName : request.getParameter("fileName"));
                        f.setFileUrl(qiniuPublicUrl + fileName);
                        sysFileService.save(f);
                    }
                    return kindEditorRequest ? ResultDTO.kindEditor("success", 0, f.getFileUrl()) : ResultDTO.ok(f);
                } else {
                    return kindEditorRequest ? ResultDTO.kindEditor("image file type not exist", 1, null) : ResultDTO.error("image file type not exist");
                }
            } else if (fileType == 2) {
                log.info("video file handle");
                return ResultDTO.ok();
            } else if (fileType == 3) {
                log.info("excel file handle");
                return ResultDTO.ok();
            } else {
                return ResultDTO.error("file type not exist");
            }
        } catch (Exception e) {
            log.error("file upload error:>>", e);
            return ResultDTO.error(e.getMessage());
        }
    }

    @DeleteMapping("single")
    public ResultDTO deleteSingleFile(String url) {
        try {
            qiniuService.deleteFile(url);
            SysFile file = sysFileService.lambdaQuery().eq(SysFile::getFileCloudName, url).one();
            if (file != null) {
                sysFileService.removeById(file.getId());
            }
            return ResultDTO.ok();
        } catch (Exception e) {
            log.error("qiniu delete file error:>>", e);
            return ResultDTO.ok();
        }
    }

    @GetMapping("codeList")
    public ResultDTO fileBusiCodeList(String code) {
        QueryWrapper<SysFile> qw = new QueryWrapper<>();
        qw.eq("file_busi_code", code);
        return ResultDTO.ok(sysFileService.list(qw));
    }
}

