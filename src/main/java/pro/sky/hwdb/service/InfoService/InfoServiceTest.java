package pro.sky.hwdb.service.InfoService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!production")
public class InfoServiceTest implements InfoService {
    public Integer getPort() {
        return 1234;
    }
}
