package pro.sky.hwdb.service.InfoService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
public class InfoServiceProduction implements InfoService {
    @Value("${spring.rsocket.server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }
}
