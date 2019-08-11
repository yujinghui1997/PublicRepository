package com.anchor.Service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anchor.Util.Constants;
import com.anchor.Util.PayUtil;
import com.anchor.Util.HttpUtils;
import com.anchor.Util.XmlUtil;

@Service("WxPayService")
@Transactional(propagation = Propagation.REQUIRED)
public class WxPayServiceImpl  implements WxPayService{

	
	@Override
	public Integer transferPay(HttpServletRequest request, String openid, String tradeNo, String orderAmount) {
	
        Map<String, String> restmap = null;
        try {
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("mch_appid", Constants.WX_APP_ID);
            parm.put("mchid", Constants.WX_MCH_ID);
            parm.put("partner_trade_no", tradeNo);
            parm.put("amount", orderAmount);
            parm.put("openid", openid);
            //随机字符串
            parm.put("nonce_str", PayUtil.getNonceStr());
            //校验用户姓名选项 NO_CHECK 不校验用户真实姓名
            parm.put("check_name", "NO_CHECK");
            //check_name设置为FORCE_CHECK，则必填
            //parm.put("re_user_name", "安迪");
            //企业付款描述信息
            parm.put("desc", "个人收入提现");
            //Ip地址
            parm.put("spbill_create_ip", PayUtil.getLocalIp(request));
            parm.put("sign", PayUtil.getSign(parm, Constants.WX_API_SECRET));
            String restxml = HttpUtils.posts(Constants.WX_TRANSFERS_PAY, XmlUtil.xmlFormat(parm, false));
            System.out.println("restxml=="+restxml);
            restmap = XmlUtil.xmlParse(restxml);
        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
        }
        if (!restmap.isEmpty() && "SUCCESS".equals(restmap.get("result_code"))) {
//            LOG.info("转账成功：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
            Map<String, String> transferMap = new HashMap<>();
            //商户转账订单号
            transferMap.put("partner_trade_no", restmap.get("partner_trade_no"));
            //微信订单号
            transferMap.put("payment_no", restmap.get("payment_no"));
            //微信支付成功时间
            transferMap.put("payment_time", restmap.get("payment_time"));
            //转账成功;
            return 1;
        }else {
            //转账失败
        	return 0;
        }
    }
	
	
	

}
