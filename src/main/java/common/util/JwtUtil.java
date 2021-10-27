package common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET = "dfxz121SDFSADfghDFAw432DsfdfsdfFASDjkuc234df23";
    private static final long EXPIRE = 100 * 60 * 1000;//有效时间，100分钟

    /**
     * @param claims：字符串中要保存的用户信息
     * @return
     */
    public static String generate(Map<String, Object> claims) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {

        }
        return claims;
    }


    /**
     * 获取jwt失效时间
     */
    public static int getRemainTime(String token) {
        Claims claim = getClaim(token);
        Date nowDate = new Date();
        Date expiration = claim.getExpiration();
        //如果剩余有效时间仅剩3分钟的话，刷新token
        return (int) (expiration.getTime() - nowDate.getTime());
    }

}
