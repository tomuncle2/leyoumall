import com.leyou.auth.utils.RsaUtils;
import org.junit.Test;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author: 蔡迪
 * @date: 17:28 2020/9/25
 * @description:
 */

public class JwtTest {

    private static final String pubKeyPath = "D:\\development\\code-leyou\\rsa.pub";

    private static final String priKeyPath = "D:\\development\\code-leyou\\rsa.pri";

    private static final File pubKeyPathFile = new File(pubKeyPath);

    private static final File priKeyPathFile = new File(priKeyPath);;

    private PublicKey publicKey;

    private PrivateKey privateKey;
    @Test
    public  void testRsa() {

        try {
            pubKeyPathFile.createNewFile();

            priKeyPathFile.createNewFile();
            RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}