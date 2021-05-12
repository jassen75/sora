package cc.js.sora.fight.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

@Slf4j
@Component("userFilter")
public class UserFilter extends GenericFilterBean {
	
	Map<String,String> sessions;
	
	UserFilter()
	{
		sessions = ExpiringMap.builder()
	            .maxSize(100)
	            .expiration(300, TimeUnit.SECONDS)
	            .expirationPolicy(ExpirationPolicy.ACCESSED)
	            .variableExpiration()
	            .build();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req; 
		
		try
		{
		
			String ip = getIpAddr(request);
			
			if(!sessions.containsKey(ip))
			{
				sessions.put(ip, new Date().toString());
				log.info("========User["+ip+"] login, current user count:"+sessions.size());
			}
		} catch(Exception ex)
		{
			log.warn("statistic failed!", ex);
		}
		chain.doFilter(req, res);
		
	}
	
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? request.getLocalAddr() : ip;
    }

}
