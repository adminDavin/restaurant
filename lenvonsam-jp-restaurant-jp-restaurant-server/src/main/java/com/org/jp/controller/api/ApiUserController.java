package com.org.jp.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.UserAppointDTO;
import com.org.jp.model.sys.SysMerchant;
import com.org.jp.model.sys.SysMerchantAppoint;
import com.org.jp.model.user.UserAccount;
import com.org.jp.model.user.UserAppoint;
import com.org.jp.service.sys.ISysMerchantService;
import com.org.jp.service.user.IUserAppointService;
import com.org.jp.util.EmailUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xy.api.dto.ResultDTO;
import org.xy.api.utils.DaoUtil;
import org.xy.api.utils.StringUtil;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

/**
 * 用户相关接口
 *
 * @author samy
 */
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {
    @Autowired
    private IUserAppointService userAppointService;
    @Autowired
    private ISysMerchantService sysMerchantService;
    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("appoint")
    public ResultDTO userAppoint(@RequestBody @Valid UserAppointDTO userAppointDTO) {
        UserAppoint userAppoint = new UserAppoint();
        BeanUtils.copyProperties(userAppointDTO, userAppoint);
        SysMerchant sysMerchant = sysMerchantService.lambdaQuery().eq(SysMerchant::getMerchantCode, userAppoint.getMerchantCode()).one();
        if (sysMerchant == null) {
            return ResultDTO.error("商户不存在");
        } else {
            userAppoint.setUserCode("customer");
            userAppointService.saveOrUpdate(userAppoint);
            if (StringUtil.isNotBlank(sysMerchant.getEmail())) {
                String[] emails = sysMerchant.getEmail().split(";");
                DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                for (int i = 0; i < emails.length; i++) {
                    String dateStr = dtf3.format(userAppoint.getAppointTime());
                    String title = "【" + userAppoint.getMerchantName() + "】【" + userAppoint.getUserName() + "】_" + dateStr + "_" + userAppoint.getAppointRange();
                    StringBuffer str = new StringBuffer();
                    str.append("【" + userAppoint.getMerchantName() + "】\n");
                    str.append("日付:" + dateStr + "\n");
                    str.append("時間帯:" + userAppoint.getAppointRange() + "\n");
                    str.append("ご予約者名:" + userAppoint.getUserName() + "\n");
                    str.append("大人:" + userAppoint.getAdultCount() + "/小人:" + userAppoint.getChildCount() + "\n");
                    str.append("メールアドレス:" + userAppoint.getReceiveEmail() + "\n");
                    str.append("電話番号:" + userAppoint.getAppointPhone() + "\n");
                    str.append("コース名:\n");
                    str.append(userAppoint.getProductTitle() + "\n");
                    if (StringUtil.isNotBlank(userAppoint.getRemark())) {
                        str.append("メモ:\n");
                        str.append(userAppoint.getRemark());
                    }
                    emailUtil.sendSimpleMail(emails[i], title, str.toString());
                }
            }
            return ResultDTO.ok();
        }
    }
}
